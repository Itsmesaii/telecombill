package com.training.service;

import com.training.dao.CustomerDAO;
import com.training.dao.CustomerDAOImpl;
import com.training.dao.PlanDAO;
import com.training.dao.PlanDAOImpl;
import com.training.dao.CustomerPlanDAO;
import com.training.dao.CustomerPlanDAOImpl;
import com.training.dao.AddonPurchaseDAO;
import com.training.dao.AddonPurchaseDAOImpl;
import com.training.model.Customer;
import com.training.model.Plan;
import com.training.model.Addon;
import com.training.model.CustomerPlan;
import com.training.model.PlanType;
import com.training.utility.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlanServiceImpl implements PlanService {
    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final PlanDAO planDAO = new PlanDAOImpl();
    private final CustomerPlanDAO customerPlanDAO = new CustomerPlanDAOImpl();
    private final AddonPurchaseDAO addonPurchaseDAO = new AddonPurchaseDAOImpl();

    @Override
    public void subscribePlan(Customer customer, Plan plan) throws Exception {
        customer = customerDAO.findCustomerById(customer.getCustomerId());
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        plan = planDAO.findPlanById(plan.getPlanId());
        if (plan == null) {
            throw new IllegalArgumentException("Plan not found");
        }
        CustomerPlan customerPlan = new CustomerPlan();
        customerPlan.setCustomerPlanId(UUID.randomUUID().toString());
        customerPlan.setCustomerId(customer.getCustomerId());
        customerPlan.setPlan(plan);
        customerPlan.setStartDate(DateUtil.getCurrentDate());
        customerPlan.setEndDate(DateUtil.addDays(DateUtil.getCurrentDate(), plan.getValidityDays()));
        customerPlan.setStatus("ACTIVE");
        customerPlanDAO.createCustomerPlan(customerPlan);
        customer.getCustomerPlans().add(customerPlan);
    }

    @Override
    public void addAddon(Customer customer, Addon addon) throws Exception {
        customer = customerDAO.findCustomerById(customer.getCustomerId());
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        addon = (Addon) planDAO.findPlanById(addon.getPlanId());
        if (addon == null || addon.getPlanType() != PlanType.ADDON) {
            throw new IllegalArgumentException("Invalid addon");
        }
        Date purchaseDate = DateUtil.getCurrentDate();
        Date expiryDate = DateUtil.addDays(purchaseDate, addon.getValidityDays());
        addonPurchaseDAO.createAddonPurchase(customer.getCustomerId(), addon, purchaseDate, expiryDate);
        customer.getAddons().add(addon);
    }

    @Override
    public List<Plan> getAvailablePlans(PlanType planType) throws Exception {
        return planDAO.findPlansByType(planType);
    }

    @Override
    public List<CustomerPlan> getCustomerPlans(String customerId) throws Exception {
        return customerPlanDAO.findCustomerPlansByCustomerId(customerId);
    }

    @Override
    public List<Addon> getCustomerAddons(String customerId) throws Exception {
        return addonPurchaseDAO.findAddonsByCustomerId(customerId);
    }
}