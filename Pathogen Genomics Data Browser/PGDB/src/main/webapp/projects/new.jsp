<%-- 
    Document   : new
    Created on : May 24, 2017, 3:01:48 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Add New Project</p>
        <p class="w3-right"><button class="w3-button w3-padding-small w3-circle w3-black" onclick="goBack('projects/base.jsp');"><i class="fa fa-arrow-left" aria-hidden="true"></i></button></p>
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
                <td><label>Name :</label></td>
                <td><input class="w3-input prjName" type="text"></td>
            </tr>
            <tr>
                <td><label>Description :</label></td>
                <td><input class="w3-input prjDesc" type="text"></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td></td>
                <td>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnSave">SAVE</button>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnClear" onclick="clearTxtboxes();">CLEAR</button>
                </td>
            </tr>
        </table>
    </div>
</div>

<script>

    $('.btnSave').on('click', function () {
        var projectName = $(".prjName").val();
        var projectDesc = $(".prjDesc").val();
        var validateStatus = validateProject(projectName, projectDesc);
        if (validateStatus) {
            $.ajax({
                type: "POST",
                url: "DataMaintenance",
                data: {prjNameParam: projectName, prjDescParam: projectDesc, subAction: "project-creation"},
                success: function (data) {
                    $(".msg").show().html(data);
                    var response = $('.msg div').attr("class");
                    if (response.includes("alert-success")) {
                        $(".msg").html(data).show("fast").delay(1000).hide("slow", function () {
                            goBack('projects/base.jsp');
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
    });

</script>
