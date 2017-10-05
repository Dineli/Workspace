/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.servlet;

import com.nus.pgdb.entity.SharedUserProjects;
import com.nus.pgdb.service.IProject;
import com.nus.pgdb.service.IUser;
import com.nus.pgdb.service.impl.ProjectServiceImpl;
import com.nus.pgdb.service.impl.UserServiceImpl;
import com.nus.pgdb.util.Constants;
import com.nus.pgdb.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ProjectShare", urlPatterns = {"/ProjectShare"})
public class ProjectShare extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectShare.class);
    IProject projectService = null;
    IUser userService = null;

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
        projectService = new ProjectServiceImpl();
        userService = new UserServiceImpl();
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
        String subAction = request.getParameter("subAction");
        HttpSession session = request.getSession();
        int userID = (Integer) session.getAttribute("userId");

        if (subAction.equals("project-share")) {
            int sharedProjectId = Integer.valueOf(request.getParameter("proIdParam"));
            int sharedUserId = Integer.valueOf(request.getParameter("selectedUserParam"));

            SharedUserProjects sharedUserProjectObj = new SharedUserProjects();
            sharedUserProjectObj.setSharedProjectId(projectService.getProjectObjByIdService(sharedProjectId));
            sharedUserProjectObj.setProjectOwnerId(userService.getUserObjById(userID));
            sharedUserProjectObj.setSharedUserId(userService.getUserObjById(sharedUserId));
            boolean createStatus = projectService.persistProjectShareService(sharedUserProjectObj);
            out.println(Utility.getProcessStatus(Constants.CREATE, createStatus));
        }if (subAction.equals("project-share-remove")) {
            int recordID = Integer.valueOf(request.getParameter("recordIdParam"));
            
            boolean deleteStatus = projectService.removeSharedProjectService(recordID);
            out.println(Utility.getProcessStatus(Constants.DELETE, deleteStatus));
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
