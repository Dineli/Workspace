<%-- 
    Document   : changePw
    Created on : Aug 16, 2017, 1:27:00 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" href="images/titleIcon.png" type="image/x-icon">
        <title>PGDB</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/w3.css">
        <link rel="stylesheet" href="css/custom.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <script src="js/jquery.min.js"></script>
        <script src="js/custom.js"></script>
        <style>
            body{
                background-color: #22c7a9;
            }
        </style>
    </head>
    <body>
        <div class="w3-container login-frm w3-table">
            <div class="w3-panel w3-border-left w3-border-black">
                <div id="preloader"></div>
                <div class="msg"><p></p></div>
                <div class="w3-panel">
                    <div class="login-frm-title">
                        <h2 class="w3-wide w3-opacity"><i class="fa fa-lock" aria-hidden="true"></i>&nbsp; Change Password</h2>
                        <p class="w3-opacity">Enter your new account password below.</p>
                    </div>

                    <div class="w3-row w3-section">
                        <div class="w3-col"></div>
                        <div class="w3-rest">
                            <input class="w3-input w3-border" id="newPassword" type="password" placeholder="New Password">
                        </div>
                    </div>

                    <div class="w3-row w3-section">
                        <div class="w3-col"></div>
                        <div class="w3-rest">
                            <input class="w3-input w3-border" id="confirmPassword" type="password" placeholder="Confirm Password">
                        </div>
                    </div>
                    <button class="w3-btn w3-dark-gray w3-border w3-round w3-opacity w3-ripple btnLogin">SUBMIT</button>
                    <button class="w3-btn w3-dark-gray w3-border w3-round w3-opacity w3-ripple btnCancel" onclick="clearTxtboxes();">CANCEL</button>
                </div>
            </div>
        </div>
    </body>
</html>
<script type="text/javascript">

    $('.btnLogin').on('click', function () {
        var newPw = $('#newPassword').val();
        var confirmPw = $('#confirmPassword').val();
        var tokenString = window.location.href.split("=");
        var validatePw = validatePassword(newPw, confirmPw, "-");

        if (validatePw) {
            $.ajax({
                url: "Authentication",
                type: "POST",
                data: {newPwParam: newPw, tokenParam: tokenString[1], subAction: "password-change"},
                success: function (data) {
                    $('.msg').html(data);
                    var response = $('.msg div').attr("class");
                    if (response.includes("alert-success")) {
                        $(".msg").html(data).show("fast");
                        clearTxtboxes();
                    } else {
                        $('.msg').show("fast");
                    }
                },
                complete: function () {
                    hidePagePreload();
                },
                beforeSend: function () {
                    $("#preloader").html('');
                    showLoginPreloader();
                }
            });
        }
    });
</script>
