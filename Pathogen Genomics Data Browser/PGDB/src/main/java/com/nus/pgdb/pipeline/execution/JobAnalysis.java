/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.pipeline.execution;

import com.nus.pgdb.entity.PipelineJobs;
import com.nus.pgdb.entity.PipelineJobsHistory;
import com.nus.pgdb.service.IPipelineJob;
import com.nus.pgdb.service.impl.PipelineJobServiceImpl;
import com.nus.pgdb.util.Constants;
import com.nus.pgdb.util.FileManager;
import com.nus.pgdb.util.Utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
@WebServlet(name = "JobAnalysis", urlPatterns = {"/JobAnalysis"})
public class JobAnalysis extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobAnalysis.class);
    IPipelineJob iPipelineService = null;
    List<PipelineJobs> activeJobs = null;

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
        response.setContentType("text/plain");
        iPipelineService = new PipelineJobServiceImpl();

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
        String subAction = request.getParameter("subAction");

        if (subAction.equals("downloadFile")) {
            String outputFolderPath = request.getParameter("file");
            String outputFolderName = outputFolderPath.substring(outputFolderPath.lastIndexOf(File.separator) + 1);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + outputFolderName);

            File file = new File(outputFolderPath);
            ServletOutputStream out;
            try (FileInputStream fileIn = new FileInputStream(file)) {
                out = response.getOutputStream();
                byte[] outputByte = new byte[4096];
                //copy binary contect to output stream
                while (fileIn.read(outputByte, 0, 4096) != -1) {
                    out.write(outputByte, 0, 4096);
                }
            }
            out.flush();
            out.close();
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
        String subAction = request.getParameter("subAction");
        PrintWriter printWriter = response.getWriter();
        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("userId");

        if (subAction.equals("updateJobStatus")) {
            //fetch all analysis data belong to the above user which are in the 0,1 and 2 states
            activeJobs = iPipelineService.fetchActiveJobs(userId);
            if ((null != activeJobs) && (!activeJobs.isEmpty())) {
                for (PipelineJobs activeJob : activeJobs) {
                    BufferedReader br = null;
                    FileReader fr = null;

                    try {
                        fr = new FileReader(activeJob.getFilePath());
                        br = new BufferedReader(fr);
                        String jobStatus[] = null;
                        String line;

                        br = new BufferedReader(new FileReader(activeJob.getFilePath()));
                        while ((line = br.readLine()) != null) {
                            if (line.contains("status")) {
                                jobStatus = line.split(":");
                                //update the job status to the table
                                iPipelineService.updateJobStatus(activeJob.getId(), jobStatus[1]);
                            }
                        }
                    } catch (IOException e) {
                        LOGGER.error("IOException :" + e.getMessage());
                    } finally {
                        try {
                            if (br != null) {
                                br.close();
                            }
                            if (fr != null) {
                                fr.close();
                            }
                        } catch (IOException ex) {
                            LOGGER.error("IOException :" + ex.getMessage());
                        }
                    }
                }
            }
        } else if (subAction.equals("deleteFile")) {
            int recordId = Integer.valueOf(request.getParameter("recordId"));
            String pipelineName = request.getParameter("pipelineName");
            String pipelineType = request.getParameter("pipelineType");
            String absInPath = request.getParameter("absoluteInputPath");
            String absCompressOutPath = request.getParameter("absoluteOutputPath");

            PipelineJobsHistory pipelineJobsHistory = new PipelineJobsHistory();
            pipelineJobsHistory.setName(pipelineName);
            pipelineJobsHistory.setType(pipelineType);
            pipelineJobsHistory.setCreatedDate(Utility.getCurrentDateTime());
            boolean saveStatus = iPipelineService.persistJobHistoryService(pipelineJobsHistory);

            if (saveStatus) {
                //deleting input folder path
                FileManager.deleteFolder(absInPath);
                //deleting compress output folder
                FileManager.deleteFolder(absCompressOutPath);
                //deleting output folder path
                String to_remove = ".tgz";
                String outputFolderPath = absCompressOutPath.replace(to_remove, "");
                FileManager.deleteFolder(outputFolderPath);

                //delete record from pipeline_jobs
                boolean deleteStatus = iPipelineService.deleteJobService(recordId);
                printWriter.println(Utility.getProcessStatus(Constants.DELETE, deleteStatus));
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
