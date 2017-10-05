<%-- 
    Document   : base
    Created on : Dec 1, 2016, 9:56:05 AM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
 
<div class="panel panel-default">
    <div class="panel-heading" id="panelText"></div>
    <div class="panel-body">
        <div id="preloader"></div>
        <div class="drug-data-content"></div>
    </div>
</div>

<script type="text/javascript">
    
    displayData();
    
    function displayData(){
        $.ajax({
            type: "GET",
            url: "drugs/view.jsp",
            data: {},
            success: function (data) {
                $("#panelText").empty();
                document.getElementById('panelText').innerHTML += 'Drugs Details';
                $(".drug-data-content").html(data).show();
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