<%-- 
    Document   : base
    Created on : Feb 12, 2016, 11:08:21 AM
    Author     : ephaadk
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
                    <li class="active"><a href="#link1" id="001"><p><b>1001:</b> Developing the P. falciparum Community Project with partners in Mali</p></a></li>
                    <li><a href="" id="002"><p><b>1006:</b>  Genome-wide analysis of genetic variation in the Gambia</p></a></li>
                    <li><a href="" id="055"><p><b>1012:</b>   Population genetics of natural populations in Northern Ghana</p></a></li>
                    <li><a href="" id="018"><p><b>1006:</b>   Genome variation and selection in clinical isolates from rural Malawi</p></a></li>
                </ul>

            </div>
            <div id="page-content-wrapper">
                <div class="page-content">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <div id="study-content"></div>
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

        loadFirstStudy();

        function loadFirstStudy() {
            $.ajax({
                type: "GET",
                url: "studies/content.jsp",
                data: {studyId : 001},
                success: function (data) {
                    $("#study-content").html(data).show();
                }
            });
        }

        $('a').on('click', function () {
            var studyId = $(this).attr('id');
            $.ajax({
                type: "GET",
                url: "studies/content.jsp",
                data: {studyId : studyId},
                success: function (data) {
                    $("#study-content").html(data).show();
                }
            });
        });

    });
</script>