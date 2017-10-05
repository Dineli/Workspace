<%-- 
    Document   : base
    Created on : Dec 12, 2016, 11:12:37 AM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="panel panel-default">
    <div class="panel-heading" id="panelText"></div>
    <div class="panel-body">
        <div id="preloader"></div>
        <div class="dr-data-content"></div>
    </div>
</div>

<script type="text/javascript">

    displayDRData();

    function displayDRData() {
        $.ajax({
            type: "GET",
            url: "drugResistance/view.jsp",
            data: {},
            success: function (data) {
                $("#panelText").empty();
                document.getElementById('panelText').innerHTML += 'Drug Resistance Details';
                $(".dr-data-content").html(data).show();
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
