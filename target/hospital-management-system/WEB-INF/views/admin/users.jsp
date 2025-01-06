<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../common/header.jsp"/>
    <div class="container">
        <h2>User Management</h2>
        <div class="action-bar">
            <a href="${pageContext.request.contextPath}/admin/users/add" class="btn btn-primary">Add New User</a>
        </div>
        
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Phone</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.fullName}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>${user.phoneNumber}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/users/edit?id=${user.id}" class="btn btn-small">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/users/delete?id=${user.id}" 
                               class="btn btn-small btn-danger" 
                               onclick="return confirm('Are you sure you want to delete this user?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <jsp:include page="../common/footer.jsp"/>
</body>
</html> 