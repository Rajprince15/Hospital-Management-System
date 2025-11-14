package main.java.servlet;

import main.java.entities.User;
import main.java.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Add CORS headers
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Get credentials from form data
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            System.out.println("Login attempt for username: " + username);

            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println(gson.toJson(new LoginResponse(false, "Username and password are required", null)));
                return;
            }

            // Authenticate user
            User user = userService.authenticateUser(username, password);

            if (user != null) {
                // Create session
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                session.setMaxInactiveInterval(30 * 60); // 30 minutes

                System.out.println("Login successful for user: " + username);
                
                response.setStatus(HttpServletResponse.SC_OK);
                out.println(gson.toJson(new LoginResponse(true, "Login successful", new UserInfo(user))));
            } else {
                System.out.println("Login failed for user: " + username);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.println(gson.toJson(new LoginResponse(false, "Invalid username or password", null)));
            }
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson(new LoginResponse(false, "An error occurred during login", null)));
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle preflight CORS requests
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    // Response classes
    private static class LoginResponse {
        private final boolean success;
        private final String message;
        private final UserInfo user;

        public LoginResponse(boolean success, String message, UserInfo user) {
            this.success = success;
            this.message = message;
            this.user = user;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public UserInfo getUser() {
            return user;
        }
    }

    private static class UserInfo {
        private final int id;
        private final String username;
        private final String email;
        private final String fullName;
        private final String role;

        public UserInfo(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.fullName = user.getFullName();
            this.role = user.getRole();
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getFullName() {
            return fullName;
        }

        public String getRole() {
            return role;
        }
    }
}
