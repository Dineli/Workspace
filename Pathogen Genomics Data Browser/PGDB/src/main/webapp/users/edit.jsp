<%-- 
    Document   : edit
    Created on : Jul 27, 2017, 4:51:31 PM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.entity.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    int selectedUserId = Integer.valueOf(request.getParameter("recordParam"));
    String name = request.getParameter("nameParam");
    String email = request.getParameter("emailParam");
    String userName = request.getParameter("userNameParam");
    String userType = request.getParameter("userTypeParam");

%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Update User</p>
    </div>
    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p></p>
        </div>
    </div>

    <div class="w3-container">
        <table class="tbl-add-new">
            <tr>
                <td><label>Name :</label></td>
                <td><input class="w3-input editUserName" type="text" value="<%= name%>"></td>
            </tr>
            <tr>
                <td><label>Email :</label></td>
                <td><input class="w3-input editUserEmail" type="text" value="<%= email%>"></td>
            </tr>
            <tr>
                <td><label>User Name :</label></td>
                <td><input class="w3-input editUN" type="text" value="<%= userName%>"></td>
            </tr>
            <tr>
                <td><label>Admin User :</label></td>
                <td>
                    <label>Yes</label>
                    <input class="w3-radio" type="radio" name="editUserType" value="true" <%=(userType.equals("true")) ? "checked" : ""%>>
                    <label>No</label>
                    <input class="w3-radio" type="radio" name="editUserType" value="false" <%=(userType.equals("false")) ? "checked" : ""%>>
                </td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td></td>
                <td>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnUpdate">UPDATE</button>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnClear" onclick="goBack('users/view.jsp');">BACK</button>
                </td>
            </tr>
        </table>
    </div>

    <script>

        $('.btnUpdate').on('click', function () {
            var editName = $(".editUserName").val();
            var editEmail = $(".editUserEmail").val();
            var editUserName = $(".editUN").val();
            var editUserType = $('input[name=editUserType]:checked').val();
            var validateStatus = validate(editName, editEmail, editUserName, "password"); //since password is not validated, a string is passed instead
            if (validateStatus) {
                $.ajax({
                    type: "POST",
                    url: "DataMaintenance",
                    data: {recordId: '<%= selectedUserId%>', editNameParam: editName, editEMParam: editEmail, editUNParam: editUserName, editUTParam: editUserType, subAction: "user-update"},
                    success: function (data) {
                        $(".msg").show().html(data);
                        var response = $('.msg div').attr("class");
                        if (response.includes("alert-success")) {
                            $(".msg").html(data).show("fast").delay(1000).hide("slow", function () {
                                goBack('users/view.jsp');
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
