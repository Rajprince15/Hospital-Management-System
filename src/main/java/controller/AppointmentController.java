package main.java.controller;

import main.java.service.AppointmentService;
import main.java.entities.Appointment;

import java.util.List;

public class AppointmentController {
    private final AppointmentService appointmentService;

    // Constructor
    public AppointmentController() {
        this.appointmentService = new AppointmentService();
    }

    // Schedule a new appointment
    public boolean scheduleAppointment(int patientId, int doctorId, String appointmentDate, String status) {
        Appointment appointment = new Appointment(0, patientId, doctorId, appointmentDate, status);
        return appointmentService.scheduleAppointment(appointment);
    }

    // Update appointment details
    public boolean updateAppointment(int id, int patientId, int doctorId, String appointmentDate, String status) {
        Appointment appointment = new Appointment(id, patientId, doctorId, appointmentDate, status);
        return appointmentService.updateAppointment(appointment);
    }

    // Get appointment by ID
    public Appointment getAppointmentById(int id) {
        return appointmentService.getAppointmentById(id);
    }

    // Get all appointments
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // Cancel appointment by ID
    public boolean cancelAppointment(int id) {
        return appointmentService.cancelAppointment(id);
    }
}
