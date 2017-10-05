<%-- 
    Document   : view
    Created on : Dec 19, 2016, 9:43:18 AM
    Author     : Dineli
--%>

<%@page import="com.nus.tbdr.helper.FetchValues"%>
<%@page import="com.nus.tbdr.entity.Variants"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.tbdr.service.impl.VariantServiceImpl"%>
<%@page import="com.nus.tbdr.service.IVariant"%>
<%@page import="com.nus.tbdr.entity.DataSources"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.nus.tbdr.entity.Drugs"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    IVariant variantService = new VariantServiceImpl();
    FetchValues fv = new FetchValues();
    List<Object[]> resultList = null;
    String searchOpt = request.getParameter("searchOption");
    String drugIds = null;
    String dsIds = null;
    String geneNames = null;
    String value1 = null;
    String value2 = null;
    String value3 = null;
    String value4 = null;
    String value5 = null;
    String condition1 = null;
    String condition2 = null;
    String condition3 = null;
    String condition4 = null;
    int selection1 = 0;
    int selection2 = 0;
    int selection3 = 0;
    int selection4 = 0;
    int selection5 = 0;

    Map<Integer, Drugs> drugNameMap = fv.readDrugName();
    Map<Integer, DataSources> dsMap = fv.readDsName();

    if (searchOpt.equals("1")) {
        drugIds = request.getParameter("selectedDrugId");
        dsIds = request.getParameter("selectedDSId");
        geneNames = request.getParameter("selectedGene");

        resultList = variantService.searchQueryBuilder(drugIds, dsIds, geneNames);
    } else if (searchOpt.equals("2")) {
        selection1 = Integer.valueOf(request.getParameter("selection1"));
        selection2 = Integer.valueOf(request.getParameter("selection2"));
        selection3 = Integer.valueOf(request.getParameter("selection3"));
        selection4 = Integer.valueOf(request.getParameter("selection4"));
        selection5 = Integer.valueOf(request.getParameter("selection5"));
        value1 = request.getParameter("selectedValue4opt1");
        value2 = request.getParameter("selectedValue4opt2");
        value3 = request.getParameter("selectedValue4opt3");
        value4 = request.getParameter("selectedValue4opt4");
        value5 = request.getParameter("selectedValue4opt5");
        condition1 = request.getParameter("andOr1");
        condition2 = request.getParameter("andOr2");
        condition3 = request.getParameter("andOr3");
        condition4 = request.getParameter("andOr4");

        resultList = variantService.searchQueryBuilder(selection1, value1, condition1, selection2, value2, condition2, selection3, value3, condition3, selection4, value4, condition4, selection5, value5);
    }
%>

<% if ((null != resultList) && (!resultList.isEmpty())) {%>
<div class="excel-header">
    <%if (searchOpt.equals("1")) {%>
    <a data-toggle="tooltip" title="Download Excel" href="GenerateExcelReport?searchOption=1&amp;excelDrugId=<%= drugIds%>&amp;excelDSId=<%= dsIds%>&amp;excelGene=<%= geneNames%>"><img  src="images/excel.png" alt="excel report" height="42" width="42"></a>
        <%} else {%>
    <a data-toggle="tooltip" title="Download Excel" href="GenerateExcelReport?searchOption=2&amp;selection1=<%= selection1%>&amp;selection2=<%= selection2%>&amp;selection3=<%= selection3%>&amp;selection4=<%= selection4%>&amp;selection5=<%= selection5%>&amp;value1=<%= value1%>&amp;value2=<%= value2%>&amp;value3=<%= value3%>&amp;value4=<%= value4%>&amp;value5=<%= value5%>&amp;condition1=<%= condition1%>&amp;condition2=<%= condition2%>&amp;condition3=<%= condition3%>&amp;condition4=<%= condition4%>"><img  src="images/excel.png" alt="excel report" height="42" width="42"></a>
        <%}%>
</div>
<div class="table-responsive">          
    <table id="hor-zebra" class="table-custom-main tablesorter" align="center">
        <thead>
            <tr>
                <th>Var. Position Genome Start</th>
                <th>Var. Position Genome Stop</th>
                <th class="show-full-text">Var. Type</th>
                <th>Number</th>
                <th class="show-full-text">WT base</th>
                <th class="show-full-text">Var. base</th>
                <th>Region</th>
                <th>Gene ID</th>
                <th>Gene Name</th>
                <th>Gene start</th>
                <th>Gene stop</th>
                <th>Gene length</th>
                <th>Dir.</th>
                <th class="show-full-text">WT AA</th>
                <th class="show-full-text">Codon nr.</th>
                <th>Codon nr. E. coli</th>
                <th class="show-full-text">Var. AA</th>
                <th>AA change</th>
                <th>Codon change</th>
                <th>Variant position gene start</th>
                <th>Variant position gene stop</th>
                <th>Remark</th>
                <th>Drug</th>
                <th>Data Source</th>
                <th>High Confidence</th>
                <th>Reference PMID</th>
            </tr>
        </thead>    
        <tbody>
            <%  for (Object[] data : resultList) {
                    String stringDsNames = "";
                    String stringHC = "";
                    String codonNr = (0 != Integer.valueOf(data[15].toString())) ? data[15].toString() : "-";
                    String codonNrEColi = (0 != Integer.valueOf(data[16].toString())) ? data[16].toString() : "-";
                    String remarks = (null != data[22]) ? data[22].toString() : "";
                    String refPMID = (Integer.valueOf(data[26].toString()) == 0) ? "-" : data[26].toString();
                    int drugID = Integer.valueOf(data[23].toString());
                    Drugs drugobj = drugNameMap.get(drugID);
                    String dsListWithCommas = data[24].toString();
                    String hcListWithCommas = (null != data[25]) ? data[25].toString() : "";
                    String[] dsListWithoutCommas = dsListWithCommas.split(",");

                    //generates comma seperated data source name list
                    if (dsListWithoutCommas.length > 1) {
                        for (String dsListWithoutComma : dsListWithoutCommas) {
                            DataSources dsObj = dsMap.get(Integer.valueOf(dsListWithoutComma));
                            stringDsNames += dsObj.getName().concat(",");
                        }
                        //removes the trailing comma
                        stringDsNames = stringDsNames.substring(0, stringDsNames.length() - 1);
                    } else {
                        int dsID = Integer.valueOf(data[24].toString());
                        DataSources dsObj = dsMap.get(dsID);
                        stringDsNames = dsObj.getName();
                    }

                    //generates comma seperated high confidence value list
                    if (!hcListWithCommas.isEmpty()) {
                        String[] hcListWithoutCommas = hcListWithCommas.split(",");
                        if (hcListWithoutCommas.length > 1) {
                            for (String hcListWithoutComma : hcListWithoutCommas) {
                                String hcValue = (hcListWithoutComma.equals("1")) ? "Yes" : "-";
                                stringHC += hcValue.concat(",");
                            }
                        } else {
                            String hcValue = (data[25].equals("1")) ? "Yes" : "-";
                            stringHC += hcValue.concat(",");
                        }
                        //removes the trailing comma
                        stringHC = stringHC.substring(0, stringHC.length() - 1);
                    } else {
                        stringHC = "-";
                    }
            %>
            <tr>
                <td class="col-md-1 content-align-right"><%= data[1]%></td>
                <td class="col-md-1 content-align-right"><%= data[2]%></td>
                <td class="col-md-1"><%= data[3]%></td>
                <td class="col-md-1"><%= data[4]%></td>
                <td class="col-md-1 content-align-left"><%= data[5]%></td>
                <td class="col-md-1 content-align-left"><%= data[6]%></td>
                <td class="col-md-1 content-align-left"><%= data[7]%></td>
                <td class="col-md-1 content-align-left"><%= data[8]%></td>
                <td class="col-md-1 content-align-left"><%= data[9]%></td>
                <td class="col-md-1 content-align-right"><%= data[10]%></td>
                <td class="col-md-1 content-align-right"><%= data[11]%></td>
                <td class="col-md-1 content-align-right"><%= data[12]%></td>
                <td class="col-md-1"><%= data[13]%></td>
                <td class="col-md-1 content-align-left"><%= data[14]%></td>
                <td class="col-md-1 content-align-right"><%= codonNr%></td>
                <td class="col-md-1 content-align-right"><%= codonNrEColi%></td>
                <td class="col-md-1 content-align-left"><%= data[17]%></td>
                <td class="col-md-1 content-align-left"><%= data[18]%></td>
                <td class="col-md-1 content-align-left"><%= data[19]%></td>
                <td class="col-md-1 content-align-right"><%= data[20]%></td>
                <td class="col-md-1 content-align-right"><%= data[21]%></td>
                <td class="col-md-1 content-align-left show-full-text"><%= remarks%></td>
                <td class="col-md-1 content-align-left show-full-text"><%= drugobj.getName()%></td>
                <td class="col-md-1 content-align-left show-full-text"><%= stringDsNames%></td>
                <td class="col-md-1 content-align-left"><%= stringHC%></td>
                <td class="col-md-1 content-align-right"><%= refPMID%></td>
            </tr>
            <%}%>
        </tbody>
    </table>
    <div class="tfootData"></div>  
</div>
<% } else {%>
<div class="alert alert-danger" style="margin-top: 10px; overflow: hidden;">
    <strong>No Data to Display</strong> 
</div>
<% }%>

<script type="text/javascript">

    $(document).ready(function () {
        //bSortable: disabling the sorting option for unwanted columns           
        //bFilter: removes the search option
        $('.table-custom-main').DataTable({
            bFilter: false,
            "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 22, 23, 24, 25]
                }],
        });

        $('[data-toggle="tooltip"]').tooltip();
    });
</script>