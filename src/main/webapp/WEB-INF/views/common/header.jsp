<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <div class="banner">
        <div class="banner-text">
            <span><b>Helpline: </b></span>
            <i class="fas fa-phone-volume fa-beat"></i>
            <span>1-800-123-4567</span>
            <span>|</span>
            <i class="fa fa-envelope"></i>
            <span>info@elitecare.com</span>
        </div>
        <div class="banner-icon">
            <span><b>Our Socials: </b></span>
            <i class="fab fa-twitter"></i>
            <i class="fab fa-instagram"></i>
            <i class="fab fa-facebook"></i>
            <i class="fab fa-linkedin"></i>
            <i class="fab fa-telegram"></i>
            <i class="fab fa-whatsapp"></i>
        </div>
    </div>
    
    <nav class="navbar navbar-expand-lg sticky-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <img src="https://i.pinimg.com/564x/d2/9e/b9/d29eb99cbe1e1e181a7551ec6416743e.jpg" 
                     alt="Elite Care Logo" 
                     style="mix-blend-mode: multiply; max-width: 50px;" />
                Elite Care Hospital
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <c:choose>
                        <c:when test="${not empty sessionScope.user}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/${sessionScope.user.role.toLowerCase()}/dashboard">Dashboard</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/appointments/">Appointments</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/#home">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/#departments">Departments</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/#doctors">Doctors</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
        <div class="nav-links">
            <c:if test="${not empty sessionScope.user}">
                <span>Welcome, ${sessionScope.user.fullName}</span>
                <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
            </c:if>
        </div>
    </nav>
</header>