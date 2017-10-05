<%-- 
    Document   : base
    Created on : Dec 9, 2016, 4:17:23 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="panel panel-default">
    <div class="panel-heading" id="panelText"></div>
    <div class="panel-body">
        <div id="preloader"></div>
        <div id ="msg"></div>
        <div class="variant-data-content"></div>
    </div>
</div>

<script type="text/javascript">

    displayVariantData();

    function displayVariantData() {
        $.ajax({
            type: "GET",
            url: "variants/view.jsp",
            data: {},
            success: function (data) {
                $("#panelText").empty();
                document.getElementById('panelText').innerHTML += 'Variant Details';
                $(".variant-data-content").html(data).show();
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