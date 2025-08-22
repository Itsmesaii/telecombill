package com.training.dao;

import com.training.model.Bill;
import com.training.utility.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDAOImpl implements BillDAO {
    @Override
    public void createBill(Bill bill) throws Exception {
        String sql = "INSERT INTO Bill (customer_id, amount, billing_cycle_start, billing_cycle_end, generated_date, due_date, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bill.getCustomerId());
            stmt.setDouble(2, bill.getAmount());
            stmt.setDate(3, bill.getBillingCycleStart() != null ? new java.sql.Date(bill.getBillingCycleStart().getTime()) : null);
            stmt.setDate(4, bill.getBillingCycleEnd() != null ? new java.sql.Date(bill.getBillingCycleEnd().getTime()) : null);
            stmt.setDate(5, new java.sql.Date(bill.getGeneratedDate().getTime()));
            stmt.setDate(6, bill.getDueDate() != null ? new java.sql.Date(bill.getDueDate().getTime()) : null);
            stmt.setString(7, bill.getStatus());
            stmt.executeUpdate();
        }
    }

    @Override
    public Bill findBillById(String billId) throws Exception {
        String sql = "SELECT * FROM Bill WHERE bill_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, billId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getString("bill_id"));
                bill.setCustomerId(rs.getString("customer_id"));
                bill.setAmount(rs.getDouble("amount"));
                bill.setBillingCycleStart(rs.getDate("billing_cycle_start"));
                bill.setBillingCycleEnd(rs.getDate("billing_cycle_end"));
                bill.setGeneratedDate(rs.getDate("generated_date"));
                bill.setDueDate(rs.getDate("due_date"));
                bill.setStatus(rs.getString("status"));
                return bill;
            }
            return null;
        }
    }

    @Override
    public List<Bill> findBillsByCustomerId(String customerId) throws Exception {
        String sql = "SELECT * FROM Bill WHERE customer_id = ?";
        List<Bill> bills = new ArrayList<>();
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getString("bill_id"));
                bill.setCustomerId(rs.getString("customer_id"));
                bill.setAmount(rs.getDouble("amount"));
                bill.setBillingCycleStart(rs.getDate("billing_cycle_start"));
                bill.setBillingCycleEnd(rs.getDate("billing_cycle_end"));
                bill.setGeneratedDate(rs.getDate("generated_date"));
                bill.setDueDate(rs.getDate("due_date"));
                bill.setStatus(rs.getString("status"));
                bills.add(bill);
            }
            return bills;
        }
    }

    @Override
    public void updateBill(Bill bill) throws Exception {
        String sql = "UPDATE Bill SET customer_id = ?, amount = ?, billing_cycle_start = ?, billing_cycle_end = ?, generated_date = ?, due_date = ?, status = ? WHERE bill_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bill.getCustomerId());
            stmt.setDouble(2, bill.getAmount());
            stmt.setDate(3, bill.getBillingCycleStart() != null ? new java.sql.Date(bill.getBillingCycleStart().getTime()) : null);
            stmt.setDate(4, bill.getBillingCycleEnd() != null ? new java.sql.Date(bill.getBillingCycleEnd().getTime()) : null);
            stmt.setDate(5, new java.sql.Date(bill.getGeneratedDate().getTime()));
            stmt.setDate(6, bill.getDueDate() != null ? new java.sql.Date(bill.getDueDate().getTime()) : null);
            stmt.setString(7, bill.getStatus());
            stmt.setString(8, bill.getBillId());
            stmt.executeUpdate();
        }
    }
}