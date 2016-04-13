<%-- 
    Document   : base
    Created on : Apr 6, 2016, 10:16:42 AM
    Author     : Dineli
--%>
<%@page import="com.nus.mtc.entity.Drugs"%>
<%@page import="com.nus.mtc.service.impl.DrugServiceImpl"%>
<%@page import="com.nus.mtc.service.IDrugs"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    IDrugs idrugService = new DrugServiceImpl();
    List<String> drugNames = idrugService.getAllDrugNames();
    List<String> locusNames = idrugService.getAllLocusNames();

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    </head>
    <body>
        <div class="panel-body">
            <fieldset class="scheduler-border">
                <legend class="scheduler-border">Search By:</legend>
                <!--<div class="ui-widget">-->
                <div class="radio">
                    <label><input type="radio" name="optradio" value="1" checked />View All</label>
                </div>
                <div class="radio">
                    <label><input type="radio" name="optradio" value="2" />Drug</label>
                    <input type="text" id="drugName"/>
                </div>
                <div class="radio">
                    <label><input type="radio" name="optradio" value="3" />Locus Name</label>
                    <input type="text" id="locusName"/>
                </div>
                <input type="button" class="btn btn-info" value="Search">
                <!--</div>-->
            </fieldset>
        </div>

        <div class="page-content">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div id="preloader"></div>
                        <div id="search-content"></div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>


<script type="text/javascript">
    $(document).ready(function () {

        disableTxtBoxes();

        $('.btn-info').on('click', function () {
            var selectedOption = $("input[name=optradio]:checked").val();
            var drugName = $("#drugName").val();
            var locusName = $("#locusName").val();
            $.ajax({
                type: "GET",
                url: "search/content.jsp",
                data: {selectedOption: selectedOption, drugName: drugName, locusName: locusName},
                success: function (data) {
                    $("#search-content").html(data).show();
                },
                complete: function () {
                    hidePagePreload();
                },
                beforeSend: function () {
                    $("#preloader").html('');
                    showPagePreload();
                }
            });
            return false;
        });

        function showPagePreload() {
            $("#preloader").append("<div class='appender'><p style='text-align:center;'><img src='images/pgLoader.gif' /><br/>Loading Data...</p></div>");
        }

        function hidePagePreload() {
            $(".appender").html("");
        }

        //auto-complete    
        $(function () {
            var drugAbbrevations = new Array();
            var locusNameList = new Array();

    <% if (null != drugNames && drugNames.size() > 0) {
            for (String drug : drugNames) {%>
            drugAbbrevations.push("<%=drug%>");
    <%}%>
    <%}%>

    <% if (null != locusNames && locusNames.size() > 0) {
            for (String locus : locusNames) {%>
            locusNameList.push("<%=locus%>");
    <%}%>
    <%}%>

            $("#drugName").autocomplete({
                source: drugAbbrevations
            });

            $("#locusName").autocomplete({
                source: locusNameList
            });
        });

        //validating the search function
        $(function () {
            $('input[type="radio"]').click(function () {
                if ($(this).is(':checked'))
                {
                    if ($(this).val() == 1) {
                        clearTxtBoxes();
                        disableTxtBoxes();
                    } else if ($(this).val() == 2) {
                        clearTxtBoxes();
                        $("#drugName").removeAttr("disabled");
                        $("#locusName").attr("disabled", "disabled");
                    } else if ($(this).val() == 3) {
                        clearTxtBoxes();
                        $("#locusName").removeAttr("disabled");
                        $("#drugName").attr("disabled", "disabled");
                    }
                }
            });
        });

        function clearTxtBoxes() {
            $("#drugName").val("");
            $("#locusName").val("");
        }

        function disableTxtBoxes() {
            $("#locusName").attr("disabled", "disabled");
            $("#drugName").attr("disabled", "disabled");
        }




    });
</script>
