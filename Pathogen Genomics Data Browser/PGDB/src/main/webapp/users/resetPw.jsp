<%-- 
    Document   : resetPw
    Created on : Jul 12, 2017, 11:32:19 AM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large" >Reset Password </p> 
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
                <td><label>Old Password :</label></td>
                <td><input class="w3-input oldPw" type="password"></td>
            </tr>
            <tr>
                <td><label>New Password :</label></td>
                <td><input class="w3-input newPw1" type="password"></td>
            </tr>
            <tr>
                <td><label>Re-enter new Password :</label></td>
                <td><input class="w3-input newPw2" type="password"></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td></td>
                <td>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnReset">RESET</button>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnClear" onclick="clearTxtboxes();">CLEAR</button>
                </td>
            </tr>
        </table>
    </div>
</div>


<script>

    $('.btnReset').on('click', function () {
        var oldPassword = $(".oldPw").val();
        var password1 = $(".newPw1").val();
        var password2 = $(".newPw2").val();
        var validatePass = validatePassword(password1, password2, oldPassword);
        if (validatePass) {
            $.ajax({
                url: "Authentication",
                type: "POST",
                data: {newPwParam: password1, oldPwParam: oldPassword, subAction: "password-reset"},
                success: function (data) {
                    $(".msg").show().html(data);
                    var response = $('.msg div').attr("class");
                    if (response.includes("alert-success")) {
                        $(".msg").html(data).show("fast").delay(2000).hide("slow", function () {
                            clearTxtboxes();
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