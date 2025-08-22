package com.training.service;

import com.training.model.Plan;
import com.training.model.Customer;
import com.training.model.Bill;

public interface AdminService {
    void createPlan(Plan plan) throws Exception;
    void updatePlan(Plan plan) throws Exception;
    void deletePlan(String planId) throws Exception;
    Customer viewCustomer(String customerId) throws Exception;
    void resolveBillingIssue(String billId, String resolution) throws Exception;
}