<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>RunAustralia Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include Leaflet CSS -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.4/dist/leaflet.css"/>
    <style>
        #map { height: 400px; }
    </style>
    <link rel="stylesheet" href="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.css" />

</head>
<body>
<div class="container mt-4">
    <h1>Dashboard</h1>

    <!-- Statistics Cards -->
    <div class="row mb-4">
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Members</h5>
                    <p class="card-text">Total: ${stats.totalMembers}</p>
                    <p class="card-text">Active: ${stats.activeMembers}</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Activities (${stats.period})</h5>
                    <p class="card-text">Count: ${stats.totalActivities}</p>
                    <p class="card-text">Distance: ${stats.totalDistance} km</p>
                    <p class="card-text">Time: ${stats.totalTime} hrs</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Map -->
    <div class="row mb-4">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Activity Map</h5>
                    <div id="map"></div>
                </div>
            </div>
        </div>
    </div>

    <!-- Recent Activities -->
    <div class="row mb-4">
        <div class="col-md-6">
            <h3>Recent Activities</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Member</th>
                    <th>Type</th>
                    <th>Distance</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${recentActivities}" var="activity">
                    <tr>
                        <td>${activity.datedone}</td>
                        <td>${activity.memberid.firstname} ${activity.memberid.lastname}</td>
                        <td>${activity.type}</td>
                        <td>${activity.distance} km</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="col-md-6">
            <h3>Active Members</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Join Date</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${activeMembers}" var="member">
                    <tr>
                        <td>${member.firstname} ${member.lastname}</td>
                        <td>${member.joindate}</td>
                        <td>${member.status}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Include Leaflet JS -->
<!-- <script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script> -->


<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.js"></script>
<script src="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.js"></script>


<script>
    // Initialize the map
  //  const map = L.map('map').setView([-25.2744, 133.7751], 4); // Center on Australia

  //  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
 //       attribution: '© OpenStreetMap contributors'
 //   }).addTo(map);

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
       // const startLat = 153.02528;
       // const startLng = -27.4695;
       // const endLat = 153.1352;
       // const endLng = -26.80512;

        // To this:
        const startLat = -27.4695;
        const startLng = 153.02528;
        const endLat = -26.80512;
        const endLng = 153.1352;


        //   const startLat = parseFloat(document.getElementById('startLat').value);
     //   const startLng = parseFloat(document.getElementById('startLng').value);
     //   const endLat = parseFloat(document.getElementById('endLat').value);
     //   const endLng = parseFloat(document.getElementById('endLng').value);

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