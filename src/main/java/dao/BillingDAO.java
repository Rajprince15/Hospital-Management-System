package main.java.dao;

import main.java.entities.Billing;
import main.java.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    // Add a new bill
    public boolean addBill(Billing billing) {
        String sql = "INSERT INTO billing (patient_id, amount, bill_date) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, billing.getPatientId());
            statement.setDouble(2, billing.getAmount());
            statement.setString(3, billing.getBillDate());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update billing details
    public boolean updateBill(Billing billing) {
        String sql = "UPDATE billing SET patient_id = ?, amount = ?, bill_date = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, billing.getPatientId());
            statement.setDouble(2, billing.getAmount());
            statement.setString(3, billing.getBillDate());
            statement.setInt(4, billing.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get billing information by ID
    public Billing getBillById(int id) {
        String sql = "SELECT * FROM billing WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Billing(
                        resultSet.getInt("id"),
                        resultSet.getInt("patient_id"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("bill_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all billing records
    public List<Billing> getAllBills() {
        String sql = "SELECT * FROM billing";
        List<Billing> bills = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                bills.add(new Billing(
                        resultSet.getInt("id"),
                        resultSet.getInt("patient_id"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("bill_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    // Delete billing record by ID
    public boolean deleteBill(int id) {
        String sql = "DELETE FROM billing WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
