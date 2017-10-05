<%-- 
    Document   : view
    Created on : Dec 9, 2016, 4:17:30 PM
    Author     : Dineli
--%>

<%@page import="com.nus.tbdr.service.impl.DrugResistanceServiceImpl"%>
<%@page import="com.nus.tbdr.service.IDrugResistance"%>
<%@page import="com.nus.tbdr.entity.DrugResistance"%>
<%@page import="com.nus.tbdr.entity.Variants"%>
<%@page import="java.util.List"%>
<%@page import="com.nus.tbdr.service.impl.VariantServiceImpl"%>
<%@page import="com.nus.tbdr.service.IVariant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    IVariant ivariantService = new VariantServiceImpl();
    IDrugResistance iDrugResistance = new DrugResistanceServiceImpl();

    List<Variants> variantList = ivariantService.fetchAllData();
    
%>

<% if ((null != variantList) && (!variantList.isEmpty())) { %>
<div class="table-responsive">          
    <!--<table class="table table-striped tblDrugs table-custom-main" align="center">-->
    <table id="hor-zebra" class="table-custom-main" align="center">
        <thead>
            <tr>
                <th></th>
                <th>Var. Position Genome Start</th>
                <th>Var. Position Genome Stop</th>
                <th>Var. Type</th>
                <th>Number</th>
                <th>WT base</th>
                <th>Var. base</th>
                <th class="content-align-left">Region</th>
                <th class="content-align-left">Gene ID</th>
                <th class="content-align-left">Gene Name</th>
                <th>Gene start</th>
                <th>Gene stop</th>
                <th>Gene length</th>
                <th>Dir.</th>
                <th>WT AA</th>
                <th>Codon nr.</th>
                <th>Codon nr. E. coli</th>
                <th>Var. AA</th>
                <th class="content-align-left">AA change</th>
                <th class="content-align-left">Codon change</th>
                <th>Variant position gene start</th>
                <th>Variant position gene stop</th>
                <th class="content-align-left">Remark</th>
            </tr>
        </thead>    
        <tbody>

            <%for (Variants var : variantList) {
                    String remarks = (null != var.getRemarks()) ? var.getRemarks() : "";
                    String codonNr = (0 != var.getCodonNr()) ? var.getCodonNr().toString() : "-";
                    String codonNrEColi = (0 != var.getCodonNrEColi()) ? var.getCodonNrEColi().toString() : "-";
            %>
            <tr>
                <td><input type="button" data-href="#<%= var.getId()%>" value="+"/></td>
                <td class="col-md-1"><%= var.getVarPositionGenomeStart()%></td>
                <td class="col-md-1"><%= var.getVarPositionGenomeStop()%></td>
                <td class="col-md-1"><%= var.getVarType()%></td>
                <td class="col-md-1"><%= var.getNumber()%></td>
                <td class="col-md-1"><%= var.getWtBase()%></td>
                <td class="col-md-1"><%= var.getVarBase()%></td>
                <td class="col-md-1 content-align-left"><%= var.getRegion()%></td>
                <td class="col-md-1 content-align-left"><%= var.getGeneId()%></td>
                <td class="col-md-1 content-align-left"><%= var.getGeneName()%></td>
                <td class="col-md-1"><%= var.getGeneStart()%></td>
                <td class="col-md-1"><%= var.getGeneStop()%></td>
                <td class="col-md-1"><%= var.getGeneLength()%></td>
                <td class="col-md-1"><%= var.getDir()%></td>
                <td class="col-md-1"><%= var.getWtAa()%></td>
                <td class="col-md-1"><%= codonNr%></td>
                <td class="col-md-1"><%= codonNrEColi%></td>
                <td class="col-md-1"><%= var.getVarAa()%></td>
                <td class="col-md-1 content-align-left"><%= var.getAaChange()%></td>
                <td class="col-md-1 content-align-left"><%= var.getCodonChange()%></td>
                <td class="col-md-1"><%= var.getVarPositionGeneStart()%></td>
                <td class="col-md-1"><%= var.getVarPositionGeneStop()%></td>
                <td class="col-md-1 content-align-left"><%= remarks%></td>
            </tr>
            <tr> 
                <td colspan="24" id="<%=var.getId()%>" style="display: none;">
                    <div>
                        <table class="innerTbl">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th class="content-align-left">Drug</th>
                                    <th class="content-align-left">Data Source</th>
                                    <th>Reference PMID</th>
                                    <th>High Confidence</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<DrugResistance> correspondingDRList = iDrugResistance.fetchByVariantId(var.getId());
                                    int count = 1;
                                    for (DrugResistance dr : correspondingDRList) {
                                %>

                                <%
                                    String hc = (dr.getHighConfidence() != null) ? "Yes" : "-";
                                    String refPMID = (dr.getReferencePmid() == 0) ? "-" : dr.getReferencePmid().toString();
                                    
                                %>
                                <tr>    
                                    <td><%= count%></td>
                                    <td class="content-align-left"><%= dr.getDrugId().getName()%></td>
                                    <td class="content-align-left"><%= dr.getDataSourceId().getName()%></td>
                                    <td class=""><%= refPMID%></td>
                                    <td class=""><%= hc%></td>
                                </tr>  
                                <% count +=1;%>
                                <% }%>
                            </tbody>
                        </table>
                    </div>
                </td>
            </tr>
            <%}%>
        </tbody>
    </table>
    <div class="tfootData"></div>  
    <% } else {%>
    <div class="alert alert-danger" style="margin-top: 10px; overflow: hidden;">
        <strong>No Variant Data to Display</strong> 
    </div>
    <% }%>
</div>

<script type="text/javascript">

    $(document).ready(function () {
        $('.table-custom-main').paging({limit: 20});

        $("input").click(function () {
            var $el = $($(this).data('href'));
            if ($el.is(":visible")) {
                $el.hide();
            } else {
                $el.show();
            }
        });
    });
</script>