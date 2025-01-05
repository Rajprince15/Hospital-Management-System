package main.java.controller;

import main.java.entities.Patient;
import main.java.service.PatientService;

import java.util.List;

public class PatientController {
    private final PatientService patientService;

    // Constructor
    public PatientController() {
        this.patientService = new PatientService();
    }

    // Add a new patient
    public boolean addPatient(Patient patient) {
        return patientService.addPatient(patient);
    }

    // Update patient details
    public boolean updatePatient(Patient patient) {
        return patientService.updatePatient(patient);
    }

    // Get patient by ID
    public Patient getPatientById(int id) {
        return patientService.getPatientById(id);
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // Delete patient by ID
    public boolean deletePatient(int id) {
        return patientService.deletePatient(id);
    }
}
