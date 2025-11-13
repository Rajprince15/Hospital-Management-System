package main.java.controller;


import com.google.gson.Gson;
import main.java.entities.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/*")
public class HospitalManagementServlet extends HttpServlet {
    private final PatientController patientController;
    private final DoctorController doctorController;
    private final AppointmentController appointmentController;
    private final BillingController billingController;
    private final Gson gson;

    public HospitalManagementServlet() {
        patientController = new PatientController();
        doctorController = new DoctorController();
        appointmentController = new AppointmentController();
        billingController = new BillingController();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Add CORS headers
        addCorsHeaders(response);

        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] paths = pathInfo.split("/");
            if (paths.length < 2) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            switch (paths[1]) {
                case "patients":
                    if (paths.length == 2) {
                        out.println(gson.toJson(patientController.getAllPatients()));
                    } else {
                        int id = Integer.parseInt(paths[2]);
                        out.println(gson.toJson(patientController.getPatientById(id)));
                    }
                    break;

                case "doctors":
                    if (paths.length == 2) {
                        out.println(gson.toJson(doctorController.getAllDoctors()));
                    } else {
                        int id = Integer.parseInt(paths[2]);
                        out.println(gson.toJson(doctorController.getDoctorById(id)));
                    }
                    break;

                case "appointments":
                    if (paths.length == 2) {
                        out.println(gson.toJson(appointmentController.getAllAppointments()));
                    } else {
                        int id = Integer.parseInt(paths[2]);
                        out.println(gson.toJson(appointmentController.getAppointmentById(id)));
                    }
                    break;

                case "bills":
                    if (paths.length == 2) {
                        out.println(gson.toJson(billingController.getAllBills()));
                    } else {
                        int id = Integer.parseInt(paths[2]);
                        out.println(gson.toJson(billingController.getBillById(id)));
                    }
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson("Error: " + e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Add CORS headers
        addCorsHeaders(response);

        String pathInfo = request.getPathInfo();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] paths = pathInfo.split("/");
            String requestBody = request.getReader().lines()
                    .reduce("", (accumulator, actual) -> accumulator + actual);

            switch (paths[1]) {
                case "patients":
                    Patient patient = gson.fromJson(requestBody, Patient.class);
                    boolean patientAdded = patientController.addPatient(patient);
                    out.println(gson.toJson(patientAdded));
                    break;

                case "doctors":
                    Doctor doctor = gson.fromJson(requestBody, Doctor.class);
                    boolean doctorAdded = doctorController.addDoctor(doctor);
                    out.println(gson.toJson(doctorAdded));
                    break;

                case "appointments":
                    Appointment appointment = gson.fromJson(requestBody, Appointment.class);
                    boolean appointmentAdded = appointmentController.scheduleAppointment(
                            appointment.getPatientId(),
                            appointment.getDoctorId(),
                            appointment.getAppointmentDate(),
                            appointment.getStatus()
                    );
                    out.println(gson.toJson(appointmentAdded));
                    break;

                case "bills":
                    Billing billing = gson.fromJson(requestBody, Billing.class);
                    boolean billAdded = billingController.addBill(
                            billing.getPatientId(),
                            billing.getAmount(),
                            billing.getBillDate()
                    );
                    out.println(gson.toJson(billAdded));
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println(gson.toJson("Error: " + e.getMessage()));
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle preflight CORS requests
        addCorsHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Max-Age", "3600");
    }
}