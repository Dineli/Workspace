<%-- 
    Document   : base
    Created on : Feb 15, 2016, 2:12:29 PM
    Author     : EPHAADK
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Custom CSS -->
        <link href="css/other.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <div id="sidebar-wrapper">
                <ul class="nav nav-pills nav-stacked"> <!-- db generated-->
                    <li class="active"><a href="#sin" id="008">Tuas, <b>Singapore</b></a></li>
                    <li><a href="#my" id="006">Woodland, <b>Singapore</b></a></li>
                    <li><a href="#cam" id="055">Pailin,<b>Cambodia</b></a></li>
                    <li><a href="#cam1" id="059">Pursat,<b>Cambodia</b></a></li>
                    <li><a href="#ind" id="015">Bali,<b>Indonesia</b></a></li>
                    <li><a href="#ind1" id="014">Batam,<b>Indonesia</b></a></li>
                    <li><a href="#th" id="012">Phuket,<b>Thailand</b></a></li>
                </ul>
            </div>
        </div>
        <div id="page-content-wrapper-loc">
            <div class="page-content">
                <div class="container">
                    <div class="row">
                        <div class="col-md-9">
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
            $.ajax({
                type: "GET",
                url: "location/content.jsp",
                data: {locId: "008"},
                success: function (data) {
                    $("#loc-content").html(data).show();
                }
            });
        }

        $('a').on('click', function () {
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