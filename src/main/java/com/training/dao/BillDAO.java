package com.training.dao;

import com.training.model.Bill;

import java.util.List;

public interface BillDAO {
    void createBill(Bill bill) throws Exception;
    Bill findBillById(String billId) throws Exception;
    List<Bill> findBillsByCustomerId(String customerId) throws Exception;
    void updateBill(Bill bill) throws Exception;
}