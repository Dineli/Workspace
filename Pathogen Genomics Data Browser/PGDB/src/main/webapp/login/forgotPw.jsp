<%-- 
    Document   : forgotPw
    Created on : Aug 15, 2017, 3:03:03 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="w3-container login-frm w3-table">
    <div class="w3-panel w3-border-left w3-border-black">
        <div id="preloader"></div>
        <div class="msg"><p></p></div>
        <div class="w3-panel">
            <div class="login-frm-title">
                <h2 class="w3-wide w3-opacity"><i class="fa fa-unlock-alt" aria-hidden="true"></i></i>&nbsp; Forgot Password</h2>
                <p class="w3-opacity">If you have forgotten the password used to log into your account, we can send you a link that will allow to change your password.</p>
            </div>

            <div class="w3-row w3-section">
                <div class="w3-col"></div>
                <div class="w3-rest">
                    <input class="w3-input w3-border" id="email" type="text" placeholder="Enter your e-mail">
                </div>
            </div>
            <button class="w3-btn w3-dark-gray w3-border w3-round w3-opacity w3-ripple btnMail">SEND</button>
            <button class="w3-btn w3-dark-gray w3-border w3-round w3-opacity w3-ripple btnCancel" onclick="goTo('login/login.jsp');">BACK</button>
        </div>
    </div>
</div>

<script type="text/javascript">

    $('.btnMail').on('click', function () {
        var userEmail = $('#email').val();
        var validateEmail = validate("name", userEmail, "userName", "pw");
        if (validateEmail) {
            $.ajax({
                url: "DataMaintenance",
                type: "POST",
                data: {userEmailParam: userEmail, subAction: "send-forgot-pw-mail"},
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