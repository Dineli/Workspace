<%-- 
    Document   : content
    Created on : Feb 15, 2016, 2:23:04 PM
    Author     : EPHAADK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    System.out.println("-------------------locId--------------" + request.getParameter("locId"));
    String locId = request.getParameter("locId");


%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- FusionMaps CSS -->
        <script type="text/javascript" src="fusioncharts/fusioncharts.js"></script>
        <script type="text/javascript" src="fusioncharts/themes/fusioncharts.theme.fint.js"></script>
        <%if (locId.equals("008") || (locId.equals("006"))) {%>
        <script type="text/javascript" src="fusioncharts/countriesJS/singaporeLoc.js" ></script>
        <script language="text/javascript">
//            var locationID = <#%=locId%>;
            MYLIBRARY.init([<%=locId%>]);
//            MYLIBRARY.helloWorld();
        </script>
        <% } else if (locId.equals("055")) {%>
        <script type="text/javascript" src="fusioncharts/countriesJS/cambodiaLoc.js"></script>
        <%}%>
    </head>
    <body>
        <div class="panel-group">
            <div class="panel panel-info">
                <div class="panel-heading">HEADER </div>
                <div class="panel-body">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-target="#location" data-toggle="tab">Location</a></li>
                        <li><a data-target="#overview" data-toggle="tab">Overview</a></li>
                        <li><a data-target="#contriSamples" data-toggle="tab">Contributed Samples</a></li>
                    </ul>

                    <div class="tab-content">
                        <!--<div id="location" class="tab-pane fade in active">-->
                        <div class="tab-pane active" id="location">
                            <h3></h3>
                            <div id="chart-container-loc"></div>
                        </div>
                        <div class="tab-pane" id="overview">
                            <!--<h2>Table</h2>-->
                            <div class="table-responsive">          
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Firstname</th>
                                            <th>Lastname</th>
                                            <th>Age</th>
                                            <th>City</th>
                                            <th>Country</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>Anna</td>
                                            <td>Pitt</td>
                                            <td>35</td>
                                            <td>New York</td>
                                            <td>USA</td>
                                        </tr>
                                        <tr>
                                            <td>1</td>
                                            <td>Anna</td>
                                            <td>Pitt</td>
                                            <td>35</td>
                                            <td>New York</td>
                                            <td>USA</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="tab-pane" id="contriSamples">
                            <!--<h3><#%= // locId%></h3>-->
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