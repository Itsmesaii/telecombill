package com.training.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill {
    private String billId;
    private String customerId;
    private double amount;
    private Date billingCycleStart;
    private Date billingCycleEnd;
    private Date generatedDate;
    private Date dueDate;
    private String status;
    private List<Payment> payments = new ArrayList<>();

    public Bill() {}

    public Bill(String billId, String customerId, double amount, Date billingCycleStart,
                Date billingCycleEnd, Date generatedDate, Date dueDate, String status) {
        this.billId = billId;
        this.customerId = customerId;
        this.amount = amount;
        this.billingCycleStart = billingCycleStart;
        this.billingCycleEnd = billingCycleEnd;
        this.generatedDate = generatedDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getBillId() { return billId; }
    public void setBillId(String billId) { this.billId = billId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Date getBillingCycleStart() { return billingCycleStart; }
    public void setBillingCycleStart(Date billingCycleStart) { this.billingCycleStart = billingCycleStart; }
    public Date getBillingCycleEnd() { return billingCycleEnd; }
    public void setBillingCycleEnd(Date billingCycleEnd) { this.billingCycleEnd = billingCycleEnd; }
    public Date getGeneratedDate() { return generatedDate; }
    public void setGeneratedDate(Date generatedDate) { this.generatedDate = generatedDate; }
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Payment> getPayments() { return payments; }
    public void setPayments(List<Payment> payments) { this.payments = payments; }

    public void generateBill() {
        this.status = "PENDING";
    }
}
