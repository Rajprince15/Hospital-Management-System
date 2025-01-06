<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Patients</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../common/header.jsp"/>
    <div class="container">
        <h2>Patient Management</h2>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <div class="action-bar">
            <a href="${pageContext.request.contextPath}/admin/patients/add" class="btn btn-primary">
                Add New Patient
            </a>
        </div>

        <div class="search-section">
            <form action="${pageContext.request.contextPath}/admin/patients" method="get">
                <input type="text" name="search" value="${param.search}" placeholder="Search by name...">
                <button type="submit" class="btn">Search</button>
            </form>
        </div>

        <c:choose>
            <c:when test="${empty patients}">
                <p>No patients found.</p>
            </c:when>
            <c:otherwise>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Contact</th>
                            <th>Address</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${patients}" var="patient">
                            <tr>
                                <td>${patient.id}</td>
                                <td>${patient.name}</td>
                                <td>${patient.contact}</td>
                                <td>${patient.address}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/patients/edit?id=${patient.id}" 
                                       class="btn btn-small">Edit</a>
                                    <a href="${pageContext.request.contextPath}/admin/patients/view?id=${patient.id}" 
                                       class="btn btn-small">View Details</a>
                                    <c:url value="/admin/patients/delete" var="deleteUrl">
                                        <c:param name="id" value="${patient.id}"/>
                                    </c:url>
                                    <a href="${deleteUrl}" 
                                       class="btn btn-small btn-danger"
                                       onclick="return confirm('Are you sure you want to delete this patient?')">
                                        Delete
                                    </a>
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