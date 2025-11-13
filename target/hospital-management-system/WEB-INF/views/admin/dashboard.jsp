<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../common/header.jsp"/>
    <div class="dashboard-container">
        <h2>Admin Dashboard</h2>
        <div class="dashboard-menu">
            <a href="${pageContext.request.contextPath}/admin/doctors">Manage Doctors</a>
            <a href="${pageContext.request.contextPath}/admin/patients">Manage Patients</a>
            <a href="${pageContext.request.contextPath}/admin/appointments">View Appointments</a>
            <a href="${pageContext.request.contextPath}/admin/billing">Billing Management</a>
        </div>
    </div>
    <jsp:include page="../common/footer.jsp"/>
</body>
</html> 