<%-- 
    Document   : view
    Created on : Jul 10, 2017, 11:09:09 AM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.entity.Users"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.pgdb.service.impl.UserServiceImpl"%>
<%@page import="com.nus.pgdb.service.IUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%

    IUser iUserService = new UserServiceImpl();
    List<Users> userList = iUserService.fetchAllUsersService();

%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel" style="background-color:#9ed0e0;">
        <p class="w3-left w3-wide w3-large" >Users </p> 
        <p class="w3-right"><button class="w3-button w3-padding-small w3-circle w3-black" onclick="goTo('users/new.jsp');"><i class="fa fa-plus" aria-hidden="true"></i></button></p>
    </div>
    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p> </p>
        </div>
    </div>

    <% if ((null != userList) && (!userList.isEmpty())) { %>
    <div class="w3-panel w3-leftbar ">
        <div class="w3-container">
            <table class="tbl-dispaly">
                <thead>
                    <tr>
                        <th></th>
                        <th>User ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>User Name</th>
                        <th>Admin User</th>
                        <th class="w3-center">Edit</th>
                        <th class="w3-center">Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Users user : userList) {%>
                    <%
                        String adminUser = (user.getIsAdmin() == true) ? "Yes" : "No";
                    %>
                    <%if (user.getIsPwReset() == false) {%>
                    <tr>
                        <td class="w3-center"><button class="btn btnMailPreview"  uEmail="<%=user.getEmail()%>" uOriginalName="<%= user.getName()%>" uName="<%= user.getUserName()%>" uPw="<%= user.getDummyPassword()%>"><i class="fa fa-envelope-o" aria-hidden="true"></i></button></td>
                                <%} else {%>
                        <td></td>
                        <%}%>
                        <td><%= user.getId()%></td>
                        <td><%= user.getName()%></td>
                        <td><%= user.getEmail()%></td>
                        <td><%= user.getUserName()%></td>
                        <td><%= adminUser%></td>
                        <td class="w3-center"><a href="#" class="userUpdate" uId="<%= user.getId()%>" uName="<%= user.getName()%>" uEmail="<%= user.getEmail()%>" userName="<%= user.getUserName()%>" userType="<%= user.getIsAdmin()%>"><i class="fa fa-pencil" aria-hidden="true"></i></td>
                        <td class="w3-center"><a href="#" class="userDelete" uId="<%= user.getId()%>"><i class="fa fa-trash" aria-hidden="true"></i></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
            <div class="tfootData"></div>  
        </div>
    </div>
    <%} else {%>
    <div class="w3-panel w3-border w3-light-grey w3-round-large">
        <p>Currently no users available.</p>
    </div>
    <%}%>
</div>

<div id="pw-reset-mail" class="w3-modal w3-display-middle">
    <div class="w3-modal-content w3-card-4 w3-round w3-animate-opacity">
        <header class="w3-container"> 
            <span onclick="document.getElementById('pw-reset-mail').style.display = 'none'" 
                  class="w3-button w3-large w3-display-topright"><i class="fa fa-times" aria-hidden="true"></i></span>
        </header>
        <div class="w3-container mail-content w3-margin w3-light-grey">
        </div>
    </div>
</div>    

<script type="text/javascript">

    $('.tbl-dispaly').DataTable({
        bFilter: false,
        "aoColumnDefs": [{
                'bSortable': false,
                'aTargets': [0, 1, 3, 4, 5, 6, 7]
            }]
    });

    //when using pagination u need to bind the handler to the asset in page that is permanent in this case it's the table
    //as rows not needed by current page are removed from the DOM in pagination.
    $('.tbl-dispaly').on('click', '.btnMailPreview', function () {
        var uName = $(this).attr("uOriginalName");
        var userName = $(this).attr("uName");
        var pw = $(this).attr("uPw");
        var email = $(this).attr("uEmail");
        $.ajax({
            type: "GET",
            url: "users/sendMail.jsp",
            data: {originalName: uName, userName: userName, userPw: pw, userEmail: email},
            success: function (data) {
                $('#pw-reset-mail').show();
                $('.mail-content').html(data).show()
            }
        });
    });

    $('.tbl-dispaly').on('click', '.userUpdate', function () {
        var selectedRecord = $(this).attr("uId");
        var uOriginalName = $(this).attr("uName");
        var userEmail = $(this).attr("uEmail");
        var userName = $(this).attr("userName");
        var userType = $(this).attr("userType");

        $.ajax({
            type: "GET",
            url: "users/edit.jsp",
            data: {recordParam: selectedRecord, nameParam: uOriginalName, emailParam: userEmail, userNameParam: userName, userTypeParam: userType},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });

    $('.tbl-dispaly').on('click', '.userDelete', function () {
        var selectedRecordId = $(this).attr("uId");

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
                        data: {recordId: selectedRecordId, subAction: "user-delete"},
                        success: function (data) {
                            $(".msg").show().html(data);
                            var response = $('.msg div').attr("class");
                            if (response.includes("alert-success")) {
                                $(".msg").html(data).show("fast").delay(1000).hide("slow", function () {
                                    goBack('users/view.jsp');
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
