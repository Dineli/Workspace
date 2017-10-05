/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.util;

import com.nus.pgdb.servlet.FileUpload;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
public class Utility {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);

    public static String getCurrentDateTime(String format) {
        Calendar currentDateTime = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(currentDateTime.getTime());
    }

    public static Date getCurrentDateTime() {
        Calendar currentDateTime = Calendar.getInstance();
        return currentDateTime.getTime();
    }

    public static String getDate(Date dbDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(dbDate);
    }

    public static String removeHtmlTags(String text) {
        String pureText = text.replaceAll("\\<.*?\\>", "");
        return pureText;
    }

    //gets the last sampleId, removes the prefix and generate the next sampleId by incrementing it by 1    
    public static String generateSampleId(String lastRecordId) {
        String pattern = String.format("%06d", Integer.valueOf(lastRecordId.substring(1)) + 1); //Ex: S000001 [prefix with 'S' along with 6 digits]
        return "S".concat(pattern);
    }

    public static String getProcessStatus(String operationType, boolean status) {
        String message = null;
        String successCss = "alert alert-success login-msg w3-panel w3-wide w3-round w3-animate-opacity";
        String failCss = "alert alert-danger login-error-msg w3-panel w3-wide w3-round w3-animate-opacity";
        switch (operationType) {
            case "create":
                message = (status) ? "<div class='" + successCss + "'><p>Record Saved Successfully</p></div>" : "<div class='" + failCss + "'><p>Unable to save record successfully</p></div>";
                break;
            case "update":
                message = (status) ? "<div class='" + successCss + "'><p>Record Updated Successfully</p></div>" : "<div class='" + failCss + "'><p>Unable to update record successfully</p></div>";
                break;
            case "delete":
                message = (status) ? "<div class='" + successCss + "'><p>Record Deleted Successfully</p></div>" : "<div class='" + failCss + "'><p>Unable to delete record successfully</p></div>";
                break;
            case "customResetPasswordMsg1":
                message = (status) ? "<div class='" + successCss + "'><p>Password reset is successful.</p></div>." : "<div class='" + failCss + "'><p>Unable to reset password successfully</p></div>";
                break;
            case "customResetPasswordMsg2":
                message = (status) ? "<div class='" + successCss + "'><p>Password reset is successful. You may now <a href=" + Constants.SITE_URL + " style='color:blue'>login</a></p></div>" : "<div class='" + failCss + "'><p>Unable to reset password successfully</p></div>";
                break;
        }
        return message;
    }

    public static String getValidateMsgs(String validationElement) {
        String validationMsg = null;
        String divClass = "<div class='validate-msgs w3-panel w3-small w3-wide w3-round w3-animate-opacity'><p><i class='fa fa-exclamation fa-lg' aria-hidden='true'></i>";
        switch (validationElement) {
            case "sampleName":
                validationMsg = " Sample name already exists, use a different name.";
                break;
            case "oldPassword":
                validationMsg = " Incorrect old password.";
                break;
            case "userName":
                validationMsg = " Please chose a different user name.";
                break;
            case "fileUploadError1":
                validationMsg = " You cannot upload more than 2 files per sample.";
                break;
            case "fileUploadError2":
                validationMsg = " File doesn't match.";
                break;
            case "fileUploadError3":
                validationMsg = " Upload the correct file pair.";
                break;
            case "fileUploadError4":
                validationMsg = " Something went wrong while file upload.";
                break;
            case "fileUploadError5":
//                validationMsg = " File/s did not saved properly to the destination.";
                validationMsg = " Uploaded files do not match with the text file.";
                break;
            case "email":
                validationMsg = " No such email exists in the system, please try again.";
                break;
            case "emailExists":
                validationMsg = " Email already exists. Please insert a different email.";
                break;
            case "tokenExpire":
                validationMsg = " The password reset link you tried to use is no longer valid.";
                break;
            case "txtFileUploadError6":
                validationMsg = " Invalid file format, please check.";
                break;
            case "txtFileUploadError7":
                validationMsg = " Sample/s not exists. Please create the missing sample/s and re-upload.";
                break;
            case "txtFileUploadError8":
                validationMsg = " Invalid file suffix type only [R1 and R2], [1 and 2] considered valid.";
                break;

        }
        return divClass.concat(validationMsg).concat("</p></div>");
    }

    public static String generateWelcomeMailContent(String name, String userName, String password) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ".concat(name).concat(",\n\n"));
        sb.append("We are delighted to announce that you are now an official member of the PGDB module.\n\n");
        sb.append("Following are your credentials and please make sure to reset your password immediately.\n\n");
        sb.append("Site URL - ".concat(Constants.SITE_URL));
        sb.append(" [Menu -> Reset Password]");
        sb.append("\n\n");
        sb.append("Username - ".concat(userName).concat(" /").concat(" Password - ").concat(password));
        sb.append("\n\n");
        sb.append("---------------\n");
        sb.append("Cheers,\nPGDB Admin Team");

        return sb.toString();
    }

    public static String generateChangePasswordMailContent(String tokenStr) {
        StringBuilder sb = new StringBuilder();
        sb.append("We have received a request to reset the password for this email address. \n\n");
        sb.append("To reset your password, click the link below:\n\n");
        sb.append(tokenStr);
        sb.append("\n\n");
        sb.append("This link takes you to a secure page where you can change your password. \n\n");
        sb.append("If you do not want to reset the password, please ignore this message.");
        sb.append("\n\n");
        sb.append("---------------\n");
        sb.append("Cheers,\n PGDB Admin Team");

        return sb.toString();
    }
    
    public static int upload2Server(HttpServletRequest request, String dirPath) {
        LOGGER.info("Preparing to store files on server ....");
        int uploadStatus = -1;
        List items = null;
        File file = null;
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            items = upload.parseRequest(request);
        } catch (Exception exception) {
            LOGGER.error("parsing error " + exception.getMessage());
            exception.getStackTrace();
        }
        Iterator itr = items.iterator();
        while (itr.hasNext()) {
            FileItem item = (FileItem) itr.next();
            if (!item.isFormField()) {
                String itemname = item.getName();
                if ((itemname == null || itemname.equals(""))) {
                    continue;
                }
                file = new File(dirPath, item.getName());
                try {
                    item.write(file);
                    LOGGER.info("File/s stored successfully.");
                    uploadStatus = 2;
                } catch (Exception ex) {
                    LOGGER.error("Error occured while writing the file/s to folder " + ex.getMessage());
                }
            }
        }
        return uploadStatus;
    }

}
