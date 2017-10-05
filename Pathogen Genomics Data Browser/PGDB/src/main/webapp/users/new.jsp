<%-- 
    Document   : new
    Created on : Jul 10, 2017, 11:08:59 AM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Add New User </p> 
        <p class="w3-right"><button class="w3-button w3-padding-small w3-circle w3-black" onclick="goBack('users/view.jsp');"><i class="fa fa-arrow-left" aria-hidden="true"></i></button></p>
    </div>
    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p> </p>
        </div>
    </div>

    <div class="w3-container">
        <table class="tbl-add-new">
            <tr>
                <td><label>Name :</label></td>
                <td><input class="w3-input name" type="text"></td>
            </tr>
            <tr>
                <td><label>E-mail :</label></td>
                <td><input class="w3-input email" name="userEmail" type="text"></td>
            </tr>
            <tr>
                <td><label>User Name :</label></td>
                <td><input class="w3-input uname" name="userName" type="text"></td>
            </tr>
            <tr>
                <td><label>Password :</label></td>
                <td><input class="w3-input password" name="userPassword" type="text"></td>
            </tr>
            <tr>
                <td><label>Admin User :</label></td>
                <td>
                    <label>Yes</label>
                    <input class="w3-radio" type="radio" name="adminUser" value="true">
                    <label>No</label>
                    <input class="w3-radio" type="radio" name="adminUser" value="false" checked>
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
</div>


<script type="text/javascript">

    $('.btnSave').on('click', function () {
        var name = $(".name").val();
        var email = $(".email").val();
        var userName = $(".uname").val();
        var pw = $(".password").val();
        var isAdminUser = $('input[name=adminUser]:checked').val();

        var status = validate(name, email, userName, pw);
        if (status) {
            $.ajax({
                type: "POST",
                url: "DataMaintenance",
                data: {nameParam: name, emailParam: email, userNameParam: userName, pwParam: pw, adminUserParam: isAdminUser, subAction: "user-creation"},
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