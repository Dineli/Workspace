<%-- 
    Document   : edit
    Created on : Jul 17, 2017, 11:45:04 AM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String proName = request.getParameter("nameParam");
    String proDesc = request.getParameter("descParam");
    int proId = Integer.valueOf(request.getParameter("recordId"));

%>

<div class="w3-container w3-animate-left w3-table">
    <div class="w3-panel data-panel">
        <p class="w3-left w3-wide w3-large">Update Project</p>
    </div>
    <div id="preloader"></div>
    <div class="w3-cell-row msg" style="display: none;">
        <div class="w3-container w3-cell">
            <p></p>
        </div>
    </div>

    <div class="w3-container">
        <table class="tbl-add-new">
            <tr>
                <td><label>Name :</label></td>
                <td><input class="w3-input editPrjName" type="text" value="<%= proName%>"></td>
            </tr>
            <tr>
                <td><label>Description :</label></td>
                <td><input class="w3-input editPrjDesc" type="text" value="<%= proDesc%>"></td>
            </tr>
            <tr><td></td></tr>
            <tr>
                <td></td>
                <td>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnUpdate">UPDATE</button>
                    <button class="w3-btn w3-wide w3-border w3-round w3-opacity w3-ripple btnClear" onclick="goBack('projects/base.jsp');">BACK</button>
                </td>
            </tr>
        </table>
    </div>
</div>


<script>

    $('.btnUpdate').on('click', function () {
        var editName = $(".editPrjName").val();
        var editDesc = $(".editPrjDesc").val();
        var validateStatus = validateProject(editName, editDesc);
        if (validateStatus) {
            $.ajax({
                type: "POST",
                url: "DataMaintenance",
                data: {recordId: <%=proId%>, editNameParam: editName, editDescParam: editDesc, subAction: "project-update"},
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