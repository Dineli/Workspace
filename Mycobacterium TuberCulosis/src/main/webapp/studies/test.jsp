<%-- 
    Document   : test
    Created on : Feb 12, 2016, 1:45:10 PM
    Author     : ephaadk
--%>


<!DOCTYPE html>
<html>
    <head>
        <title>Leaflet Quick Start Guide Example</title>
        <meta charset="utf-8" />

        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.css" />
    </head>
    <body>
        <div id="map" style="width: 600px; height: 400px"></div>

        <script src="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.js"></script>
        <script>
            window.setTimeout(initMap, 100);

            function initMap() {
                //this should check if your leaflet is available or wait if not. 
                if (typeof L === "undefined") {
                    window.setTimeout(initMap, 100);
                    return;
                }
//        var map = L.map('map');
                var map = L.map('map').setView([45.8167, 15.9833], 10);
                var mbUrl = 'https://{s}.tiles.mapbox.com/v3/{id}/{z}/{x}/{y}.png';
                L.tileLayer(mbUrl, {id: 'examples.map-i875mjb7'}).addTo(map);
                var marker = L.marker([45.8167, 15.9833]).bindPopup("Zagreb").addTo(map);
            }
            ;
        </script>
    </body>
</html>

