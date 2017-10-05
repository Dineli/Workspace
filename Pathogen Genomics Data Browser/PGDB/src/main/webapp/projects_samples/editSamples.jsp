<%-- 
    Document   : editSamples
    Created on : Jul 18, 2017, 1:28:27 PM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.entity.Organisms"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.pgdb.service.impl.SampleProjectServiceImpl"%>
<%@page import="com.nus.pgdb.service.ISampleProject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    int projectId = Integer.valueOf(request.getParameter("projectId"));
    String sampleId = request.getParameter("recordId");
    String sampleName = request.getParameter("nameParam");
    String projectName = request.getParameter("projectName");
    String organismName = request.getParameter("orgNameParam");

    ISampleProject iSampleProjectService = new SampleProjectServiceImpl();
    List<Organisms> organismsList = iSampleProjectService.fetchAllOrganismService();

%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Update Sample</p>
    </div>
    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p>ccc </p>
        </div>
    </div>

    <div class="w3-container">
        <table class="tbl-add-new">
            <tr>
                <td><label>Name :</label></td>
                <td><input class="w3-input editSampName" type="text" value="<%= sampleName%>"></td>
            </tr>
            <tr>
                <td><label>Organism :</label></td>
                <td>
                    <select class="w3-container editOrgSelect" style="width: 75%">
                        <%for (Organisms organism : organismsList) {%>
                        <option class="w3-bar-item w3-button" <%if (organismName.equals(organism.getName())) {%> selected <%}%> value="<%= organism.getId()%>"><%= organism.getName()%></option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td></td>
                <td>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnUpdate">UPDATE</button>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnClear" onclick="goBack('projects_samples/base.jsp', '<%= projectId%>', '<%= projectName%>', '');">BACK</button>
                </td>
            </tr>
        </table>
    </div>
</div>

<script>

    $('.btnUpdate').on('click', function () {
        var editSamName = $(".editSampName").val();
        var editOrganismValue = $(".editOrgSelect").val();
        var validateStatus = validateSample(editSamName, editOrganismValue);
        if (validateStatus) {
            $.ajax({
                type: "POST",
                url: "DataMaintenance",
                data: {recordId: '<%= sampleId%>', editNameParam: editSamName, editOrgParam: editOrganismValue, subAction: "sample-update"},
                success: function (data) {
                    $(".msg").show().html(data);
                    var response = $('.msg div').attr("class");
                    if (response.includes("alert-success")) {
                        $(".msg").html(data).show("fast").delay(1000).hide("slow", function () {
                            goBack('projects_samples/base.jsp', '<%= projectId%>', '<%= projectName%>', '');
                        });
                    }
                },
                complete: function () {
                    hidePagePreload();
                },
                beforeSend: function () {
                    $("#preloader").html('');
                    showPagePreload();
                }
            });
        }
    });


</script>                
