package com.training.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String username;
    private String passwordHash;
    private List<CustomerPlan> customerPlans = new ArrayList<>();
    private List<Addon> addons = new ArrayList<>();
    private List<Bill> bills = new ArrayList<>();

    // Constructors
    public Customer() {}

    public Customer(String customerId, String name, String phoneNumber, String email, String address,
                    String username, String passwordHash) {
        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public List<CustomerPlan> getCustomerPlans() { return customerPlans; }
    public void setCustomerPlans(List<CustomerPlan> customerPlans) { this.customerPlans = customerPlans; }
    public List<Addon> getAddons() { return addons; }
    public void setAddons(List<Addon> addons) { this.addons = addons; }
    public List<Bill> getBills() { return bills; }
    public void setBills(List<Bill> bills) { this.bills = bills; }

    // Methods
    public boolean login(String username, String password) {
        // Placeholder: Compare username and hashed password (requires utility for hashing)
        return this.username.equals(username) && this.passwordHash.equals(password); // Simplified
    }

    public void subscribePlan(Plan plan) {
        CustomerPlan customerPlan = new CustomerPlan();
        customerPlan.setPlan(plan);
        customerPlan.setCustomerId(this.customerId);
        customerPlans.add(customerPlan);
    }

    public void addAddon(Addon addon) {
        addons.add(addon);
    }

    public Bill viewBill() {
        // Return the latest bill or null if none exists
        return bills.isEmpty() ? null : bills.get(bills.size() - 1);
    }

    public void makePayment(Payment payment) {
        // Associate payment with the latest bill
        if (!bills.isEmpty()) {
            Bill latestBill = bills.get(bills.size() - 1);
            payment.setBillId(latestBill.getBillId());
            latestBill.getPayments().add(payment);
        }
    }
}