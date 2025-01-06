package main.java.entities;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String role;
    private int associatedId;
    private String phoneNumber;
    private String address;

    // Constructor
    public User(int id, String username, String password, String email, String fullName, String role, int associatedId, String phoneNumber, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.associatedId = associatedId;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getAssociatedId() { return associatedId; }
    public void setAssociatedId(int associatedId) { this.associatedId = associatedId; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
