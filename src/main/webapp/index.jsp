<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Elite Care Hospital - Advanced Healthcare</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <c:redirect url="/${sessionScope.user.role.toLowerCase()}/dashboard"/>
        </c:when>
        <c:otherwise>
            <!-- Include the main hospital website content -->
            <jsp:include page="main.html"/>
        </c:otherwise>
    </c:choose>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Handle login form submission
        function handleLogin(event) {
            event.preventDefault();
            const formData = new FormData(event.target);
            
            fetch('${pageContext.request.contextPath}/auth/login', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Redirect based on role
                    window.location.href = '${pageContext.request.contextPath}/' + data.role.toLowerCase() + '/dashboard';
                } else {
                    alert(data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Login failed. Please try again.');
            });
        }
        
        // Handle registration form submission
        function handleRegister(event) {
            event.preventDefault();
            const formData = new FormData(event.target);
            
            fetch('${pageContext.request.contextPath}/auth/register', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Registration successful! Please login.');
                    // Switch to login form
                } else {
                    alert(data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Registration failed. Please try again.');
            });
        }
        
        // Handle appointment booking
        function handleAppointmentBooking(event) {
            event.preventDefault();
            const formData = new FormData(event.target);
            
            fetch('${pageContext.request.contextPath}/appointments/', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Appointment booked successfully!');
                    event.target.reset();
                } else {
                    alert(data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Booking failed. Please try again.');
            });
        }
    </script>
</body>
</html>

            </div>
        <% } %>
        <form action="${pageContext.request.contextPath}/auth/login" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">Login</button>
        </form>
    </div>
</body>
</html> 