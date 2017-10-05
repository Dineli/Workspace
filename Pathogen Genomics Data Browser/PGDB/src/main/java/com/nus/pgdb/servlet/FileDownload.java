/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb.servlet;

import com.nus.pgdb.entity.ProjectSamples;
import com.nus.pgdb.entity.SamplesSequenceFiles;
import com.nus.pgdb.service.ISampleProject;
import com.nus.pgdb.service.ISampleSequence;
import com.nus.pgdb.service.impl.SampleProjectServiceImpl;
import com.nus.pgdb.service.impl.SampleSequenceServiceImpl;
import com.nus.pgdb.util.FileManager;
import com.nus.pgdb.util.Utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
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
@WebServlet(name = "FileDownload", urlPatterns = {"/FileDownload"})
public class FileDownload extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileDownload.class);
    ISampleSequence iSampleSequenceService = null;
    ISampleProject iSampleProjectService = null;
    private File fileDownloadDir;

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

        HttpSession session = request.getSession();
        List<String> totalFiles2zip = new ArrayList<String>();
        int userId = (Integer) session.getAttribute("userId");
        String subAction = request.getParameter("subAction");
        String zipFolder = "FileDownload_" + Utility.getCurrentDateTime("yyyyMMdd_HHmmss") + ".zip";
        fileDownloadDir = new File(FileManager.createDir(userId, "BulkFileDownloads", ""));
        String zipFileLocation = fileDownloadDir + File.separator + zipFolder;

        if (subAction.equals("TotalFileDownload")) {
            int projectId = Integer.valueOf(request.getParameter("recordId"));

            List<ProjectSamples> sampleList = iSampleProjectService.getSamplesbyProject(projectId);
            if (null != sampleList) {
                for (ProjectSamples sample : sampleList) {
                    List<SamplesSequenceFiles> sequenceFileList = iSampleSequenceService.fetchSequenceFilesBySampleIdService(sample.getSampleId().getId(), userId, false);
                    if (null != sequenceFileList) {
                        for (SamplesSequenceFiles seqFile : sequenceFileList) {
                            totalFiles2zip.add(seqFile.getSequenceFileId().getFilePath());
                        }
                    }
                }
            }

        } else if (subAction.equals("SelectedFileDownload")) {
            String selectedSamples[] = request.getParameter("sampleIds").split(",");
            boolean isSharedProject = Boolean.valueOf(request.getParameter("isSharedProject"));

            for (String sample : selectedSamples) {
                List<SamplesSequenceFiles> sequenceFileList = iSampleSequenceService.fetchSequenceFilesBySampleIdService(sample, userId, isSharedProject);
                if (null != sequenceFileList) {
                    for (SamplesSequenceFiles seqFile : sequenceFileList) {
                        totalFiles2zip.add(seqFile.getSequenceFileId().getFilePath());
                    }
                }
            }
        }
        
        boolean compressStatus = createZipFiles(totalFiles2zip, zipFileLocation);

        if (compressStatus) {
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment;filename=" + zipFolder);

            File file = new File(zipFileLocation);
            ServletOutputStream out;
            boolean downloadStatus = false;
            try (FileInputStream fileIn = new FileInputStream(file)) {
                out = response.getOutputStream();
                byte[] outputByte = new byte[4096];
                //copy binary contect to output stream
                while (fileIn.read(outputByte, 0, 4096) != -1) {
                    out.write(outputByte, 0, 4096);
                    downloadStatus = true;
                }
            }
            out.flush();
            out.close();

            //deletes the compressed file as soon as it gets downloaded
            if (downloadStatus) {
                FileManager.deleteFile(zipFileLocation);
            }
        } else {
            LOGGER.error("Something went wrong during file compression.");
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
    }

    private boolean createZipFiles(List<String> files2Compress, String filePath) {
        int length = 0;
        boolean isFileCompressed = false;
        try {
            // create byte buffer
            byte[] buffer = new byte[1024];
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (int i = 0; i < files2Compress.size(); i++) {
                File srcFile = new File(files2Compress.get(i));
                FileInputStream fis = new FileInputStream(srcFile);
                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(srcFile.getName()));

                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                    isFileCompressed = true;
                }
                zos.closeEntry();
                // close the InputStream
                fis.close();
            }
            // close the ZipOutputStream
            zos.close();
        } catch (IOException ioe) {
            LOGGER.error("Error creating zip file: " + ioe);
        }

        return isFileCompressed;
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
