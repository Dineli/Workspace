/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function showPagePreload() {
    $("#preloader").append("<div class='appender'><p style='text-align:center;'><img src='images/pgLoader.gif'/><br/>Loading Data...</p></div>");
}

function showLoginPreloader() {
    $("#preloader").append("<div class='appender'><p style='text-align:center;'><img src='images/loginLoader.gif'/><br/></p></div>");
}


function hidePagePreload() {
    $(".appender").html("");
}

function goBack(url) {
    $.ajax({
        type: "GET",
        url: url,
        data: {},
        success: function (data) {
            $(".container").html(data).show();
        }
    });
}

function goBack(url, param1, param2, param3) {
    $.ajax({
        type: "GET",
        url: url,
        data: {prjId: param1, prjName: param2, prjType: param3},
        success: function (data) {
            $(".container").html(data).show();
        }
    });
}

function goTo(url) {
    $.ajax({
        type: "GET",
        url: url,
        data: {type: "homePg"}, //requires only for pipeline page
        success: function (data) {
            $(".container").html(data).show();
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

function validateProject(prjName, prjDesc) {
    $(".msg").show();
    var errorMsg = "<div class='validate-msgs w3-panel w3-small w3-wide w3-round w3-animate-opacity'><p><i class='fa fa-exclamation fa-lg' aria-hidden='true'></i> ";
    var validationStatus = false;

    if (prjName === "") {
        errorMsg += " Name cannot be blank.";
    } else if (prjDesc === "") {
        errorMsg += " Description cannot be blank.";
    } else {
        $(".msg").hide();
        validationStatus = true;
    }
    errorMsg += "</p></div>";

    $(".msg p").html(errorMsg);
    return validationStatus;
}

function validateSample(sampleName, organism) {
    $(".msg").show();
    var errorMsg = "<div class='validate-msgs w3-panel w3-small w3-wide w3-round w3-animate-opacity' style=''><p><i class='fa fa-exclamation fa-lg' aria-hidden='true'></i> ";
    var validationStatus = false;

    if (sampleName === "") {
        errorMsg += " Sample name cannot be blank.";
    } else if (organism === "0") {
        errorMsg += " Please select an organism.";
    } else {
        $(".msg").hide();
        validationStatus = true;
    }
    errorMsg += "</p></div>";

    $(".msg p").html(errorMsg);
    return validationStatus;
}

function validate(name, email, userName, pw) {
    $(".msg").show();
    var testEmail = /^[A-Z0-9._%+-]+@([A-Z0-9-]+\.)+[A-Z]{2,4}$/i;
    var errorMsg = "<div class='validate-msgs w3-panel w3-small w3-wide w3-round w3-animate-opacity'><p><i class='fa fa-exclamation fa-lg' aria-hidden='true'></i> ";
    var validationStatus = false;

    if (name === "") {
        errorMsg += " Name cannot be blank.";
    } else if (email === "") {
        errorMsg += " Email cannot be blank.";
    } else if (!testEmail.test(email)) {
        errorMsg += " Insert a valid email.";
    } else if (userName === "") {
        errorMsg += " User name cannot be blank.";
    } else if (pw === "") {
        errorMsg += " Password cannot be blank.";
    } else {
        $(".msg").hide();
        validationStatus = true;
    }
    errorMsg += "</p></div>";

    $(".msg p").html(errorMsg);
    return validationStatus;
}

function validatePassword(pw1, pw2, oldPasswd) {
    var validationStatus = false;
    var errorMsg = "<div class='validate-msgs w3-panel w3-small w3-wide w3-round w3-animate-opacity'><p><i class='fa fa-exclamation fa-lg' aria-hidden='true'></i> ";

    if (pw1 !== pw2) {
        errorMsg += " New passwords do not match. Please re-enter.";
    } else if (oldPasswd === "") {
        errorMsg += " Old password cannot be blank.";
    } else if (pw1 === "" || pw2 === "") {
        errorMsg += " New password cannot be blank.";
    } else {
        $(".msg p").hide();
        validationStatus = true;
    }
    errorMsg += "</p></div>";

    $(".msg").show();
    $(".msg p").html(errorMsg);
    return validationStatus;
}

function validateDropDown(selectTagValue) {
    $(".msg").show();
    var errorMsg = "<div class='validate-msgs w3-panel w3-small w3-wide w3-round w3-animate-opacity' style=''><p><i class='fa fa-exclamation fa-lg' aria-hidden='true'></i> ";
    var validationStatus = false;

    if (selectTagValue === "0") {
        errorMsg += " Please select a user to share the project.";
    } else {
        $(".msg").hide();
        validationStatus = true;
    }
    errorMsg += "</p></div>";

    $(".msg p").html(errorMsg);
    return validationStatus;
}

function clearTxtboxes() {
    var elements = [];
    elements = document.getElementsByClassName("w3-input");

    for (var i = 0; i < elements.length; i++) {
        elements[i].value = "";
    }
}

//var _validFileExtensions = [".fastq", ".fq", ".gz"];
function validateUpload(input, validFileExtensions) {
    if (input.type === "file") {
        var sFileName = input.value;
        if (sFileName.length > 0) {
            var blnValid = false;
            for (var j = 0; j < validFileExtensions.length; j++) {
                var sCurExtension = validFileExtensions[j];
                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() === sCurExtension.toLowerCase()) {
                    blnValid = true;
                    break;
                }
            }
            if (!blnValid) {
                $.alert({
                    title: 'Invalid File Upload!',
                    content: 'Allowed file extensions are: ' + validFileExtensions.join(", "),
                    useBootstrap: false,
                    boxWidth: '30%',
                });
                input.value = "";
                return false;
            } else {
                return true;
            }
        }
    }
}
