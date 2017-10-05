/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.servlet;

import com.nus.pgdb.entity.SamplesSequenceFiles;
import com.nus.pgdb.entity.SequenceFiles;
import com.nus.pgdb.service.ISampleProject;
import com.nus.pgdb.service.ISampleSequence;
import com.nus.pgdb.service.impl.SampleProjectServiceImpl;
import com.nus.pgdb.service.impl.SampleSequenceServiceImpl;
import com.nus.pgdb.util.Constants;
import com.nus.pgdb.util.FileManager;
import com.nus.pgdb.util.Utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
@WebServlet(name = "FileUpload", urlPatterns = {"/FileUpload"})
public class FileUpload extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUpload.class);
    ISampleSequence iSampleSequenceService = null;
    ISampleProject iSampleProjectService = null;
    private File destinationDir;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        iSampleSequenceService = new SampleSequenceServiceImpl();
        iSampleProjectService = new SampleProjectServiceImpl();
        destinationDir = createDirPath(request);
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
        boolean ismultipart = ServletFileUpload.isMultipartContent(request);
        String subAction = request.getParameter("subAction");

        if (subAction.equals("singleFileUpload")) {
            int uploadStatus = 0;
            if (ismultipart) {
                uploadStatus = validateFastQFiles(request, 1, null, null);
                switch (uploadStatus) {
                    case 1:
                        out.println(Utility.getValidateMsgs("fileUploadError1"));
                        break;
                    case 2:
                        out.println("<div class='alert alert-success login-msg w3-panel w3-wide w3-round w3-animate-opacity'><p>File uploaded successfully</p></div>");
                        break;
                    case 3:
                        out.println(Utility.getValidateMsgs("fileUploadError2"));
                        break;
                    case 4:
                        out.println(Utility.getValidateMsgs("fileUploadError3"));
                        break;
                    case 8:
                        out.println(Utility.getValidateMsgs("txtFileUploadError8"));
                        break;
                    default:
                        out.println(Utility.getValidateMsgs("fileUploadError4"));
                        break;
                }
            }
        } else if (subAction.equals("textFileUpload")) {
            if (ismultipart) {
                int txtFileStatus = processTxtFile(request);
                switch (txtFileStatus) {
                    case 2:
                        out.println("<div class='alert alert-success login-msg w3-panel w3-wide w3-round w3-animate-opacity'><p>File validation successful, you may now proceed uploading multiple files.</p></div>");
                        break;
                    case 3:
                        out.println(Utility.getValidateMsgs("fileUploadError2"));
                        break;
                    case 4:
                        out.println(Utility.getValidateMsgs("fileUploadError3"));
                        break;
                    case 6:
                        out.println(Utility.getValidateMsgs("txtFileUploadError6"));
                        break;
                    case 7:
                        out.println(Utility.getValidateMsgs("txtFileUploadError7"));
                        break;
                    case 8:
                        out.println(Utility.getValidateMsgs("txtFileUploadError8"));
                        break;
                    default:
                        out.println(Utility.getValidateMsgs("fileUploadError4"));
                        break;
                }
            }
        } else if (subAction.equals("bulkFileUpload")) {
            if (ismultipart) {
                int bulkUploadStatus = processBulkFile(request);
                switch (bulkUploadStatus) {
                    case 1:
                        out.println(Utility.getValidateMsgs("fileUploadError1"));
                        break;
                    case 2:
                        out.println("<div class='alert alert-success login-msg w3-panel w3-wide w3-round w3-animate-opacity'><p>File uploaded successfully</p></div>");
                        break;
                    case 3:
                        out.println(Utility.getValidateMsgs("fileUploadError2"));
                        break;
                    case 4:
                        out.println(Utility.getValidateMsgs("fileUploadError3"));
                        break;
                    case 5:
                        out.println(Utility.getValidateMsgs("fileUploadError5"));
                        break;
                    default:
                        out.println(Utility.getValidateMsgs("fileUploadError4"));
                        break;
                }
            }

        }
    }

    private int processTxtFile(HttpServletRequest request) {
        int processStatus = -1;
        processStatus = (Utility.upload2Server(request, destinationDir.toString()) > 0) ? ReadTxtFile(request, 1) : -1;
        return processStatus;
    }

    private int processBulkFile(HttpServletRequest request) {
        int processStatus = -1;
        processStatus = (Utility.upload2Server(request, destinationDir.toString()) > 0) ? ReadTxtFile(request, 2) : -1;
        //delete the content in the upload folder once the process is completed
        FileManager.deleteFolderContent(destinationDir);
        return processStatus;
    }

    /*
    Validation check:
    - Only 2 files can be uploaded per sample
    - The prefix of the file pair should be the same
    - File pair reads should maintain the combination of R1 & R2 or 1 & 2
    
    uploadType:
    1 -> individual file upload
    2 -> bulk file upload
     */
    private int validateFastQFiles(HttpServletRequest request, int uploadType, String sampleName, String uploadedFileName) {
        LOGGER.info("Validating fastq files....");
        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("userId");
        int msgType = 0;

        if (uploadType == 1) {
            sampleName = request.getParameter("selectedSample");
            uploadedFileName = request.getParameter("fastqFile");
        }

        //create the folder path to store uploaded files if not exists
        String folderPath = FileManager.createDir2StoreFastqs(userId, sampleName);
        //get number of files stored in the above path
        int fileCount = FileManager.countFiles(folderPath);

        if (fileCount < 2) {
            //file name validation
            if (fileCount == 1) {
                String storedFileName = FileManager.getFileName(new File(folderPath));
                String storedFileSuffix = getSuffixType(storedFileName);
                String uploadedFileSuffix = getSuffixType(uploadedFileName);
                String[] splitStoredFile = null;
                String[] splitUploadedFile = null;

                if ((null != storedFileSuffix) && (null != uploadedFileSuffix)) {
                    splitStoredFile = splitFileName(storedFileName);
                    splitUploadedFile = splitFileName(uploadedFileName);

                    //comparing the prefix of both the files
                    if (splitStoredFile[0].equalsIgnoreCase(splitUploadedFile[0])) {
                        if ((storedFileSuffix.equals("R1") && uploadedFileSuffix.equals("R2")) || (uploadedFileSuffix.equals("R1") && storedFileSuffix.equals("R2"))) {
                            LOGGER.info("Validation process successful.");
                            msgType = processAction(request, folderPath, uploadType, sampleName, uploadedFileName);
                        } else if ((uploadedFileSuffix.equals("1") && storedFileSuffix.equals("2")) || (storedFileSuffix.equals("1") && uploadedFileSuffix.equals("2"))) {
                            LOGGER.info("Validation process successful.");
                            msgType = processAction(request, folderPath, uploadType, sampleName, uploadedFileName);
                        } else {
                            msgType = 4;
                            LOGGER.error("Invalid file pair.");
                        }
                    } else {
                        msgType = 3;
                        LOGGER.error("Invalid file name.");
                    }
                } else {
                    msgType = 8;
                    LOGGER.error("Invalid suffix type.");
                }
            } else if (fileCount == 0) {
                LOGGER.info("Validation process successful.");
                msgType = processAction(request, folderPath, uploadType, sampleName, uploadedFileName);
            }
        } else {
            msgType = 1;
            LOGGER.error("Invalid file count in folder path");
        }

        return msgType;
    }

    //validationType 1 -> validate text file / 2-> redirect to fastq file validation
    private int ReadTxtFile(HttpServletRequest request, int validationType) {
        LOGGER.info("Preparing to read the uploaded file ....");
        int status = -1;
        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("userId");
        String txtFileName = request.getParameter("fileName");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(destinationDir + File.separator + txtFileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String datavalue[] = line.split("\\t");
                if (datavalue.length == 3) {
                    String sampleName = datavalue[0];
                    String fastqR1 = datavalue[1];
                    String fastqR2 = datavalue[2];
                    if (validationType == 1) {
                        //validate sample exists
                        status = (iSampleProjectService.isSmapleNameExistsService(userId, sampleName)) ? 2 : 7;
                        if (status == 2) {
                            //validates the content in the txt file
                            status = validateTxtFile(fastqR1, fastqR2);
                            if (status != 2) {
                                break;
                            }
                        } else if (status == 7) {
                            break;
                        }
                    } else if (validationType == 2) {
                        //validate fastq files and upload to the desired destination
                        status = validateFastQFiles(request, 2, sampleName, fastqR1);
                        status = validateFastQFiles(request, 2, sampleName, fastqR2);
                    }
                } else {
                    status = 6;
                    break;
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            LOGGER.error("FileNotFoundException thrown while bulk file upload:" + ex.getMessage());
        } catch (IOException ex) {
            LOGGER.error("IOException thrown while bulk file upload:" + ex.getMessage());
        }
        return status;
    }

    //validates the fasqfile formats in the txt file
    private int validateTxtFile(String file1, String file2) {
        LOGGER.info("Validating text file....");
        int readStatus = -1;
        String suffixOfFile1 = getSuffixType(file1);
        String suffixOfFile2 = getSuffixType(file2);
        String[] splitFile1 = null;
        String[] splitFile2 = null;

        if ((null != suffixOfFile1) && (null != suffixOfFile2)) {
            splitFile1 = splitFileName(file1);
            splitFile2 = splitFileName(file2);
            if (splitFile1[0].equalsIgnoreCase(splitFile2[0])) {
                if ((suffixOfFile1.equals("R1") && suffixOfFile2.equals("R2")) || (suffixOfFile1.equals("R2") && suffixOfFile2.equals("R1"))) {
                    readStatus = 2;
                    LOGGER.info("Text file validation process successful.");
                } else if ((suffixOfFile1.equals("1") && suffixOfFile2.equals("2")) || (suffixOfFile1.equals("2") && suffixOfFile2.equals("1"))) {
                    readStatus = 2;
                    LOGGER.info("Text file validation process successful.");
                } else {
                    readStatus = 4;
                    LOGGER.error("Invalid file pair.");
                }
            } else {
                readStatus = 3;
                LOGGER.error("Invalid file name.");
            }
        } else {
            readStatus = 8;
            LOGGER.error("Invalid suffix type.");
        }
        return readStatus;
    }

    /*
    uploadType:
    1 -> individual file upload
    2 -> bulk file upload
     */
    private int processAction(HttpServletRequest request, String dirPath, int uploadType, String smapleName, String uploadedFileName) {
        int status = -1;
        String sampleId = null;
        String absoluteFolderPath = null;
        boolean fileMoveStatus = false;
        boolean dbStatus = false;
        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("userId");

        if (uploadType == 1) {
            status = Utility.upload2Server(request, dirPath);
            sampleId = request.getParameter("selectedSampleId");
            uploadedFileName = request.getParameter("fastqFile");
            absoluteFolderPath = dirPath.concat(File.separator).concat(uploadedFileName);
        } else if (uploadType == 2) {
            absoluteFolderPath = dirPath.concat(File.separator).concat(uploadedFileName);
            sampleId = iSampleProjectService.getSampleService(userId, smapleName).getId();
            fileMoveStatus = FileManager.moveFile(destinationDir + File.separator + uploadedFileName, dirPath);
        }

        if (fileMoveStatus || status > 0) {
            dbStatus = save2DB(absoluteFolderPath, sampleId);
            if (dbStatus) {
                status = 2;
                LOGGER.info("Persist to DB successfully.");
            } else {
                LOGGER.error("Error occured while persisting to DB");
            }
        } else {
            //file did not store properly
            status = 5;
        }
        return status;
    }

    //successful files written to the folder will get saved to 2 tables namely sequence_files and samples_sequence_files
    private boolean save2DB(String absoluteFilePath, String sampleId) {
        LOGGER.info("Preparing to persist to DB ....");
        int sequnceId = 0;
        boolean saveStatus = false;
        Calendar currentDateTime = Calendar.getInstance();
        SequenceFiles seqObj = new SequenceFiles();
        seqObj.setFilePath(absoluteFilePath);
        seqObj.setCreatedDate(currentDateTime.getTime());
        sequnceId = iSampleSequenceService.persistSequenceDataService(seqObj);

        if (sequnceId > 0) {
            SamplesSequenceFiles samplesSequenceObj = new SamplesSequenceFiles();
            samplesSequenceObj.setSampleId(iSampleProjectService.getSampleObjByIdService(sampleId));
            samplesSequenceObj.setSequenceFileId(seqObj);
            saveStatus = iSampleSequenceService.persistSampleSequenceDataService(samplesSequenceObj);
        }
        return saveStatus;
    }

    private String getSuffixType(String fileName) {
        String type = null;

        if (fileName.contains("_R1")) {
            type = "R1";
        } else if (fileName.contains("_R2")) {
            type = "R2";
        } else if (fileName.contains("_1")) {
            type = "1";
        } else if (fileName.contains("_2")) {
            type = "2";
        }
        return type;
    }

    private String[] splitFileName(String fileName) {
        String[] splitString = null;

        if (fileName.contains("_R1")) {
            splitString = fileName.split("_R1");
        } else if (fileName.contains("_R2")) {
            splitString = fileName.split("_R2");
        } else if (fileName.contains("_1")) {
            splitString = fileName.split("_1");
        } else if (fileName.contains("_2")) {
            splitString = fileName.split("_2");
        }
        return splitString;
    }

    private File createDirPath(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("userId");
        destinationDir = new File(FileManager.createDir(userId, "BulkFileUploads", Constants.DIRECTORY_LOCATION + userId));
        return destinationDir;
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
