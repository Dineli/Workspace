<%-- 
    Document   : view
    Created on : Dec 12, 2016, 11:12:46 AM
    Author     : Dineli
--%>

<%@page import="com.nus.tbdr.entity.DrugResistance"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.tbdr.service.impl.DrugResistanceServiceImpl"%>
<%@page import="com.nus.tbdr.service.IDrugResistance"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    IDrugResistance iDrugResistance = new DrugResistanceServiceImpl();
    List<DrugResistance> drList = iDrugResistance.fetchAllData();
%>

<% if ((null != drList) && (!drList.isEmpty())) {%>
<div class="table-responsive">          
    <table id="hor-zebra" class="table-custom-main" align="center">
        <thead>
            <tr>
                <th>Variant</th>
                <th class="content-align-left">Drug</th>
                <th class="content-align-left">Data Source</th>
                <th>Reference PMID</th>
                <th>High Confidence</th>
            </tr>
        </thead>    
        <tbody>
            <% for (DrugResistance dr : drList) {%>  
            <% String hc = (dr.getHighConfidence() != null) ? "Yes" : "-";
                String refPMID = (dr.getReferencePmid() == 0) ? "-" : dr.getReferencePmid().toString();
            %>
            <tr>
                <td class="col-md-1"></td>
                <td class="col-md-1 content-align-left"><%= dr.getDrugId().getName()%></td>
                <td class="col-md-1 content-align-left"><%= dr.getDataSourceId().getName()%></td>
                <td class="col-md-1"><%= refPMID%></td>
                <td class="col-md-1"><%= hc%></td>
            </tr>
            <%}%>
        </tbody>
    </table>
</div>
<div class="tfootData"></div>
<% } else {%>
<div class="alert alert-danger" style="margin-top: 10px; overflow: hidden;">
    <strong>No Drug resistance Data to Display</strong> 
</div>
<% }%>

<script type="text/javascript">
    $(document).ready(function () {
        $('.table-custom-main').paging({limit: 10});
    });
</script>