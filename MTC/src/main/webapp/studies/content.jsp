<%-- 
    Document   : content
    Created on : Feb 12, 2016, 4:30:04 PM
    Author     : Dineli
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.nus.mtc.entity.Accessions"%>
<%@page import="com.nus.mtc.entity.Studys"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.nus.mtc.service.impl.SampleServiceImpl"%>
<%@page import="com.nus.mtc.service.ISampleService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.mtc.entity.Samples"%>
<!DOCTYPE html>
<%
    Studys study = new Studys();
    ISampleService iSampleService = new SampleServiceImpl();

    String studyId = request.getParameter("studyId");

    List<Object[]> sampleData = iSampleService.getSampleDataByStudyId(studyId);

    if (null != sampleData && sampleData.size() > 0) {
        for (Object entity[] : sampleData) {
            study = (Studys) entity[0];
        }
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
            var locationPointers = new Array();
            locationPointers = [
            <%  if (null != sampleData && sampleData.size() > 0) {
                    for (Object entity[] : sampleData) {
                        Samples mapData = (Samples) entity[1];
            %>
                [<%= mapData.getLocationId().getLatitude()%>, <%=mapData.getLocationId().getLongitude()%>, '<%=mapData.getLocationId().getCity()%>', '<%=mapData.getLocationId().getCountryId().getName()%>']
            <%if (sampleData.size() > 0) {%>
                ,
            <% }
                    }
                }
            %>
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

                    latlngset = new google.maps.LatLng(long, lat);

                    var marker = new google.maps.Marker({
                        map: map, position: latlngset
//                        animation:google.maps.Animation.BOUNCE
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
                <div class="panel-heading"><%= study.getId()%> || <%= study.getName()%></div>
                <div class="panel-body">
                    <%if (null != sampleData && sampleData.size() > 0) {%>
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#home">Details</a></li>
                        <li><a data-target="#menu1" data-toggle="tab">Samples</a></li>
                        <li><a data-target="#menu2" data-toggle="tab">Key Contact</a></li>
                    </ul>

                    <div class="tab-content">
                        <div id="home" class="tab-pane fade in active">
                            <div id="googleMap" style="width:100%;height:380px;"></div>
                        </div>
                        <div id="menu1" class="tab-pane fade">

                            <div class="table-responsive">          
                                <table class="table table-custom-main tablesorter">
                                    <thead>
                                        <tr>
                                            <th>Sample ID</th>
                                            <th>Country</th>
                                            <th>Sampling Site</th>
                                            <th>Accession</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%for (Object entity[] : sampleData) {
                                                Samples sample = (Samples) entity[1];
                                                Accessions accession = (Accessions) entity[2];
                                        %>
                                        <tr>
                                            <td class="col-md-1"><%= sample.getId()%></td>
                                            <td class="col-md-1"><%= sample.getLocationId().getCountryId().getName()%></td>
                                            <td class="col-md-2"><%if (true == sample.getLocationId().getCity().isEmpty()) {%> - <%} else {%><%= sample.getLocationId().getCity()%><%}%></td>
                                            <%if (null != accession) {%>
                                            <td class="col-md-3 accData"><a href="#" id="<%= accession.getSampleId().getId()%>"><%= accession.getId()%></a></td>
                                                <%} else {%>
                                            <td>-</td>
                                            <%}%>
                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                                <div class="tfootData"></div>  
                            </div>
                            <div class="modal fade" id="myModal">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title"></h4>
                                        </div>
                                        <div class="modal-body">
                                            <div id="accession-content"></div>
                                        </div>
                                    </div><!-- /.modal-content -->
                                </div><!-- /.modal-dialog -->
                            </div><!-- /.modal -->
                        </div>
                        <div id="menu2" class="tab-pane fade">
                            <h3></h3>
                            <div class="well well-lg">
                                <%if (sampleData != null && sampleData.size() > 0) {%>
                                <p><i class="fa fa-user"></i>&nbsp;&nbsp;<%= study.getContactName()%> </p>
                                <p><i class="fa fa-info-circle"></i>&nbsp;&nbsp;<%= study.getContactDetails()%> </p>
                                <p><i class="fa fa-envelope"></i>&nbsp;&nbsp;<%= study.getContactEmail()%> </p>
                                <%}%>
                            </div>
                        </div>
                    </div>
                    <% } else {%>
                    <div class="alert alert-danger" style="margin-top: 10px">
                        <strong> No Sample Data to Display</strong> 
                    </div>
                    <%}%> 
                </div>
            </div>
        </div>
    </body>
</html>

<script type="text/javascript">

    $(document).ready(function () {

        //initializing google maps
        initialize();

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

        $(".accData a").click(function () {
            var sampleId = $(this).attr('id');
            $("h4.modal-title").html('Run Data for Sample : ' + sampleId);
            $('#myModal').modal('show');
            $.ajax({
                type: "GET",
                url: "studies/accessionData.jsp",
                data: {sampleId: sampleId},
                success: function (data) {
                    $("#accession-content").html(data).show();
                }
            });
            //href - stays on the same page 
            return false;
        });

    });



</script>
