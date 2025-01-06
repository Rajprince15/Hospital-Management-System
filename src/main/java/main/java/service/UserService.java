package main.java.service;

import main.java.dao.UserDAO;
import main.java.entities.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    // Constructor
    public UserService() {
        this.userDAO = new UserDAO();
    }

    // Registers a new user
    public boolean registerUser(User user) {
        // Add validation if needed (e.g., unique username, valid email format)
        if (userDAO.getUserByUsername(user.getUsername()) != null) {
            System.err.println("Username already exists: " + user.getUsername());
            return false;
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            System.err.println("Invalid username: " + user.getUsername());
            return false;
        }
        return userDAO.addUser(user);

    }

    // Updates an existing user
    public boolean updateUser(User user) {
        if (userDAO.getUserById(user.getId()) == null) {
            System.err.println("User not found with ID: " + user.getId());
            return false;
        }
        return userDAO.updateUser(user);
    }

    // Deletes a user by ID
    public boolean deleteUser(int userId) {
        if (userDAO.getUserById(userId) == null) {
            System.err.println("User not found with ID: " + userId);
            return false;
        }
        return userDAO.deleteUser(userId);
    }

    // Authenticates a user by username and password
    public User authenticateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Implement hashed password comparison in production
            return user;
        }
        System.err.println("Authentication failed for username: " + username);
        return null;
    }

    // Retrieves a user by their ID
    public User getUserById(int id) {
        User user = userDAO.getUserById(id);
        if (user == null) {
            System.err.println("User not found with ID: " + id);
        }
        return user;
    }

    // Retrieves a user by their username
    public User getUserByUsername(String username) {
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            System.err.println("User not found with username: " + username);
        }
        return user;
    }

    // Retrieves all users
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
