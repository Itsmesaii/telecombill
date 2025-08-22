package com.training.model;

import java.util.Date;

public class Admin {
    private String adminId;
    private String username;
    private String passwordHash;
    private String name;
    private String email;
    private AdminRole role;
    private Date createdAt;

    public Admin() {}

    public Admin(String adminId, String username, String passwordHash, String name, String email,
                 AdminRole role, Date createdAt) {
        this.adminId = adminId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public AdminRole getRole() { return role; }
    public void setRole(AdminRole role) { this.role = role; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.passwordHash.equals(password);
    }

    public void managePlan(Plan plan) {
    }

    public Customer viewCustomer(String customerId) {
        return null;
    }

    public void resolveBillingIssue(String billId) {
    }
}
