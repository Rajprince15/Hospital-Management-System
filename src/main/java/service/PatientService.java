package main.java.service;

import main.java.dao.PatientDAO;
import main.java.entities.Patient;

import java.util.List;

public class PatientService {
    private final PatientDAO patientDAO;

    // Constructor
    public PatientService() {
        this.patientDAO = new PatientDAO();
    }

    // Add a new patient
    public boolean addPatient(Patient patient) {
        return patientDAO.addPatient(patient);
    }

    // Update patient details
    public boolean updatePatient(Patient patient) {
        return patientDAO.updatePatient(patient);
    }

    // Get patient by ID
    public Patient getPatientById(int id) {
        return patientDAO.getPatientById(id);
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientDAO.getAllPatients();
    }

    // Delete patient by ID
    public boolean deletePatient(int id) {
        return patientDAO.deletePatient(id);
    }
}
