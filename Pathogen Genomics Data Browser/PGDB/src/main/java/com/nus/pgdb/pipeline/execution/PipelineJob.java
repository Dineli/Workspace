/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.pipeline.execution;

import com.nus.pgdb.entity.PipelineJobs;
import com.nus.pgdb.entity.SamplesSequenceFiles;
import com.nus.pgdb.entity.Users;
import com.nus.pgdb.service.IPipelineJob;
import com.nus.pgdb.service.ISampleSequence;
import com.nus.pgdb.service.IUser;
import com.nus.pgdb.service.impl.PipelineJobServiceImpl;
import com.nus.pgdb.service.impl.SampleSequenceServiceImpl;
import com.nus.pgdb.service.impl.UserServiceImpl;
import com.nus.pgdb.util.FileManager;
import com.nus.pgdb.util.Constants;
import com.nus.pgdb.util.Utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
@WebServlet(name = "PipelineJob", urlPatterns = {"/PipelineJob"})
public class PipelineJob extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PipelineJob.class);
    IUser userService = new UserServiceImpl();
    IPipelineJob pipelineJobService = new PipelineJobServiceImpl();
    ISampleSequence sampleSequenceService = new SampleSequenceServiceImpl();

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
//        ServletContext servletContext = getServletContext();
        HttpSession session = request.getSession();

        int userId = (Integer) session.getAttribute("userId"); 
        String pipelineName = request.getParameter("pipeNameParam");//workflow name given by user
        String pipelineType = "mTB_pipeline";//real pipeline name

        //inorder to maintain the same time stamp, datetime variable is created beforehand and passed it to required functions 
        String currentTimeStamp = Utility.getCurrentDateTime("yyyyMMdd_HHmmss");

        String pipelineInputFolderPath = FileManager.createDir(userId, pipelineType.concat("_inputs_").concat(currentTimeStamp), "");
        String pipelineOutputFolderPath = FileManager.createDir(userId, pipelineType.concat("_outputs_").concat(currentTimeStamp), "");
        String pairedInputsFolderPath = FileManager.createDir(userId, "paired", pipelineInputFolderPath);
        String unpairedInputsFolderPath = FileManager.createDir(userId, "unpaired", pipelineInputFolderPath);

        Users userObj = userService.getUserObjById(userId);
        // Build a FileName
        String fileName = FileManager.getFileName(userId, currentTimeStamp);
        // Create the log file and retrive the fully qualified path
        String logFilePath = FileManager.createFile(userId, fileName, pipelineInputFolderPath);

        PipelineJobs job = new PipelineJobs();
        job.setUserId(userObj);
        job.setName(pipelineName);
        job.setType(pipelineType);
        job.setFileName(fileName);
        job.setFilePath(logFilePath);
        job.setStatus(Constants.SUBMIT);
        job.setInputPath(pipelineInputFolderPath);
        job.setOutputPath(pipelineOutputFolderPath.concat(".tgz"));
        job.setCreatedDate(Utility.getCurrentDateTime());
        boolean recordStatus = pipelineJobService.persistJobDetailService(job);

        if (recordStatus) {
            //run trimmomatic tool
            runTrimmomaticTool(request, pairedInputsFolderPath, unpairedInputsFolderPath, userId);
            int countFiles = FileManager.countFiles(pairedInputsFolderPath);

            if (countFiles > 0) {
                LOGGER.info("Trimmomatic process successfull");
                //fetch json file path
                String pipelineParameterPath = createJsonFile(request, pipelineInputFolderPath, currentTimeStamp);
                //call python script
                boolean processStatus = executePipeline01(logFilePath, pairedInputsFolderPath, pipelineOutputFolderPath, pipelineParameterPath);
                if (processStatus) {
                    LOGGER.info("mTB_pipeline execution completed successfully");
//                    servletContext.setAttribute("pipelineStatus", "Successfull!");
                }
            } else {
                LOGGER.error("No paired files created. Check trimmomatic process.");
//                servletContext.setAttribute("pipelineStatus", "Error in trimmomatic process!");
            }
        } else {
            LOGGER.error("Error in saving data. Cannot proceed.");
//            servletContext.setAttribute("pipelineStatus", "Error saving data to the DB!");
        }
    }

    private boolean executePipeline01(String logFilePath, String inputPath, String outputPath, String parameterPath) {
        //TODO : modify below for the production
        String[] cmd = new String[8];
        cmd[0] = Constants.PYTHON_LOCATION; //Python location
        cmd[1] = Constants.PYTHON_SCRIPT_LOCATION; //Python script location
        cmd[2] = Constants.PYTHON_ARGUMENT; //flushes the .py script in order to get the print statements as and when it happens
        cmd[3] = inputPath; //Param 1
        cmd[4] = outputPath; //Param 2
        cmd[5] = logFilePath; //Param 3
        cmd[6] = parameterPath; //Param 4
        cmd[7] = Constants.GALAXY_DICTIONARY_PATH; //Param 5
        boolean status = false;
        String line = "";

        ProcessBuilder probuilder = new ProcessBuilder(cmd[0], cmd[2], cmd[1], cmd[3], cmd[4], cmd[5], cmd[6], cmd[7]);
        try {
            LOGGER.info("mTB_pipeline execution started ....");
            Process process = probuilder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder pipelineSB = new StringBuilder();
            while ((line = br.readLine()) != null) {
                pipelineSB.append(line).append("\n");
                status = true;
            }
            LOGGER.info(pipelineSB.toString());
        } catch (IOException ex) {
            LOGGER.error("IOException occurred while executing the pipeline: " + ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Pipeline execution failure: " + ex.getMessage());
        }
        return status;
    }

    private String createJsonFile(HttpServletRequest request, String fileLocation, String currentDateTime) {
        String pipeName = request.getParameter("pipeNameParam");
        String lb = request.getParameter("lbParam");
        String cfd = request.getParameter("cfdParam");
        String mrq = request.getParameter("mrqParam");
        String mbq = request.getParameter("mbqParam");
        String mmq = request.getParameter("mmqParam");

        String actualValues[] = {pipeName, lb, cfd, mrq, mbq, mmq};
        String parameterName[] = {"pipeline_name", "LB", "coefficient_for_downgrading", "max_reads_per_bam", "minimum_base_quality", "minimum_mapping_quality"};
        String defaultValues[] = {"mTB_pipeline", "CTD0113", "50", "2000", "20", "5"};

        JSONObject obj = new JSONObject();
        FileWriter file = null;
        int paramCount = 6;
        String fileName = "customizeParams_" + currentDateTime.concat(".json");
        String logFilePath = fileLocation.concat(File.separator).concat(fileName);
        //check against the default values. Changes will be written to a JSON file
        for (int i = 1; i < paramCount; i++) {
            if (actualValues[i] != null) {
                if (!actualValues[i].equals(defaultValues[i])) {
                    obj.put(parameterName[i], actualValues[i]);
                }
            }
        }
        //pipeline name will always written to the JSON file
        obj.put(parameterName[0], defaultValues[0]);

        try {
            file = new FileWriter(logFilePath);
            file.write(obj.toJSONString());
            file.flush();
            LOGGER.info("Json file created");
        } catch (IOException ex) {
            LOGGER.error("Error occured while writing to json file" + ex.getMessage());
        }
        return logFilePath;
    }

    private void runTrimmomaticTool(HttpServletRequest request, String pairedPath, String unpairedPath, int userId) {
        LOGGER.info("Started trimmomatic tool against the selected samples");
        String sampleIdList = request.getParameter("sampleIds");
        String sampleIdArr[] = sampleIdList.split(",");
        String line = "";
        String[] cmd = new String[6];
        for (String sampleIdArr1 : sampleIdArr) {
            int randomNo = (int) Math.floor(Math.random() * 1000);
            List<SamplesSequenceFiles> sampleSqObj = sampleSequenceService.fetchSequenceFilesBySampleIdService(sampleIdArr1, userId, false);

            cmd[0] = sampleSqObj.get(0).getSequenceFileId().getFilePath();
            cmd[1] = sampleSqObj.get(1).getSequenceFileId().getFilePath();
            cmd[2] = pairedPath.concat(File.separator).concat("paired_" + randomNo + "_R1_forward").concat(".fq.gz");
            cmd[3] = unpairedPath.concat(File.separator).concat("unpaired_" + randomNo + "_R1_forward").concat(".fq.gz");
            cmd[4] = pairedPath.concat(File.separator).concat("paired_" + randomNo + "_R2_reverse").concat(".fq.gz");
            cmd[5] = unpairedPath.concat(File.separator).concat("unpaired_" + randomNo + "_R2_reverse").concat(".fq.gz");

            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", Constants.JAR_PATH, "PE", "-threads", "5", "-phred33", cmd[0], cmd[1], cmd[2], cmd[3], cmd[4], cmd[5], Constants.TRIMMO_ARG_1, Constants.TRIMMO_ARG_2, Constants.TRIMMO_ARG_3, Constants.TRIMMO_ARG_4);
            try {
                Process processTrimmomatic = processBuilder.start();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(processTrimmomatic.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bfr.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
//                LOGGER.info(stringBuilder.toString());
            } catch (IOException ex) {
                LOGGER.error("Process execution failure while running trimmomatic tool: " + ex.getMessage());
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
