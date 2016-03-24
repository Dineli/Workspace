<%-- 
    Document   : base
    Created on : Mar 7, 2016, 11:26:59 AM
    Author     : Dineli
--%>

<%@page import="java.util.List"%>
<%@page import="com.nus.mtc.entity.Countrys"%>
<%@page import="com.nus.mtc.service.impl.LocationServiceImpl"%>
<%@page import="com.nus.mtc.service.ILocationService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%

    ILocationService iLocationService = new LocationServiceImpl();
    List<Countrys> countryList = iLocationService.getAllCountrys();

%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Custom CSS -->
        <link href="css/other.css" rel="stylesheet">
        <!-- GoogleMaps JS -->
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
    </head>
    <% if (null != countryList && countryList.size() > 0) {%>
    <body>
        <div id="wrapper">
            <div class="mobile-sidebar">
                <div id="sidebar-wrapper">
                    <ul class="nav nav-pills nav-stacked studyMenu"> 
                        <%for (Countrys country : countryList) {%>
                        <li class="<%= country.getId()%>">
                            <a href="#" class="country_info" id="<%= country.getId()%>"><b><%= country.getName()%></b></a>
                        </li>
                        <%}%>
                    </ul>
                </div>
            </div>
        </div>
        <div id="page-content-wrapper-loc">
            <div class="page-content">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div id="country-content"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } else {%>
        <div class="alert alert-danger">
            <strong> No Country Data to Display</strong> 
        </div>
        <%}%>
    </body>
</html>


<script type="text/javascript">
    $(document).ready(function () {

        loadFirstLoc();

        function loadFirstLoc() {
    <%
        if (null != countryList && countryList.size() > 0) {
            int countryId = countryList.get(0).getId();
    %>
            $('li.<%=countryId%>').addClass('active');
            $.ajax({
                type: "GET",
                url: "country/content.jsp",
                data: {countryId: <%=countryId%>},
                success: function (data) {
                    $("#country-content").html(data).show();
                }
            });
    <%}%>
        }

        $('a.country_info').on('click', function () {
            var countryId = $(this).attr('id');
            $.ajax({
                type: "GET",
                url: "country/content.jsp",
                data: {countryId: countryId},
                success: function (data) {
                    $("#country-content").html(data).show();
                }
            });
            return false;
        });

    });
</script>