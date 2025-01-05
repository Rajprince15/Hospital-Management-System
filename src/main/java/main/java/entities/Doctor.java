package main.java.entities;

public class Doctor {
    private int id;
    private String name;
    private String specialty;
    private String contact;

    // Constructor
    public Doctor(int id, String name, String specialty, String contact) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.contact = contact;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
