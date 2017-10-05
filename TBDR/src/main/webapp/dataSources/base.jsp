<%-- 
    Document   : base
    Created on : Dec 2, 2016, 4:06:22 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="panel panel-default">
    <div class="panel-heading" id="panelText"></div>
    <div class="panel-body">
        <div id="preloader"></div>
        <div class="data-source-content"></div>
    </div>
</div>

<script type="text/javascript">

    displayDSData();

    function displayDSData() {
        $.ajax({
            type: "GET",
            url: "dataSources/view.jsp",
            data: {},
            success: function (data) {
                $("#panelText").empty();
                document.getElementById('panelText').innerHTML += 'Data Source Details';
                $(".data-source-content").html(data).show();
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

</script>
