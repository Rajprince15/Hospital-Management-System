<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="container">
        <div class="profile-card">
            <h2>User Profile</h2>
            <div class="profile-info">
                <div class="info-group">
                    <label>Username:</label>
                    <span>${user.username}</span>
                </div>
                <div class="info-group">
                    <label>Full Name:</label>
                    <span>${user.fullName}</span>
                </div>
                <div class="info-group">
                    <label>Email:</label>
                    <span>${user.email}</span>
                </div>
                <div class="info-group">
                    <label>Role:</label>
                    <span>${user.role}</span>
                </div>
                <div class="info-group">
                    <label>Phone Number:</label>
                    <span>${user.phoneNumber}</span>
                </div>
                <div class="info-group">
                    <label>Address:</label>
                    <span>${user.address}</span>
                </div>
            </div>
            <div class="profile-actions">
                <a href="${pageContext.request.contextPath}/profile/edit" class="btn btn-primary">Edit Profile</a>
                <a href="${pageContext.request.contextPath}/profile/changePassword" class="btn">Change Password</a>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html> 