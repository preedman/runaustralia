<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Route Map</title>

    <%-- Leaflet CSS --%>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.css" />

    <style>
        #map {
            height: 600px;
            width: 100%;
        }
        .form-container {
            margin: 20px;
            padding: 10px;
        }
        .input-group {
            margin: 10px 0;
        }
        label {
            display: inline-block;
            width: 100px;
        }
        input[type="text"] {
            width: 200px;
            padding: 5px;
        }
        button {
            padding: 5px 10px;
            margin: 5px;
        }
    </style>
</head>
<body>
<%-- Form for entering coordinates --%>
<div class="form-container">
    <div class="input-group">
        <label for="startLat">Start Lat:</label>
        <input type="text" id="startLat" value="51.5" />

        <label for="startLng">Start Lng:</label>
        <input type="text" id="startLng" value="-0.09" />
    </div>

    <div class="input-group">
        <label for="endLat">End Lat:</label>
        <input type="text" id="endLat" value="51.51" />

        <label for="endLng">End Lng:</label>
        <input type="text" id="endLng" value="-0.1" />
    </div>

    <button onclick="updateRoute()">Update Route</button>
</div>

<%-- Map container --%>
<div id="map"></div>

<%-- Leaflet JS --%>
<script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>

<script src="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.js"></script>
<script src="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.js"></script>


<script>
    // Initialize map and routing control variables
    let map;
    let routingControl;

    // Initialize the map when the page loads
    window.onload = function() {
        initializeMap();
    };

    function initializeMap() {
        // Create the map
      //  map = L.map('map').setView([51.505, -0.09], 13);
        map = L.map('map').setView([153.02528, -27.4695], 13);

        // Add the OpenStreetMap tiles
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '© OpenStreetMap contributors'
        }).addTo(map);

        // Initialize the routing control with default coordinates
        createRoute();
    }

    function createRoute() {
        // Remove existing routing control if it exists
        if (routingControl) {
            map.removeControl(routingControl);
        }

        // Get coordinates from input fields
        const startLat = parseFloat(document.getElementById('startLat').value);
        const startLng = parseFloat(document.getElementById('startLng').value);
        const endLat = parseFloat(document.getElementById('endLat').value);
        const endLng = parseFloat(document.getElementById('endLng').value);

        // Create new routing control
        routingControl = L.Routing.control({
            waypoints: [
                L.latLng(startLat, startLng),
                L.latLng(endLat, endLng)
            ],
            routeWhileDragging: true,
            showAlternatives: true,
            lineOptions: {
                styles: [
                    {color: 'blue', opacity: 0.6, weight: 4}
                ]
            }
        }).addTo(map);

        // Add markers for start and end points
        addMarkers(startLat, startLng, endLat, endLng);
    }

    function addMarkers(startLat, startLng, endLat, endLng) {
        // Add start marker
        L.marker([startLat, startLng])
            .bindPopup('Start Point')
            .addTo(map);

        // Add end marker
        L.marker([endLat, endLng])
            .bindPopup('End Point')
            .addTo(map);
    }

    function updateRoute() {
        createRoute();
    }

    // Optional: Add error handling for invalid coordinates
    function validateCoordinates() {
        const inputs = ['startLat', 'startLng', 'endLat', 'endLng'];
        let isValid = true;

        inputs.forEach(id => {
            const value = parseFloat(document.getElementById(id).value);
            if (isNaN(value)) {
                alert(`Invalid coordinate value for ${id}`);
                isValid = false;
            }
        });

        return isValid;
    }
</script>
</body>
</html>