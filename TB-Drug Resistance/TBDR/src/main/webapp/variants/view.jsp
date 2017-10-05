<%-- 
    Document   : view
    Created on : Dec 21, 2016, 6:56:58 PM
    Author     : Dineli
--%>

<%@page import="com.nus.tbdr.helper.FetchValues"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.nus.tbdr.entity.Variants"%>
<%@page import="com.nus.tbdr.entity.Drugs"%>
<%@page import="com.nus.tbdr.entity.DataSources"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.tbdr.service.impl.VariantServiceImpl"%>
<%@page import="com.nus.tbdr.service.IVariant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    IVariant ivariantService = new VariantServiceImpl();
    FetchValues fv = new FetchValues();
    Map<Integer, Drugs> drugNameMap = fv.readDrugName();
    Map<Integer, DataSources> dsMap = fv.readDsName();

    List<Object[]> variantDrList = ivariantService.fetchAll();
%>

<% if ((null != variantDrList) && (!variantDrList.isEmpty())) {%>
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
            <%  for (Object[] data : variantDrList) {
                    String stringDsNames = "";
                    String stringHC = "";
                    String codonNr = (0 != Integer.valueOf(data[15].toString())) ? data[15].toString() : "-";
                    String codonNrEColi = (0 != Integer.valueOf(data[16].toString())) ? data[16].toString() : "-";
                    String remarks = (null != data[22]) ? data[22].toString() : "";
                    String refPMID = (Integer.valueOf(data[26].toString()) == 0) ? "-" : data[26].toString();
                    int drugId = Integer.valueOf(data[23].toString());
                    Drugs drugobj = drugNameMap.get(drugId);
                    String hcListWithCommas = (null != data[25]) ? data[25].toString() : "";
                    String dsListWithCommas = data[24].toString();
                    String[] dsListWithoutCommas = dsListWithCommas.split(",");

                    //generats comma seperated data source name list
                    if (dsListWithoutCommas.length > 1) {
                        for (String dsListWithoutComma : dsListWithoutCommas) {
                            DataSources dsObj = dsMap.get(Integer.valueOf(dsListWithoutComma));
                            stringDsNames += dsObj.getName().concat(",");
                        }
						stringDsNames = stringDsNames.substring(0, stringDsNames.length() - 1);
                    } else {
                        int dsId = Integer.valueOf(data[24].toString());
                        DataSources dsObj = dsMap.get(dsId);
                        stringDsNames = dsObj.getName();
                    }
                    

                    //generats comma seperated high confidence value list
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
        //disabling the sorting option for unwanted columns
        $('.table-custom-main').DataTable({
            "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 22, 23, 24, 25]
                }]
        });
    });
</script>

