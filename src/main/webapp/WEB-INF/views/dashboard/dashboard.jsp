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
<script src="https://unpkg.com/leaflet@1.9.4/dist/leaflet.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    // Initialize the map
    const map = L.map('map').setView([-25.2744, 133.7751], 4); // Center on Australia

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
    }).addTo(map);
</script>
</body>
</html>