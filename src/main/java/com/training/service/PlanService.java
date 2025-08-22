package com.training.service;

import com.training.model.Customer;
import com.training.model.Plan;
import com.training.model.Addon;
import com.training.model.CustomerPlan;
import com.training.model.PlanType;
import com.training.exceptions.PlanNotFoundException;

import java.util.List;

public interface PlanService {
    void subscribePlan(Customer customer, Plan plan) throws Exception;
    void addAddon(Customer customer, Addon addon) throws Exception;
    List<Plan> getAvailablePlans(PlanType planType) throws Exception;
    List<CustomerPlan> getCustomerPlans(String customerId) throws Exception;
    List<Addon> getCustomerAddons(String customerId) throws Exception;
}