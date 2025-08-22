package com.training.dao;

import com.training.model.CustomerPlan;

import java.util.List;

public interface CustomerPlanDAO {
    void createCustomerPlan(CustomerPlan customerPlan) throws Exception;
    CustomerPlan findCustomerPlanById(String customerPlanId) throws Exception;
    List<CustomerPlan> findCustomerPlansByCustomerId(String customerId) throws Exception;
}