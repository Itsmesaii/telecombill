package com.training.service;

import com.training.model.Payment;
import com.training.model.Bill;

import java.util.List;

public interface PaymentService {
    void processPayment(Bill bill, double amount, String paymentMethod, String transactionId) throws Exception;
    Payment getPaymentById(String paymentId) throws Exception;
    List<Payment> getPaymentsByBillId(String billId) throws Exception;
}