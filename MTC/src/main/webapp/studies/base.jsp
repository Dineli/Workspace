<%-- 
    Document   : base
    Created on : Feb 12, 2016, 11:08:21 AM
    Author     : Dineli
--%>

<%@page import="com.nus.mtc.service.impl.SampleServiceImpl"%>
<%@page import="com.nus.mtc.service.ISampleService"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.mtc.entity.Samples"%>
<%@page import="com.nus.mtc.service.impl.StudyServiceImpl"%>
<%@page import="com.nus.mtc.service.IStudy"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    ISampleService iSampleService = new SampleServiceImpl();

    List<Samples> sampleList = iSampleService.getUniqueSampleDataGroupByStudy();
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- GoogleMaps JS -->
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
        <!--<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&callback=initialize"></script>-->
    </head>
    <% if (null != sampleList && sampleList.size() > 0) {%>
    <body>
        <div id="wrapper">
            <div id="sidebar-wrapper">
                <ul class="nav nav-pills nav-stacked studyMenu"> 
                    <%for (Samples sample : sampleList) {%>
                    <li class="<%=sample.getLocationId().getCountryId().getId()%>"><a class="study_info" href="#" id="<%=sample.getStudyId().getId()%>"><p><b><%=sample.getStudyId().getId()%>:</b>  <%=sample.getStudyId().getName()%></p></a></li>
                                    <% }%>
                </ul>
            </div>
            <div id="page-content-wrapper-study">
                <div class="page-content">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <div id="preloader"></div>
                                <div id="study-content"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } else {%>
        <div class="alert alert-danger">
            <strong> No Study Data to Display</strong> 
        </div>
        <%}%>
    </body>
</html>


<script type="text/javascript">

    $(document).ready(function () {

        loadFirstStudy();

        function loadFirstStudy() {
    <%
        if (null != sampleList && sampleList.size() > 0) {
            int countryId = sampleList.get(0).getLocationId().getCountryId().getId();
    %>
            $('li.<%=countryId%>').addClass('active');
            $.ajax({
                type: "GET",
                url: "studies/content.jsp",
                data: {studyId: '<%= sampleList.get(0).getStudyId().getId()%>'},
                success: function (data) {
                    $("#study-content").html(data).show();
                }
            });
    <%}%>
        }

        $('a.study_info').on('click', function () {
            var studyId = $(this).attr('id');
            $.ajax({
                type: "GET",
                url: "studies/content.jsp",
                data: {studyId: studyId},
                success: function (data) {
                    $("#study-content").html(data).show();
                },
                complete: function () {
                    hidePagePreload();
                },
                beforeSend: function () {
                    $("#preloader").html('');
                    showPagePreload();
                }
            });
        });

        function showPagePreload() {
            $("#preloader").append("<div class='appender'><p style='text-align:center;'><img src='images/pgLoader.gif' /><br/>Loading Data...</p></div>");
        }

        function hidePagePreload() {
            $(".appender").html("");
        }
    });
</script>