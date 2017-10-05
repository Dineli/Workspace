<%-- 
    Document   : generateDropdown
    Created on : Feb 21, 2017, 5:01:38 PM
    Author     : Dineli
--%>
<%@page import="com.nus.tbdr.service.impl.VariantServiceImpl"%>
<%@page import="com.nus.tbdr.service.IVariant"%>
<%@page import="com.nus.tbdr.service.impl.DataSourceServiceImpl"%>
<%@page import="com.nus.tbdr.entity.DataSources"%>
<%@page import="com.nus.tbdr.service.IDataSource"%>
<%@page import="com.nus.tbdr.entity.Drugs"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.tbdr.service.impl.DrugServiceImpl"%>
<%@page import="com.nus.tbdr.service.IDrug"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String option = request.getParameter("selectedOption");
    String count = request.getParameter("count");

    IDrug idrugService = new DrugServiceImpl();
    IDataSource dsService = new DataSourceServiceImpl();
    IVariant ivariantService = new VariantServiceImpl();

    List<Drugs> drugList = idrugService.fetchAllDrugs();
    List<DataSources> dsList = dsService.fetchAllDataSources();
    List<String> geneNameList = ivariantService.fetchGeneNames();

%>

<table class="tblDropDowns">
    <tr>
        <%if (option.equals("1")) {%>
        <td> 
            <select class="form-control drugSelection<%=count%>" id="sel1">
                <% for (Drugs drug : drugList) {%>
                <option value="<%= drug.getId()%>"><%= drug.getName()%></option>
                <% }%>
            </select>
        </td>
        <%} else if (option.equals("2")) {%>
        <td> 
            <select class="form-control geneSelection<%=count%>" id="sel1">
                <% for (String gene : geneNameList) {%>
                <option value="<%= gene%>"><%= gene%></option>
                <% } %>
            </select>
        </td>
        <%} else if (option.equals("3")) {%>
        <td> 
            <select class="form-control dsSelection<%=count%>" id="sel1">
                <% for (DataSources ds : dsList) {%>
                <option value="<%= ds.getId()%>"><%= ds.getName()%></option>
                <% }%>
            </select>
        </td>
        <%}%>
    </tr>
</table>


