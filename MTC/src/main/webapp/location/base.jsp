<%-- 
    Document   : base
    Created on : Feb 15, 2016, 2:12:29 PM
    Author     : Dineli
--%>

<%@page import="java.util.List"%>
<%@page import="com.nus.mtc.entity.Locations"%>
<%@page import="com.nus.mtc.service.impl.LocationServiceImpl"%>
<%@page import="com.nus.mtc.service.ILocationService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%

    ILocationService iLocationService = new LocationServiceImpl();
    List<Locations> locationList = iLocationService.getAllLocationData();

%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Custom CSS -->
        <link href="css/other.css" rel="stylesheet">
        <!-- GoogleMaps JS -->
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
    </head>
    <body>
        <div id="wrapper">
            <div id="sidebar-wrapper">
                <ul class="nav nav-pills nav-stacked studyMenu"> 
                    <%for (Locations location : locationList) {%>
                    <li class="<%= location.getId()%>">
                        <a href="#" class="loc_info" id="<%= location.getId()%>"><%= location.getCity()%> <b><%= location.getCountryId().getName()%></b></a>
                    </li>
                    <%}%>
                </ul>
            </div>
        </div>
        <div id="page-content-wrapper-loc">
            <div class="page-content">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div id="loc-content"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>


<script type="text/javascript">
    $(document).ready(function () {

        loadFirstLoc();

        function loadFirstLoc() {
    <%
        if (null != locationList && locationList.size() > 0) {
            int locId = locationList.get(0).getCountryId().getId();
    %>
            $('li.<%=locId%>').addClass('active');

            $.ajax({
                type: "GET",
                url: "location/content.jsp",
                data: {locId: <%=locationList.get(0).getId()%>},
                success: function (data) {
                    $("#loc-content").html(data).show();
                }
            });
    <%}%>
        }

        $('a.loc_info').on('click', function () {
            var locId = $(this).attr('id');
            $.ajax({
                type: "GET",
                url: "location/content.jsp",
                data: {locId: locId},
                success: function (data) {
                    $("#loc-content").html(data).show();
                }
            });
        });

    });
</script>