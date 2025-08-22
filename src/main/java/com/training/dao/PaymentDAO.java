package com.training.dao;

import com.training.model.Payment;

import java.util.List;

public interface PaymentDAO {
    void createPayment(Payment payment) throws Exception;
    Payment findPaymentById(String paymentId) throws Exception;
    List<Payment> findPaymentsByBillId(String billId) throws Exception;
}