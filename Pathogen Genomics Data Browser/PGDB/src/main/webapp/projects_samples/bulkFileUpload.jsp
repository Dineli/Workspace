<%-- 
    Document   : bulkFileUpload
    Created on : Sep 13, 2017, 11:07:09 AM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    int projectId = Integer.parseInt(request.getParameter("prjId"));
    String projectName = request.getParameter("prjName");
%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Multiple File Upload</p>
        <p class="w3-right"><button class="w3-button w3-padding-small w3-circle w3-black" onclick="goBack('projects_samples/base.jsp', '<%= projectId%>', '<%= projectName%>', '');"><i class="fa fa-arrow-left" aria-hidden="true"></i></button></p>
    </div>
    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p> </p>
        </div>
    </div>

    <div class="w3-container">
        <table class="tbl-add-new">
            <tr>
                <td><label>Select Text File :</label></td>
                <td><input type="file" id="txtFile" onchange="uploadTxtFile(this);">
                    <a href="#" id="txtFileImg" title="" >     
                        <i class="fa fa-question-circle fa-lg" aria-hidden="true"></i>
                    </a>          
                </td>
            </tr>
            <tr class="compressFileUploader" style="display: none">
                <td><label>Select Multiple File :</label></td>
                <td><input type="file" id="zipFile" name="files[]" multiple onchange="uploadBulkFiles(this);"/></td>
            </tr>
        </table>
    </div>
</div>

<script>

    function uploadTxtFile(value) {
        var validFileExtensions = [".txt"];
        var status = validateUpload(value, validFileExtensions);
        var txtFileName = $('#txtFile').val().split('\\').pop();

        if (status) {
            var formData = new FormData();
            formData.append('file', $('input[type=file]')[0].files[0]);
            $.ajax({
                url: "FileUpload?fileName=" + txtFileName + "&subAction=textFileUpload",
                type: "POST",
                data: formData,
                contentType: false,
                cache: false,
                processData: false,
                success: function (data) {
                    $("#preloader").show().html(data);
                    var response = $('#preloader div').attr("class");
                    if (response.includes("alert-success")) {
                        $("#preloader").html(data).show("fast").delay(1000, function () {
                            $(".compressFileUploader").show();
                        });
                    }
                },
                complete: function () {
                    hidePagePreload();
                },
                beforeSend: function () {
                    $("#preloader").html('');
                    showPagePreload();
                }
            });
        }
    }


    function uploadBulkFiles(value) {
        var validFileExtensions = [".fastq", ".fq", ".gz"];
        var status = validateUpload(value, validFileExtensions);
        var txtFileName = $('#txtFile').val().split('\\').pop();
        if (status) {
            var formData = new FormData();
            $.each($('#zipFile')[0].files, function (k, value)
            {
                formData.append(k, value);
            });
            $.ajax({
                url: "FileUpload?fileName=" + txtFileName + "&subAction=bulkFileUpload",
                type: "POST",
                data: formData,
                contentType: false,
                cache: false,
                processData: false,
                success: function (data) {
                    $("#preloader").show().html(data);
//                    $('input[type=file]').val('');
                    var response = $('#preloader div').attr("class");
                    if (response.includes("alert-success")) {
                        $("#preloader").html(data).show("fast").delay(1000, function () {
//                            $(".compressFileUploader").show();
                        });
                    }
                },
                complete: function () {
                    hidePagePreload();
                },
                beforeSend: function () {
                    $("#preloader").html('');
                    showPagePreload();
                }
            });
        }
    }

    $("#txtFileImg").tooltip({content: '<img src="images/upload_file_format.png" />'});


</script>