package main.java;

import main.java.entities.Appointment;
import main.java.service.AppointmentService;

public class Main {
    public static void main(String[] args) {
        // Create an AppointmentService instance for testing
        AppointmentService appointmentService = new AppointmentService();

        // Test: Create and schedule a new appointment
        Appointment appointment1 = new Appointment(1, 101, 201, "2024-11-20 10:00", "Scheduled");
        boolean isScheduled1 = appointmentService.scheduleAppointment(appointment1);
        System.out.println("Appointment 1 scheduled: " + isScheduled1);
        System.out.println("Appointment 1: " + appointment1);

        // Test: Create and schedule another appointment
        Appointment appointment2 = new Appointment(2, 102, 202, "2024-11-21 11:00", "Scheduled");
        boolean isScheduled2 = appointmentService.scheduleAppointment(appointment2);
        System.out.println("Appointment 2 scheduled: " + isScheduled2);
        System.out.println("Appointment 2: " + appointment2);

        // Test: Get appointment by ID
        Appointment fetchedAppointment1 = appointmentService.getAppointmentById(1);
        System.out.println("Fetched Appointment 1: " + fetchedAppointment1);

        // Test: Update appointment status
        appointment1.setStatus("Completed");
        boolean isUpdated = appointmentService.updateAppointment(appointment1);
        System.out.println("Appointment 1 updated: " + isUpdated);
        System.out.println("Updated Appointment 1: " + appointment1);

        // Test: Get all appointments
        System.out.println("All appointments:");
        for (Appointment appointment : appointmentService.getAllAppointments()) {
            System.out.println(appointment);
        }

        // Test: Cancel an appointment by ID
        boolean isCancelled = appointmentService.cancelAppointment(1);
        System.out.println("Appointment 1 cancelled: " + isCancelled);
    }
}
