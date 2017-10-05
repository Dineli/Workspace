<%-- 
    Document   : base
    Created on : Dec 19, 2016, 9:43:02 AM
    Author     : Dineli
--%>
<%@page import="com.nus.tbdr.service.impl.VariantServiceImpl"%>
<%@page import="com.nus.tbdr.service.IVariant"%>
<%@page import="com.nus.tbdr.service.impl.DataSourceServiceImpl"%>
<%@page import="com.nus.tbdr.entity.DataSources"%>
<%@page import="com.nus.tbdr.service.IDataSource"%>
<%@page import="com.nus.tbdr.entity.Drugs"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.tbdr.service.impl.DrugServiceImpl"%>
<%@page import="com.nus.tbdr.service.IDrug"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    IDrug idrugService = new DrugServiceImpl();
    IDataSource dsService = new DataSourceServiceImpl();
    IVariant ivariantService = new VariantServiceImpl();

    List<Drugs> drugList = idrugService.fetchAllDrugs();
    List<DataSources> dsList = dsService.fetchAllDataSources();
    List<String> geneNameList = ivariantService.fetchGeneNames();

%>

<div class="panel panel-default">
    <div class="panel-heading" id="panelText">SEARCH</div>
    <div class="panel-body">
        <div id="msg"></div>
        <form class="searchForm">
            <fieldset class="scheduler-border">
                <legend class="scheduler-border">Search by :</legend>
                <div class="form-group">
                    <label class="control-label col-sm-2"> Drug Name : </label>
                    <div class="col-sm-10">          
                        <select class="drug-dropdown" multiple="multiple">
                            <% for (Drugs drug : drugList) {%>
                            <option value="<%= drug.getId()%>"><%= drug.getName()%></option>
                            <% } %>
                        </select>
                    </div>
                    <!--</br> </br>--> 
                    <label class="control-label col-sm-2"> Gene Name : </label>
                    <div class="col-sm-10">          
                        <select class="gene-dropdown" multiple="multiple">
                            <% for (String gene : geneNameList) {%>
                            <option value="<%= gene%>"><%= gene%></option>
                            <% } %>
                        </select>
                    </div>
                    <!--</br> </br>--> 
                    <label class="control-label col-sm-2"> Data Source : </label>
                    <div class="col-sm-10">          
                        <select class="dataSource-dropdown" multiple="multiple">
                            <% for (DataSources ds : dsList) {%>
                            <option value="<%= ds.getId().toString().concat(" ")%>"><%= ds.getName()%></option><!-- a space has been concat in order to generate a unique value name. Since drugId and dsId has same id values, multiselect fails to function properly -->
                            <% }%>
                        </select>
                    </div>
                </div>
                <div style="text-align: center;">
                    <button type="button" class="btn btn-primary btnSearch">Submit</button>
                </div>
            </fieldset>
        </form>
        </br>
        <div id="preloader"></div>
        <div class="search-content"></div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {

        $('.drug-dropdown').multiselect({
            nonSelectedText: 'Please Select...',
            buttonWidth: '312px',
            checkboxName: function (option) {
                return generateRandomNo('multiselect_drug_');
            },
            onChange: function (option, checked) {
                maxOption('.drug-dropdown');
            }
        });

        $('.gene-dropdown').multiselect({
            nonSelectedText: 'Please Select...',
            buttonWidth: '312px',
            checkboxName: function (option) {
                return generateRandomNo('multiselect_gene_');
            },
            onChange: function (option, checked) {
                maxOption('.gene-dropdown');
            }
        });

        $('.dataSource-dropdown').multiselect({
            nonSelectedText: 'Please Select...',
            buttonWidth: '312px',
            checkboxName: function (option) {
                return generateRandomNo('multiselect_ds_');
            },
            onChange: function (option, checked) {
                maxOption('.dataSource-dropdown');
            }
        });

        function maxOption(selectionType) {
            // Get selected options.
            var selectedOptions = $(selectionType + ' option:selected');
            if (selectedOptions.length >= 2) {
                // Disable all other checkboxes.
                var nonSelectedOptions = $(selectionType + ' option').filter(function () {
                    return !$(this).is(':selected');
                });
                nonSelectedOptions.each(function () {
                    var input = $('input[value="' + $(this).val() + '"]');
                    input.prop('disabled', true);
                    input.parent('li').addClass('disabled');
                });
            } else {
                // Enable all checkboxes.
                $(selectionType + ' option').each(function () {
                    var input = $('input[value="' + $(this).val() + '"]');
                    input.prop('disabled', false);
                    input.parent('li').addClass('disabled');
                });
            }
            return $(selectionType).val();
        }

        //generates unique checkbox names
        function generateRandomNo(nameStr) {
            var randomNo = Math.round(Math.random() * 1000);
            return nameStr + randomNo;
        }


        $('.btnSearch').on('click', function () {
            var selectedDrug = maxOption('.drug-dropdown');
            var selectedGene = maxOption('.gene-dropdown');
            var selectedDataSource = maxOption('.dataSource-dropdown');

            if (selectedDrug.length === 0) {
                selectedDrug = 0;
            }
            if (selectedGene.length === 0) {
                selectedGene = 0;
            }
            if (selectedDataSource.length === 0) {
                selectedDataSource = 0;
            }

            $.ajax({
                url: "search/view.jsp",
                type: "GET",
                data: {selectedDrugId: selectedDrug.toString(), selectedDSId: selectedDataSource.toString(), selectedGene: selectedGene.toString(), searchOption: 1},
                success: function (data) {
                    $(".search-content").html(data);
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
    });
</script>