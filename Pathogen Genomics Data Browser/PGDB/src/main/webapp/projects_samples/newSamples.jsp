<%-- 
    Document   : addSamples
    Created on : Jul 17, 2017, 1:54:40 PM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.entity.Organisms"%>
<%@page import="com.nus.pgdb.service.ISampleProject"%>
<%@page import="com.nus.pgdb.service.impl.SampleProjectServiceImpl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    int projectId = Integer.valueOf(request.getParameter("prjId"));
    String projectName = request.getParameter("prjName");

    ISampleProject iSampleProjectService = new SampleProjectServiceImpl();
    List<Organisms> organismsList = iSampleProjectService.fetchAllOrganismService();
%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Add New Sample</p>
        <p class="w3-right"><button class="w3-button w3-padding-small w3-circle w3-black" onclick="goBack('projects_samples/base.jsp', '<%= projectId%>', '<%= projectName%>', '');"><i class="fa fa-arrow-left" aria-hidden="true"></i></button></p>
    </div>
    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p> </p>
        </div>
    </div>
    
     <div class="w3-bar project-tab-panel">
        <button class="w3-bar-item w3-button tablink w3-red w3-opacity-min w3-border-right w3-card-4 w3-wide" onclick="openSampleTab(event, 'individualSample')">SINGLE SAMPLE</button> 
        <button class="w3-bar-item w3-button tablink w3-opacity-min w3-card-4 w3-wide" onclick="openSampleTab(event, 'multiSample')">MULTI SAMPLE</button>
    </div>

    <div id="individualSample" class="w3-container sampleType">
        <table class="tbl-add-new individualSamplesTbl">
            <tr><td></td></tr>
            <tr><td></td></tr>
            <tr>
                <td><label>Sample Name :</label></td>
                <td><input class="w3-input samName" type="text"></td>
            </tr>
            <tr>
                <td><label>Organism :</label></td>
                <td>
                    <select class="w3-container orgSelect" style="width: 75%">
                        <option value="0" class="w3-bar-item w3-button">SELECT</option>
                        <%for (Organisms organism : organismsList) {%>
                        <option class="w3-bar-item w3-button" value="<%= organism.getId()%>"><%= organism.getName()%></option>
                        <%}%>
                    </select>
                </td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td></td>
                <td>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnSave">SAVE</button>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnClear" onclick="clearTxtboxes();">CLEAR</button>
                </td>
            </tr>
        </table>
    </div>

    <div id="multiSample" class="w3-container sampleType" style="display:none">
        <table class="tbl-add-new multiSamplesTbl">
            <tr><td></td></tr>
            <tr><td></td></tr>
            <tr>
                <td><label>Select Text File :</label></td>
                <td><input type="file" id="sampleTxtFile" onchange="uploadSampleTxtFile(this);">
                    <a href="#" id="multiSampleFormatImg" title="" >     
                        <i class="fa fa-question-circle fa-lg" aria-hidden="true"></i>
                    </a>          
                </td>
            </tr>
            <tr><td></td></tr>
        </table>
    </div>

</div>

<script>

    $('.btnSave').on('click', function () {
        var sampleName = $(".samName").val();
        var organismValue = $(".orgSelect").val();
        var validateStatus = validateSample(sampleName, organismValue);
        if (validateStatus) {
            $.ajax({
                type: "POST",
                url: "DataMaintenance",
                data: {samNameParam: sampleName, orgParam: organismValue, projectIdParam: '<%=projectId%>', subAction: "sample-creation"},
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

    function uploadSampleTxtFile(value) {
        var validFileExtensions = [".txt"];
        var status = validateUpload(value, validFileExtensions);
        var txtFileName = $('#sampleTxtFile').val().split('\\').pop();

        if (status) {
            var formData = new FormData();
            formData.append('file', $('input[type=file]')[0].files[0]);
            $.ajax({
                url: "DataMaintenance?fileName=" + txtFileName + "&projectIdParam="+<%=projectId%>+"&subAction=multi-sample-creation",
                type: "POST",
                data: formData,
                contentType: false,
                cache: false,
                processData: false,
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
    }
    
    function openSampleTab(evt, smpType) {
        var i, x, tablinks;
        x = document.getElementsByClassName("sampleType");
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablink");
        for (i = 0; i < x.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" w3-red", "");
        }
        document.getElementById(smpType).style.display = "block";
        evt.currentTarget.className += " w3-red";
    }
    
    $("#multiSampleFormatImg").tooltip({content: '<img src="images/multi_sample_format.png" />'});

</script>
