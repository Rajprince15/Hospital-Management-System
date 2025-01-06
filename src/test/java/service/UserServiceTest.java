package service;

import main.java.entities.User;
import main.java.service.UserService;
import main.java.util.DatabaseUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class UserServiceTest {

    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
        // Truncate the users table before each test
        try (Connection connection = DatabaseUtil.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE users");
            System.out.println("Truncated users table successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to set up the database for tests.");
        }
    }

    @Test
    public void testRegisterUser() {
        User user = new User(0, "testuser", "password123", "test@test.com",
                "Test User", "PATIENT", 1, "1234567890", "Test Address");

        boolean result = userService.registerUser(user);
        assertTrue("User registration should succeed", result);

        System.out.println("Registered User ID: " + user.getId());
        assertTrue("Generated ID should be greater than 0", user.getId() > 0);

        User savedUser = userService.getUserById(user.getId());
        assertNotNull("Saved user should not be null", savedUser);
        assertEquals("Username should match", "testuser", savedUser.getUsername());
        assertEquals("Email should match", "test@test.com", savedUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        // Register a new user
        User user = new User(0, "updateuser", "password123", "update@test.com",
                "Update User", "PATIENT", 1, "1234567890", "Update Address");
        boolean registerResult = userService.registerUser(user);
        assertTrue("User registration should succeed", registerResult);

        // Retrieve the saved user
        User savedUser = userService.getUserById(user.getId());
        assertNotNull("Saved user should not be null", savedUser);
        System.out.println("Saved User ID: " + savedUser.getId());

        // Update the user details
        savedUser.setEmail("updated@test.com");
        savedUser.setAddress("Updated Address");

        boolean updateResult = userService.updateUser(savedUser);
        assertTrue("User update should succeed", updateResult);

        // Retrieve the updated user and verify changes
        User updatedUser = userService.getUserById(savedUser.getId());
        assertNotNull("Updated user should not be null", updatedUser);
        assertEquals("Email should be updated", "updated@test.com", updatedUser.getEmail());
        assertEquals("Address should be updated", "Updated Address", updatedUser.getAddress());
    }

    @Test
    public void testDeleteUser() {
        // First register a user
        User user = new User(0, "deleteuser", "password123", "delete@test.com",
                "Delete User", "PATIENT", 1, "1234567890", "Delete Address");
        userService.registerUser(user);

        // Delete the user
        User savedUser = userService.getUserById(user.getId());
        assertNotNull("Saved user should not be null", savedUser);

        boolean result = userService.deleteUser(savedUser.getId());
        assertTrue("User deletion should succeed", result);

        User deletedUser = userService.getUserById(savedUser.getId());
        assertNull("Deleted user should be null", deletedUser);
    }

    @Test
    public void testAuthenticateUser() {
        User user = new User(0, "authuser", "password123", "auth@test.com",
                "Auth User", "PATIENT", 1, "1234567890", "Auth Address");
        userService.registerUser(user);

        User authenticatedUser = userService.authenticateUser("authuser", "password123");
        assertNotNull("Authentication should succeed", authenticatedUser);
        assertEquals("Username should match", "authuser", authenticatedUser.getUsername());
    }

    @Test
    public void testAuthenticateUserWithWrongPassword() {
        User user = new User(0, "wrongpass", "password123", "wrong@test.com",
                "Wrong Pass", "PATIENT", 1, "1234567890", "Wrong Address");
        userService.registerUser(user);

        User authenticatedUser = userService.authenticateUser("wrongpass", "wrongpassword");
        assertNull("Authentication should fail with wrong password", authenticatedUser);
    }
}
