package test.java.service;

import main.java.entities.Doctor;
import main.java.service.DoctorService;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class DoctorServiceTest {
    private DoctorService doctorService;

    @Before
    public void setUp() {
        doctorService = new DoctorService();
    }

    @Test
    public void testAddDoctor() {
        Doctor doctor = new Doctor(0, "Dr. Test", "Cardiology", "1234567890");
        boolean result = doctorService.addDoctor(doctor);
        assertTrue("Doctor should be added successfully", result);

        // Verify doctor exists
        Doctor savedDoctor = doctorService.getDoctorById(doctor.getId());
        assertNotNull("Saved doctor should not be null", savedDoctor);
        assertEquals("Name should match", "Dr. Test", savedDoctor.getName());
    }

    @Test
    public void testGetAllDoctors() {
        // Add some test doctors
        doctorService.addDoctor(new Doctor(0, "Dr. One", "Cardiology", "1111111111"));
        doctorService.addDoctor(new Doctor(0, "Dr. Two", "Neurology", "2222222222"));

        List<Doctor> doctors = doctorService.getAllDoctors();
        assertNotNull("Doctors list should not be null", doctors);
        assertTrue("Should have at least 2 doctors", doctors.size() >= 2);
    }

    @Test
    public void testDeleteDoctor() {
        // First add a doctor
        Doctor doctor = new Doctor(0, "Dr. Delete", "Pediatrics", "3333333333");
        doctorService.addDoctor(doctor);

        // Then delete it
        boolean result = doctorService.deleteDoctor(doctor.getId());
        assertTrue("Doctor should be deleted successfully", result);

        // Verify doctor no longer exists
        Doctor deletedDoctor = doctorService.getDoctorById(doctor.getId());
        assertNull("Deleted doctor should not exist", deletedDoctor);
    }
} 