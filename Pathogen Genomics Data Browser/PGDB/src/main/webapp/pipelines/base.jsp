<%-- 
    Document   : base
    Created on : May 31, 2017, 11:13:05 AM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String sampleIds = request.getParameter("selectedSamapleIds");
    String type = request.getParameter("type");
%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-row-padding pipeline-boxes" style="padding-left: 15% !important" >
        <div class="w3-third" >
            <div class="w3-card-4 w3-light-gray w3-margin-bottom">
                <header class="w3-container w3-pale-blue">
                    <h3 class="w3-wide">mTB_pipeline</h3>
                </header>
                <div class="w3-container  w3-margin-bottom">
                    <p>SNV detection pipeline for paired-end sequencing samples from Mycobacterium tuberculosis (H37Rv)</p>
                </div>
                <div class="w3-container" style="margin-top: -6%;">
                    <!--button will be visible only when executing a pipeline-->
                    <%if (type.equals("innerPg")) {%>
                    <button class="w3-button w3-blue w3-right" onclick="showPipelineParameters('<%= sampleIds%>');">SELECT</button>
                    <%}%>
                </div>
            </div>
        </div>
        <div class="w3-third">
            <div class="w3-card-4 w3-light-gray w3-margin-bottom">
                <header class="w3-container w3-pale-blue">
                    <h3>Sample Pipeline 2</h3>
                </header>
                <div class="w3-container w3-margin-bottom">
                    <p>Pipeline Description goes here .....</p>
                </div>
            </div>
        </div>
        <div class="w3-third">
            <div class="w3-card-4 w3-light-gray w3-margin-bottom">
                <header class="w3-container w3-pale-blue">
                    <h3>Sample Pipeline 2</h3>
                </header>
                <div class="w3-container w3-margin-bottom">
                    <p>Pipeline Description goes here .....</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script>

    function showPipelineParameters(selectedIds) {
        $.ajax({
            type: "GET",
            url: "pipelines/parameterForm.jsp",
            data: {sampleIds: selectedIds},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    }

</script>
