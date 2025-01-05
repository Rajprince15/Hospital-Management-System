package main.java.entities;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String role; // ADMIN, DOCTOR, PATIENT
    private int associatedId; // doctorId or patientId depending on role
    private String phoneNumber;
    private String address;

    public User(int id, String username, String password, String email, 
                String fullName, String role, int associatedId, 
                String phoneNumber, String address) {
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

    // Add getters and setters
}