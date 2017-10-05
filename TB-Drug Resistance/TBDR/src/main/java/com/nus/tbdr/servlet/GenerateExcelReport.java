/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.tbdr.servlet;

import com.nus.tbdr.entity.DataSources;
import com.nus.tbdr.entity.Drugs;
import com.nus.tbdr.exception.TBDRException;
import com.nus.tbdr.helper.FetchValues;
import com.nus.tbdr.service.IVariant;
import com.nus.tbdr.service.impl.VariantServiceImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dineli
 */
@WebServlet(name = "GenerateExcelReport", urlPatterns = {"/GenerateExcelReport"})
public class GenerateExcelReport extends HttpServlet {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GenerateExcelReport.class);
    IVariant variantService = new VariantServiceImpl();
    FetchValues fv = new FetchValues();

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

        String searchOpt = request.getParameter("searchOption");
        List<Object[]> reportValueList = null;
        int rowIndex = 1;
        List<String> headers = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        try {
            if (searchOpt.equals("1")) {
                String selectedDrugId = request.getParameter("excelDrugId");
                String selectedDSId = request.getParameter("excelDSId");
                String selectedGene = request.getParameter("excelGene");

                reportValueList = variantService.searchQueryBuilder(selectedDrugId, selectedDSId, selectedGene);
            } else if (searchOpt.equals("2")) {
                int selection1 = Integer.valueOf(request.getParameter("selection1"));
                int selection2 = Integer.valueOf(request.getParameter("selection2"));
                int selection3 = Integer.valueOf(request.getParameter("selection3"));
                int selection4 = Integer.valueOf(request.getParameter("selection4"));
                int selection5 = Integer.valueOf(request.getParameter("selection5"));
                String value1 = request.getParameter("value1");
                String value2 = request.getParameter("value2");
                String value3 = request.getParameter("value3");
                String value4 = request.getParameter("value4");
                String value5 = request.getParameter("value5");
                String condition1 = request.getParameter("condition1");
                String condition2 = request.getParameter("condition2");
                String condition3 = request.getParameter("condition3");
                String condition4 = request.getParameter("condition4");

                reportValueList = variantService.searchQueryBuilder(selection1, value1, condition1, selection2, value2, condition2, selection3, value3, condition3, selection4, value4, condition4, selection5, value5);
            }
            Map<Integer, Drugs> drugNameMap = fv.readDrugName();
            Map<Integer, DataSources> dsMap = fv.readDsName();

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet(" Sheet 1 ");

            //column header generation
            String[] coulmnHeaders = {"Var. Position Genome Start", "Var. Position Genome Stop", "Var. Type", "Number", "WT base", "Var. base", "Region", "Gene ID", "Gene Name", "Gene start",
                "Gene stop", "Gene length", "Dir.", "WT AA", "Codon nr.", "Codon nr. E. coli", "Var. AA", "AA change", "Codon change", "Variant position gene start", "Variant position gene stop",
                "Remarks", "Drug", "Data Source", "HighConfident", "Reference PMID"};
            headers.addAll(Arrays.asList(coulmnHeaders));

            Row rowHeader = spreadsheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                rowHeader.createCell(i).setCellValue(headers.get(i));
            }

            //cell value generation
            for (Object[] value : reportValueList) {
                String stringHC = "";
                String stringDsNames = "";
                String codonNr = (0 != Integer.valueOf(value[15].toString())) ? value[15].toString() : "-";
                String codonNrEColi = (0 != Integer.valueOf(value[16].toString())) ? value[16].toString() : "-";
                String refPMID = (Integer.valueOf(value[26].toString()) == 0) ? "-" : value[26].toString();
                String remarks = (null != value[22]) ? value[22].toString() : "";
                int drugID = Integer.valueOf(value[23].toString());
                Drugs drugobj = drugNameMap.get(drugID);
                String hcListWithCommas = (null != value[25]) ? value[25].toString() : "";
                String dsListWithCommas = value[24].toString();
                String[] dsListWithoutCommas = dsListWithCommas.split(",");

                //generats comma seperated data source name list from DS id list
                if (dsListWithoutCommas.length > 1) {
                    for (String dsListWithoutComma : dsListWithoutCommas) {
                        DataSources dsObj = dsMap.get(Integer.valueOf(dsListWithoutComma));
                        stringDsNames += dsObj.getName().concat(",");
                    }
                } else {
                    int dsID = Integer.valueOf(value[24].toString());
                    DataSources dsObj = dsMap.get(dsID);
                    stringDsNames = dsObj.getName();
                }
                stringDsNames = stringDsNames.substring(0, stringDsNames.length() - 1);

                //generats comma seperated high confidence value list from int HC list
                if (!hcListWithCommas.isEmpty()) {
                    String[] hcListWithoutCommas = hcListWithCommas.split(",");
                    if (hcListWithoutCommas.length > 1) {
                        for (String hcListWithoutComma : hcListWithoutCommas) {
                            String hcValue = (hcListWithoutComma.equals("1")) ? "Yes" : "-";
                            stringHC += hcValue.concat(",");
                        }
                    } else {
                        String hcValue = (value[25].equals("1")) ? "Yes" : "-";
                        stringHC += hcValue.concat(",");
                    }
                    stringHC = stringHC.substring(0, stringHC.length() - 1);
                } else {
                    stringHC = "-";
                }

                Row row = spreadsheet.createRow(rowIndex++);
                int cellIndex = 0;

                row.createCell(cellIndex++).setCellValue(value[1].toString());
                row.createCell(cellIndex++).setCellValue(value[2].toString());
                row.createCell(cellIndex++).setCellValue(value[3].toString());
                row.createCell(cellIndex++).setCellValue(value[4].toString());
                row.createCell(cellIndex++).setCellValue(value[5].toString());
                row.createCell(cellIndex++).setCellValue(value[6].toString());
                row.createCell(cellIndex++).setCellValue(value[7].toString());
                row.createCell(cellIndex++).setCellValue(value[8].toString());
                row.createCell(cellIndex++).setCellValue(value[9].toString());
                row.createCell(cellIndex++).setCellValue(value[10].toString());
                row.createCell(cellIndex++).setCellValue(value[11].toString());
                row.createCell(cellIndex++).setCellValue(value[12].toString());
                row.createCell(cellIndex++).setCellValue(value[13].toString());
                row.createCell(cellIndex++).setCellValue(value[14].toString());
                row.createCell(cellIndex++).setCellValue(codonNr);
                row.createCell(cellIndex++).setCellValue(codonNrEColi);
                row.createCell(cellIndex++).setCellValue(value[17].toString());
                row.createCell(cellIndex++).setCellValue(value[18].toString());
                row.createCell(cellIndex++).setCellValue(value[19].toString());
                row.createCell(cellIndex++).setCellValue(value[20].toString());
                row.createCell(cellIndex++).setCellValue(value[21].toString());
                row.createCell(cellIndex++).setCellValue(remarks);
                row.createCell(cellIndex++).setCellValue(drugobj.getName());
                row.createCell(cellIndex++).setCellValue(stringDsNames);
                row.createCell(cellIndex++).setCellValue(stringHC);
                row.createCell(cellIndex++).setCellValue(refPMID);
            }

            Calendar cal = Calendar.getInstance();
            String fileName = "Mtb Drug Resistance Report-".concat(dateFormat.format(cal.getTime())).concat(".xlsx");

            try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
                workbook.write(outputStream);
                outputStream.close();
                downloadFile(fileName, response);

            }
        } catch (TBDRException ex) {
            Logger.getLogger(GenerateExcelReport.class.getName()).log(Level.SEVERE, null, ex);
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

    private void downloadFile(final String fileName, final HttpServletResponse response) {
        try {
            final File f = new File(fileName);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(f);
            } catch (final FileNotFoundException e) {
            }
            final int size = 1024;
            try {
                response.setContentLength(fin.available());
                final byte[] buffer = new byte[size];
                ServletOutputStream os = null;

                os = response.getOutputStream();
                int length = 0;
                while ((length = fin.read(buffer)) != -1) {
                    os.write(buffer, 0, length);
                }
                fin.close();
                os.flush();
                os.close();
            } catch (final IOException e) {
                e.getMessage();
            }
        } catch (final Exception ex) {
            LOGGER.error("Error downloading excel file ::: {}", ex.getMessage());
        }
    }

    public static void makeRowBold(Workbook wb, Row row) {
        CellStyle style = wb.createCellStyle();//Create style
        Font font = wb.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);//set it to bold
        System.out.println("===========row.getLastCellNum()========== " + row.getLastCellNum());
        for (int i = 0; i < row.getLastCellNum(); i++) {//For each cell in the row 
            row.getCell(i).setCellStyle(style);//Set the sty;e
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
