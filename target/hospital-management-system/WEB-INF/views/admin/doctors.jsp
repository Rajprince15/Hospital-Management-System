<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Doctors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../common/header.jsp"/>
    <div class="container">
        <h2>Doctor Management</h2>
        
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <div class="action-bar">
            <a href="${pageContext.request.contextPath}/admin/doctors/add" class="btn btn-primary">Add New Doctor</a>
        </div>

        <c:choose>
            <c:when test="${empty doctors}">
                <p>No doctors found in the system.</p>
            </c:when>
            <c:otherwise>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Specialty</th>
                            <th>Contact</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${doctors}" var="doctor">
                            <tr>
                                <td>${doctor.id}</td>
                                <td>${doctor.name}</td>
                                <td>${doctor.specialty}</td>
                                <td>${doctor.contact}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/doctors/edit?id=${doctor.id}" 
                                       class="btn btn-small">Edit</a>
                                    <c:url value="/admin/doctors/delete" var="deleteUrl">
                                        <c:param name="id" value="${doctor.id}"/>
                                    </c:url>
                                    <a href="${deleteUrl}" 
                                       class="btn btn-small btn-danger"
                                       onclick="return confirm('Are you sure you want to delete this doctor?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="../common/footer.jsp"/>
</body>
</html> 