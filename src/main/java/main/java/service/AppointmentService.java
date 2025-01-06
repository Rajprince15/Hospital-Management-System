package main.java.service;

import main.java.dao.AppointmentDAO;
import main.java.entities.Appointment;

import java.util.List;

public class AppointmentService {

    private final AppointmentDAO appointmentDAO;

    public AppointmentService() {
        this.appointmentDAO = new AppointmentDAO();
    }

    // Schedule a new appointment
    public boolean scheduleAppointment(Appointment appointment) {
        if (appointment == null || appointment.getPatientId() <= 0 || appointment.getDoctorId() <= 0) {
            throw new IllegalArgumentException("Invalid appointment details provided.");
        }
        return appointmentDAO.scheduleAppointment(appointment);
    }

    // Update an existing appointment
    public boolean updateAppointment(Appointment appointment) {
        if (appointment == null || appointment.getId() <= 0) {
            throw new IllegalArgumentException("Invalid appointment details provided.");
        }
        return appointmentDAO.updateAppointment(appointment);
    }

    // Get an appointment by ID
    public Appointment getAppointmentById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid appointment ID.");
        }
        return appointmentDAO.getAppointmentById(id);
    }

    // Get all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    // Cancel an appointment by ID
    public boolean cancelAppointment(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid appointment ID.");
        }
        return appointmentDAO.cancelAppointment(id);
    }

    // Get appointments by doctor ID
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        if (doctorId <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID.");
        }
        return appointmentDAO.getAppointmentsByDoctor(doctorId);
    }
}
