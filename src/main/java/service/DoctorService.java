package main.java.service;

import main.java.dao.DoctorDAO;
import main.java.entities.Doctor;

import java.util.List;

public class DoctorService {
    private final DoctorDAO doctorDAO;

    // Constructor
    public DoctorService() {
        this.doctorDAO = new DoctorDAO();
    }

    // Add a new doctor
    public boolean addDoctor(Doctor doctor) {
        return doctorDAO.addDoctor(doctor);
    }

    // Update doctor details
    public boolean updateDoctor(Doctor doctor) {
        return doctorDAO.updateDoctor(doctor);
    }

    // Get doctor by ID
    public Doctor getDoctorById(int id) {
        return doctorDAO.getDoctorById(id);
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorDAO.getAllDoctors();
    }

    // Delete doctor by ID
    public boolean deleteDoctor(int id) {
        return doctorDAO.deleteDoctor(id);
    }
}
