<%-- 
    Document   : edit
    Created on : Dec 2, 2016, 4:06:52 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String recordId = request.getParameter("recordId");
    String dsName = request.getParameter("editName");
    String dsDesc = request.getParameter("editDesc");
%>
<div id ="msg"></div>
<form class="form-horizontal">
    <div class="form-group">
        <label class="control-label col-sm-2">Name :</label>
        <div class="col-sm-10">          
            <input type="text" class="form-control" id="editedDsName" value="<%= dsName%>">
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2">Description :</label>
        <div class="col-sm-10">          
            <textarea class="form-control" rows="3" id="editedDsDesc"><%= dsDesc%></textarea>
        </div>
    </div>
    <div class="form-group">        
        <div class="col-sm-offset-2 col-sm-10">
            <button type="button" class="btn btn-default btnUpdate">Update</button>
            <button type="button" class="btn btn-default btnback">Back</button>
        </div>
    </div>
</form>

<script type="text/javascript">

    $('.btnUpdate').on('click', function () {
        var updatedDsName = $('#editedDsName').val();
        var updatedDsDesc = $('#editedDsDesc').val();

        $.ajax({
            url: "data",
            type: "POST",
            data: {subAction: "ds-update", dsName: updatedDsName, dsDesc: updatedDsDesc, recordId: <%=recordId%>},
            cache: false,
            dataType: 'html',
            success: function (data) {
                $('#msg').html(data);
                var response = $('#msg div').attr("class");
                if ("alert alert-success" === response) {
                    $("#msg").html(data).show("fast").delay(1000).hide("slow", function () {
                        displayDSData();
                    });
                } else {
                    $('#msg').show("fast");
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

    });

    $('.btnback').on('click', function () {
        $.ajax({
            type: "GET",
            url: "dataSources/base.jsp",
            data: {},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });

</script>
