<%-- 
    Document   : content
    Created on : Mar 7, 2016, 1:16:43 PM
    Author     : Dineli
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.nus.mtc.service.impl.SampleServiceImpl"%>
<%@page import="com.nus.mtc.service.ISampleService"%>
<%@page import="com.nus.mtc.entity.Locations"%>
<%@page import="com.nus.mtc.service.ILocationService"%>
<%@page import="com.nus.mtc.service.impl.LocationServiceImpl"%>
<%@page import="com.nus.mtc.entity.Studys"%>
<%@page import="com.nus.mtc.entity.Accessions"%>
<%@page import="com.nus.mtc.entity.Samples"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    ILocationService iLocationService = new LocationServiceImpl();
    ISampleService iSampleService = new SampleServiceImpl();
    List<Object[]> sampleDataByCountry = null;
    List<Object[]> countryWithManyLocs = new ArrayList<Object[]>();
    String countryName = "";
    int locationId[];
    int i = 0;

    int countryId = Integer.parseInt(request.getParameter("countryId"));

    List<Locations> countryData = iLocationService.getLocationDataByCountryId(countryId);
    locationId = new int[countryData.size()];
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- GoogleMaps JS -->
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&callback=initialize"></script>
        <script>
            var locationPointers = new Array();
            locationPointers = [
            <%  if (null != countryData && countryData.size() > 0) {
                    for (Locations country : countryData) {
                        countryName = country.getCountryId().getName();
                        locationId[i] = country.getId();
                        i = i + 1;
            %>
                [<%= country.getLatitude()%>, <%=country.getLongitude()%>, '<%=country.getCity()%>', '<%=country.getCountryId().getName()%>']
            <%if (countryData.size() > 0) {%>
                ,
            <% }
                    }
                }
            %>
            ];
            function initialize() {
                var myOptions = {
                    center: new google.maps.LatLng(33.890542, 151.274856),
                    zoom: 5,
                    minZoom: 1,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                var map = new google.maps.Map(document.getElementById("countryMap"), myOptions);
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

                    latlngset = new google.maps.LatLng(long, lat);

                    var marker = new google.maps.Marker({
                        map: map, position: latlngset
                    });
                    map.setCenter(marker.getPosition())

                    content = country;

                    var infowindow = new google.maps.InfoWindow({
                        content: ("" == city) ? (content) : (content + " , " + city)
                    });

                    infowindow.open(map, marker);
                    google.maps.event.addDomListener(window, 'idle');
                }
            }
        </script>
    </head>
    <body>
        <div class="panel-group">
            <div class="panel panel-info">
                <div class="panel-heading"><%= countryName%></div>
                <div class="panel-body">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-target="#country" data-toggle="tab">Country</a></li>
                        <li><a data-target="#contributedSamples" data-toggle="tab">Contributed Samples</a></li>
                    </ul>

                    <div class="tab-content">
                        <div class="tab-pane active" id="country">
                            <h3></h3>
                            <div id="countryMap" style="width:100%;height:380px;"></div>
                        </div>

                        <div class="tab-pane" id="contributedSamples">

                            <%

                                if (locationId.length > 0) {
                                    for (int count = 0; count < locationId.length; count++) {
                                        sampleDataByCountry = iSampleService.getSampleDataByLocationId(locationId[count]);

                                        //country with multiple locations will gets add to a new list
                                        if (locationId.length > 1) {
                                            countryWithManyLocs.addAll(sampleDataByCountry);
                                        }
                                    }
                                }

                                //new list will again gets added to the main list (if only it consists of data) so that
                                //the same loop (to display data) can be used without any modifications
                                if (countryWithManyLocs.size() > 0) {
                                    sampleDataByCountry.clear();
                                    sampleDataByCountry.addAll(countryWithManyLocs);
                                }
                            %>
                            <%if (null != sampleDataByCountry && sampleDataByCountry.size() > 0) {%>
                            <div class="table-responsive">          
                                <table class="table table-custom-main tablesorter">
                                    <thead>
                                        <tr>
                                            <th>Sample ID</th>
                                            <th>Study</th>
                                            <th>Country</th>
                                            <th>Accession</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Object entity[] : sampleDataByCountry) {
                                                Studys study = (Studys) entity[0];
                                                Samples sample = (Samples) entity[1];
                                                Accessions accession = (Accessions) entity[2];
                                                String city = (!sample.getLocationId().getCity().isEmpty()) ? sample.getLocationId().getCity().concat(" - ") : "";
                                        %>
                                        <tr>
                                            <td><%= sample.getId()%></td>
                                            <td><%= study.getId()%></td>
                                            <td><%= city%> <%= sample.getLocationId().getCountryId().getName()%></td>
                                            <%if (null != accession) {%>
                                            <td class="accessionInfo1"><a href="#" id="<%= accession.getSampleId().getId()%>" ><%= accession.getId()%></a></td>
                                                <%} else {%>
                                            <td>-</td>
                                            <%}%>
                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                                <div class="tfootData"></div>  
                            </div>
                            <div class="modal fade" id="myModal2">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title"></h4>
                                        </div>
                                        <div class="modal-body">
                                            <div id="accession-content-loc1"></div>
                                        </div>
                                    </div><!-- /.modal-content -->
                                </div><!-- /.modal-dialog -->
                            </div><!-- /.modal -->
                            <% } else {%>
                            <div class="alert alert-danger" style="margin-top: 10px">
                                <strong> No Sample Data to Display</strong> 
                            </div>
                            <%}%> 
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>


<script type="text/javascript">

    $(document).ready(function () {

//        initialize();

        $(".table-custom-main").tablesorter();

        $('.table-custom-main').paging({limit: 10});

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

        $(".accessionInfo1 a").click(function () {
            var sampleId = $(this).attr('id');
            $("h4.modal-title").html('Run Data for Sample : ' + sampleId);
            $('#myModal2').modal('show');
            $.ajax({
                type: "GET",
                url: "studies/accessionData.jsp",
                data: {sampleId: sampleId},
                success: function (data) {
                    $("#accession-content-loc1").html(data).show();
                }
            });
            //href - stays on the same page 
            return false;
        });

    });



</script>