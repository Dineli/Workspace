<%-- 
    Document   : sendMail
    Created on : Jul 11, 2017, 2:23:41 PM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.util.Utility"%>
<%@page import="com.nus.pgdb.util.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String fullName = request.getParameter("originalName");
    String userName = request.getParameter("userName");
    String userPw = request.getParameter("userPw");
    String recipientEmail = request.getParameter("userEmail");
%>

<p class="w3-medium">
    Hello <%= fullName%>,</br>
    We are delighted to announce that you are now an official member of the PGDB module. </br>
    Following are your credentials and please make sure to reset your password immediately.</br>
    
    Site URL - <a href="<%= Constants.SITE_URL %>"><%= Constants.SITE_URL %></a> [Menu -> Reset Password]</br>
    Username - <i><%= userName%></i> / Password - <i><%= userPw%></i>
    </br>
    ---------------</br>
    Cheers,</br>
    PGDB Admin Team
</p>
<button class="w3-btn w3-round w3-ripple w3-opacity w3-grey btnSendMail">Send Mail</button>

<script>

    $('.btnSendMail').on('click', function () {
        $.ajax({
            type: "POST",
            url: "DataMaintenance",
            data: {paramFullName: '<%= fullName%>', paramUserName: '<%= userName%>', paramUserPw: '<%= userPw%>', paramRecipientEmail: '<%= recipientEmail%>', subAction: "send-reset-pw-mail"},
            success: function (data) {
                $('#pw-reset-mail').hide();
                $(".msg").html(data).show("fast").delay(3000).hide("slow");
            },
            error: function () {
                $('#pw-reset-mail').hide();
                $(".msg").show().html("<div class='alert alert-danger w3-panel w3-round w3-animate-opacity'><p>Error occured while sending mail to " + '<%= recipientEmail%>' + "</p></div>");
            },
            complete: function () {
                hidePagePreload();
            },
            beforeSend: function () {
                $("#preloader").html('');
                showPagePreload();
            }
        });
    });


</script>