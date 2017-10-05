<%-- 
    Document   : base
    Created on : Jun 9, 2017, 10:43:51 AM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.util.Constants"%>
<%@page import="com.nus.pgdb.util.Utility"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.pgdb.entity.PipelineJobs"%>
<%@page import="com.nus.pgdb.service.impl.PipelineJobServiceImpl"%>
<%@page import="com.nus.pgdb.service.IPipelineJob"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    int userId = (Integer) session.getAttribute("userId");
    IPipelineJob pipelineJobService = new PipelineJobServiceImpl();
    List<PipelineJobs> jobList = pipelineJobService.fetchPipelineDataByUserId(userId);
%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Your Analyses </p> 
        <p class="w3-right"><button class="btnRefresh w3-button w3-padding-small w3-circle w3-black" onclick="goTo('analysis/base.jsp');"><i class="fa fa-refresh" aria-hidden="true"></i></button></p>
    </div>
    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p style="width: 400px;"> </p>
        </div>
    </div>
    <% if ((null != jobList) && (!jobList.isEmpty())) { %>
    <div class="w3-panel w3-leftbar ">
        <div class="w3-container">
            <table class="tbl-dispaly">
                <thead>
                    <tr>
                        <th>State</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Date Created</th>
                        <th class="w3-center">Download</th>
                        <th class="w3-center">Delete</th>
                    </tr>
                </thead>
                <% for (PipelineJobs job : jobList) {%>
                <%
                    String status = (job.getStatus().equals(Constants.SUBMIT)) ? "Submitting" : (job.getStatus().equals(Constants.EXECUTE)) ? "In Execution" : job.getStatus().equals(Constants.SUCCESS) ? "Downloading Results" : job.getStatus().equals(Constants.DOWNLOADED) ? "Successful" : "Failed";
                    String statusClr = (job.getStatus().equals(Constants.SUBMIT)) ? "blue" : (job.getStatus().equals(Constants.EXECUTE)) ? "purple" : job.getStatus().equals(Constants.SUCCESS) ? "#ff8000" : job.getStatus().equals(Constants.DOWNLOADED) ? "green" : "red";
                %>
                <tr>
                    <td class="jobStatus" style="color: <%= statusClr%>;"> <%= status%></td>
                    <td><%= job.getName()%></td>
                    <td><%= job.getType()%></td> 
                    <td><%= Utility.getDate(job.getCreatedDate(), "yyyy-MM-dd HH:mm:ss")%></td>
                    <%if (job.getStatus().equals(Constants.DOWNLOADED)) {%>
                    <td class="w3-center"><a href="JobAnalysis?file=<%= job.getOutputPath()%>&subAction=downloadFile"><i class="fa fa-download" aria-hidden="true"></i></a></td>
                            <%} else {%>
                    <td class="w3-center">-</td>
                    <%}%>
                    <td class="w3-center"><a href="#" class="deleteJob" pipelineName="<%= job.getName()%>" pipelineType="<%= job.getType()%>" recordId="<%= job.getId()%>" inputPath="<%= job.getInputPath()%>" outputPath="<%= job.getOutputPath()%>"><i class="fa fa-trash" aria-hidden="true"></i></a></td>
                </tr>
                <%}%>
            </table>
            <div class="tfootData"></div>  
        </div>
    </div>
    <%} else {%>
    <div class="w3-panel w3-border w3-light-grey w3-round-large">
        <p>Currently no analysis data available.</p>
    </div>
    <%}%>
</div>

<script>

    $('.tbl-dispaly').DataTable({
        bFilter: false,
        "aoColumnDefs": [{
                'bSortable': false,
                'aTargets': [1, 2, 3, 4, 5]
            }]
    })

    checkJobStatus();

    function checkJobStatus() {
        $.ajax({
            type: "POST",
            url: "JobAnalysis",
            data: {subAction: "updateJobStatus"},
            success: function (data) {
            }
        });
    }

    $('.tbl-dispaly').on('click', '.deleteJob', function () {
        var name = $(this).attr("pipelineName");
        var type = $(this).attr("pipelineType");
        var recordId = $(this).attr("recordId");
        var inputPath = $(this).attr("inputPath");
        var outputPath = $(this).attr("outputPath");

        $.confirm({
            title: 'Delete Confirmation!',
            content: 'Are you sure to delete this record?',
            useBootstrap: false,
            boxWidth: '30%',
            icon: 'fa fa-warning',
            buttons: {
                confirm: function () {
                    $.ajax({
                        type: "POST",
                        url: "JobAnalysis",
                        data: {recordId: recordId, pipelineName: name, pipelineType: type, absoluteInputPath: inputPath, absoluteOutputPath: outputPath, subAction: "deleteFile"},
                        success: function (data) {
                            $(".msg").show().html(data);
                            var response = $('.msg div').attr("class");
                            if (response.includes("alert-success")) {
                                $(".msg").html(data).show("fast").delay(1000).hide("slow", function () {
                                    goTo('analysis/base.jsp');
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
                },
                cancel: function () {
                }
            }
        });
    });


</script>

