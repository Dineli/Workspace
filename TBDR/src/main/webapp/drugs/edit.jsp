<%-- 
    Document   : edit
    Created on : Dec 1, 2016, 3:59:17 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String recordId = request.getParameter("recordId");
    String drugName = request.getParameter("drugName");

%>
<div id ="msg"></div>
<form class="form-horizontal">
    <div class="form-group">
        <label class="control-label col-sm-2">Drug Name :</label>
        <div class="col-sm-10">          
            <input type="text" class="form-control" value="<%= drugName%>" id="name">
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
        var updatedDrugName = $('#name').val();
        $.ajax({
            url: "data",
            type: "POST",
            data: {subAction: "drugs-update", drugName: updatedDrugName, recordId: <%=recordId%>},
            cache: false,
            dataType: 'html',
            success: function (data) {
                $('#msg').html(data);
                var response = $('#msg div').attr("class");
                if ("alert alert-success" === response) {
                    $("#msg").html(data).show("fast").delay(1000).hide("slow", function () {
                        displayData();
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
            url: "drugs/base.jsp",
            data: {},
            success: function (data) {
                $(".container").html(data).show();
            }
        });
    });
</script>