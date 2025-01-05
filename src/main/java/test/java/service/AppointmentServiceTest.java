package test.java.service;

import main.java.entities.Appointment;
import main.java.service.AppointmentService;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class AppointmentServiceTest {
    private AppointmentService appointmentService;

    @Before
    public void setUp() {
        appointmentService = new AppointmentService();
    }

    @Test
    public void testScheduleAppointment() {
        Appointment appointment = new Appointment(0, 1, 1, "2024-03-20 10:00:00", "SCHEDULED");
        boolean result = appointmentService.scheduleAppointment(appointment);
        assertTrue("Appointment should be scheduled successfully", result);

        // Verify appointment exists
        Appointment savedAppointment = appointmentService.getAppointmentById(appointment.getId());
        assertNotNull("Saved appointment should not be null", savedAppointment);
        assertEquals("Status should match", "SCHEDULED", savedAppointment.getStatus());
    }

    @Test
    public void testGetAppointmentsByDoctor() {
        // Schedule test appointments
        appointmentService.scheduleAppointment(
            new Appointment(0, 1, 1, "2024-03-20 10:00:00", "SCHEDULED"));
        appointmentService.scheduleAppointment(
            new Appointment(0, 2, 1, "2024-03-20 11:00:00", "SCHEDULED"));

        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(1);
        assertNotNull("Appointments list should not be null", appointments);
        assertTrue("Should have at least 2 appointments", appointments.size() >= 2);
    }

    @Test
    public void testCancelAppointment() {
        // First schedule an appointment
        Appointment appointment = new Appointment(0, 1, 1, "2024-03-20 10:00:00", "SCHEDULED");
        appointmentService.scheduleAppointment(appointment);

        // Then cancel it
        boolean result = appointmentService.cancelAppointment(appointment.getId());
        assertTrue("Appointment should be cancelled successfully", result);

        // Verify appointment status is updated
        Appointment cancelledAppointment = appointmentService.getAppointmentById(appointment.getId());
        assertNotNull("Cancelled appointment should exist", cancelledAppointment);
        assertEquals("Status should be CANCELLED", "CANCELLED", cancelledAppointment.getStatus());
    }
} 