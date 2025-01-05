package main.java.controller;

import main.java.entities.Doctor;
import main.java.service.DoctorService;

import java.util.List;

public class DoctorController {
    private final DoctorService doctorService;

    // Constructor
    public DoctorController() {
        this.doctorService = new DoctorService();
    }

    // Add a new doctor
    public boolean addDoctor(Doctor doctor) {
        return doctorService.addDoctor(doctor);
    }

    // Update doctor details
    public boolean updateDoctor(Doctor doctor) {
        return doctorService.updateDoctor(doctor);
    }

    // Get doctor by ID
    public Doctor getDoctorById(int id) {
        return doctorService.getDoctorById(id);
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // Delete doctor by ID
    public boolean deleteDoctor(int id) {
        return doctorService.deleteDoctor(id);
    }
}
