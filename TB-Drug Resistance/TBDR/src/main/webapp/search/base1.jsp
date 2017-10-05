<%-- 
    Document   : base1
    Created on : Feb 21, 2017, 4:45:32 PM
    Author     : Dineli
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="panel panel-default">
    <div class="panel-heading" id="panelText">SEARCH</div>
    <div class="panel-body">
        <div id="msg"></div>
        <form class="advanceSearch">
            <fieldset class="scheduler-border">
                <legend class="scheduler-border">Search by :</legend>
                <table class="">
                    <tr>
                        <td><label class="control-label"> Option 1 : </label></td>
                        <td class="option-td"> 
                            <select class="form-control selection-1" id="sel1">
                                <option value="0">Please Select...</option>
                                <option value="1">Drugs</option>
                                <option value="2">Gene Name</option>
                                <option value="3">Data Source</option>
                            </select>
                        </td>
                        <td><div class="generate-selected-option-1"></div></td>
                        <td class="queryOption-1">
                            <select class="form-control andOr-1" id="sel1">
                                <option value="">Select...</option>
                                <option value="AND">AND</option>
                                <option value="OR">OR</option>
                            </select> 
                        </td>
                    </tr>
                    <tr>
                        <td><label class="control-label"> Option 2 : </label></td>
                        <td class="option-td"> 
                            <select class="form-control selection-2" id="sel1">
                                <option value="0">Please Select...</option>
                                <option value="1">Drugs</option>
                                <option value="2">Gene Name</option>
                                <option value="3">Data Source</option>
                            </select>
                        </td>
                        <td><div class="generate-selected-option-2"></div></td>
                        <td class="queryOption-2">
                            <select class="form-control andOr-2" id="sel1">
                                <option value="">Select...</option>
                                <option value="AND">AND</option>
                                <option value="OR">OR</option>
                            </select> 
                        </td>
                    </tr>
                    <tr>
                        <td><label class="control-label"> Option 3 : </label></td>
                        <td class="option-td"> 
                            <select class="form-control selection-3" id="sel1" disabled="">
                                <option value="0">Please Select...</option>
                                <option value="1">Drugs</option>
                                <option value="2">Gene Name</option>
                                <option value="3">Data Source</option>
                            </select>
                        </td>
                        <td><div class="generate-selected-option-3"></div></td>
                        <td class="queryOption-3">
                            <select class="form-control andOr-3" id="sel1">
                                <option value="">Select...</option>
                                <option value="AND">AND</option>
                                <option value="OR">OR</option>
                            </select> 
                        </td>
                    </tr>
                    <tr>
                        <td><label class="control-label"> Option 4 : </label></td>
                        <td class="option-td"> 
                            <select class="form-control selection-4" id="sel1" disabled="">
                                <option value="0">Please Select...</option>
                                <option value="1">Drugs</option>
                                <option value="2">Gene Name</option>
                                <option value="3">Data Source</option>
                            </select>
                        </td>
                        <td><div class="generate-selected-option-4"></div></td>
                        <td class="queryOption-4">
                            <select class="form-control andOr-4" id="sel1">
                                <option value="">Select...</option>
                                <option value="AND">AND</option>
                                <option value="OR">OR</option>
                            </select> 
                        </td>
                    </tr>
                    <tr>
                        <td><label class="control-label"> Option 5 : </label></td>
                        <td class="option-td"> 
                            <select class="form-control selection-5" id="sel1" disabled="">
                                <option value="0">Please Select...</option>
                                <option value="1">Drugs</option>
                                <option value="2">Gene Name</option>
                                <option value="3">Data Source</option>
                            </select>
                        </td>
                        <td><div class="generate-selected-option-5"></div></td>
                    </tr>
                </table>
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

        //generates the drug/gene/ds dropdowns with unique ids
        function optionSelection(optNo) {
            $('.queryOption-' + optNo).show();
            var selectedOption = $(".selection-" + optNo + " option:selected").val();
            if (selectedOption === "0") {
                $('.queryOption-' + optNo).hide();
                disableDivs(optNo);
            }
            $.ajax({
                type: "GET",
                url: "search/generateDropdown.jsp",
                data: {selectedOption: selectedOption, count: optNo},
                success: function (data) {
                    $(".generate-selected-option-" + optNo).html(data).show();
                }
            });
        }

        function andOrSelection(enableId, optionSelected) {
            //enables the next select tag
            $(".selection-" + enableId).prop("disabled", false);
            var currentAndOrDivId = enableId - 1;
            //disables the preceding select tags if the selected option=0
            if (optionSelected === "") {
                disableDivs(currentAndOrDivId);
            }
        }

        function disableDivs(selectId) {
            switch (selectId) {
                case 1:
                    $(".generate-selected-option-2").hide();
                    $(".generate-selected-option-3").hide();
                    $(".generate-selected-option-4").hide();
                    $(".generate-selected-option-5").hide();
                    $('.queryOption-2').hide();
                    $('.queryOption-3').hide();
                    $('.queryOption-4').hide();
                    $('.queryOption-5').hide();
                    $(".selection-3").prop("disabled", true);
                    $(".selection-4").prop("disabled", true);
                    $(".selection-5").prop("disabled", true);
                    $(".selection-2").val("0");
                    $(".selection-3").val("0");
                    $(".selection-4").val("0");
                    $(".selection-5").val("0");
                    $(".andOr-1").val("");
                    $(".andOr-2").val("");
                    $(".andOr-3").val("");
                    $(".andOr-4").val("");
                case 2:
                    $(".generate-selected-option-3").hide();
                    $(".generate-selected-option-4").hide();
                    $(".generate-selected-option-5").hide();
                    $('.queryOption-3').hide();
                    $('.queryOption-4').hide();
                    $('.queryOption-5').hide();
                    $(".selection-3").prop("disabled", true);
                    $(".selection-4").prop("disabled", true);
                    $(".selection-5").prop("disabled", true);
                    $(".selection-3").val("0");
                    $(".selection-4").val("0");
                    $(".selection-5").val("0");
                    $(".andOr-2").val("");
                    $(".andOr-3").val("");
                    $(".andOr-4").val("");
                    break;
                case 3:
                    $(".generate-selected-option-4").hide();
                    $(".generate-selected-option-5").hide();
                    $('.queryOption-4').hide();
                    $(".selection-4").prop("disabled", true);
                    $(".selection-5").prop("disabled", true);
                    $(".selection-4").val("0");
                    $(".selection-5").val("0");
                    $(".andOr-3").val("");
                    $(".andOr-4").val("");
                    break;
                case 4:
                    $(".generate-selected-option-5").hide();
                    $(".selection-5").prop("disabled", true);
                    $(".selection-5").val("0");
                    $(".andOr-4").val("");
                    break;
            }
        }

        //categoryId: whether the selected category is drug/gene or ds (1->drug,2->gene,3->ds)
        //optionId: which option 1/2/3/4/ or 5
        function fetchSelectedValue(categoryId, optionId) {
            var selection;
            if (categoryId === "1") {
                selection = $(".drugSelection" + optionId + " option:selected").val();
            } else if (categoryId === "2") {
                selection = $(".geneSelection" + optionId + " option:selected").val();
            } else if (categoryId === "3") {
                selection = $(".dsSelection" + optionId + " option:selected").val();
            }
            return selection;
        }

        function generateAlert(msgType, optId1, optId2) {
            switch (msgType) {
                case 1:
                    $.alert({
                        title: 'Invalid Search Entry',
                        content: 'Please select the conditional operator in option ' + optId1,
                    });
                    break;
                case 2:
                    $.alert({
                        title: 'Invalid Search Entry',
                        content: 'Remove the conditional operator in option ' + optId1 + ' OR populate option ' + optId2,
                    });
                    break;
                case 3:
                    $.alert({
                        title: 'Invalid Search Entry',
                        content: 'Please populate option 1 to proceed',
                    });
                    break;
            }

        }

        function validateDropDowns() {
            var opt1 = $(".selection-1 option:selected").val();
            var opt2 = $(".selection-2 option:selected").val();
            var opt3 = $(".selection-3 option:selected").val();
            var opt4 = $(".selection-4 option:selected").val();
            var opt5 = $(".selection-5 option:selected").val();
            var status = true;

            if ((opt1 > "0" && opt2 > "0")) {
                var opt1JoinCmd = $(".andOr-1 option:selected").val();
                if (opt1JoinCmd === "") {
                    status = false;
                    generateAlert(1, 1, 0);
                }
            }

            if ((opt1 > "0" && opt2 === "0")) {
                var opt1JoinCmd = $(".andOr-1 option:selected").val();
                if (opt1JoinCmd !== "") {
                    status = false;
                    generateAlert(2, 1, 2);
                }
            }

            if ((opt1 === "0" && opt2 > "0")) {
                status = false;
                generateAlert(3, 0, 0);
            }


            if ($('.selection-3').is(':enabled')) {
                if (opt3 === "0") {
                    status = false;
                    generateAlert(2, 2, 3);
                }
            }
            if ($('.selection-4').is(':enabled')) {
                if (opt4 === "0") {
                    status = false;
                    generateAlert(2, 3, 4);
                }
            }

            if ($('.selection-5').is(':enabled')) {
                if (opt5 === "0") {
                    status = false;
                    generateAlert(2, 4, 5);
                }
            }
            return status;
        }

        $(".andOr-2").change(function () {
            var optionSelected = $(".andOr-2 option:selected").val();
            //passes the id of the next select tag
            andOrSelection(3, optionSelected);

        });

        $(".andOr-3").change(function () {
            var optionSelected = $(".andOr-3 option:selected").val();
            andOrSelection(4, optionSelected);
        });

        $(".andOr-4").change(function () {
            var optionSelected = $(".andOr-4 option:selected").val();
            andOrSelection(5, optionSelected);
        });

        $(".selection-1").change(function () {
            optionSelection(1);
        });

        $(".selection-2").change(function () {
            optionSelection(2);
        });

        $(".selection-3").change(function () {
            optionSelection(3);
        });

        $(".selection-4").change(function () {
            optionSelection(4);
        });

        $(".selection-5").change(function () {
            optionSelection(5);
        });



        $('.btnSearch').on('click', function () {
            if (validateDropDowns()) {
                var selection1 = $(".selection-1 option:selected").val();
                var selectedValue1 = fetchSelectedValue(selection1, 1);
                var andOr1 = $(".andOr-1 option:selected").val();
                var selection2 = $(".selection-2 option:selected").val();
                var selectedValue2 = fetchSelectedValue(selection2, 2);
                var andOr2 = $(".andOr-2 option:selected").val();
                var selection3 = 0;
                var selection4 = 0;
                var selection5 = 0;
                var selectedValue3 = 0;
                var selectedValue4 = 0;
                var selectedValue5 = 0;
                var andOr3 = "";
                var andOr4 = "";
                if ($('.selection-3').is(':enabled')) {
                    selection3 = $(".selection-3 option:selected").val();
                    selectedValue3 = fetchSelectedValue(selection3, 3);
                    andOr3 = $(".andOr-3 option:selected").val();
                }

                if ($('.selection-4').is(':enabled')) {
                    selection4 = $(".selection-4 option:selected").val();
                    selectedValue4 = fetchSelectedValue(selection4, 4);
                    andOr4 = $(".andOr-4 option:selected").val();
                }

                if ($('.selection-5').is(':enabled')) {
                    selection5 = $(".selection-5 option:selected").val();
                    selectedValue5 = fetchSelectedValue(selection5, 5);
                }

                $.ajax({
                    type: "GET",
                    url: "search/view.jsp",
                    data: {
                        selection1: selection1, selectedValue4opt1: selectedValue1, andOr1: andOr1,
                        selection2: selection2, selectedValue4opt2: selectedValue2, andOr2: andOr2,
                        selection3: selection3, selectedValue4opt3: selectedValue3, andOr3: andOr3,
                        selection4: selection4, selectedValue4opt4: selectedValue4, andOr4: andOr4,
                        selection5: selection5, selectedValue4opt5: selectedValue5, searchOption: 2
                    },
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
            }
        });

    });

</script>