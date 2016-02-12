<%-- 
    Document   : content
    Created on : Feb 12, 2016, 4:30:04 PM
    Author     : ephaadk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


   <%
       
    String studyId = request.getParameter("studyId");
   
   
   
   %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class="panel-group">
            <div class="panel panel-info">
                <div class="panel-heading">HEADER </div>
                <div class="panel-body">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#home">Details</a></li>
                        <li><a href="#menu1">Samples</a></li>
                    </ul>

                    <div class="tab-content">
                        <div id="home" class="tab-pane fade in active">
                            <h3>HOME</h3>
                            <p><%= studyId%>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                        </div>
                        <div id="menu1" class="tab-pane fade">
                            <h3>Menu 1</h3>
                            <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </body>
</html>

<script type="text/javascript">
    $(document).ready(function () {

        $(".nav-tabs a").click(function () {
            $(this).tab('show');
        });

        $('.nav-pills li a').click(function (e) {
            $('.nav-pills li').removeClass('active');
            var $parent = $(this).parent();
            if (!$parent.hasClass('active')) {
                $parent.addClass('active');
            }
            e.preventDefault();
        });
    });
</script>
