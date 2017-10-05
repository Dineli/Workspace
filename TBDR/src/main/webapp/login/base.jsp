<%-- 
    Document   : base
    Created on : Jan 18, 2017, 2:08:25 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div id ="msg"></div>
<div class="login">
    <form action="Authentication" method="GET">
        <h2 class="login-title">LOGIN</h2>
        <div class="input-group col-xs-12 form-group" style="margin: 0 auto;padding-top: 2%;">
            <input id="userName" type="text" class="form-control" name="userName" placeholder="User Name">
            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>   
        </div>
        <div class="input-group col-xs-12 form-group" style="padding-top: 2%;margin: 0 auto;">
            <input id="password" type="password" class="form-control" name="password" placeholder="Password">
            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
        </div>
        <div class="button-box">        
            <button type="button" class="btn btn-default btnLogin">Login</button>
            <button type="button" class="btn btn-default btnCancel">Cancel</button>
        </div>
    </form>
</div>

<script type="text/javascript">

    function login() {
        var un = $('#userName').val();
        var pw = $('#password').val();
        $.ajax({
            url: "Authentication",
            type: "GET",
            data: {un: un, pw: pw, subAction: "login"},
            success: function (data) {
                $('#msg').html(data);
                var response = $('#msg div').attr("class");
                if ("alert alert-success" === response) {
                    $("#msg").html(data).show("fast").delay(1000).hide("slow", function () {
                        location.reload();
                    });
                } else {
                    $('#msg').show("fast");
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

    //enable login for Enter key
    $(document).keypress(function (event) {
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if (keycode == '13') {
            login();
        }
    });

    $('.btnCancel').on('click', function () {
        $('#userName').val("");
        $('#password').val("");
    });

    $('.btnLogin').on('click', function () {
        login();
    });


</script>