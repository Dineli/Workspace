<%-- 
    Document   : accessionData
    Created on : Feb 29, 2016, 5:42:54 PM
    Author     : Dineli
--%>

<%@page import="com.nus.mtc.entity.Accessions"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.mtc.service.impl.SampleServiceImpl"%>
<%@page import="com.nus.mtc.service.ISampleService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    ISampleService iSampleService = new SampleServiceImpl();

    String sampleId = request.getParameter("sampleId");
    List<Accessions> accessionData = iSampleService.getAccessionDataBySampleId(sampleId);

%>


<%if (null != accessionData && accessionData.size() > 0) {%>
<table class="table table-hover table-striped table-condensed table-custom-modal table-custom-modal-rounded">
    <thead>
        <tr>
            <th>Accession ID</th>
            <th>Run Data</th>
            <th>URL 1</th>
            <th>URL 2</th>
        </tr>
    </thead>
    <tbody>
        <%for (Accessions accession : accessionData) {%>
        <%
            String repId = (null != accession.getSampleReplicationId()) ? accession.getSampleReplicationId().getId() : "-";
        %>
        <tr>
            <td><%= accession.getId()%></td>
            <td><%= repId%></td>
            <td style="word-break: break-all;"><a href="http://<%= accession.getUrl1()%>" target="blank"><%= accession.getUrl1()%></a></td>
            <td style="word-break: break-all;"><a href="http://<%= accession.getUrl2()%>" target="blank"><%= accession.getUrl2()%></a></td>
        </tr>
        <%}%>
    </tbody>
</table>
<% } else {%>
<div class="alert alert-danger">
    <strong> No Data to Display</strong> 
</div>
<%}%>


