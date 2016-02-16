/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


FusionCharts.ready(function () {
    var populationMap = new FusionCharts({
        type: 'maps/cambodia',
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
            "data": [
                {
                    "id": "KH.OM"
                },
                {
                    "id": "KH.BA"
                },
                {
                    "id": "KH.KM"
                },
                {
                    "id": "KH.KG"
                },
                {
                    "id": "KH.KS"
                },
                {
                    "id": "KH.KT"
                },
                {
                    "id": "KH.KP"
                },
                {
                    "id": "KH.KN"
                },
                {
                    "id": "KH.KK"
                },
                {
                    "id": "KH.KH"
                },
                {
                    "id": "KH.KB"
                },
                {
                    "id": "KH.MK"
                },
                {
                    "id": "KH.OC"
                },
                {
                    "id": "KH.PL"
                },
                {
                    "id": "KH.PP"
                },
                {
                    "id": "KH.PO"
                },
                {
                    "id": "KH.PH"
                },
                {
                    "id": "KH.PY"
                },
                {
                    "id": "KH.RO"
                },
                {
                    "id": "KH.SI"
                },
                {
                    "id": "KH.KA"
                },
                {
                    "id": "KH.ST"
                },
                {
                    "id": "KH.SR"
                },
                {
                    "id": "KH.TA"
                }
            ],
            "markers": {
                "shapes": [
                    {
                        "id": "myCustomShape",
                        "type": "circle",
                        "fillcolor": "FFFFFF,333333",
                        "fillpattern": "radial",
                        "showborder": "0",
                        "radius": "4"
                    },
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
                        "id": "PP",
                        "shapeid": "myCustomShape",
                        "x": "202.79",
                        "y": "237.79",
                        "label": "Phnom Penh",
                        "labelpos": "right"
                    },
                    {
                        "id": "01",
                        "shapeid": "newCustomShape",
                        "x": "107.05",
                        "y": "131.76",
                        "label": "Batdambang"
                    },
                    {
                        "id": "02",
                        "shapeid": "newCustomShape",
                        "x": "157.5",
                        "y": "103.97",
                        "label": "Siemreab"
                    },
                    {
                        "id": "03",
                        "shapeid": "newCustomShape",
                        "x": "71.02",
                        "y": "236.76",
                        "label": "Krong Kaoh Kong"
                    },
                    {
                        "id": "04",
                        "shapeid": "newCustomShape",
                        "x": "107.05",
                        "y": "307.79",
                        "label": "Kampong Cham"
                    },
                    {
                        "id": "05",
                        "shapeid": "newCustomShape",
                        "x": "163.67",
                        "y": "316.02",
                        "label": "Kampot",
                        "labelpos": "right"
                    },
                    {
                        "id": "06",
                        "shapeid": "newCustomShape",
                        "x": "278.97",
                        "y": "88.52",
                        "label": "Stoeng Treng"
                    },
                    {
                        "id": "08",
                        "shapeid": "newCustomShape",
                        "x": "276.91",
                        "y": "157.5",
                        "label": "Kracheh",
                        "labelpos": "left"
                    },
                    {
                        "id": "07",
                        "shapeid": "newCustomShape",
                        "x": "231.61",
                        "y": "187.35",
                        "label": "Kampong Cham",
                        "labelpos": "left"
                    }
                ]
            }
        }
    }).render();
});