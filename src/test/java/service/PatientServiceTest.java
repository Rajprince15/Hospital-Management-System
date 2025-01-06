package service;

import main.java.entities.Patient;
import main.java.service.PatientService;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class PatientServiceTest {
    private PatientService patientService;

    @Before
    public void setUp() {
        patientService = new PatientService();
    }

    @Test
    public void testAddPatient() {
        Patient patient = new Patient(0, "Test Patient", "1234567890", "Test Address");
        boolean result = patientService.addPatient(patient);
        assertTrue("Patient should be added successfully", result);

        // Verify patient exists
        Patient savedPatient = patientService.getPatientById(patient.getId());
        assertNotNull("Saved patient should not be null", savedPatient);
        assertEquals("Name should match", "Test Patient", savedPatient.getName());
    }

    @Test
    public void testGetAllPatients() {
        // Add some test patients
        patientService.addPatient(new Patient(0, "Patient One", "1111111111", "Address One"));
        patientService.addPatient(new Patient(0, "Patient Two", "2222222222", "Address Two"));

        List<Patient> patients = patientService.getAllPatients();
        assertNotNull("Patients list should not be null", patients);
        assertTrue("Should have at least 2 patients", patients.size() >= 2);
    }

    @Test
    public void testDeletePatient() {
        // First add a patient
        Patient patient = new Patient(0, "Delete Patient", "3333333333", "Delete Address");
        patientService.addPatient(patient);

        // Then delete it
        boolean result = patientService.deletePatient(patient.getId());
        assertTrue("Patient shou" +
                "ld be deleted successfully", result);

        // Verify patient no longer exists
        Patient deletedPatient = patientService.getPatientById(patient.getId());
        assertNull("Deleted patient should not exist", deletedPatient);
    }
} 