<%-- 
    Document   : content
    Created on : Feb 12, 2016, 4:30:04 PM
    Author     : ephaadk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<%

    String studyId = request.getParameter("studyId");
    System.out.println("============ studyId================"+studyId);
    


%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- FusionMaps CSS -->
        <script type="text/javascript" src="fusioncharts/fusioncharts.js"></script>
        <script type="text/javascript" src="fusioncharts/themes/fusioncharts.theme.fint.js"></script>
        <%if (studyId.equals("001")){%>
        <script type="text/javascript" src="fusioncharts/countriesJS/singapore.js"></script>
        <% }else{%>
        <script type="text/javascript" src="fusioncharts/countriesJS/thailand.js"></script>
        <%}%>
    
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
                            <h3></h3>
                            <div id="chart-container"></div>
                        </div>
                        <div id="menu1" class="tab-pane fade">
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
