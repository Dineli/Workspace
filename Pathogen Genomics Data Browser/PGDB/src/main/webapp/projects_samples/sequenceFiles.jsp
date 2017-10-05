<%-- 
    Document   : sequenceFiles
    Created on : May 29, 2017, 11:16:58 AM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.util.Utility"%>
<%@page import="java.io.File"%>
<%@page import="com.nus.pgdb.entity.SamplesSequenceFiles"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.pgdb.service.impl.SampleSequenceServiceImpl"%>
<%@page import="com.nus.pgdb.service.ISampleSequence"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String sampleName = request.getParameter("sampleName");
    String sampleId = request.getParameter("sampleId");
    String selectedProjectName = request.getParameter("projectName");
    String projectType = request.getParameter("prjType");
    int selectedProjectId = Integer.valueOf(request.getParameter("projectId"));
    int userId = (Integer) session.getAttribute("userId"); 
    int count = 0;
    //status to show records belong to shared projects irrespective of the userId
    boolean status = (projectType.isEmpty())? false : true; 

    ISampleSequence iSampleSequence = new SampleSequenceServiceImpl();
    List<SamplesSequenceFiles> sampleSeqList = iSampleSequence.fetchSequenceFilesBySampleIdService(sampleId, userId, status);
%>

<div class="w3-container w3-table w3-animate-left">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large"><%= sampleName%> <%if (!projectType.isEmpty()) {%><span style="text-shadow:1px 1px 0 #444">[ Shared Project ]</span> <%}%></p>
         <%if (projectType.isEmpty()) {%>
         <p class="w3-right"><input type="file" id="fileUpload" style="display:none" onchange="selection(this);"/><button id="OpenFileUpload" class="w3-button w3-padding-small w3-circle w3-black"><i class="fa fa-upload" aria-hidden="true"></i></button></p>
         <%}%>
    </div>
    <div id="preloader"></div>
    <div class="w3-cell-row uploadProgress" style="display: none;">
        <div class="w3-container w3-cell">
            <p style="width: 400px;"></p>
        </div>
    </div>

    <% if ((null != sampleSeqList) && (!sampleSeqList.isEmpty())) { %>
    <div class="w3-panel w3-pale-blue w3-leftbar w3-rightbar w3-border-blue">
        <div class="w3-container">
            <table class="tbl-dispaly dataTable"> <!-- included dataTable class to apply table styles    -->
                <thead>
                    <tr>
                        <th></th>
                        <th>File</th>
                        <th class="w3-left">Created On</th>
                         <th class="w3-center">Download</th>
                            <%if (projectType.isEmpty()) {%>
                        <th class="w3-center">Delete</th>
                            <%}%>
                       
                    </tr>
                </thead>
                <% for (SamplesSequenceFiles sampleSeqObj : sampleSeqList) {%>
                <%
                    File file = new File(sampleSeqObj.getSequenceFileId().getFilePath());
                    count = count + 1;
                    String imgChange = (count == 1) ? "fa-arrow-left" : "fa-arrow-right";
                %>
                <tr>
                    <td><i class="fa <%= imgChange%>" aria-hidden="true"></i></td>
                    <td><%= file.getName()%></td>
                    <td class="w3-left"><%= Utility.getDate(sampleSeqObj.getSequenceFileId().getCreatedDate(), "yyyy-MM-dd")%></td>
                    <td class="w3-center"><a href="JobAnalysis?file=<%= sampleSeqObj.getSequenceFileId().getFilePath()%>&subAction=downloadFile"><i class="fa fa-download" aria-hidden="true"></i></a></td>
                    <%if (projectType.isEmpty()) {%>
                    <td class="w3-center"><a href="#" class="deleteFile" recordId="<%= sampleSeqObj.getSequenceFileId().getId()%>" filePath="<%=sampleSeqObj.getSequenceFileId().getFilePath()%>"><i class="fa fa-trash" aria-hidden="true"></i></a></td>
                            <%}%>
                </tr>
                <%}%>
            </table>
        </div>
    </div>
    <%} else {%>
    <div class="w3-panel w3-border w3-light-grey w3-round-large">
        <p class="">Please upload sequence files to proceed.</p>
    </div>
    <%}%>
    <a class="w3-button w3-blue w3-small goback" onclick="goBack('projects_samples/base.jsp', '<%= selectedProjectId%>', '<%= selectedProjectName%>', '<%= projectType%>');"><i class="fa fa-arrow-left" aria-hidden="true"></i> Go Back</a>
</div>

<script>

    $('#OpenFileUpload').click(function () {
        $('#fileUpload').trigger('click'); //opens dialog box
    });

    function selection(value) {
        var validFileExtensions = [".fastq", ".fq", ".gz"];
        var status = validateUpload(value, validFileExtensions); //validates selected files
        var fastqFileName = $('input[type=file]').val().split('\\').pop();

        if (status) { //submit uploaded files
            var formData = new FormData();
            formData.append('file', $('input[type=file]')[0].files[0]);
            $.ajax({
                url: "FileUpload?selectedSample=" + '<%= sampleName%>' + "&fastqFile=" + fastqFileName + "&selectedSampleId=" + '<%= sampleId%>' + "&subAction=singleFileUpload",
                type: "POST",
                data: formData,
                contentType: false,
                cache: false,
                processData: false,
                success: function (data) {
                    $(".uploadProgress").show().html(data);
                    $('input[type=file]').val('');
                    var response = $('.uploadProgress div').attr("class");
                    if (response.includes("alert-success")) {
                        $(".uploadProgress").html(data).show("fast").delay(1000).hide("slow", function () {
                            showSequenceFiles('<%= sampleName%>', '<%= sampleId%>', '<%= selectedProjectId%>', '<%= selectedProjectName%>');
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
    }

    //test it more ...doesn't refresh real time 
    function showSequenceFiles(sampleName, sampleId, projectId, projectName) {
        $.ajax({
            type: "GET",
            url: "projects_samples/sequenceFiles.jsp",
            data: {sampleName: sampleName, sampleId: sampleId, projectId: projectId, projectName: projectName, prjType: ''},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    }

    $(".deleteFile").click(function () {
        var recordId = $(this).attr("recordId");
        var absoluteFilePath = $(this).attr("filePath");

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
                        url: "DataMaintenance",
                        data: {recordId: recordId, absoluteFilePath: absoluteFilePath, subAction: "sampleSq-Delete"},
                        success: function (data) {
                            $(".uploadProgress").show().html(data);
                            var response = $('.uploadProgress div').attr("class");
                            if (response.includes("alert-success")) {
                                $(".uploadProgress").html(data).show("fast").delay(1000).hide("slow", function () {
                                    showSequenceFiles('<%= sampleName%>', '<%= sampleId%>', '<%= selectedProjectId%>', '<%= selectedProjectName%>');
                                });
                            }
                        }
                    });
                },
                cancel: function () {
                }
            }
        });
    });


</script>