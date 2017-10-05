/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function showPagePreload() {
    $("#preloader").append("<div class='appender'><p style='text-align:center;'><img src='images/pgLoader.gif' /><br/>Loading Data...</p></div>");
}

function hidePagePreload() {
    $(".appender").html("");
}