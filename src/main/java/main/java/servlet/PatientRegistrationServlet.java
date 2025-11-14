package main.java.servlet;

import main.java.entities.Patient;
import main.java.entities.User;
import main.java.service.PatientService;
import main.java.service.UserService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/register-patient")
public class PatientRegistrationServlet extends HttpServlet {
    private UserService userService;
    private PatientService patientService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        patientService = new PatientService();
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
            // Collect form parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");

            // Validation
            if (username == null || password == null || email == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println(gson.toJson(new RegistrationResponse(false, "Username, password, and email are required", 0)));
                return;
            }

            // Check if username already exists
            if (userService.getUserByUsername(username) != null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.println(gson.toJson(new RegistrationResponse(false, "Username already exists", 0)));
                return;
            }

            // First, create patient record
            Patient patient = new Patient(0, fullName != null ? fullName : username,
                    phoneNumber != null ? phoneNumber : "",
                    address != null ? address : "");
            boolean patientCreated = patientService.addPatient(patient);

            if (!patientCreated) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(gson.toJson(new RegistrationResponse(false, "Failed to create patient record", 0)));
                return;
            }

            // Get the created patient ID
            int patientId = patient.getId();

            // Create user object with associated patient ID
            User user = new User(0, username, password, email,
                    fullName != null ? fullName : username,
                    "PATIENT",
                    patientId,
                    phoneNumber != null ? phoneNumber : "",
                    address != null ? address : "");

            // Register user
            boolean userRegistered = userService.registerUser(user);

            if (userRegistered) {
                System.out.println("User and patient registered successfully: " + username + " with patient ID: " + patientId);
                response.setStatus(HttpServletResponse.SC_OK);
                out.println(gson.toJson(new RegistrationResponse(true, "Registration successful", patientId)));
            } else {
                // If user registration fails, we should ideally rollback patient creation
                // For now, just report the error
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(gson.toJson(new RegistrationResponse(false, "User registration failed", 0)));
            }
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson(new RegistrationResponse(false, "An error occurred during registration", 0)));
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

    // Response class
    private static class RegistrationResponse {
        private final boolean success;
        private final String message;
        private final int patientId;

        public RegistrationResponse(boolean success, String message, int patientId) {
            this.success = success;
            this.message = message;
            this.patientId = patientId;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public int getPatientId() {
            return patientId;
        }
    }
}
