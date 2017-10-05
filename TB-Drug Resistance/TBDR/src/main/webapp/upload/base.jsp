<%-- 
    Document   : base
    Created on : Nov 28, 2016, 3:06:41 PM
    Author     : Dineli
--%>

<%@page import="com.nus.tbdr.entity.DataSources"%>
<%@page import="com.nus.tbdr.service.impl.DataSourceServiceImpl"%>
<%@page import="com.nus.tbdr.service.IDataSource"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    IDataSource dsService = new DataSourceServiceImpl();
    List<DataSources> datasourceList = dsService.fetchAllDataSources();

%>


<div class="panel panel-default">
    <div class="panel-heading">Upload Data</div>
    <div class="panel-body">
        <div id ="msg"></div>
        <div class="result"></div>
        <form class="uploadForm form-horizontal" enctype="multipart/form-data">
            <div class="form-group">
                <label class="control-label col-sm-2"> Data Source : </label>
                <div class="col-sm-10">          
                    <select class="form-control ds-dropdown" id="sel1">
                        <option value="0">Please Select...</option>
                        <% for (DataSources ds : datasourceList) {%>     
                        <option value="<%= ds.getId()%>"><%= ds.getName()%></option>
                        <%}%>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-2"> Select File : </label>
                <div class="col-sm-10">          
                    <input type="file" name="filetoupload" onchange="ValidateSingleInput(this);">
                </div>
            </div>

            <div class="form-group">        
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary btn-md btnAdd">Upload File</button>
                </div>
            </div>
            <div id="preloader"></div>
        </form>
        
    </div>
</div>


<script type="text/javascript">
    var _validFileExtensions = [".xlsx", ".xls"];
    function ValidateSingleInput(oInput) {
        if (oInput.type === "file") {
            var sFileName = oInput.value;
            if (sFileName.length > 0) {
                var blnValid = false;
                for (var j = 0; j < _validFileExtensions.length; j++) {
                    var sCurExtension = _validFileExtensions[j];
                    if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() === sCurExtension.toLowerCase()) {
                        blnValid = true;
                        break;
                    }
                }
                if (!blnValid) {
                    $.alert({
                        title: 'Invalid file upload!',
                        content: 'Allowed file extensions are:' + _validFileExtensions.join(", "),
                    });
                    oInput.value = "";
                    return false;
                }
            }
        }
        return true;
    }

    $(document).ready(function (e) {
        $(".uploadForm").on('submit', (function (e) {
            var selectedDSid = $(".ds-dropdown option:selected").val();
            var file = document.getElementsByName("filetoupload")[0].value;
            if(selectedDSid === '0'){
                $.alert({
                    title: 'Invalid file upload!',
                    content: 'Please select a suitable data source.',
                });
            }else if (file === "") {
                $.alert({
                    title: 'Invalid file upload!',
                    content: 'Please upload a file to proceed.',
                });
            } else {
                e.preventDefault();
                $.ajax({
                    url: "UploadtoDB?selectedDSid=" + selectedDSid,
                    type: "POST",
                    data: new FormData(this),
                    contentType: false,
                    cache: false,
                    processData: false,
                    success: function (data) {
                        $(".result").html(data);
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
            return false;
        }));
    });

</script>