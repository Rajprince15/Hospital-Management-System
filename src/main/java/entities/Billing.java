package main.java.entities;

public class Billing {
    private int id; // Bill ID (Primary Key)
    private int patientId; // Patient ID (Foreign Key from Patients table)
    private double amount; // Total bill amount
    private String billDate; // Date when the bill was generated

    // Constructor
    public Billing(int id, int patientId, double amount, String billDate) {
        this.id = id;
        this.patientId = patientId;
        this.amount = amount;
        this.billDate = billDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    @Override
    public String toString() {
        return "Billing [id=" + id + ", patientId=" + patientId + ", amount=" + amount + ", billDate=" + billDate + "]";
    }
}
