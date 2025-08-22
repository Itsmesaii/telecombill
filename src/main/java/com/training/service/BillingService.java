package com.training.service;

import com.training.model.Bill;
import com.training.model.Customer;

import java.util.List;

public interface BillingService {
    Bill generateBill(Customer customer) throws Exception;
    Bill getBillById(String billId) throws Exception;
    List<Bill> getCustomerBills(String customerId) throws Exception;
    void updateBillStatus(String billId, String status) throws Exception;
}