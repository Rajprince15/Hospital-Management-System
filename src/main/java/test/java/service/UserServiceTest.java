package test.java.service;

import main.java.entities.User;
import main.java.service.UserService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testRegisterUser() {
        User user = new User(0, "testuser", "password123", "test@test.com", 
                           "Test User", "PATIENT", 1, "1234567890", "Test Address");
        
        boolean result = userService.registerUser(user);
        assertTrue("User registration should succeed", result);
        
        // Verify user exists
        User savedUser = userService.getUserByUsername("testuser");
        assertNotNull("Saved user should not be null", savedUser);
        assertEquals("Email should match", "test@test.com", savedUser.getEmail());
    }

    @Test
    public void testAuthenticateUser() {
        // First register a user
        User user = new User(0, "authtest", "password123", "auth@test.com", 
                           "Auth User", "PATIENT", 1, "1234567890", "Test Address");
        userService.registerUser(user);

        // Test authentication
        User authenticatedUser = userService.authenticateUser("authtest", "password123");
        assertNotNull("Authentication should succeed", authenticatedUser);
        assertEquals("Username should match", "authtest", authenticatedUser.getUsername());
    }

    @Test
    public void testAuthenticateUserWithWrongPassword() {
        User user = new User(0, "wrongpass", "password123", "wrong@test.com", 
                           "Wrong Pass", "PATIENT", 1, "1234567890", "Test Address");
        userService.registerUser(user);

        User authenticatedUser = userService.authenticateUser("wrongpass", "wrongpassword");
        assertNull("Authentication should fail with wrong password", authenticatedUser);
    }
} 