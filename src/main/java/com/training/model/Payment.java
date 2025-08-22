package com.training.model;

import java.util.Date;

public class Payment {
    private String paymentId;
    private String billId;
    private double amount;
    private PaymentMethod method;
    private Date paymentDate;
    private String transactionId;

    public Payment() {}

    public Payment(String paymentId, String billId, double amount, PaymentMethod method,
                   Date paymentDate, String transactionId) {
        this.paymentId = paymentId;
        this.billId = billId;
        this.amount = amount;
        this.method = method;
        this.paymentDate = paymentDate;
        this.transactionId = transactionId;
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getBillId() { return billId; }
    public void setBillId(String billId) { this.billId = billId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public void processPayment() {
    }
}
