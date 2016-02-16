/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

FusionCharts.ready(function () {
    var populationMap = new FusionCharts({
        type: 'maps/singapore',
        renderAt: 'chart-container',
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
                    "id": "SG.CS"
                },
                {
                    "id": "SG.EA"
                },
                {
                    "id": "SG.NO"
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
                        "id": "SI",
                        "shapeid": "myCustomShape",
                        "x": "327.83",
                        "y": "255",
                        "label": "Singapore",
                        "labelpos": "right"
                    },
                    {
                        "id": "01",
                        "shapeid": "newCustomShape",
                        "x": "383.08",
                        "y": "85",
                        "label": "Punggol"
                    },
                    {
                        "id": "02",
                        "shapeid": "newCustomShape",
                        "x": "254.52",
                        "y": "30.81",
                        "label": "Woodlands",
                        "labelpos": "left"
                    },
                    {
                        "id": "03",
                        "shapeid": "newCustomShape",
                        "x": "202.45",
                        "y": "69.06",
                        "label": "Kranji",
                        "labelpos": "right"
                    },
                    {
                        "id": "04",
                        "shapeid": "newCustomShape",
                        "x": "124.89",
                        "y": "104.12",
                        "label": "Chao Chu Kang",
                        "labelpos": "left"
                    },
                    {
                        "id": "05",
                        "shapeid": "newCustomShape",
                        "x": "87.7",
                        "y": "263.5",
                        "label": "Tuas",
                        "labelpos": "right"
                    },
                    {
                        "id": "06",
                        "shapeid": "newCustomShape",
                        "x": "225.83",
                        "y": "223.12",
                        "label": "Queen Towns"
                    },
                    {
                        "id": "07",
                        "shapeid": "newCustomShape",
                        "x": "343.77",
                        "y": "192.31",
                        "label": "Toa Payoh",
                        "labelpos": "right"
                    },
                    {
                        "id": "08",
                        "shapeid": "newCustomShape",
                        "x": "478.7",
                        "y": "202.93",
                        "label": "Bedok"
                    },
                    {
                        "id": "09",
                        "shapeid": "newCustomShape",
                        "x": "520.14",
                        "y": "148.75",
                        "label": "Changi",
                        "labelpos": "left"
                    }
                ]
            }
        }
    }).render();
});


