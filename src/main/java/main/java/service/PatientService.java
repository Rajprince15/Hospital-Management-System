package main.java.service;

import main.java.dao.PatientDAO;
import main.java.entities.Patient;

import java.util.List;

public class PatientService {

    private final PatientDAO patientDAO;

    public PatientService() {
        this.patientDAO = new PatientDAO();
    }

    // Add a new patient
    public boolean addPatient(Patient patient) {
        if (patient == null || patient.getName() == null || patient.getContact() == null) {
            throw new IllegalArgumentException("Invalid patient details provided.");
        }
        return patientDAO.addPatient(patient);
    }

    // Update an existing patient's details
    public boolean updatePatient(Patient patient) {
        if (patient == null || patient.getId() <= 0) {
            throw new IllegalArgumentException("Invalid patient details provided.");
        }
        return patientDAO.updatePatient(patient);
    }

    // Get patient by ID
    public Patient getPatientById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid patient ID.");
        }
        return patientDAO.getPatientById(id);
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    // Delete patient by ID
    public boolean deletePatient(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid patient ID.");
        }
        return patientDAO.deletePatient(id);
    }
}
