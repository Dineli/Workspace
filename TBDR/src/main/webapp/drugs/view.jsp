<%-- 
    Document   : view
    Created on : Dec 1, 2016, 10:03:46 AM
    Author     : Dineli
--%>

<%@page import="java.util.List"%>
<%@page import="com.nus.tbdr.service.IDrug"%>
<%@page import="com.nus.tbdr.entity.Drugs"%>
<%@page import="com.nus.tbdr.service.impl.DrugServiceImpl"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    IDrug idrugService = new DrugServiceImpl();
    List<Drugs> drugList = idrugService.fetchAllDrugs();
    int count = 0;
%>

<button type="button" class="btn btn-primary btn-md btnAdd">Add Data</button></br></br>
<div id ="msg"></div>
<% if ((null != drugList) && (!drugList.isEmpty())) { %>
<div class="table-responsive">          
    <table id="hor-zebra" align="center">
        <thead>
            <tr>
                <th class="content-align-center">#</th>
                <th>Name</th>
                <th class="content-align-center">Edit</th>
                <th class="content-align-center">Delete</th>
            </tr>
        </thead>    
        <tbody>
            <% for (Drugs drug : drugList) {%>  
            <% count += 1;%>
            <tr>
                <td class="col-md-1"><%= count%></td>
                <td class="col-md-1 content-align-left"><%= drug.getName()%></td>
                <td class="col-md-1"><a href="#" class="editDrug" drugId="<%= drug.getId()%>" drugName="<%= drug.getName()%>"><i class="fa fa-pencil" aria-hidden="true"></i></a></td>
                <td class="col-md-1"><a href="#" class="deleteDrug" drugId="<%= drug.getId()%>" ><i class="fa fa-times" aria-hidden="true"></i></a></td>

            </tr>
            <%}%>
        </tbody>
    </table>
    <% } else {%>
    <div class="alert alert-danger" style="margin-top: 10px; overflow: hidden;">
        <strong>No Drug Data to Display</strong> 
    </div>
    <% }%>
</div>

<script type="text/javascript">
    $('.btnAdd').on('click', function () {
        $.ajax({
            type: "GET",
            url: "drugs/add.jsp",
            data: {},
            success: function (data) {
                document.getElementById('panelText').innerHTML += ' : Insert';
                $(".drug-data-content").html(data).show();
            }
        });
    });

    $('.editDrug').on('click', function () {
        var selectedRecordId = $(this).attr("drugId");
        var drugName = $(this).attr("drugName");
        $.ajax({
            type: "GET",
            url: "drugs/edit.jsp",
            data: {recordId: selectedRecordId, drugName: drugName},
            success: function (data) {
                document.getElementById('panelText').innerHTML += ' : Update';
                $(".drug-data-content").html(data).show();
            }
        });
    });

    $(".deleteDrug").click(function () {
        var recordId = $(this).attr("drugId");

        $.confirm({
            title: 'Delete Confirmation!',
            content: 'Are you sure to delete this record?',
            buttons: {
                confirm: function () {
                    deleteDrugData(recordId);
                },
                cancel: function () {
                }
            }
        });
    });

    function deleteDrugData(recordId) {
        $.ajax({
            type: "POST",
            url: "data",
            data: {recordId: recordId, subAction: "drugs-delete"},
            success: function (data) {
                $("#msg").html(data).show("fast").delay(1000).hide("slow", function () {
                    displayData();
                });
            }
        });
    }


</script>