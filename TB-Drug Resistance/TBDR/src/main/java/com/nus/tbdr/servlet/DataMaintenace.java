/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.servlet;

import com.nus.tbdr.entity.DataSources;
import com.nus.tbdr.entity.Drugs;
import com.nus.tbdr.exception.InvalidArgumentException;
import com.nus.tbdr.exception.RecordExistException;
import com.nus.tbdr.exception.TBDRException;
import com.nus.tbdr.service.IDataSource;
import com.nus.tbdr.service.IDrug;
import com.nus.tbdr.service.impl.DataSourceServiceImpl;
import com.nus.tbdr.service.impl.DrugServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dineli
 */
@WebServlet(name = "DataMaintenace", urlPatterns = {"/DataMaintenace"})
public class DataMaintenace extends HttpServlet {

    IDrug drugService = new DrugServiceImpl();
    IDataSource dsService = new DataSourceServiceImpl();

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

        if (subAction.equalsIgnoreCase("drugs-save")) {
            String drugName = request.getParameter("drugName");
            int newDrugId = -1;
            Drugs drugObj = new Drugs();
            drugObj.setName(drugName);

            try {
                newDrugId = drugService.save(drugObj);
            } catch (InvalidArgumentException ex) {
                out.println("<div class='alert alert-danger'>Drug name cannot be blank.</div>");
                ex.getMessage();
            } catch (RecordExistException ex) {
                out.println("<div class='alert alert-danger'>Record Already Exists!</div>");
                ex.getMessage();
            } catch (TBDRException ex) {
                out.println("<div class='alert alert-danger'>Unable to save drug data successfully</div>");
                ex.getMessage();
            }

            if (newDrugId > 0) {
                out.println("<div class='alert alert-success'>Record Saved Successfully</div>");
            }

        } else if (subAction.equalsIgnoreCase("drugs-update")) {
            int drugId = Integer.parseInt(request.getParameter("recordId"));
            String drugName = request.getParameter("drugName");
            boolean updateStatus = false;

            try {
                updateStatus = drugService.update(drugId, drugName);
            } catch (InvalidArgumentException ex) {
                out.println("<div class='alert alert-danger'>Drug name cannot be blank.</div>");
                ex.getMessage();
            } catch (RecordExistException ex) {
                out.println("<div class='alert alert-danger'>Record Already Exists!</div>");
                ex.getMessage();
            } catch (TBDRException ex) {
                out.println("<div class='alert alert-danger'>Unable to update drug data successfully</div>");
                ex.getMessage();
            }

            if (updateStatus) {
                out.println("<div class='alert alert-success'>Record Updated Successfully</div>");
            }
        } else if (subAction.equalsIgnoreCase("drugs-delete")) {
            int drugId = Integer.parseInt(request.getParameter("recordId"));
            int deleteStatus = drugService.delete(drugId);
            if (deleteStatus > 0) {
                out.println("<div class='alert alert-success'>Record Deleted Successfully</div>");
            } else {
                out.println("<div class='alert alert-danger'>Unable to delete drug data successfully</div>");
            }
        } else if (subAction.equalsIgnoreCase("ds-save")) {
            String dsName = request.getParameter("dsName");
            String dsDesc = request.getParameter("dsDesc");
            int newDSId = -1;
            DataSources dataSourceObj = new DataSources();
            dataSourceObj.setName(dsName);
            dataSourceObj.setDescription(dsDesc);
            try {
                newDSId = dsService.save(dataSourceObj);
            } catch (InvalidArgumentException ex) {
                out.println("<div class='alert alert-danger'>Data source cannot be blank.</div>");
                ex.getMessage();
            } catch (RecordExistException ex) {
                out.println("<div class='alert alert-danger'>Record Already Exists!</div>");
                ex.getMessage();
            } catch (TBDRException ex) {
                out.println("<div class='alert alert-danger'>Unable to save data source successfully</div>");
                ex.getMessage();
            }

            if (newDSId > 0) {
                out.println("<div class='alert alert-success'>Record Saved Successfully</div>");
            }
        } else if (subAction.equalsIgnoreCase("ds-update")) {
            int dsId = Integer.parseInt(request.getParameter("recordId"));
            String dsName = request.getParameter("dsName");
            String dsDesc = request.getParameter("dsDesc");
            boolean updateStatus = false;

            DataSources dataSourceObj = new DataSources();
            dataSourceObj.setId(dsId);
            dataSourceObj.setName(dsName);
            dataSourceObj.setDescription(dsDesc);

            try {
                updateStatus = dsService.update(dataSourceObj);
            } catch (InvalidArgumentException ex) {
                out.println("<div class='alert alert-danger'>Data source cannot be blank.</div>");
                ex.getMessage();
            } catch (RecordExistException ex) {
                out.println("<div class='alert alert-danger'>Record Already Exists!</div>");
                ex.getMessage();
            } catch (TBDRException ex) {
                out.println("<div class='alert alert-danger'>Unable to update data source successfully</div>");
                ex.getMessage();
            }

            if (updateStatus) {
                out.println("<div class='alert alert-success'>Record Updated Successfully</div>");
            }
        } else if (subAction.equalsIgnoreCase("ds-delete")) {
            int dsId = Integer.parseInt(request.getParameter("recordId"));
            int deleteStatus = -1;
            try {
                deleteStatus = dsService.delete(dsId);
            } catch (TBDRException ex) {
                out.println("<div class='alert alert-danger'>Unable to delete data source successfully</div>");
                ex.getMessage();
            }
            if (deleteStatus > 0) {
                out.println("<div class='alert alert-success'>Record Deleted Successfully</div>");
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
