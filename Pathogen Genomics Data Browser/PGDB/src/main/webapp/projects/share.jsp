<%-- 
    Document   : share
    Created on : Jul 19, 2017, 2:45:08 PM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.entity.SharedUserProjects"%>
<%@page import="com.nus.pgdb.service.impl.ProjectServiceImpl"%>
<%@page import="com.nus.pgdb.service.IProject"%>
<%@page import="com.nus.pgdb.entity.Users"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.pgdb.service.impl.UserServiceImpl"%>
<%@page import="com.nus.pgdb.service.IUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    int userID = (Integer) session.getAttribute("userId");
    int sharedPrjId = Integer.valueOf(request.getParameter("proIdParam"));
    String sharedPrjName = request.getParameter("proNameParam");

    IUser iUserService = new UserServiceImpl();
    IProject iProject = new ProjectServiceImpl();
    //fetches all users except the loged user and users who are already share this project
    List<Users> userList = iUserService.fetchAllUsersWithNoSharedProjectService(userID, sharedPrjId);
    List<SharedUserProjects> usersWithSharedProjectsList = iProject.fetchUsersWithSharedProjectsService(sharedPrjId, userID);

%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large" >Share <%= sharedPrjName%></p>
        <p class="w3-right"><button class="w3-button w3-padding-small w3-circle w3-black" onclick="goBack('projects/base.jsp');"><i class="fa fa-arrow-left" aria-hidden="true"></i></button></p>
    </div>

    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p> </p>
        </div>
    </div>

    <div class="w3-row">
        <%if (userList != null && userList.size() > 0) {%>
        <div class="w3-container w3-half" >
            <table class="tbl-add-new">
                <tr>
                    <td><label>Share with :</label></td>
                    <td>
                        <select class="w3-container users" style="width: 100%">
                            <option value="0" class="w3-bar-item w3-button">SELECT</option>
                            <%for (Users user : userList) {%>
                            <option class="w3-bar-item w3-button" value="<%= user.getId()%>"><%= user.getName()%></option>
                            <%}%>
                        </select>
                    </td>
                    <td><button class="w3-btn  w3-wide w3-border w3-round w3-opacity w3-ripple btnShare">SHARE</button></td>
                </tr>
            </table>
        </div>
        <%} else {%>
        <div class="w3-panel w3-border w3-light-grey w3-round-large">
            <p>Currently no users are available to share the project.</p>
        </div>
        <%}%>                
        <div class="w3-container w3-half">
            <%if (usersWithSharedProjectsList != null && usersWithSharedProjectsList.size() > 0) {%>
            <p>Users you have already shared this project with :</p>
            <%for (SharedUserProjects sharedProject : usersWithSharedProjectsList) {%>
            <ul>
                <li><%= sharedProject.getSharedUserId().getName()%>&nbsp;&nbsp;&nbsp;<a href="#" class="removeSharedProject" recordId="<%= sharedProject.getId()%>"><i class="fa fa-times" style="color: red;" aria-hidden="true"></i></a></li>
            </ul>
            <%}%>
            <%} else {%>
            <div class="w3-panel w3-border w3-light-grey w3-round-large">
                <p>This Project is not yet shared with anyone.</p>
            </div>
            <%}%>
        </div>
    </div>
</div>


<script>
    $('.btnShare').on('click', function () {
        var selectedUser = $('.users').val();
        var validateStatus = validateDropDown(selectedUser);
        if (validateStatus) {
            $.ajax({
                type: "POST",
                url: "ProjectShare",
                data: {proIdParam: '<%= sharedPrjId%>', selectedUserParam: selectedUser, subAction: "project-share"},
                success: function (data) {
                    $(".msg").show().html(data);
                    var response = $('.msg div').attr("class");
                    if (response.includes("alert-success")) {
                        $(".msg").html(data).show("fast").delay(1000).hide("slow", function () {
                            goBack('projects/base.jsp');
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

    $('.removeSharedProject').on('click', function () {
        var selectedRecordId = $(this).attr("recordId");

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
                        url: "ProjectShare",
                        data: {recordIdParam: selectedRecordId, subAction: "project-share-remove"},
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



</script>