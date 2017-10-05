/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.servlet;

import com.nus.tbdr.entity.DataSources;
import com.nus.tbdr.entity.DrugResistance;
import com.nus.tbdr.entity.Drugs;
import com.nus.tbdr.entity.Variants;
import com.nus.tbdr.exception.TBDRException;
import com.nus.tbdr.service.IDataSource;
import com.nus.tbdr.service.IDrug;
import com.nus.tbdr.service.IDrugResistance;
import com.nus.tbdr.service.IVariant;
import com.nus.tbdr.service.impl.DataSourceServiceImpl;
import com.nus.tbdr.service.impl.DrugResistanceServiceImpl;
import com.nus.tbdr.service.impl.DrugServiceImpl;
import com.nus.tbdr.service.impl.VariantServiceImpl;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
@WebServlet(name = "UploadtoDB", urlPatterns = {"/UploadtoDB"})
public class UploadtoDB extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadtoDB.class);
    private static final String DESTINATION_DIR_PATH = "/UploadedFiles";
    private File destinationDir;
    private File storedFile;
    IVariant iVariant = new VariantServiceImpl();
    IDrug iDrug = new DrugServiceImpl();
    IDataSource iDataSource = new DataSourceServiceImpl();
    IDrugResistance iDrugResistance = new DrugResistanceServiceImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        destinationDir = new File(getServletContext().getRealPath(DESTINATION_DIR_PATH));
    }

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
        int msgStatus = 0;
        int selectedDSId = Integer.valueOf(request.getParameter("selectedDSid"));

        try {
            boolean ismultipart = ServletFileUpload.isMultipartContent(request);
            if (ismultipart) {
                storedFile = uploadNstoreExcel(request, response);
                msgStatus = readNsaveExcel(storedFile, selectedDSId);
                switch (msgStatus) {
                    case 1:
                        out.println("<div class='alert alert-danger'>Excel content is invalid. Please upload a correct file.</div>");
                        break;
                    case 2:
                        out.println("<div class='alert alert-success'>File uploaded successfully.</div>");
                        break;
                    case 3:
                        out.println("<div class='alert alert-danger'>Drug name not in database.</div>");
                        break;
                    default:
                        out.println("<div class='alert alert-danger'>Something went wrong while file upload.</div>");
                        break;
                }
            }
        } catch (Exception exception) {
            LOGGER.error("Error " + exception.getMessage());
            exception.getStackTrace();
        } finally {
            out.close();
        }

    }

    private File uploadNstoreExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("preparing to store the excel file on server");
        PrintWriter out = response.getWriter();
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = null;
        File file = null;
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
                String filename = FilenameUtils.getName(itemname);
//                if (checkExist(destinationDir.toString(), filename)) {
//                    out.println("<div class='alert alert-danger'>This file is already uploaded.</div>");
//                } else {
//                    file = new File(destinationDir, item.getName());
//                    item.write(file);
//                }

                file = new File(destinationDir, item.getName());
                item.write(file);
            }
        }
        return file;
    }

    private int readNsaveExcel(File fileObj, int dataSourceId) throws Exception {
        LOGGER.info("preparing to read and save excel data to DB");
        boolean variantSaveStatus;
        int msgType = 0;
        // Keep the Drug Information Stored in the Map
        Map<String, Drugs> drugNameMap = readDrugInfo();
        // Maintain a map to keep track of Variant and Drug Resistance instances
        Set<Variants> varaintsToSave = new HashSet<>();
        try {
            // Populate the Variant Map with all the Variants
            List<Variants> storedVariants = fetchAllVariants();
            InputStream inputStream = new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(fileObj)));
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            DataSources dsObj = iDataSource.fetchDSbyId(dataSourceId);
            if ((sheet.getRow(0).getPhysicalNumberOfCells() != 23) && (sheet.getRow(0).getPhysicalNumberOfCells() != 24) && (sheet.getRow(0).getPhysicalNumberOfCells() != 25)) {
                msgType = 1;
            } else {
                Iterator<Row> rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {
                    Variants variantObj = new Variants();
                    DrugResistance drObj = new DrugResistance();
                    XSSFRow myRow = (XSSFRow) rowIterator.next();
                    //For each row, iterate through all the columns
                    Iterator<Cell> cellIterator = myRow.cellIterator();
                    drObj.setDataSourceId(dsObj);

                    while (cellIterator.hasNext()) {
                        XSSFCell myCell = (XSSFCell) cellIterator.next();
                        int columnIndex = myCell.getColumnIndex();
                        switch (columnIndex) {
                            case 0:
                                variantObj.setVarPositionGenomeStart(Long.valueOf(readCellValues(myCell).toString()));
                                break;
                            case 1:
                                variantObj.setVarPositionGenomeStop(Long.valueOf(readCellValues(myCell).toString()));
                                break;
                            case 2:
                                variantObj.setVarType(readCellValues(myCell).toString());
                                break;
                            case 3:
                                variantObj.setNumber(Long.valueOf(readCellValues(myCell).toString()));
                                break;
                            case 4:
                                variantObj.setWtBase(readCellValues(myCell).toString());
                                break;
                            case 5:
                                variantObj.setVarBase(readCellValues(myCell).toString());
                                break;
                            case 6:
                                variantObj.setRegion(readCellValues(myCell).toString());
                                break;
                            case 7:
                                variantObj.setGeneId(readCellValues(myCell).toString());
                                break;
                            case 8:
                                variantObj.setGeneName(readCellValues(myCell).toString());
                                break;
                            case 9:
                                variantObj.setGeneStart(Long.valueOf(readCellValues(myCell).toString()));
                                break;
                            case 10:
                                variantObj.setGeneStop(Long.valueOf(readCellValues(myCell).toString()));
                                break;
                            case 11:
                                variantObj.setGeneLength(Long.valueOf(readCellValues(myCell).toString()));
                                break;
                            case 12:
                                variantObj.setDir(readCellValues(myCell).toString());
                                break;
                            case 13:
                                variantObj.setWtAa(readCellValues(myCell).toString());
                                break;
                            case 14:
                                if (readCellValues(myCell).toString().contains("-")) {
                                    variantObj.setCodonNr(0L);
                                } else {
                                    variantObj.setCodonNr(Long.valueOf(readCellValues(myCell).toString()));
                                }
                                break;
                            case 15:
                                if (readCellValues(myCell).toString().contains("-")) {
                                    variantObj.setCodonNrEColi(0L);
                                } else {
                                    variantObj.setCodonNrEColi(Long.valueOf(readCellValues(myCell).toString()));
                                }
                                break;
                            case 16:
                                variantObj.setVarAa(readCellValues(myCell).toString());
                                break;
                            case 17:
                                variantObj.setAaChange(readCellValues(myCell).toString());
                                break;
                            case 18:
                                variantObj.setCodonChange(readCellValues(myCell).toString());
                                break;
                            case 19:
                                variantObj.setVarPositionGeneStart(Long.valueOf(readCellValues(myCell).toString()));
                                break;
                            case 20:
                                variantObj.setVarPositionGeneStop(Long.valueOf(readCellValues(myCell).toString()));
                                break;
                            case 21:
                                String drugName = readCellValues(myCell).toString();
                                Drugs drugobj = drugNameMap.get(drugName);
                                if (null != drugobj) {
                                    drObj.setDrugId(drugobj);
                                } else {
                                    LOGGER.error("Drug name not in database " + drugName);
                                    return msgType = 3;
                                }
                                break;
                            case 22:
                                if (readCellValues(myCell).toString().contains("-")) {
                                    drObj.setReferencePmid(0L);
                                } else {
                                    drObj.setReferencePmid(Long.valueOf(readCellValues(myCell).toString()));
                                }
                                break;
                            case 23:
                                if (readCellValues(myCell).toString().contains("yes")) {
                                    drObj.setHighConfidence(true);
                                } else if (readCellValues(myCell).equals("")) {
                                    drObj.setHighConfidence(false);
                                } else {
                                    drObj.setHighConfidence(false);
                                }
                                break;
                            case 24:
                                variantObj.setRemarks(readCellValues(myCell).toString());
                                break;
                            default:
                                msgType = 0;
                                break;
                        }
                    }

                    int index = storedVariants.indexOf(variantObj);
                    if (index > -1) {
                        mergeChanges(variantObj, storedVariants.get(index));
                        // Set the existing ID to the variant Obj
                        variantObj = storedVariants.get(index);
                    }

                    for (Variants saveObj : varaintsToSave) {
                        if (saveObj.equals(variantObj)) {
                            // This variants is already existing in the save list
                            // merge the latest changes
                            mergeChanges(variantObj, saveObj);
                            variantObj = saveObj;
                        }
                    }

                    Set<DrugResistance> drugResistanceList = variantObj.getDrugResistanceList();
                    if (drugResistanceList == null || drugResistanceList.isEmpty()) {
                        drugResistanceList = new HashSet<>();
                    }
                    drObj.setVariantId(variantObj);
                    drugResistanceList.add(drObj);
                    variantObj.setDrugResistanceList(drugResistanceList);
                    varaintsToSave.add(variantObj);
                }
                //saving to varint table
                variantSaveStatus = write2tblVariants(new ArrayList<>(varaintsToSave));

                if (variantSaveStatus) {
                    msgType = 2;
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return msgType;
    }

    private void mergeChanges(final Variants source, final Variants target) {
        /* following check will append remarks data instead of updating it 
           - If the new remarks data is empty it will display the existing remarks data
         */
        String existingRemarksData = (null != target.getRemarks()) ? target.getRemarks() : "";
        //if the new remarks value is not empty/null it will get appended to the existing value
        if ((null != source.getRemarks()) && (!source.getRemarks().isEmpty())) {
            if (!existingRemarksData.isEmpty()) {
                boolean remarkExists = existingRemarksData.contains(source.getRemarks());
                //if new remark exists it won't get appended
                if (!remarkExists) {
                    String newRemarksData = existingRemarksData.concat("; ").concat(source.getRemarks());
                    target.setRemarks(newRemarksData);
                }
            }else{
                target.setRemarks(source.getRemarks());
            }
        } else {
            target.setRemarks(existingRemarksData);
        }
        target.setAaChange(source.getAaChange());
        target.setCodonChange(source.getCodonChange());
        target.setCodonNr(source.getCodonNr());
        target.setCodonNrEColi(source.getCodonNrEColi());
        target.setDir(source.getDir());
        target.setGeneId(source.getGeneId());
        target.setGeneLength(source.getGeneLength());
        target.setGeneName(source.getGeneName());
        target.setGeneStart(source.getGeneStart());
        target.setGeneStop(source.getGeneStop());
        target.setNumber(source.getNumber());
        target.setRegion(source.getRegion());
        target.setVarAa(source.getVarAa());
        target.setVarBase(source.getVarBase());
        target.setVarPositionGeneStart(source.getVarPositionGeneStart());
        target.setVarPositionGeneStop(source.getVarPositionGeneStop());
        target.setVarPositionGenomeStart(source.getVarPositionGenomeStart());
        target.setVarPositionGenomeStop(source.getVarPositionGenomeStop());
        target.setVarType(source.getVarType());
        target.setWtAa(source.getWtAa());
        target.setWtBase(source.getWtBase());
    }

    private boolean write2tblVariants(List<Variants> dataList) throws Exception {
        boolean saveStatus;
        saveStatus = iVariant.save(dataList);
        if (saveStatus) {
            LOGGER.info("Variant data saved successfully to DB");
            //updating null values to 0 if exists for high_confidence in drug resistance table
            iDrugResistance.updateNullValues();
        } else {
            LOGGER.error("Variant data did NOT save successfully to DB");
        }
        return saveStatus;
    }

    private boolean checkExist(String filePath, String fileName) {
        File f = new File(filePath + "/" + fileName);
        boolean isPathExist = false;
        if (f.exists() && !f.isDirectory()) {
            isPathExist = true;
        }
        return isPathExist;
    }

    private Map<String, Drugs> readDrugInfo() {
        Map<String, Drugs> drugNameMap = new HashMap<>();
        List<Drugs> drugs = iDrug.fetchAllDrugs();

        for (Drugs drugsObj : drugs) {
            drugNameMap.put(drugsObj.getName(), drugsObj);
        }

        return drugNameMap;
    }

    private List<Variants> fetchAllVariants() throws TBDRException {
        return iVariant.fetchAllData();
    }

    private Object readCellValues(XSSFCell cell) {
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC:
                Double d = cell.getNumericCellValue();
                long l = d.longValue();
                return l;
            case XSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case XSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case XSSFCell.CELL_TYPE_BLANK:
                return "";
            default:
                return cell.getRawValue();
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
