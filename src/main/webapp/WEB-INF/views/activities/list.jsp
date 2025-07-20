<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Activities List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Activities List</h2>

    <!-- Filter Form -->
    <form class="row g-3 mb-4" method="GET">
        <div class="col-md-3">
            <label for="memberId" class="form-label">Member</label>
            <select name="memberId" id="memberId" class="form-select">
                <option value="">All Members</option>
                <c:forEach items="${members}" var="member">
                    <option value="${member.id}" ${param.memberId == member.id ? 'selected' : ''}>
                            ${member.firstname} ${member.lastname}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-3">
            <label for="startDate" class="form-label">Start Date</label>
            <input type="date" class="form-control" id="startDate" name="startDate"
                   value="${param.startDate}">
        </div>
        <div class="col-md-3">
            <label for="endDate" class="form-label">End Date</label>
            <input type="date" class="form-control" id="endDate" name="endDate"
                   value="${param.endDate}">
        </div>
        <div class="col-md-3">
            <label class="form-label">&nbsp;</label>
            <button type="submit" class="btn btn-primary d-block">Filter</button>
        </div>
    </form>

    <!-- Activities Table -->
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>Date</th>
            <th>Member</th>
            <th>Type</th>
            <th>Distance</th>
            <th>Time</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${activities.content}" var="activity">
            <tr>
                <td>${activity.datedone.format(DateTimeFormatter.ofPattern('yyyy-MM-dd'))}</td>

                <td>${activity.memberid.firstname} ${activity.memberid.lastname}</td>
                <td>${activity.type}</td>
                <td>${activity.distance} km</td>
                <td>${activity.activitytime} hrs</td>
                <td>${activity.description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Pagination -->
    <nav>
        <ul class="pagination justify-content-center">
            <li class="page-item ${activities.first ? 'disabled' : ''}">
                <a class="page-link" href="?page=0&size=${activities.size}&memberId=${param.memberId}&startDate=${param.startDate}&endDate=${param.endDate}">First</a>
            </li>
            <li class="page-item ${activities.first ? 'disabled' : ''}">
                <a class="page-link" href="?page=${activities.number - 1}&size=${activities.size}&memberId=${param.memberId}&startDate=${param.startDate}&endDate=${param.endDate}">Previous</a>
            </li>
            <li class="page-item disabled">
                <span class="page-link">Page ${activities.number + 1} of ${activities.totalPages}</span>
            </li>
            <li class="page-item ${activities.last ? 'disabled' : ''}">
                <a class="page-link" href="?page=${activities.number + 1}&size=${activities.size}&memberId=${param.memberId}&startDate=${param.startDate}&endDate=${param.endDate}">Next</a>
            </li>
            <li class="page-item ${activities.last ? 'disabled' : ''}">
                <a class="page-link" href="?page=${activities.totalPages - 1}&size=${activities.size}&memberId=${param.memberId}&startDate=${param.startDate}&endDate=${param.endDate}">Last</a>
            </li>
        </ul>
    </nav>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>