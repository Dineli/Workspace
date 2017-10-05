/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.servlet;

import com.nus.pgdb.entity.Users;
import com.nus.pgdb.service.IUser;
import com.nus.pgdb.service.impl.UserServiceImpl;
import com.nus.pgdb.util.PWAuthentication;
import com.nus.pgdb.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
@WebServlet(name = "Authentication", urlPatterns = {"/Authentication"})
public class Authentication extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(Authentication.class);
    PWAuthentication pwa = null;
    IUser iUserService = null;

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
        pwa = new PWAuthentication();
        iUserService = new UserServiceImpl();

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
        PrintWriter out = response.getWriter();
        String subAction = request.getParameter("subAction");
        HttpSession session = request.getSession();

        if (subAction.equals("login-authenticate")) {
            try {
                String userName = request.getParameter("un");
                String password = request.getParameter("pw");

                //fetch hash and salt for inserted user name
                Users userObj = iUserService.getUserDataByUsernameService(userName);
                if (null != userObj) {

                    byte[] encryptedPw = userObj.getEncryptedPassword();
                    byte[] saltValue = userObj.getSalt();

                    //comparison
                    boolean pwStatus = pwa.userAuthenticate(password, encryptedPw, saltValue);

                    if (pwStatus) {
                        session.setAttribute("name", userName);
                        session.setAttribute("fullName", userObj.getName());
                        session.setAttribute("userId", userObj.getId());
                        session.setAttribute("isAdmin", userObj.getIsAdmin());
                        out.println("<div class='alert alert-success login-msg w3-panel w3-wide w3-round w3-animate-opacity'><p>Login successful!</p></div>");
                        LOGGER.info("Login successful");
                    } else {
                        out.println("<div class='alert alert-danger w3-panel w3-round w3-animate-opacity w3-wide login-error-msg'><p>Sorry, incorrect password!</p></div>");
                        LOGGER.error("Incorrect user name or password");
                    }
                } else {
                    out.println("<div class='alert alert-danger w3-panel w3-round w3-animate-opacity w3-wide login-error-msg'><p>Sorry, incorrect user name!</p></div>");
                    LOGGER.error("Incorrect user name");
                }

            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("Throws NoSuchAlgorithmException :" + ex.getMessage());
            } catch (InvalidKeySpecException ex) {
                LOGGER.error("Throws InvalidKeySpecException :" + ex.getMessage());
            }

        } else if (subAction.equals("logout")) {
            session.invalidate();
            //delete cart values if available
            LOGGER.info("User logged out successfully.");
        }
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

        if (subAction.equals("password-change")) {
            String newPassword = request.getParameter("newPwParam");
            String userToken = request.getParameter("tokenParam");

            Users user = iUserService.getUserDataByTokenService(userToken);
            if (null != user) {
                try {
                    //generate salt for user password
                    byte[] salt = pwa.generateSaltForPassword();
                    //generate pw encryption
                    byte[] encryptedPw = pwa.getEncryptedPassword(newPassword, salt);

                    boolean pwChangeStatus = iUserService.updateUserService(user.getId(), encryptedPw, salt, true);
                    out.println(Utility.getProcessStatus("customResetPasswordMsg2", pwChangeStatus));

                } catch (NoSuchAlgorithmException ex) {
                    LOGGER.error("Throws NoSuchAlgorithmException :" + ex.getMessage());
                } catch (InvalidKeySpecException ex) {
                    LOGGER.error("Throws InvalidKeySpecException :" + ex.getMessage());
                }
            } else {
                out.println(Utility.getValidateMsgs("tokenExpire"));
            }
        } else if (subAction.equals("password-reset")) {
            String oldPassword = request.getParameter("oldPwParam");
            String newPassword = request.getParameter("newPwParam");

            Users userObj = iUserService.getUserObjById(userID);
            String oldPw = userObj.getDummyPassword();

            try {
                //generate salt for user password
                byte[] salt = pwa.generateSaltForPassword();
                //generate pw encryption
                byte[] encryptedPw = pwa.getEncryptedPassword(newPassword, salt);

                //if oldPW is empty that means user is trying to change his password again. If that's the case fetch required fields for the current PW.
                if (oldPw.isEmpty()) {
                    boolean match = pwa.userAuthenticate(oldPassword, userObj.getEncryptedPassword(), userObj.getSalt());
                    if (match) {
                        passwordReset(false, userID, encryptedPw, salt, out);
                    } else {
                        out.println(Utility.getValidateMsgs("oldPassword"));
                    }
                } else if (oldPw.equals(oldPassword)) { //first time re-setting the PW
                    passwordReset(true, userID, encryptedPw, salt, out);
                } else {
                    out.println(Utility.getValidateMsgs("oldPassword"));
                }

            } catch (NoSuchAlgorithmException ex) {
                LOGGER.error("Throws NoSuchAlgorithmException :" + ex.getMessage());
            } catch (InvalidKeySpecException ex) {
                LOGGER.error("Throws InvalidKeySpecException :" + ex.getMessage());
            }
        }

    }

    private void passwordReset(boolean firstTime, int userId, byte[] encrypPW, byte[] salt, PrintWriter pw) {
        boolean resetStatus;
        if (firstTime) {
            resetStatus = iUserService.updateUserService(userId, encrypPW, salt, firstTime);
        } else {
            resetStatus = iUserService.updateUserService(userId, encrypPW, salt, firstTime);
        }
        pw.println(Utility.getProcessStatus("customResetPasswordMsg1", resetStatus));
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
