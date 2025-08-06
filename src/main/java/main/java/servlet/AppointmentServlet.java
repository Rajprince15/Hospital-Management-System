package main.java.servlet;

import com.google.gson.Gson;
import main.java.controller.AppointmentController;
import main.java.controller.DoctorController;
import main.java.controller.PatientController;
import main.java.entities.Appointment;
import main.java.entities.Doctor;
import main.java.entities.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/appointments/*")
public class AppointmentServlet extends HttpServlet {
    private final AppointmentController appointmentController;
    private final DoctorController doctorController;
    private final PatientController patientController;
    private final Gson gson;

    public AppointmentServlet() {
        appointmentController = new AppointmentController();
        doctorController = new DoctorController();
        patientController = new PatientController();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Return all appointments
            List<Appointment> appointments = appointmentController.getAllAppointments();
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(appointments));
        } else if (pathInfo.equals("/doctors")) {
            // Return all doctors for appointment booking
            List<Doctor> doctors = doctorController.getAllDoctors();
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(doctors));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Please login first\"}");
            return;
        }

        String patientIdStr = request.getParameter("patientId");
        String doctorIdStr = request.getParameter("doctorId");
        String appointmentDate = request.getParameter("appointmentDate");
        String department = request.getParameter("department");

        if (patientIdStr == null || doctorIdStr == null || appointmentDate == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Missing required fields\"}");
            return;
        }

        try {
            int patientId = Integer.parseInt(patientIdStr);
            int doctorId = Integer.parseInt(doctorIdStr);

            boolean success = appointmentController.scheduleAppointment(patientId, doctorId, appointmentDate, "SCHEDULED");
            
            response.setContentType("application/json");
            if (success) {
                response.getWriter().write("{\"success\": true, \"message\": \"Appointment scheduled successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"success\": false, \"message\": \"Failed to schedule appointment\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Invalid patient or doctor ID\"}");
        }
    }
}