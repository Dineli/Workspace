<%-- 
    Document   : parameterForm
    Created on : Jun 21, 2017, 9:54:56 PM
    Author     : Dineli
--%>

<%@page import="com.nus.pgdb.util.Utility"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String selectedSampleIds = request.getParameter("sampleIds");
    String currentDate = Utility.getCurrentDateTime("yyyyMdd");
%>

<div class="w3-container w3-animate-left w3-table setParameters">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Set Pipeline Parameters</p>
    </div>
    <div class="w3-container">
        <table class="tbl-add-new">
            <tr>
                <td><label>Pipeline Name :</label></td>
                <td><input class="w3-input" type="text" value="mTB_pipeline_<%= currentDate%>" name="pipelineName"></td>
            </tr>
            <tr>
                <td><label>LB :</label></td>
                <td><input class="w3-input" type="text" value="CTD0113" name="LB"></td>
            </tr>
            <tr>
                <td><label>Coefficient for Downgrading :</label></td>
                <td><input class="w3-input" type="text" value="50" name="coefficient_for_downgrading"></td>
            </tr>
            <tr>
                <td><label>Max Reads per Bam :</label></td>
                <td><input class="w3-input" type="text" value="2000" name="max_reads_per_bam"></td>
            </tr>
            <tr>
                <td><label>Minimum Base Quality :</label></td>
                <td><input class="w3-input" type="text" value="20" name="minimum_base_quality"></td>
            </tr>
            <tr>
                <td><label>Minimum Mapping Quality :</label></td>
                <td><input class="w3-input" type="text" value="5" name="minimum_mapping_quality"></td>
            </tr>

            <tr>
                <td></td>
                <td>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnExecute">EXECUTE PIPELINE</button>
                </td>
            </tr>
        </table>
    </div>
</div>

<div class="w3-container" style=" margin-left: 25%;">
    <div class="w3-panel w3-border w3-padding-large w3-round-large w3-center pipeline-notice">
        <p class="w3-large">Your pipeline has been launched and this will take few minutes. Go to <a href="#" onclick="goTo('analysis/base.jsp');" class="w3-wide"> ANALYSIS</a> to check the progress.</p>
    </div>
</div>

<script>


    $('.btnExecute').on('click', function () {
        $(this).prop('disabled', true);
        var pipelineName = $("input[name=pipelineName]").val();
        var lb = $("input[name=LB]").val();
        var cfd = $("input[name=coefficient_for_downgrading]").val();
        var mrq = $("input[name=max_reads_per_bam]").val();
        var mbq = $("input[name=minimum_base_quality]").val();
        var mmq = $("input[name=minimum_mapping_quality]").val();

        $.ajax({
            type: "POST",
            url: "PipelineJob",
            data: {sampleIds: '<%= selectedSampleIds%>', pipeNameParam: pipelineName, lbParam: lb, cfdParam: cfd, mrqParam: mrq, mbqParam: mbq, mmqParam: mmq},
            success: function (data) {
            },
            beforeSend: function () {
                $(".setParameters").hide();
                $(".pipeline-notice").show();
            }
        });
    });

</script>