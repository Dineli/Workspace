<%-- 
    Document   : view
    Created on : Dec 2, 2016, 4:06:35 PM
    Author     : Dineli
--%>

<%@page import="com.nus.tbdr.entity.DataSources"%>
<%@page import="com.nus.tbdr.service.impl.DataSourceServiceImpl"%>
<%@page import="com.nus.tbdr.service.IDataSource"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String loggedUser = null;

    if (session.getAttribute("name") != null) {
        loggedUser = String.valueOf(session.getAttribute("name"));
    }

    IDataSource dsService = new DataSourceServiceImpl();
    List<DataSources> dsList = dsService.fetchAllDataSources();
    int count = 0;
    
    //public users can view the ds details without its maintenace features
%>
<%if (loggedUser != null) {%>
    <button type="button" class="btn btn-primary btn-md btnAdd">Add Data</button></br></br>
<%}%>
<div id ="msg"></div>
<% if ((null != dsList) && (!dsList.isEmpty())) { %>
<div class="table-responsive">          
    <table id="hor-zebra" align="center">
        <thead>
            <tr>
                <th class="content-align-center">#</th>
                <th>Name</th>
                <th>Description</th>
                <%if (loggedUser != null) {%>
                    <th class="content-align-center">Edit</th>
                    <th class="content-align-center">Delete</th>
                <%}%>
            </tr>
        </thead>    
        <tbody>
            <% for (DataSources ds : dsList) {%>  
            <% count += 1;%>
            <tr>
                <td class="col-md-1"><%= count%></td>
                <td class="col-md-1 content-align-left"><%= ds.getName()%></td>
                <td class="col-md-1 content-align-left"><%= ds.getDescription()%></td>
                <%if (loggedUser != null) {%>
                    <td class="col-md-1"><a href="#" class="editDataSource" dsId="<%= ds.getId()%>" dsName="<%= ds.getName()%>" dsDesc="<%= ds.getDescription()%>"><i class="fa fa-pencil" aria-hidden="true"></i></a></td>
                    <td class="col-md-1"><a href="#" class="deleteDataSource" dsId="<%= ds.getId()%>" ><i class="fa fa-times" aria-hidden="true"></i></a></td>
                <%}%>
            </tr>
            <% }%>
        </tbody>
    </table>
    <% } else {%>
    <div class="alert alert-danger" style="margin-top: 10px; overflow: hidden;">
        <strong>No Data Sources to Display</strong> 
    </div>
    <% }%>
</div>

<script type="text/javascript">
    $('.btnAdd').on('click', function () {
        $.ajax({
            type: "GET",
            url: "dataSources/add.jsp",
            data: {},
            success: function (data) {
                document.getElementById('panelText').innerHTML += ' : Insert';
                $(".data-source-content").html(data).show();
            }
        });
    });

    $('.editDataSource').on('click', function () {
        var selectedRecordId = $(this).attr("dsId");
        var editName = $(this).attr("dsName");
        var editDesc = $(this).attr("dsDesc");
        $.ajax({
            type: "GET",
            url: "dataSources/edit.jsp",
            data: {recordId: selectedRecordId, editName: editName, editDesc: editDesc},
            success: function (data) {
                document.getElementById('panelText').innerHTML += ' : Update';
                $(".data-source-content").html(data).show();
            }
        });
    });

    $('.deleteDataSource').on('click', function () {
        var recordId = $(this).attr("dsId");

        $.confirm({
            title: 'Delete Confirmation!',
            content: 'Are you sure to delete this record?',
            buttons: {
                confirm: function () {
                    deleteDataSource(recordId);
                },
                cancel: function () {
                }
            }
        });
    });

    function deleteDataSource(id) {
        $.ajax({
            type: "POST",
            url: "data",
            data: {recordId: id, subAction: "ds-delete"},
            success: function (data) {
                $("#msg").html(data).show("fast").delay(1000).hide("slow", function () {
                    displayDSData();
                });
            }
        });
    }

</script>