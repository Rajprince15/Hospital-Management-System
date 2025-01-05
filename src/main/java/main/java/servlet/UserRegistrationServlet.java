package main.java.servlet;

import main.java.entities.User;
import main.java.service.UserService;
import main.java.service.DoctorService;
import main.java.service.PatientService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/register")
public class UserRegistrationServlet extends HttpServlet {
    private final UserService userService;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final Gson gson;

    public UserRegistrationServlet() {
        this.userService = new UserService();
        this.doctorService = new DoctorService();
        this.patientService = new PatientService();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Read registration form data
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullName = request.getParameter("fullName");
            String role = request.getParameter("role");
            String phoneNumber = request.getParameter("phoneNumber");
            String address = request.getParameter("address");

            // Validate required fields
            if (username == null || password == null || email == null || 
                fullName == null || role == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println(gson.toJson("Required fields are missing"));
                return;
            }

            int associatedId = 0;

            // Handle role-specific registration
            if ("DOCTOR".equals(role)) {
                // Create doctor record first
                Doctor doctor = new Doctor(0, fullName, specialization, 
                                         phoneNumber, email, address);
                boolean doctorAdded = doctorService.addDoctor(doctor);
                if (!doctorAdded) {
                    throw new Exception("Failed to create doctor record");
                }
                associatedId = doctor.getId();
            } else if ("PATIENT".equals(role)) {
                // Create patient record first
                Patient patient = new Patient(0, fullName, phoneNumber, 
                                           email, address);
                boolean patientAdded = patientService.addPatient(patient);
                if (!patientAdded) {
                    throw new Exception("Failed to create patient record");
                }
                associatedId = patient.getId();
            }

            // Create user account
            User newUser = new User(0, username, password, email, fullName, 
                                  role, associatedId, phoneNumber, address);
            boolean registered = userService.registerUser(newUser);

            if (registered) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.println(gson.toJson("User registered successfully"));
            } else {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.println(gson.toJson("Username already exists"));
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson("Error: " + e.getMessage()));
        }
    }
}