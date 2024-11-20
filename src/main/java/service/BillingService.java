package main.java.service;

import main.java.dao.BillingDAO;
import main.java.entities.Billing;

import java.util.List;

public class BillingService {
    private final BillingDAO billingDAO;

    // Constructor
    public BillingService() {
        this.billingDAO = new BillingDAO();
    }

    // Add a new bill
    public boolean addBill(Billing billing) {
        return billingDAO.addBill(billing);
    }

    // Update billing details
    public boolean updateBill(Billing billing) {
        return billingDAO.updateBill(billing);
    }

    // Get billing information by ID
    public Billing getBillById(int id) {
        return billingDAO.getBillById(id);
    }

    // Get all billing records
    public List<Billing> getAllBills() {
        return billingDAO.getAllBills();
    }

    // Delete billing record by ID
    public boolean deleteBill(int id) {
        return billingDAO.deleteBill(id);
    }
}
