/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.servlet;

import com.nus.pgdb.entity.JobsCart;
import com.nus.pgdb.entity.Organisms;
import com.nus.pgdb.entity.ProjectSamples;
import com.nus.pgdb.entity.Projects;
import com.nus.pgdb.entity.Samples;
import com.nus.pgdb.entity.SamplesSequenceFiles;
import com.nus.pgdb.entity.UserProjects;
import com.nus.pgdb.entity.Users;
import com.nus.pgdb.service.IJobsCart;
import com.nus.pgdb.service.IProject;
import com.nus.pgdb.service.ISampleProject;
import com.nus.pgdb.service.ISampleSequence;
import com.nus.pgdb.service.IUser;
import com.nus.pgdb.service.impl.JobsCartServiceImpl;
import com.nus.pgdb.service.impl.ProjectServiceImpl;
import com.nus.pgdb.service.impl.SampleProjectServiceImpl;
import com.nus.pgdb.service.impl.SampleSequenceServiceImpl;
import com.nus.pgdb.service.impl.UserServiceImpl;
import com.nus.pgdb.util.Constants;
import com.nus.pgdb.util.FileManager;
import com.nus.pgdb.util.MailSender;
import com.nus.pgdb.util.PWAuthentication;
import com.nus.pgdb.util.Utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
@WebServlet(name = "DataMaintenance", urlPatterns = {"/DataMaintenance"})
public class DataMaintenance extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataMaintenance.class);
    ISampleSequence iSampleSequenceService = null;
    ISampleProject iSampleProjectService = null;
    IJobsCart iJobsCartService = null;
    IUser userService = null;
    PWAuthentication pwa = null;
    IUser iUserService = null;
    IProject iProjectService = null;
//    private File destinationDir;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        iSampleSequenceService = new SampleSequenceServiceImpl();
        iSampleProjectService = new SampleProjectServiceImpl();
        iJobsCartService = new JobsCartServiceImpl();
        userService = new UserServiceImpl();
        pwa = new PWAuthentication();
        iUserService = new UserServiceImpl();
        iProjectService = new ProjectServiceImpl();
//        destinationDir = createDirPath(request);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String subAction = request.getParameter("subAction");
        int userID = (null != session.getAttribute("userId")) ? (Integer) session.getAttribute("userId") : -1;

        if (subAction.equals("sampleSq-Delete")) {
            int sampleSeqId = Integer.parseInt(request.getParameter("recordId"));
            String filePath = request.getParameter("absoluteFilePath");

            //physical file removal
            FileManager.deleteFile(filePath);
            //removes the record from db
            boolean deleteStatus = iSampleSequenceService.deleteSampleSequenceDataService(sampleSeqId);
            out.println(Utility.getProcessStatus(Constants.DELETE, deleteStatus));
        } else if (subAction.equals("cartAdd")) {
            String sampleIds = request.getParameter("sampleIdList");
            String projectName = request.getParameter("projectName");
            boolean recordStatus = false;
            boolean recordExists = false;

            if (!sampleIds.isEmpty()) {
                String sampleIdArr[] = sampleIds.split(",");
                for (String sampleId : sampleIdArr) {
                    recordExists = iJobsCartService.isExistsService(userID, sampleId);
                    if (!recordExists) {
                        JobsCart cart = new JobsCart();
                        cart.setSampleId(iSampleProjectService.getSampleObjByIdService(sampleId));
                        cart.setUserId(userService.getUserObjById(userID));
                        cart.setProjectName(projectName);
                        recordStatus = iJobsCartService.persistJobsCartDetailService(cart);
                    } else {
                        recordStatus = true;
                    }
                }
            }
            if (recordStatus) {
                //get the cart count
                long cartSize = iJobsCartService.fetchCartSizeService(userID);
                out.println(cartSize);
            }

        } else if (subAction.equals("cartRemove")) {
            int recordId = Integer.valueOf(request.getParameter("recordId"));

            //single record deletion
            if (recordId > 0) {
                iJobsCartService.deleteCartItemService(recordId, 0);
                //removes all records belong to the user    
            } else if (0 == recordId) {
                iJobsCartService.deleteCartItemService(0, userID);
            }
            long cartSize = iJobsCartService.fetchCartSizeService(userID);
            out.println(cartSize);

        } else if (subAction.equals("user-creation")) {
            String name = request.getParameter("nameParam");
            String email = request.getParameter("emailParam");
            String uname = request.getParameter("userNameParam");
            String pword = request.getParameter("pwParam");
            boolean isAdminUser = Boolean.valueOf(request.getParameter("adminUserParam"));

            try {
                //generate salt for user password
                byte[] salt = pwa.generateSaltForPassword();
                //generate pw encryption
                byte[] encryptedPw = pwa.getEncryptedPassword(pword, salt);
                //check for the existence of the user name
                boolean isUserNameAvailable = iUserService.userNameExistsService(uname);
                //check for the existence of the email
                Users userExist = iUserService.getUserByEmailService(email);

                if (!isUserNameAvailable) {
                    if (null == userExist) {
                        Users user = new Users();
                        user.setName(name);
                        user.setEmail(email);
                        user.setUserName(uname);
                        user.setEncryptedPassword(encryptedPw);
                        user.setSalt(salt);
                        user.setIsAdmin(isAdminUser);
                        user.setDummyPassword(pword); //admin will create a dummy password during the user creation process in order for user to reset it when logged for the first time
                        user.setIsPwReset(false);
                        boolean saveStatus = iUserService.persistUserDataService(user);
                        out.println(Utility.getProcessStatus(Constants.CREATE, saveStatus));
                    } else {
                        out.println(Utility.getValidateMsgs("emailExists"));
                    }
                } else {
                    out.println(Utility.getValidateMsgs("userName"));
                }

            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("Throws NoSuchAlgorithmException :" + ex.getMessage());
            } catch (InvalidKeySpecException ex) {
                LOGGER.error("Throws InvalidKeySpecException :" + ex.getMessage());
            }

        } else if (subAction.equals("user-update")) {
            int recordID = Integer.valueOf(request.getParameter("recordId"));
            String userOriginalName = request.getParameter("editNameParam");
            String userEmail = request.getParameter("editEMParam");
            String userName = request.getParameter("editUNParam");
            boolean userType = Boolean.valueOf(request.getParameter("editUTParam"));

            Users user = iUserService.getUserObjById(recordID);
            //check for the existence of the user name except the existing uname
            boolean isUserNameExists = iUserService.validateUserUpdateService(user.getUserName(), userName, "validateUserName");
            //check for the existence of the email except the existing email
            boolean isEmailExists = iUserService.validateUserUpdateService(user.getEmail(), userEmail, "validateEmail");

            if (!isUserNameExists) {
                if (!isEmailExists) {
                    boolean updateStatus = iUserService.updateUserService(recordID, userOriginalName, userEmail, userName, userType);
                    out.println(Utility.getProcessStatus(Constants.UPDATE, updateStatus));
                } else {
                    out.println(Utility.getValidateMsgs("emailExists"));
                }
            } else {
                out.println(Utility.getValidateMsgs("userName"));
            }

        } else if (subAction.equals("user-delete")) {
            int recordID = Integer.valueOf(request.getParameter("recordId"));

            //TODO: before deleting the user clean up projects/samples/uploaded files related to the user
            //when a user gets deleted it removes only from the user_projects and users table
            boolean deleteStatus = iUserService.deleteUserService(recordID);
            out.println(Utility.getProcessStatus(Constants.DELETE, deleteStatus));

        } else if (subAction.equals("project-creation")) {
            String name = request.getParameter("prjNameParam");
            String description = request.getParameter("prjDescParam");

            Projects project = new Projects();
            project.setName(name);
            project.setDescription(description);
            int projectId = iProjectService.persistProjectDataService(project);

            if (projectId > 0) {
                Users user = iUserService.getUserObjById(userID);
                Projects prj = iProjectService.getProjectObjByIdService(projectId);

                UserProjects userProject = new UserProjects();
                userProject.setUserId(user);
                userProject.setProjectId(prj);
                boolean createStatus = iUserService.persistUserProjectDataService(userProject);
                out.println(Utility.getProcessStatus(Constants.CREATE, createStatus));
            }

        } else if (subAction.equals("project-update")) {
            int recordID = Integer.valueOf(request.getParameter("recordId"));
            String proName = request.getParameter("editNameParam");
            String proDescription = request.getParameter("editDescParam");

            boolean updateStatus = iProjectService.updateProjectService(recordID, proName, proDescription);
            out.println(Utility.getProcessStatus(Constants.UPDATE, updateStatus));

        } else if (subAction.equals("project-delete")) {
            int recordID = Integer.valueOf(request.getParameter("recordId"));

            //deletes the samples associated with the project
            List<ProjectSamples> sampleList = iSampleProjectService.getSamplesbyProject(recordID);
            if (sampleList != null && sampleList.size() > 0) {
                for (ProjectSamples sample : sampleList) {
                    //deletes the physical files/folders associated with the samples
                    List<SamplesSequenceFiles> samplesequenceList = iSampleSequenceService.fetchSequenceFilesBySampleIdService(sample.getSampleId().getId(), userID, false);
                    if (samplesequenceList != null && samplesequenceList.size() > 0) {
                        for (SamplesSequenceFiles samplesSequence : samplesequenceList) {
                            FileManager.removeFilesNFoldersFromPhysicalLocation(samplesSequence.getSequenceFileId().getFilePath());
                            //deletes the records in sequnce_files table associated with the samples
                            iSampleSequenceService.deleteSampleSequenceDataService(samplesSequence.getSequenceFileId().getId());
                        }
                    }
                    //deletes the sample
                    iSampleProjectService.deleteSampleService(sample.getSampleId().getId());
                }
            }
            //finally deletes the project
            boolean deleteStatus = iProjectService.deleteProjectService(recordID);
            out.println(Utility.getProcessStatus(Constants.DELETE, deleteStatus));

        } else if (subAction.equals("sample-creation")) {
            String sampleName = request.getParameter("samNameParam");
            int organismId = Integer.valueOf(request.getParameter("orgParam"));
            int projId = Integer.valueOf(request.getParameter("projectIdParam"));

            //checking for sample name existance
            boolean sampleNameExists = iSampleProjectService.isSmapleNameExistsService(userID, sampleName);
            if (!sampleNameExists) {
                String lastSampleId = iSampleProjectService.getLastSampleRecordService();
                lastSampleId = (lastSampleId != null) ? lastSampleId : "S000000"; //if the sample table is empty it will return S000000
                String sampleID = Utility.generateSampleId(lastSampleId);

                Samples sampleObj = new Samples();
                sampleObj.setId(sampleID);
                sampleObj.setName(sampleName);
                sampleObj.setOrganismId(iSampleProjectService.getOrganismObjByIdService(organismId, ""));
                sampleObj.setCreatedDate(Utility.getCurrentDateTime());
                String sampleId = iSampleProjectService.persistSampleDataService(sampleObj);

                if (sampleId != null) {
                    ProjectSamples projectSampleObj = new ProjectSamples();
                    projectSampleObj.setProjectId(iProjectService.getProjectObjByIdService(projId));
                    projectSampleObj.setSampleId(iSampleProjectService.getSampleObjByIdService(sampleId));
                    boolean saveStatus = iSampleProjectService.persistSampleProjectService(projectSampleObj);
                    out.println(Utility.getProcessStatus(Constants.CREATE, saveStatus));
                } else {
                    out.println(Utility.getProcessStatus(Constants.CREATE, false));
                }
            } else {
                out.println(Utility.getValidateMsgs("sampleName"));
            }

        } else if (subAction.equals("multi-sample-creation")) {
            int projId = Integer.valueOf(request.getParameter("projectIdParam"));
            String txtFile = request.getParameter("fileName");
            String line;
            int countOfLines = 0;
            int countOfSaves = 0;
            int recordStatus = 0;
            File folderDir = new File(FileManager.createDir(userID, "BulkFileUploads", Constants.DIRECTORY_LOCATION + userID));
            try {
                //saving the text file on the given location
                int uploadStatus = Utility.upload2Server(request, folderDir.toString());
                if (uploadStatus == 2) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(folderDir + File.separator + txtFile));

                    while ((line = bufferedReader.readLine()) != null) {
                        countOfLines++;
                        String datavalue[] = line.split("\\t");
                        if (datavalue.length == 2) {
                            String sampleName = datavalue[0];
                            String organism = datavalue[1];
                            //checking for sample name existance
                            boolean sampleNameExists = iSampleProjectService.isSmapleNameExistsService(userID, sampleName);
                            if (!sampleNameExists) {
                                String lastSampleId = iSampleProjectService.getLastSampleRecordService();
                                lastSampleId = (lastSampleId != null) ? lastSampleId : "S000000"; //if the sample table is empty it will return S000000
                                String sampleID = Utility.generateSampleId(lastSampleId);

                                Samples sampleObj = new Samples();
                                sampleObj.setId(sampleID);
                                sampleObj.setName(sampleName);
                                sampleObj.setOrganismId(iSampleProjectService.getOrganismObjByIdService(-1, organism));
                                sampleObj.setCreatedDate(Utility.getCurrentDateTime());
                                String sampleId = iSampleProjectService.persistSampleDataService(sampleObj);

                                if (sampleId != null) {
                                    ProjectSamples projectSampleObj = new ProjectSamples();
                                    projectSampleObj.setProjectId(iProjectService.getProjectObjByIdService(projId));
                                    projectSampleObj.setSampleId(iSampleProjectService.getSampleObjByIdService(sampleId));
                                    boolean saveStatus = iSampleProjectService.persistSampleProjectService(projectSampleObj);
                                    if (saveStatus) {
                                        countOfSaves++;
                                    }
                                }
                                recordStatus = (countOfSaves == countOfLines)? 3 : 4;
                            } else {
                                recordStatus = 1;
                                break;
                            }
                        } else {
                            recordStatus = 2;
                            break;
                        }
                    }
                    bufferedReader.close();
                } else {
                    recordStatus = -1;
                }
                switch (recordStatus) {
                    case 1:
                        out.println(Utility.getValidateMsgs("sampleName"));
                        break;
                    case 2:
                        out.println(Utility.getValidateMsgs("txtFileUploadError6"));
                        break;
                    case 3:
                        out.println(Utility.getProcessStatus(Constants.CREATE, true));
                        break;
                    case 4:
                        out.println(Utility.getProcessStatus(Constants.CREATE, false));
                        break;
                    default:
                        out.println(Utility.getValidateMsgs("fileUploadError4"));
                        break;
                }
            } catch (FileNotFoundException ex) {
                LOGGER.error("FileNotFoundException thrown while bulk file upload:" + ex.getMessage());
            } catch (IOException ex) {
                LOGGER.error("IOException thrown while bulk file upload:" + ex.getMessage());
            }

        } else if (subAction.equals("sample-update")) {
            String recordID = request.getParameter("recordId");
            String sampleName = request.getParameter("editNameParam");
            int sampleOrg = Integer.valueOf(request.getParameter("editOrgParam"));

            //checking for sample name existance
            boolean sampleNameExists = iSampleProjectService.isSmapleNameExistsService(userID, sampleName);
            if (!sampleNameExists) {
                boolean updateStatus = iSampleProjectService.updateSampleService(recordID, sampleName, iSampleProjectService.getOrganismObjByIdService(sampleOrg, ""));
                out.println(Utility.getProcessStatus(Constants.UPDATE, updateStatus));
            } else {
                out.println(Utility.getValidateMsgs("sampleName"));
            }

        } else if (subAction.equals("sample-delete")) {
            String recordID = request.getParameter("recordId");

            List<SamplesSequenceFiles> sampleseqList = iSampleSequenceService.fetchSequenceFilesBySampleIdService(recordID, userID, false);
            if (sampleseqList != null && sampleseqList.size() > 0) {
                for (SamplesSequenceFiles samplesSequenceFile : sampleseqList) {
                    //deletes the physical files/folders associated with the samples
                    FileManager.removeFilesNFoldersFromPhysicalLocation(samplesSequenceFile.getSequenceFileId().getFilePath());
                    //deletes the records in sequnce_files table associated with the samples
                    iSampleSequenceService.deleteSampleSequenceDataService(samplesSequenceFile.getSequenceFileId().getId());
                }
            }
            //finally deletes the sample
            boolean deleteStatus = iSampleProjectService.deleteSampleService(recordID);
            //fetches the cart size to update the cart on the front end
            long cartSize = iJobsCartService.fetchCartSizeService(userID);
            out.println(Utility.getProcessStatus(Constants.DELETE, deleteStatus));
            out.println(cartSize);

        } else if (subAction.equals("send-reset-pw-mail")) {
            String recipientEmail = request.getParameter("paramRecipientEmail");
            String name = request.getParameter("paramFullName");
            String un = request.getParameter("paramUserName");
            String pw = request.getParameter("paramUserPw");

            MailSender.generateMail(recipientEmail, "Welcome!", Utility.generateWelcomeMailContent(name, un, pw));

            out.println("<div class='alert alert-success login-msg w3-panel w3-wide w3-round w3-animate-opacity'><p>Password reset mail sent successfully to " + recipientEmail + "</p></div>");
            LOGGER.info("Password reset mail sent successfully to " + recipientEmail);

        } else if (subAction.equals("send-forgot-pw-mail")) {
            String recipient = request.getParameter("userEmailParam");

            Users userObject = iUserService.getUserByEmailService(recipient);
            if (null != userObject) {
                //generate unique tocken string
                String resetTokenStr = UUID.randomUUID().toString();
                //save the token
                userObject.setDummyPassword(resetTokenStr);
                iUserService.persistUserDataService(userObject);
                //generate mail
                MailSender.generateMail(recipient, "Password Reset Request", Utility.generateChangePasswordMailContent(Constants.SITE_URL_WITH_TOKEN.concat(resetTokenStr)));
                out.println("<div class='alert alert-success login-msg w3-panel w3-wide w3-round w3-animate-opacity'><p>Mail sent successfully. Please check your mail to continue. </p></div>");
                LOGGER.info("Email sent successfully to " + recipient);
            } else {
                out.println(Utility.getValidateMsgs("email"));
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
