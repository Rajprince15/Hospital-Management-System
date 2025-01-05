package main.java.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    public static Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3001/hospital_management?useSSL=false&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "Prince1504";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to the database successfully.");
            return connection;
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database. Please check your settings.");
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            getConnection();
        } catch (SQLException e) {
            System.out.println("Connection test failed.");
        }
    }
}
