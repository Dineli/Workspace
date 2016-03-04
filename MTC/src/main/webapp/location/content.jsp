<%-- 
    Document   : content
    Created on : Feb 15, 2016, 2:23:04 PM
    Author     : Dineli
--%>

<%@page import="com.nus.mtc.entity.Studys"%>
<%@page import="com.nus.mtc.entity.Accessions"%>
<%@page import="com.nus.mtc.entity.Samples"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.mtc.service.impl.SampleServiceImpl"%>
<%@page import="com.nus.mtc.service.ISampleService"%>
<%@page import="com.nus.mtc.service.impl.LocationServiceImpl"%>
<%@page import="com.nus.mtc.entity.Locations"%>
<%@page import="com.nus.mtc.service.ILocationService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    System.out.println("-------------------locId--------------" + request.getParameter("locId"));
    ILocationService iLocationService = new LocationServiceImpl();
    ISampleService iSampleService = new SampleServiceImpl();

    int locId = Integer.parseInt(request.getParameter("locId"));

    Locations locationData = iLocationService.getLocationDataById(locId);

    List<Object[]> sampleData4location = iSampleService.getSampleDataByLocationId(locId);

    String city = (!locationData.getCity().isEmpty()) ? (" || ").concat(locationData.getCity()) : "";


%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
            //for mtc_test_1
            var myCenter = new google.maps.LatLng(<%=locationData.getLongitude()%>,<%= locationData.getLatitude()%>);
            var map;
            //for mtc_db
//            var myCenter = new google.maps.LatLng(<#%= locationData.getLatitude()%>,<#%=locationData.getLongitude()%>);

            function initialize() {
                var mapProp = {
                    center: myCenter,
                    zoom: 5,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                map = new google.maps.Map(document.getElementById("mapLocation"), mapProp);
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
                <div class="panel-heading"><%= locationData.getCountryId().getName()%>  <%= city%> </div>
                <div class="panel-body">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-target="#location" data-toggle="tab">Location</a></li>
                        <li><a data-target="#contriSamples" data-toggle="tab">Contributed Samples</a></li>
                    </ul>

                    <div class="tab-content">
                        <!--<div id="location" class="tab-pane fade in active">-->
                        <div class="tab-pane active" id="location">
                            <h3></h3>
                            <div id="mapLocation" style="width:100%;height:380px;"></div>
                        </div>

                        <div class="tab-pane" id="contriSamples">
                            <%if (null != sampleData4location && sampleData4location.size() > 0) {%>
                            <div class="table-responsive">          
                                <table class="table table-striped table-custom-main">
                                    <thead>
                                        <tr>
                                            <th>Sample ID</th>
                                            <th>Study</th>
                                            <th>Country</th>
                                            <th>Accession</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Object entity[] : sampleData4location) {
                                                Studys study = (Studys) entity[0];
                                                Samples sample = (Samples) entity[1];
                                                Accessions accession = (Accessions) entity[2];
                                        %>
                                        <tr>
                                            <td><%= sample.getId()%></td>
                                            <td><%= study.getId()%></td>
                                            <td><%= sample.getLocationId().getCountryId().getName()%></td>
                                            <td class="accessionInfo"><a href="#" id="<%= accession.getSampleId().getId()%>" ><%= accession.getId()%></a></td>
                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                            </div>
                            <div class="modal fade" id="myModal1">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title"></h4>
                                        </div>
                                        <div class="modal-body">
                                            <div id="accession-content-loc"></div>
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

        $(".accessionInfo a").click(function () {
            var sampleId = $(this).attr('id');
            $("h4.modal-title").html('Run Data for Sample : ' + sampleId);
            $('#myModal1').modal('show');
            $.ajax({
                type: "GET",
                url: "studies/accessionData.jsp",
                data: {sampleId: sampleId},
                success: function (data) {
                    $("#accession-content-loc").html(data).show();
                }
            });
            //href - stays on the same page 
            return false;
        });

    });



</script>