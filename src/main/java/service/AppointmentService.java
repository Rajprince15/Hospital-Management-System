package main.java.service;

import main.java.dao.AppointmentDAO;
import main.java.entities.Appointment;

import java.util.List;

public class AppointmentService {
    private final AppointmentDAO appointmentDAO;

    // Constructor
    public AppointmentService() {
        this.appointmentDAO = new AppointmentDAO();
    }

    // Schedule a new appointment
    public boolean scheduleAppointment(Appointment appointment) {
        return appointmentDAO.scheduleAppointment(appointment);
    }

    // Update appointment details
    public boolean updateAppointment(Appointment appointment) {
        return appointmentDAO.updateAppointment(appointment);
    }

    // Get appointment by ID
    public Appointment getAppointmentById(int id) {
        return appointmentDAO.getAppointmentById(id);
    }

    // Get all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    // Cancel appointment by ID
    public boolean cancelAppointment(int id) {
        return appointmentDAO.cancelAppointment(id);
    }
}
