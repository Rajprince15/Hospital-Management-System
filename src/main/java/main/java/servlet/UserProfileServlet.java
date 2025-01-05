package main.java.servlet;

import main.java.entities.User;
import main.java.service.UserService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/profile/*")
public class UserProfileServlet extends HttpServlet {
    private final UserService userService;
    private final Gson gson;

    public UserProfileServlet() {
        this.userService = new UserService();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Check if user is logged in
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.println(gson.toJson("User not logged in"));
                return;
            }

            int userId = (Integer) session.getAttribute("userId");
            User user = userService.getUserById(userId);

            if (user != null) {
                // Don't send password in response
                user.setPassword(null);
                out.println(gson.toJson(user));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println(gson.toJson("User not found"));
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson("Error: " + e.getMessage()));
        }
    }
}