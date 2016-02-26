<%-- 
    Document   : content
    Created on : Feb 12, 2016, 4:30:04 PM
    Author     : ephaadk
--%>

<%@page import="java.math.BigDecimal"%>
<%@page import="com.nus.mtc.service.impl.SampleServiceImpl"%>
<%@page import="com.nus.mtc.service.ISampleService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.mtc.entity.Samples"%>
<!DOCTYPE html>
<%

    ISampleService iSampleService = new SampleServiceImpl();

    String studyId = request.getParameter("studyId");
    List<Samples> sampleData = iSampleService.getSampleDataByStudyId(studyId);

    System.out.println("============ sampleData================" + sampleData.size());



%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <script>
            var locationPointers = new Array();
            locationPointers = [
            <%for (Samples mapData : sampleData) {%>
                [<%= mapData.getLocationId().getLatitude()%>, <%=mapData.getLocationId().getLongitude()%>, '<%=mapData.getLocationId().getCity()%>', '<%=mapData.getLocationId().getCountryId().getName()%>']
            <%if (sampleData.size() > 0) {%>
                ,
            <% } %>
            <% } %>
            ];

            function initialize() {
                var myOptions = {
                    center: new google.maps.LatLng(33.890542, 151.274856),
                    zoom: 2,
                    minZoom: 1,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("googleMap"), myOptions);
                setMarkers(map, locationPointers)
            }

            function setMarkers(map, locations) {
                var marker, i;
                var content;
                for (i = 0; i < locations.length; i++)
                {
                    var lat = locations[i][0]
                    var long = locations[i][1]
                    var city = locations[i][2]
                    var country = locations[i][3]

                    //for mtc_test_1
                    latlngset = new google.maps.LatLng(long, lat);
                    
                    //for mtc_db
//                    latlngset = new google.maps.LatLng(lat,long);

                    var marker = new google.maps.Marker({
                        map: map, position: latlngset
//                        animation:google.maps.Animation.BOUNCE
                    });
                    map.setCenter(marker.getPosition())

                    content = country;

                        var infowindow = new google.maps.InfoWindow({
                            content: ("" == city)? (content) : (content + " , " + city)
                        });

                    infowindow.open(map, marker);
                    google.maps.event.addDomListener(window, 'idle');

//                    google.maps.event.addListener(marker, 'click', (function (marker, content, infowindow) {
//                        return function () {
//                            infowindow.setContent(content);
//                            infowindow.open(map, marker);
//                        };
//                    })(marker, content, infowindow));
                }
            }
        </script>

    </head>
    <body>
        <div class="panel-group">
            <div class="panel panel-info">
                <!--<div class="panel-heading">HEADER </div>-->
                <div class="panel-body">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#home">Details</a></li>
                        <li><a href="#menu1">Samples</a></li>
                        <li><a href="#menu2">Key Contact</a></li>
                    </ul>

                    <div class="tab-content">
                        <div id="home" class="tab-pane fade in active">
                            <h3></h3>
                            <div id="googleMap" style="width:100%;height:380px;"></div>
                        </div>
                        <div id="menu1" class="tab-pane fade">
                            <!--<h2>Table</h2>-->
                            <div class="table-responsive">          
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Sample ID</th>
                                            <th>Country</th>
                                            <th>Sampling Site</th>
                                            <th>Accession</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%for (Samples sample : sampleData) {%>
                                        <tr>
                                            <td><%= sample.getId()%></td>
                                            <td><%= sample.getLocationId().getCountryId().getName()%></td>
                                            <td><%= sample.getLocationId().getCity()%></td>
                                            <td>01/01/2016</td>
                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div id="menu2" class="tab-pane fade">
                            <h3></h3>
                            <div class="well well-lg">

                                <%if (sampleData != null && sampleData.size() > 0) {%>
                                <p><i class="fa fa-user"></i>&nbsp;&nbsp;<%= sampleData.get(0).getStudyId().getContactName()%> </p>
                                <p><i class="fa fa-info-circle"></i>&nbsp;&nbsp;<%= sampleData.get(0).getStudyId().getContactDetails()%> </p>
                                <p><i class="fa fa-envelope"></i>&nbsp;&nbsp;<%= sampleData.get(0).getStudyId().getContactEmail()%> </p>
                                <%}%>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </body>
</html>

<script type="text/javascript">

    $(document).ready(function () {

        //initializing google maps
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
