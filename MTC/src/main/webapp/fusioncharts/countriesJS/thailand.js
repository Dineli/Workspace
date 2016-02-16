/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global FusionCharts */

FusionCharts.ready(function () {
    var populationMap = new FusionCharts({
        type: 'maps/thailand',
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
            "markers": {
                "shapes": [
//                    {
//                        "id": "myCustomShape",
//                        "type": "circle",
//                        "fillcolor": "FFFFFF,333333",
//                        "fillpattern": "radial",
//                        "showborder": "0",
//                        "radius": "4"
//                    },
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
                        "id": "BA",
                        "shapeid": "newCustomShape",
                        "x": "245.86",
                        "y": "547.57",
                        "label": "Bangkok",
                        "labelpos": "right"
                    },
                    {
                        "id": "07",
                        "shapeid": "newCustomShape",
                        "x": "105.99",
                        "y": "121.4",
                        "label": "Chaing Mai",
                        "labelpos": "left"
                    }

                ]
            }
        }
    }).render();
});
