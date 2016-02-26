<%-- 
    Document   : content
    Created on : Feb 15, 2016, 2:23:04 PM
    Author     : EPHAADK
--%>

<%@page import="com.nus.mtc.service.impl.LocationServiceImpl"%>
<%@page import="com.nus.mtc.entity.Locations"%>
<%@page import="com.nus.mtc.service.ILocationService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    System.out.println("-------------------locId--------------" + request.getParameter("locId"));
    int locId = Integer.parseInt(request.getParameter("locId"));
    ILocationService iLocationService = new LocationServiceImpl();
    Locations locationData = iLocationService.getLocationDataById(locId);

     System.out.println("-------------------lat--------------" + locationData.getLatitude());
     System.out.println("-------------------long--------------" + locationData.getLongitude());
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
            //for mtc_test_1
            var myCenter = new google.maps.LatLng(<%=locationData.getLongitude()%>,<%= locationData.getLatitude()%>);
            //for mtc_db
//            var myCenter = new google.maps.LatLng(<#%= locationData.getLatitude()%>,<#%=locationData.getLongitude()%>);

            function initialize() {
                var mapProp = {
                    center: myCenter,
                    zoom: 5,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("mapLocation"), mapProp);
                var marker = new google.maps.Marker({
                    position: myCenter,
                });
                marker.setMap(map);
            }
            google.maps.event.addDomListener(window, 'load', initialize);

        </script>
    </head>
    <body>
        <div class="panel-group">
            <div class="panel panel-info">
                <!--<div class="panel-heading">HEADER </div>-->
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
                            <div id="mapLocation" style="width:100%;height:380px;"></div>
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

        initialize();

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