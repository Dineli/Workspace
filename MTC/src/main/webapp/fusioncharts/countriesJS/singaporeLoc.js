/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global FusionCharts */

var locationId;
var _args = {};
//alert("params "+queryString)
var MYLIBRARY = MYLIBRARY || (function () {
    // private

    return {
        init: function (Args) {
            _args = Args;
            locationId = _args[0];
            // some other initialising
        },
//        helloWorld: function () {
//            alert('Hello World! -' + _args[0]);
//        }
    };


}());


FusionCharts.ready(function () {
    if (locationId == "008") {


        var populationMap = new FusionCharts({
            type: 'maps/singapore',
            renderAt: 'chart-container-loc',
            width: '600',
            height: '400',
            dataFormat: 'json',
            dataSource: {
                "map": {
                    "showshadow": "0",
                    "showlabels": "0",
                    "showmarkerlabels": "1",
                    "fillcolor": "F1f1f1",
                    "bordercolor": "CCCCCC",
                    "basefont": "Verdana",
                    "basefontsize": "10",
                    "markerbordercolor": "000000",
                    "markerbgcolor": "FF5904",
                    "markerradius": "6",
                    "usehovercolor": "0",
                    "hoveronempty": "0",
                    "showmarkertooltip": "1",
                    "canvasBorderColor": "375277",
                    "canvasBorderAlpha": "0"
                },
                "colorrange": {
                    "color": [
                        {
                            "minvalue": "0",
                            "maxvalue": "100",
                            "code": "#D0DFA3",
                            "displayValue": "< 100M"
                        }]},
                "data": [
                    {
                        "id": "SG.CS"
                    },
                    {
                        "id": "SG.EA"
                    },
                    {
                        "id": "SG.NO"
                    },
                    {
                        "id": "SG.WE",
                        "value": "50"
                    },
                    {
                        "id": "SG.SO"
                    }
                ],
                "markers": {
                    "shapes": [
                        {
                            "id": "newCustomShape",
                            "type": "circle",
                            "fillcolor": "FFFFFF,000099",
                            "fillpattern": "radial",
                            "showborder": "0",
                            "radius": "3"
                        }
                    ],
                    "items": [
                        {
                            "id": "05",
                            "shapeid": "newCustomShape",
                            "x": "87.7",
                            "y": "263.5",
                            "label": "Tuas",
                            "labelpos": "right"
                        }
                    ]

                }
            }

        }).render();
    } else {
        var populationMap = new FusionCharts({
            type: 'maps/singapore',
            renderAt: 'chart-container-loc',
            width: '600',
            height: '400',
            dataFormat: 'json',
            dataSource: {
                "map": {
                    "showshadow": "0",
                    "showlabels": "0",
                    "showmarkerlabels": "1",
                    "fillcolor": "F1f1f1",
                    "bordercolor": "CCCCCC",
                    "basefont": "Verdana",
                    "basefontsize": "10",
                    "markerbordercolor": "000000",
                    "markerbgcolor": "FF5904",
                    "markerradius": "6",
                    "usehovercolor": "0",
                    "hoveronempty": "0",
                    "showmarkertooltip": "1",
                    "canvasBorderColor": "375277",
                    "canvasBorderAlpha": "0"
                },
                "colorrange": {
                    "color": [
                        {
                            "minvalue": "0",
                            "maxvalue": "100",
                            "code": "#D0DFA3",
                            "displayValue": "< 100M"
                        }]},
                "data": [
                    {
                        "id": "SG.CS"
                    },
                    {
                        "id": "SG.EA"
                    },
                    {
                        "id": "SG.NO",
                        "value": "50"
                    },
                    {
                        "id": "SG.WE"
                    },
                    {
                        "id": "SG.SO"
                    }
                ],
                "markers": {
                    "shapes": [
                        {
                            "id": "newCustomShape",
                            "type": "circle",
                            "fillcolor": "FFFFFF,000099",
                            "fillpattern": "radial",
                            "showborder": "0",
                            "radius": "3"
                        }
                    ],
                    "items": [
                        {
                            "id": "02",
                            "shapeid": "newCustomShape",
                            "x": "254.52",
                            "y": "30.81",
                            "label": "Woodlands",
                            "labelpos": "left"
                        }
                    ]

                }
            }

        }).render();
    }
});
