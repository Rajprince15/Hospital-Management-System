package main.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    public static Connection getConnection() throws SQLException {
        // Correct MySQL port is 3306 (NOT 3001)
        String jdbcURL = "jdbc:mysql://localhost:3001/hospital_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String username = "root";
        String password = "Prince1504";


        System.out.println("Connecting to DB: " + jdbcURL);

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database successfully.");
            return connection;
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect to the database.");
            e.printStackTrace();
            throw e;
        }
    }

    // For manual testing
    public static void main(String[] args) {
        try {
            getConnection();
        } catch (SQLException e) {
            System.out.println("Connection test failed.");
        }
    }
}
