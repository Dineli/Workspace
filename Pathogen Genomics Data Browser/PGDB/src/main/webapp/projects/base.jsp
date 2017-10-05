<%-- 
    Document   : base
    Created on : May 24, 2017, 3:01:17 PM
    Author     : Dineli
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.nus.pgdb.entity.SharedUserProjects"%>
<%@page import="com.nus.pgdb.entity.Users"%>
<%@page import="com.nus.pgdb.entity.Projects"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.pgdb.service.impl.ProjectServiceImpl"%>
<%@page import="com.nus.pgdb.service.IProject"%>
<%@page import="com.nus.pgdb.service.impl.UserServiceImpl"%>
<%@page import="com.nus.pgdb.service.IUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    int userID = (Integer) session.getAttribute("userId");
    boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    IProject iProjectService = new ProjectServiceImpl();
    List<Projects> projectList = iProjectService.fetchAllProjectsByUserIdService(userID);
    List<SharedUserProjects> sharedProjectList = iProjectService.fetchAllSharedProjectsByUserIdService(userID);
    List<Object[]> allProjectsList = iProjectService.fetchAllProjects4NonAdminUsersService();
    String userName = " ";
%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Projects</p>
        <p class="w3-right"><button class="w3-button w3-padding-small w3-circle w3-black" onclick="goTo('projects/new.jsp');"><i class="fa fa-plus" aria-hidden="true"></i></button></p>
    </div>

    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <div class='downloadMsg alert alert-success login-msg w3-panel w3-wide w3-round w3-animate-opacity' style="display: none;"><p>Preparing samples to download, this will take some time ...</p></div>
            <p> </p>
        </div>
    </div>

    <div class="w3-bar project-tab-panel">
        <button class="w3-bar-item w3-button tablink w3-red w3-opacity-min w3-border-right w3-card-4 w3-wide" onclick="openProjectTab(event, 'myProjects')">YOUR PROJECTS</button> 
        <%if (isAdmin) {%>
        <button class="w3-bar-item w3-button tablink w3-opacity-min w3-card-4 w3-wide" onclick="openProjectTab(event, 'allProjects')">ALL PROJECTS</button>
        <%} else {%>
        <button class="w3-bar-item w3-button tablink w3-opacity-min w3-card-4 w3-wide" onclick="openProjectTab(event, 'sharedProjects')">SHARED PROJECTS</button>
        <%}%>
    </div>
    <div id="myProjects" class="w3-container ptojectType">
        <% if ((null != projectList) && (!projectList.isEmpty())) { %>
        <div class="w3-panel w3-leftbar">
            <div class="w3-container">
                <table class="tbl-dispaly">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th class="w3-center">Edit</th>
                            <th class="w3-center">Delete</th>
                            <th class="w3-center">Share</th>
                            <th class="w3-center">Download</th>
                        </tr>
                    </thead>
                    <% for (Projects project : projectList) {%>
                    <%
                        boolean showDownloadLink = iProjectService.isProjectWithFilesService(userID, project.getId());
                    %>
                    <tr>
                        <td><a class="w3-tag w3-round" onclick="showSamples('<%= project.getId()%>', '<%= project.getName()%>', '');" href="#"><%= project.getName()%></a></td>
                        <td><%= project.getDescription()%></td>
                        <td class="w3-center"><a href="#" class="prjUpdate" pId="<%= project.getId()%>" pName="<%= project.getName()%>" pDesc="<%= project.getDescription()%>"><i class="fa fa-pencil" aria-hidden="true"></i></td>
                        <td class="w3-center"><a href="#" class="prjDelete" pId="<%= project.getId()%>"><i class="fa fa-trash" aria-hidden="true"></i></td>
                        <td class="w3-center"><a href="#" class="prjShare" pId="<%= project.getId()%>" pName="<%= project.getName()%>"><i class="fa fa-share" aria-hidden="true"></i></td>
                        <%if(showDownloadLink) {%>
                        <td class="w3-center"><a href="FileDownload?recordId=<%= project.getId()%>&subAction=TotalFileDownload" class="SampleDownload"><i class="fa fa-download" aria-hidden="true"></i></td>
                          <%} else {%>
                        <td class="w3-center">-</td>
                        <%}%>
                    </tr>
                    <%}%>
                </table>
            </div>
        </div> 
        <%} else {%>
        <div class="w3-panel w3-border w3-light-grey w3-round-large">
            <p>Currently no projects available.</p>
        </div>
        <%}%>
    </div>

    <!-- project list of all users for the administrator  -->
    <%if (isAdmin) {%>
    <div id="allProjects" class="w3-container ptojectType" style="display:none">
        <% if ((null != allProjectsList) && (!allProjectsList.isEmpty())) { %>

        <div class="w3-panel w3-leftbar">
            <div class="w3-container">
                <table class="tbl-all-projects">
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Project Name</th>
                            <th>Sample Name</th>
                            <th>Sequence Files</th>
                        </tr>
                    </thead>
                    <% for (Object[] data : allProjectsList) {%>
                    <%
                        String bgClr = "";
                        String tdClassAttributes = "";
                        String fileNames = null;

                        String prjName = (data[1].equals("")) ? "-" : data[1].toString();
                        String sampleName = (data[2].equals("")) ? "-" : data[2].toString();
                        String seqName[] = (data[3].equals("")) ? null : data[3].toString().split(",");

                        if (seqName != null) {
                            if (seqName.length == 2) {
                                String file1 = seqName[0].substring(seqName[0].lastIndexOf("\\") + 1);
                                String file2 = seqName[1].substring(seqName[1].lastIndexOf("\\") + 1);
                                fileNames = file1.concat(" &nbsp;  <i class='fa fa-arrows-h fa-lg'></i>  &nbsp; ").concat(file2); //replace it with a </br> to print in a new line
                            } else if (seqName.length == 1) {
                                String file1 = seqName[0].substring(seqName[0].lastIndexOf("\\") + 1);
                                fileNames = file1;
                            } else {
                                fileNames = "-";
                            }
                        } else {
                            fileNames = "-";
                        }

                        userName = (userName.equals(data[0])) ? "" : data[0].toString();
                        bgClr = (!userName.equals("")) ? "#b08fbb" : "";
                        tdClassAttributes = (!userName.equals("")) ? "w3-tag w3-round" : "";
                    %>
                    <tr>
                        <td><span class="<%= tdClassAttributes%>" style="background-color: <%= bgClr%> "><%= userName%></span></td>
                        <td><%= prjName%></td>
                        <td><%= sampleName%></td>
                        <td><%= fileNames%>  </td>
                    </tr>
                    <% userName = data[0].toString(); %>
                    <%}%>
                </table>
            </div>
        </div> 
        <%} else {%>
        <div class="w3-panel w3-border w3-light-grey w3-round-large">
            <p>No data to display.</p>
        </div>
        <%}%>
    </div>
    <%} else {%>
    <!-- shared project list  -->
    <div id="sharedProjects" class="w3-container ptojectType" style="display:none">
        <% if ((null != sharedProjectList) && (!sharedProjectList.isEmpty())) { %>
        <div class="w3-panel w3-leftbar">
            <div class="w3-container">
                <table class="tbl-shared-projects">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Shared by</th>
                        </tr>
                    </thead>
                    <% for (SharedUserProjects sharedProject : sharedProjectList) {%>
                    <tr>
                        <td><a class="w3-tag w3-round" onclick="showSamples('<%= sharedProject.getSharedProjectId().getId()%>', '<%= sharedProject.getSharedProjectId().getName()%>', 'shared');" href="#"><%= sharedProject.getSharedProjectId().getName()%></a></td>
                        <td><%= sharedProject.getSharedProjectId().getDescription()%></td>
                        <td><%= sharedProject.getProjectOwnerId().getName()%></td>
                    </tr>
                    <%}%>
                </table>
            </div>
        </div> 
        <%} else {%>
        <div class="w3-panel w3-border w3-light-grey w3-round-large">
            <p>Currently no shared projects available.</p>
        </div>
        <%}%>
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

    $('.tbl-shared-projects').DataTable({
        bFilter: false,
        "aoColumnDefs": [{
                'bSortable': false,
                'aTargets': [1, 2]
            }]
    })

    $('.tbl-all-projects').DataTable({
        "bSort": false
//        "rowCallback": function (row, data, index) {
//            if (data[0] !== "")
//            {
////                $('td', row).css('background-color', 'Red');
//                $('td', row).css('border-bottom', '1px dotted silver');
//            }
//        }
    })

    function showSamples(projectId, projectName, projectType) {
        $.ajax({
            type: "GET",
            url: "projects_samples/base.jsp",
            data: {prjId: projectId, prjName: projectName, prjType: projectType},
            success: function (data) {
                $(".container").html(data).show();
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

    $('.tbl-dispaly').on('click', '.prjUpdate', function () {
        var selectedRecordId = $(this).attr("pId");
        var name = $(this).attr("pName");
        var desc = $(this).attr("pDesc");
        $.ajax({
            type: "GET",
            url: "projects/edit.jsp",
            data: {recordId: selectedRecordId, nameParam: name, descParam: desc},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });

    $('.tbl-dispaly').on('click', '.prjDelete', function () {
        var selectedRecordId = $(this).attr("pId");

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
                        data: {recordId: selectedRecordId, subAction: "project-delete"},
                        success: function (data) {
                            $(".msg").show().html(data);
                            var response = $('.msg div').attr("class");
                            if (response.includes("alert-success")) {
                                $(".msg").html(data).show("fast").delay(1000).hide("slow", function () {
                                    refreshCart();
                                    goBack('projects/base.jsp');
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

    $('.tbl-dispaly').on('click', '.prjShare', function () {
        var selectedRecordId = $(this).attr("pId");
        var selectedPrjName = $(this).attr("pName");
        $.ajax({
            type: "GET",
            url: "projects/share.jsp",
            data: {proIdParam: selectedRecordId, proNameParam: selectedPrjName},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });


    $('.tbl-dispaly').on('click', '.SampleDownload', function () {
         $(".msg").show();
         $(".downloadMsg").show();
    });

    function openProjectTab(evt, prjType) {
        var i, x, tablinks;
        x = document.getElementsByClassName("ptojectType");
        for (i = 0; i < x.length; i++) {
            x[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablink");
        for (i = 0; i < x.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" w3-red", "");
        }
        document.getElementById(prjType).style.display = "block";
        evt.currentTarget.className += " w3-red";
    }


</script>


