<%-- 
    Document   : home
    Created on : Jun 30, 2017, 4:24:48 PM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.service.ISampleProject"%>
<%@page import="com.nus.pgdb.service.impl.SampleProjectServiceImpl"%>
<%@page import="com.nus.pgdb.entity.JobsCart"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.pgdb.util.Constants"%>
<%@page import="com.nus.pgdb.service.impl.JobsCartServiceImpl"%>
<%@page import="com.nus.pgdb.service.IJobsCart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    int userId = (Integer) session.getAttribute("userId");
    String fullName = (String) session.getAttribute("fullName");
    boolean isAdminUser = (Boolean) session.getAttribute("isAdmin");

    IJobsCart iJobsCartService = new JobsCartServiceImpl();
    long cartLength = iJobsCartService.fetchCartSizeService(userId);
    List<JobsCart> cartItemsList = null;
    String tempPrjName = "";
    StringBuilder selectedSamapleIds = new StringBuilder();

    if (cartLength > 0) {
        cartItemsList = iJobsCartService.fetchCartItemsService(userId);
    }

    String titleString = (isAdminUser) ? "Admin" : "User";

%>

<html>
    <head>
        <link rel="icon" href="images/titleIcon.png" type="image/x-icon">
        <title>PGDB</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" charset="utf-8">
        <link rel="stylesheet" href="css/w3.css">
        <link rel="stylesheet" href="css/custom.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/desktops.css"><!-- required for responsive desktop platform--> 
        <link rel="stylesheet" href="css/jquery-confirm.min.css"><!-- required for Confirm Box --> 
        <link rel="stylesheet" href="css/jquery.dataTables.css"><!-- required for DataTable --> 
        <script src="js/jquery.min.js"></script>
        <script src="js/jquery-1.12.4.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script src="js/custom.js"></script>
        <script src="js/jquery-confirm.min.js"></script><!-- required for Confirm Box --> 
        <script src="js/jquery.dataTables.min.js"></script><!-- required for DataTable --> 
    </head>

    <body class="admin-body">
        <div class="w3-sidebar w3-bar-block w3-card-2 w3-animate-left" style="display:none; transition: .2s" id="leftMenu">
            <button onclick="closeLeftMenu()" class="w3-bar-item w3-button w3-large w3-right-align">&times;</button>
            <a href="#" class="w3-bar-item w3-button w3-opacity w3-wide project"><i class="fa fa-cube" aria-hidden="true"></i>&nbsp;Projects</a>
            <!--<a href="#" class="w3-bar-item w3-button w3-opacity w3-wide sq"><i class="fa fa-cube" aria-hidden="true"></i>&nbsp;SQ Files</a>-->
            <a href="#" class="w3-bar-item w3-button w3-opacity w3-wide pipeline"><i class="fa fa-cube" aria-hidden="true"></i>&nbsp;Pipelines</a>
            <a href="#" class="w3-bar-item w3-button w3-opacity w3-wide analysis"><i class="fa fa-cube" aria-hidden="true"></i>&nbsp;Analysis</a>
            <%if (isAdminUser) {%>
            <a href="#" class="w3-bar-item w3-button w3-opacity w3-wide user"><i class="fa fa-cube" aria-hidden="true"></i>&nbsp;Users</a>
            <%}%>
            <a href="#" class="w3-bar-item w3-button w3-opacity w3-wide resetPw"><i class="fa fa-cube" aria-hidden="true"></i>&nbsp;Reset Password</a>
<!--            <div class="w3-dropdown-hover w3-bar-block w3-wide">
                <a href="#" class="w3-bar-item w3-button w3-opacity w3-wide"><i class="fa fa-cube" aria-hidden="true"></i>&nbsp;Settings <i class="fa fa-caret-down"></i></a>
                <div class="w3-dropdown-content w3-bar-block menuItem-dropdown">
                    <a href="#" class="w3-bar-item w3-button w3-opacity w3-wide resetPw"><i class="fa fa-cube" aria-hidden="true"></i>&nbsp;Reset Password</a>
                    <a href="#" class="w3-bar-item w3-button w3-opacity w3-wide forgotPw"><i class="fa fa-cube" aria-hidden="true"></i>&nbsp;Forgot Password</a>
                </div>
            </div> -->
        </div>

        <div class="w3-sidebar w3-bar-block w3-card-2 w3-animate-right" style="width:25%;right:0;" id="cartMenu">
            <button onclick="closeCartMenu()" class="w3-bar-item w3-button w3-large w3-right-align">&times;</button> 
            <%if ((null != cartItemsList) && (cartItemsList.size() > 0)) {%>
            <button class="w3-button w3-display-topleft w3-wide" onclick="cartRemove(0)">CLEAR</button>
            <div class="w3-card-4 w3-light-grey cartItems">
                <div class="w3-container">
                    <%for (JobsCart item : cartItemsList) {%>
                    <% tempPrjName = (!tempPrjName.equals(item.getProjectName())) ? item.getProjectName() : " ";%>
                    <h4 class="w3-black w3-container w3-opacity-min w3-round"><%=  tempPrjName%></h4>
                    <ul class="w3-ul">
                        <%  //creates the comma seperated sample id string
                            selectedSamapleIds.append(item.getSampleId().getId().concat(","));
                            tempPrjName = item.getProjectName();
                        %>
                        <li class="w3-display-container"><%= item.getSampleId().getName()%><span onclick="this.parentElement.style.display = 'none'; cartRemove('<%= item.getId()%>');" class="w3-button w3-transparent w3-display-right">&times;</span></li>
                    </ul>  
                    <%}%>
                    <% String sampleIds = selectedSamapleIds.toString().substring(0, selectedSamapleIds.length() - 1);%>
                </div>
            </div>
            <button class="w3-btn w3-wide w3-border w3-round w3-teal btnSelectPipeline" onclick="selectPipeline('<%= sampleIds%>')">SELECT A PIPELINE</button>
            <%} else {%>
            <div class="w3-panel w3-border w3-light-grey w3-round-large">
                <p>There are no samples in the cart. Select samples from the project/samples page.</p>
            </div>
            <%}%>
        </div>

        <div class="top-menu-bar-title">
            <button class="w3-button w3-xlarge menu-button main-menu"><i class="fa fa-bars w3-animate-zoom" aria-hidden="true"></i></button>
            <button class="w3-button w3-xlarge menu-button main-cart" onclick="openCartMenu()"><i class="fa fa-shopping-cart w3-animate-zoom" aria-hidden="true"></i> <span id="cartSize" class="w3-badge w3-tiny w3-red w3-display-topright" style="display: none;"></span></button>
            <button class="w3-button w3-xlarge menu-button welcome-tag"><i class="fa fa-user w3-animate-zoom" aria-hidden="true"><font size="2" class="w3-wide w3-opacity"> [<%= fullName%>] </font></i></button>
            <button class="w3-button w3-xlarge menu-button user-logout"><i class="fa fa-sign-out w3-animate-zoom" aria-hidden="true"></i></button>
            <div class="w3-container w3-round w3-wide w3-animate-opacity w3-animate-top logout-msg"><p>You have successfully logged out!</p></div>
            <div class="w3-container">
                <h3 class="w3-wide w3-opacity w3-right-align">PGDB : <%= titleString%> Module</h3>
            </div>
        </div>

        <div class="container"></div>

        <script type="text/javascript">

            getCartSize();
            closeLeftMenu();
            closeCartMenu();
            goTo('projects/base.jsp');

//            function openLeftMenu() {
//                document.getElementById("leftMenu").style.display = "block";
//            }
            function closeLeftMenu() {
                document.getElementById("leftMenu").style.display = "none";
            }

            function openCartMenu() {
                document.getElementById("cartMenu").style.display = "block";

            }
            function closeCartMenu() {
                document.getElementById("cartMenu").style.display = "none";
            }

            $(".w3-button i").hover(function () {
                $(this).toggleClass("w3-spin");
            });

            $('a.project').on('click', function () {
                goTo('projects/base.jsp');
            });

            $('a.pipeline').on('click', function () {
                goTo('pipelines/base.jsp');
            });

            $('a.analysis').on('click', function () {
                goTo('analysis/base.jsp');
            });

            $('a.user').on('click', function () {
                goTo('users/view.jsp');
            });

            $('a.resetPw').on('click', function () {
                goTo('users/resetPw.jsp');
            });
            
            $('a.forgotPw').on('click', function () {
                goTo('users/forgotPw.jsp');
            });            
            
            $('.user-logout').on('click', function () {
                $.ajax({
                    url: "Authentication",
                    type: "GET",
                    data: {subAction: "logout"},
                    success: function () {
                        $('.logout-msg').slideToggle(300).delay(2000).queue(function () {
                            var currentUrl = window.location.href; //http://localhost:9090/pgdb/home.jsp
                            //redirect to login page
                            var redirectUrl = currentUrl.replace("/home.jsp", "");
                            window.location.href = redirectUrl;
                        });
                    }
                });
            });

            function getCartSize() {
                var size = <%= cartLength%>;
                if (size > 0) {
                    document.getElementById("cartSize").innerHTML = +size;
                    $("#cartSize").show();
                } else {
                    $("#cartSize").hide();
                }
            }

            function cartRemove(recordId) {
                $.ajax({
                    type: "POST",
                    url: "DataMaintenance",
                    data: {recordId: recordId, subAction: "cartRemove"},
                    success: function (cartLength) {
                        if (cartLength > 0) {
                            document.getElementById("cartSize").innerHTML = +cartLength;
                        } else {
                            refreshCart();
                            $("#cartSize").hide();
                        }
                    }
                });

            }

            //refresh only the div cart 
            function refreshCart() {
                var url = window.location.href + "home.jsp";
                $("#cartMenu").load(url + " #cartMenu ");
            }

            function selectPipeline(selection) {
                $.ajax({
                    type: "GET",
                    url: "pipelines/base.jsp",
                    data: {selectedSamapleIds: selection, type: "innerPg"},
                    success: function (data) {
                        $(".container").html(data).show();
                    }
                });
            }





            //slide bar close - TODO not working need to test more
            $('.main-menu').click(function (e) {
                document.getElementById("leftMenu").style.display = "block";
                e.stopPropagation();
                $("#leftMenu").toggleClass('bar')
            });

            $('body,html').click(function (e) {
                if ($('#leftMenu').hasClass('bar')) {
                    $("#leftMenu").toggleClass('bar');
                }
            })




        </script>

    </body>
</html>