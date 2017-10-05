<%-- 
    Document   : header
    Created on : Jan 26, 2017, 3:08:50 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link rel="icon" href="images/titleImg.png" type="image/x-icon">
        <title>TBDR</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.min.css"><!-- required for Bootstrap--> 
        <link rel="stylesheet" href="css/custom.css"><!-- project specific css -->
        <link rel="stylesheet" href="css/font-awesome.min.css"><!-- required for Font Awesome--> 
        <link rel="stylesheet" href="css/jquery-confirm.min.css"><!-- required for Confirm Box--> 
        <link rel="stylesheet" href="css/jquery.dataTables.css"><!-- required for Data Table-->
        <link rel="stylesheet" href="css/bootstrap-multiselect.css"/><!-- required for Multi select option--> 
        <link rel="stylesheet" href="css/mobileNdesktops.css"/><!-- required for responsive mobile and desktop platform--> 
        <script src="js/jquery-1.12.4.js"></script><!-- required for Data Table--> 
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script><!-- required for Bootstrap--> 
        <script src="js/custom.js"></script><!-- project specific js -->
        <script src="js/jquery-confirm.min.js"></script><!-- required for Confirm Box--> 
        <script src="js/jquery.dataTables.min.js"></script><!-- required for Data Table--> 
        <script src="js/bootstrap-multiselect.js"></script><!-- required for Multi select option--> 
    </head>

    <%
        String loggedUser = null;

        if (session.getAttribute("name") != null) {
            loggedUser = String.valueOf(session.getAttribute("name"));
        }
    %>

    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>                        
                    </button>
                    <a class="navbar-brand" href="#">TB Drug Resistance</a>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                        <li><a class="vari" href="#"><span class="glyphicon glyphicon-flash"></span> Variants</a></li>
                        <!--ds view is visible for the public users without the maintenance functions-->
                        <%if (loggedUser == null) {%>
                        <li><a class="ds" href="#"><span class="glyphicon glyphicon-file"></span> Data Sources</a></li>
                            <%} else { %>
                        <li><a style="display: none;" href="#">Data Sources</a></li>
                            <%}%>
                        <li><a class="search" href="#"><span class="glyphicon glyphicon-search"></span> Search</a></li>
                        <!--<li><a class="search1" href="#">Search1</a></li>-->
                        <%if (loggedUser != null) {%>
                        <li><a class="upload" href="#"><span class="glyphicon glyphicon-upload"></span> Upload</a></li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-edit"></span> Data Maintenance <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a class="drugs" href="#">Drugs</a></li>
                                <li><a class="ds" href="#">Data Sources</a></li>
                                <!--<li><a class="dr" href="#">Drug Resistance</a></li>-->
                            </ul>
                        </li>
                        <li><a class="user-logout" href="#"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                        <p class="welcome-tag">Welcome <%=loggedUser%>!</p>
                        <%}%>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <%if (loggedUser == null) {%>
                            <li><a class="user-login" href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                        <%}%>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container">
            <div id="preloader"></div>
        </div>

    </body>
</html>



<script type="text/javascript">

    showVariants();

    function showVariants() {
        $.ajax({
            type: "GET",
            url: "variants/base.jsp",
            data: {},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    }

    $('a.vari').on('click', function () {
        showVariants();
    });

    $('a.search').on('click', function () {
        $.ajax({
            type: "GET",
            url: "search/base1.jsp",
            data: {},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });

    $('a.search1').on('click', function () {
        $.ajax({
            type: "GET",
            url: "search/base.jsp",
            data: {},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });

    $('a.upload').on('click', function () {
        $.ajax({
            type: "GET",
            url: "upload/base.jsp",
            data: {},
            success: function (data) {
                $(".container").html(data).show();
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

    $('a.drugs').on('click', function () {
        $.ajax({
            type: "GET",
            url: "drugs/base.jsp",
            data: {},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });

    $('a.ds').on('click', function () {
        $.ajax({
            type: "GET",
            url: "dataSources/base.jsp",
            data: {},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });

    $('a.user-login').on('click', function () {
        $.ajax({
            type: "GET",
            url: "login/base.jsp",
            data: {},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });

    $('a.user-logout').on('click', function () {
        $.ajax({
            url: "Authentication",
            type: "GET",
            data: {subAction: "logout"},
            success: function (data) {
                $('#msg').html(data);
                $("#msg").html(data).show("fast").delay(3000).hide("slow", function () {
                    location.reload();
                });
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

    $('.navbar-nav').on('click', 'li', function () {
        $(this).addClass('active').siblings().removeClass('active');
    });



</script>