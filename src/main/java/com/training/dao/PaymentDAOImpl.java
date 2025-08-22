package com.training.dao;

import com.training.model.Payment;
import com.training.model.PaymentMethod;
import com.training.utility.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public void createPayment(Payment payment) throws Exception {
        String sql = "INSERT INTO Payment (bill_id, amount, payment_method, payment_date, transaction_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, payment.getBillId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getMethod().name());
            stmt.setTimestamp(4, new java.sql.Timestamp(payment.getPaymentDate().getTime()));
            stmt.setString(5, payment.getTransactionId());
            stmt.executeUpdate();
        }
    }

    @Override
    public Payment findPaymentById(String paymentId) throws Exception {
        String sql = "SELECT * FROM Payment WHERE payment_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getString("payment_id"));
                payment.setBillId(rs.getString("bill_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setMethod(PaymentMethod.valueOf(rs.getString("payment_method")));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                payment.setTransactionId(rs.getString("transaction_id"));
                return payment;
            }
            return null;
        }
    }

    @Override
    public List<Payment> findPaymentsByBillId(String billId) throws Exception {
        String sql = "SELECT * FROM Payment WHERE bill_id = ?";
        List<Payment> payments = new ArrayList<>();
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, billId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getString("payment_id"));
                payment.setBillId(rs.getString("bill_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setMethod(PaymentMethod.valueOf(rs.getString("payment_method")));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                payment.setTransactionId(rs.getString("transaction_id"));
                payments.add(payment);
            }
            return payments;
        }
    }
}