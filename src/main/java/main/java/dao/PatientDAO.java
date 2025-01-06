package main.java.dao;

import main.java.entities.Patient;
import main.java.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // Add a new patient
    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients (name, contact, address) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, patient.getName());
            statement.setString(2, patient.getContact());
            statement.setString(3, patient.getAddress());
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        patient.setId(generatedKeys.getInt(1)); // Set the generated ID
                    }
                }
            }
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Update patient details
    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patients SET name = ?, contact = ?, address = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, patient.getName());
            statement.setString(2, patient.getContact());
            statement.setString(3, patient.getAddress());
            statement.setInt(4, patient.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get patient by ID
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToPatient(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        String sql = "SELECT * FROM patients";
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                patients.add(mapRowToPatient(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // Delete patient by ID
    public boolean deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Utility: Map ResultSet row to Patient object
    private Patient mapRowToPatient(ResultSet resultSet) throws SQLException {
        return new Patient(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("contact"),
                resultSet.getString("address")
        );
    }
}
