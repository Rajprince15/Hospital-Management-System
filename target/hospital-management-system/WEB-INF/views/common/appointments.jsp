<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Appointments</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="container">
        <h2>Appointments</h2>

        <c:if test="${sessionScope.user.role == 'DOCTOR' || sessionScope.user.role == 'ADMIN'}">
            <div class="action-bar">
                <a href="${pageContext.request.contextPath}/appointments/add" class="btn btn-primary">
                    Schedule New Appointment
                </a>
            </div>
        </c:if>

        <div class="filter-section">
            <form action="${pageContext.request.contextPath}/appointments" method="get">
                <select name="status">
                    <option value="">All Status</option>
                    <c:forEach items="${appointmentStatuses}" var="status">
                        <option value="${status}" ${param.status == status ? 'selected' : ''}>
                            ${status}
                        </option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn">Filter</button>
            </form>
        </div>

        <c:choose>
            <c:when test="${empty appointments}">
                <p>No appointments found.</p>
            </c:when>
            <c:otherwise>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Patient</th>
                            <th>Doctor</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${appointments}" var="appointment">
                            <tr>
                                <td>${appointment.id}</td>
                                <td>${appointment.patientName}</td>
                                <td>${appointment.doctorName}</td>
                                <td>
                                    <fmt:parseDate value="${appointment.appointmentDate}" 
                                                 pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate"/>
                                    <fmt:formatDate value="${parsedDate}" 
                                                  pattern="MMM dd, yyyy HH:mm"/>
                                </td>
                                <td>
                                    <span class="status-badge status-${appointment.status.toLowerCase()}">
                                        ${appointment.status}
                                    </span>
                                </td>
                                <td>
                                    <c:if test="${sessionScope.user.role == 'DOCTOR' || 
                                                 sessionScope.user.role == 'ADMIN'}">
                                        <a href="${pageContext.request.contextPath}/appointments/edit?id=${appointment.id}" 
                                           class="btn btn-small">Edit</a>
                                    </c:if>
                                    <c:if test="${appointment.status == 'SCHEDULED'}">
                                        <a href="${pageContext.request.contextPath}/appointments/cancel?id=${appointment.id}" 
                                           class="btn btn-small btn-danger"
                                           onclick="return confirm('Are you sure you want to cancel this appointment?')">
                                            Cancel
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Pagination -->
                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:forEach begin="1" end="${totalPages}" var="page">
                            <a href="${pageContext.request.contextPath}/appointments?page=${page}" 
                               class="btn btn-small ${currentPage == page ? 'btn-primary' : ''}">
                                ${page}
                            </a>
                        </c:forEach>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html> 