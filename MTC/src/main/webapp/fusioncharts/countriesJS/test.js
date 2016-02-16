/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global FusionCharts */

FusionCharts.ready(function () {
    var populationMap = new FusionCharts({
        type: 'maps/world',
        renderAt: 'chart-container',
        width: '600',
        height: '400',
        dataFormat: 'json',
        dataSource: {
                "chart": {
                "caption": "Global Population",
                "theme": "fint",
                "formatNumberScale":"0",
                "numberSuffix":"M"
            },
            "colorrange": {
                "color": [
                    {
                        "minvalue": "0",
                        "maxvalue": "100",
                        "code": "#E0F0E0",
                        "displayValue" : "Below 100M"
                    },
                    {
                        "minvalue": "100",
                        "maxvalue": "500",
                        "code": "#D0DFA3",
                        "displayValue" : "100-500M"
                    },
                    {
                        "minvalue": "500",
                        "maxvalue": "1000",
                        "code": "#B0BF92",
                        "displayValue" : "500-1000M"
                    },
                    {
                        "minvalue": "1000",
                        "maxvalue": "5000",
                        "code": "#91AF64",
                        "displayValue" : "Above 1B"
                    }
                ]
            },
            "data": [
                {
                    "id": "NA",
                    "value": "515"
                },
                {
                    "id": "SA",
                    "value": "373"
                },
                {
                    "id": "AS",
                    "value": "3875"
                },
                {
                    "id": "EU",
                    "value": "727"
                },
                {
                    "id": "AF",
                    "value": "885"
                },
                {
                    "id": "AU",
                    "value": "32"
                }
            ]
        }
    }).render();
});


