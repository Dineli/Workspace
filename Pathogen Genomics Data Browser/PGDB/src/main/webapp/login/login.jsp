<%-- 
    Document   : login
    Created on : May 18, 2017, 10:48:43 AM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="w3-container login-frm w3-table">
    <div class="w3-panel w3-border-left w3-border-black">
        <div id="preloader"></div>
        <div class="msg"></div>
        <div class="w3-panel">

            <div class="login-frm-title">
                <h2 class="w3-wide w3-opacity"><i class="fa fa-sign-in" aria-hidden="true"></i>&nbsp; LOGIN</h2>
                <p class="w3-opacity">Please insert your credentials to proceed.</p>
            </div>

            <div class="w3-row w3-section">
                <div class="w3-col"></div>
                <div class="w3-rest">
                    <input class="w3-input w3-border" id="userName" type="text" placeholder="User Name">
                </div>
            </div>

            <div class="w3-row w3-section">
                <div class="w3-col"></div>
                <div class="w3-rest">
                    <input class="w3-input w3-border" id="password" type="password" placeholder="Password">
                </div>
                <a href="#" class="w3-left forgot-password" onclick="goTo('login/forgotPw.jsp');">Forgot Password</a>
            </div>
            <button class="w3-btn w3-dark-gray w3-border w3-round w3-opacity w3-ripple btnLogin">LOGIN</button>
            <button class="w3-btn w3-dark-gray w3-border w3-round w3-opacity w3-ripple btnCancel" onclick="clearTxtboxes();">CANCEL</button>
        </div>
    </div>
</div>

<script type="text/javascript">

    function login() {
        var un = $('#userName').val();
        var pw = $('#password').val();
        $.ajax({
            url: "Authentication",
            type: "GET",
            data: {un: un, pw: pw, subAction: "login-authenticate"},
            success: function (data) {
                $('.msg').html(data);
                var response = $('.msg div').attr("class");
                if (response.includes("alert-success")) {
                    $(".msg").html(data).show("fast").delay(1000).hide("slow", function () {
                        window.location.href = "home.jsp";
                    });
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


    //enable login for Enter key
    $(document).keypress(function (event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode === '13') {
            login();
        }
    });

    $('.btnLogin').on('click', function () {
        login();
    });

</script>