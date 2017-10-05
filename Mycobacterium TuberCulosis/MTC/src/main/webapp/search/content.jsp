<%-- 
    Document   : content
    Created on : Apr 6, 2016, 12:13:22 PM
    Author     : Dineli
--%>

<%@page import="com.nus.mtc.entity.DrugResistanceData"%>
<%@page import="com.nus.mtc.service.impl.DrugServiceImpl"%>
<%@page import="com.nus.mtc.service.IDrugs"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    IDrugs idrugService = new DrugServiceImpl();
    List<DrugResistanceData> drugResistanceData = null;
    boolean showAll = false;
    String message = "No Data to Display";

    String selectedOption = request.getParameter("selectedOption");
    String drugName = request.getParameter("drugName");
    String locus = request.getParameter("locusName");

    if (null != selectedOption) {
        if (selectedOption.equals("1")) {
            showAll = true;
            drugName = "";
            locus = "";
            drugResistanceData = idrugService.getAllDrugsData(showAll, drugName, locus);
        } else if (selectedOption.equals("2")) {
            if (!drugName.isEmpty()) {
                locus = "";
                drugResistanceData = idrugService.getAllDrugsData(showAll, drugName, locus);
            } else {
                message = "Please insert the drug name";
            }
        } else if (selectedOption.equals("3")) {
            if (!locus.isEmpty()) {
                drugName = "";
                drugResistanceData = idrugService.getAllDrugsData(showAll, drugName, locus);
            } else {
                message = "Please insert the locus name";
            }
        }
    } else {
        message = "Please select an option to proceed";
    }

%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%if (null != drugResistanceData && drugResistanceData.size() > 0) {%>
        <div class="scrollit">   
            <table class="table table-custom-main">
                <thead>
                    <tr>
                        <th>Drug</th>
                        <th>Locus Name</th>
                        <th>Locus Tag</th>
                        <th>Description</th>
                        <th>Start Chromosome Coordinate</th>
                        <th>Stop Chromosome Coordinate</th>
                        <th>Start Gene Coordinates</th>
                        <th>Stop Gene Coordinates</th>
                        <th>Nucleotide Ref</th>
                        <th>Nucleotide Alt</th>
                        <th>Start Codon No</th>
                        <th>Stop Codon No</th>
                        <th>Codon Ref</th>
                        <th>Codon Alt</th>
                        <th>Amino Ref</th>
                        <th>Amino Alt</th>
                        <th>TBDReamDB</th>
                        <th>tbproflr</th>
                        <th>KvarQ </th>
                        <th>High Confidence </th>
                        <th>References </th>
                    </tr>
                </thead>
                <tbody>
                    <%  for (DrugResistanceData drug : drugResistanceData) {
                            String desc = (null != drug.getDescription()) ? drug.getDescription() : "-";
                            String startChr = (null != drug.getStartChrCoord()) ? drug.getStartChrCoord().toString() : "-";
                            String stopChr = (null != drug.getStopChrCoord()) ? drug.getStopChrCoord().toString() : "-";
                            String startGene = (null != drug.getStartGeneCoord()) ? drug.getStartGeneCoord().toString() : "-";
                            String stopGene = (null != drug.getStopGeneCoord()) ? drug.getStopGeneCoord().toString() : "-";
                            String nulRef = (null != drug.getNucleotideRef()) ? drug.getNucleotideRef().toString() : "-";
                            String nulAlt = (null != drug.getNucleotideAlt()) ? drug.getNucleotideAlt().toString() : "-";
                            String startCodOn = (null != drug.getStartCodonNo()) ? drug.getStartCodonNo().toString() : "-";
                            String stopCodOn = (null != drug.getStopCodonNo()) ? drug.getStopCodonNo().toString() : "-";
                            String codOnRef = (null != drug.getCodonRef()) ? drug.getCodonRef().toString() : "-";
                            String codOnAlt = (null != drug.getCodonAlt()) ? drug.getCodonAlt().toString() : "-";
                            String aminoRef = (null != drug.getAminoRef()) ? drug.getAminoRef().toString() : "-";
                            String aminoAlt = (null != drug.getAminoAlt()) ? drug.getAminoAlt().toString() : "-";
                            String tbDreamDB = (null != drug.getTbdreamdb()) ? "<i class='fa fa-check'></i>" : "-";
                            String tbprofiler = (null != drug.getTbproflr()) ? "<i class='fa fa-check'></i>" : "-";
                            String kvarq = (null != drug.getKvarq()) ? "<i class='fa fa-check'></i>" : "-";
                            String hc = (null != drug.getHc()) ? "<i class='fa fa-check'></i>" : "-";
                            String ref = (null != drug.getReferences()) ? drug.getReferences().toString() : "-";
                    %>
                    <tr>
                        <td><%= drug.getDrugId().getDrugAbbreviation()%></td>
                        <td><%= drug.getTbGeneticLocusId().getLocusName()%></td>
                        <td> <a href="<%= drug.getTbGeneticLocusId().getUrl()%>" target="_blank"> <%= drug.getTbGeneticLocusId().getLocusTag()%></a></td>
                        <td><%= desc%></td>
                        <td><%= startChr%></td>
                        <td><%= stopChr%></td>
                        <td><%= startGene%></td>
                        <td><%= stopGene%></td>
                        <td><%= nulRef%></td>
                        <td><%= nulAlt%></td>
                        <td><%= startCodOn%></td>
                        <td><%= stopCodOn%></td>
                        <td><%= codOnRef%></td>
                        <td><%= codOnAlt%></td>
                        <td><%= aminoRef%></td>
                        <td><%= aminoAlt%></td>
                        <td><%= tbDreamDB%></td>
                        <td><%= tbprofiler%></td>
                        <td><%= kvarq%></td>
                        <td><%= hc%></td>
                        <td><%= ref%></td>
                    </tr>
                    <%}%>
                </tbody>
            </table>
            <div class="tfootData"></div>  
        </div>
    </div>
    <% } else {%>
    <div class="alert alert-danger" style="margin-top: 10px; overflow: hidden;">
        <strong> <%=message%> </strong> 
    </div>
    <%}%>
</body>
</html>

<script type="text/javascript">

    $(document).ready(function () {
        $('.table-custom-main').paging({limit: 100});

        //highlights the selected table row
        $("tbody tr").click(function () {
            $(this).addClass('selected').siblings().removeClass("selected");
        });


    });



</script>