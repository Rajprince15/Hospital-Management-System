<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav>
        <div class="logo">Hospital Management System</div>
        <div class="nav-links">
            <% if (session.getAttribute("user") != null) { %>
                <span>Welcome, ${user.fullName}</span>
                <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
            <% } %>
        </div>
    </nav>
</header> 