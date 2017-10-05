<%-- 
    Document   : base
    Created on : May 26, 2017, 1:12:00 PM
    Author     : Dineli
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.AbstractList"%>
<%@page import="com.nus.pgdb.util.Utility"%>
<%@page import="com.nus.pgdb.entity.SamplesSequenceFiles"%>
<%@page import="com.nus.pgdb.service.impl.SampleSequenceServiceImpl"%>
<%@page import="com.nus.pgdb.service.ISampleSequence"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.pgdb.entity.ProjectSamples"%>
<%@page import="com.nus.pgdb.service.impl.SampleProjectServiceImpl"%>
<%@page import="com.nus.pgdb.service.ISampleProject"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    int userId = (Integer) session.getAttribute("userId");
    int projectId = Integer.parseInt(request.getParameter("prjId"));
    String projectName = request.getParameter("prjName");
    String projectType = request.getParameter("prjType");

    ISampleProject iSampleProject = new SampleProjectServiceImpl();
    ISampleSequence iSampleSequence = new SampleSequenceServiceImpl();

    List<ProjectSamples> projectSampleList = iSampleProject.getSamplesbyProject(projectId);
    List<SamplesSequenceFiles> sampleSeqList = null;

    List<String> checkboxStatus = new ArrayList<String>();
    //status to show records belong to shared projects irrespective of the userId
    boolean status = (projectType.isEmpty()) ? false : true;
%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large"><%= projectName%> : Samples <%if (!projectType.isEmpty()) {%><span style="text-shadow:1px 1px 0 #444"> [ Shared Project ]</span> <%}%></p>
        <p class="w3-right w3-tooltip"><button class="w3-button w3-padding-small w3-circle w3-black" onclick="cartAdd()"> <i class="fa fa-cart-plus" aria-hidden="true"></i><span class="w3-text w3-tiny">Add to cart</span></button></p>
        <p class="w3-right w3-tooltip"><a class="w3-button w3-padding-small w3-circle w3-black" href="#" onclick="selectedSamples()" id="downloadLink"><i class="fa fa-download" aria-hidden="true"></i></a><span class="w3-text w3-tiny">Download samples</span></p>
        <%if (projectType.isEmpty()) {%>
        <p class="w3-right w3-tooltip"><button class="w3-button w3-padding-small w3-circle w3-black bulkUpload" onclick="goBack('projects_samples/bulkFileUpload.jsp', '<%= projectId%>', '<%= projectName%>', '');"> <i class="fa fa-files-o" aria-hidden="true"></i><span class="w3-text w3-tiny">Upload samples</span></button></p>
        <p class="w3-right w3-tooltip"><button class="w3-button w3-padding-small w3-circle w3-black sampleAdd" onclick="goBack('projects_samples/newSamples.jsp', '<%= projectId%>', '<%= projectName%>', '');"> <i class="fa fa-plus" aria-hidden="true"></i><span class="w3-text w3-tiny">Add Samples</span></button></p>
                    <%}%>
    </div>

    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p></p>
        </div>
    </div>

    <% if ((null != projectSampleList) && (!projectSampleList.isEmpty())) { %>
    <div class="w3-panel w3-leftbar ">
        <div class="w3-container">
            <table class="tbl-dispaly">
                <thead>
                    <tr>
                        <th><input class="w3-check chkAll" type="checkbox" disabled></th>
                        <th>Sample Name</th>
                        <th>Organism</th>
                        <th>Project</th>
                        <th>Created On</th>
                            <%if (projectType.isEmpty()) {%>
                        <th class="w3-center">Edit</th>
                        <th class="w3-center">Delete</th>
                            <%}%>
                    </tr>
                </thead>
                <% for (ProjectSamples prjSampleObj : projectSampleList) {%>
                <%
                    //validation: if no fastq files are uploaded program will prohibit user from adding an empty sample to the cart
                    String disableStatus = "disabled";
                    sampleSeqList = iSampleSequence.fetchSequenceFilesBySampleIdService(prjSampleObj.getSampleId().getId(), userId, status);
                    if ((null != sampleSeqList) && (!sampleSeqList.isEmpty())) {
                        disableStatus = (sampleSeqList.size() > 0) ? "" : "disabled";
                        checkboxStatus.add(disableStatus);
                    }
                %>
                <tr>
                    <td><input class="w3-check sampleChkBox" type="checkbox" <%= disableStatus%> value="<%= prjSampleObj.getSampleId().getName()%>" sampleIds="<%= prjSampleObj.getSampleId().getId()%>"></td>
                    <td><a class="w3-tag w3-round" onclick="showSequenceFiles('<%= prjSampleObj.getSampleId().getName()%>', '<%= prjSampleObj.getSampleId().getId()%>');" href="#"><%= prjSampleObj.getSampleId().getName()%></a></td>
                    <td><%= prjSampleObj.getSampleId().getOrganismId().getName()%></td>
                    <td><%= prjSampleObj.getProjectId().getName()%></td>
                    <td><%= Utility.getDate(prjSampleObj.getSampleId().getCreatedDate(), "yyyy-MM-dd HH:mm:ss")%></td>
                    <%if (projectType.isEmpty()) {%>
                    <td class="w3-center"><a href="#" class="sampleUpdate" samId="<%= prjSampleObj.getSampleId().getId()%>" samName="<%= prjSampleObj.getSampleId().getName()%>" organismName="<%= prjSampleObj.getSampleId().getOrganismId().getName()%>"><i class="fa fa-pencil" aria-hidden="true"></i></td>
                    <td class="w3-center"><a href="#" class="sampleDelete" samId="<%= prjSampleObj.getSampleId().getId()%>"><i class="fa fa-trash" aria-hidden="true"></i></td>
                        <%}%>
                </tr>
                <%}%>
            </table>
        </div>
    </div>
    <%} else {%>
    <div class="w3-panel w3-border w3-light-grey w3-round-large">
        <p>Currently no samples available for this project.</p>
    </div>
    <%}%>
    <a class="w3-button w3-blue w3-small goback" onclick="goBack('projects/base.jsp')"><i class="fa fa-arrow-left" aria-hidden="true"></i> Go Back</a>
</div>


<script type="text/javascript">

    //if samples are without sequence files .chkAll check box will get disable
    $(function () {
    <%if (checkboxStatus.contains("")) {%>
        $(".chkAll").prop("disabled", false);
    <%}%>
    });

    $('.tbl-dispaly').DataTable({
        bFilter: false,
        ordering: false
    });

    function showSequenceFiles(sampleName, sampleId) {
        $.ajax({
            type: "GET",
            url: "projects_samples/sequenceFiles.jsp",
            data: {sampleName: sampleName, sampleId: sampleId, projectId: '<%= projectId%>', projectName: '<%= projectName%>', prjType: '<%= projectType%>'},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    }

    $('.chkAll').on('click', function () {
        $('.sampleChkBox:enabled').prop('checked', this.checked);
    });

    function fetchSelectedSamples() {
        var selectedSampleIds = [];
        $.each($('input:checked:not(".chkAll")'), function () {
            selectedSampleIds.push($(this).attr("sampleIds"));
        });
        return selectedSampleIds;
    }

    function cartAdd() {
        var sampleIds = [];
        sampleIds = fetchSelectedSamples();

        if (sampleIds.length > 0) {
            $.ajax({
                type: "POST",
                url: "DataMaintenance",
                data: {sampleIdList: sampleIds.toString(), projectName: '<%= projectName%>', subAction: "cartAdd"},
                success: function (data) {
                    document.getElementById("cartSize").innerHTML = +data;
                    $("#cartSize").show();
                    refreshCart();
                }
            });
        } else {
            $.alert({
                title: 'Sample Selection',
                content: 'Please add samples to the cart before proceed.',
                useBootstrap: false,
                boxWidth: '30%',
            });
        }
    }

    $('.tbl-dispaly').on('click', '.sampleUpdate', function () {
        var selectedRecordId = $(this).attr("samId");
        var sName = $(this).attr("samName");
        var orgName = $(this).attr("organismName");
        $.ajax({
            type: "GET",
            url: "projects_samples/editSamples.jsp",
            data: {recordId: selectedRecordId, nameParam: sName, orgNameParam: orgName, projectId: '<%= projectId%>', projectName: '<%= projectName%>'},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });

    $('.tbl-dispaly').on('click', '.sampleDelete', function () {
        var selectedRecordId = $(this).attr("samId");

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
                        data: {recordId: selectedRecordId, subAction: "sample-delete"},
                        success: function (data) {
                            var dataArr = data.split("</div>"); //splits the output using the last div tag of the msg to retrieve the cart length
                            document.getElementById("cartSize").innerHTML = +dataArr[1];
                            $(".msg").show().html(dataArr[0]);
                            var response = $('.msg div').attr("class");
                            if (response.includes("alert-success")) {
                                $(".msg").html(dataArr[0]).show("fast").delay(1000).hide("slow", function () {
                                    refreshCart();
                                    goBack('projects_samples/base.jsp', '<%= projectId%>', '<%= projectName%>', '');
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

    function selectedSamples() {
        var sampleIds = [];
        sampleIds = fetchSelectedSamples();

        if (sampleIds.length > 0) {
            $("#downloadLink").attr("href", "FileDownload?sampleIds=" + sampleIds + "&isSharedProject=" +<%=status%> + "&subAction=SelectedFileDownload");
        } else {
            $.alert({
                title: 'Sample Selection',
                content: 'Please select the samples to download',
                useBootstrap: false,
                boxWidth: '30%',
            });
        }
    }

</script>

