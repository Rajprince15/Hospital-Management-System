package main.java.service;

import main.java.dao.DoctorDAO;
import main.java.entities.Doctor;

import java.util.List;

public class DoctorService {

    private final DoctorDAO doctorDAO;

    public DoctorService() {
        this.doctorDAO = new DoctorDAO();
    }

    // Add a new doctor
    public boolean addDoctor(Doctor doctor) {
        if (doctor == null || doctor.getName() == null || doctor.getSpecialty() == null) {
            throw new IllegalArgumentException("Invalid doctor details provided.");
        }
        return doctorDAO.addDoctor(doctor);
    }

    // Update an existing doctor's details
    public boolean updateDoctor(Doctor doctor) {
        if (doctor == null || doctor.getId() <= 0) {
            throw new IllegalArgumentException("Invalid doctor details provided.");
        }
        return doctorDAO.updateDoctor(doctor);
    }

    // Get doctor by ID
    public Doctor getDoctorById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID.");
        }
        return doctorDAO.getDoctorById(id);
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorDAO.getAllDoctors();
    }

    // Delete doctor by ID
    public boolean deleteDoctor(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID.");
        }
        return doctorDAO.deleteDoctor(id);
    }
}
