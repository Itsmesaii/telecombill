package com.training.service;

import com.training.dao.PlanDAO;
import com.training.dao.PlanDAOImpl;
import com.training.dao.CustomerDAO;
import com.training.dao.CustomerDAOImpl;
import com.training.dao.BillDAO;
import com.training.dao.BillDAOImpl;
import com.training.model.Plan;
import com.training.model.Customer;
import com.training.model.Bill;

public class AdminServiceImpl implements AdminService {
    private final PlanDAO planDAO = new PlanDAOImpl();
    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final BillDAO billDAO = new BillDAOImpl();

    @Override
    public void createPlan(Plan plan) throws Exception {
        planDAO.createPlan(plan);
    }

    @Override
    public void updatePlan(Plan plan) throws Exception {
        planDAO.updatePlan(plan);
    }

    @Override
    public void deletePlan(String planId) throws Exception {
        planDAO.deletePlan(planId);
    }

    @Override
    public Customer viewCustomer(String customerId) throws Exception {
        return customerDAO.findCustomerById(customerId);
    }

    @Override
    public void resolveBillingIssue(String billId, String resolution) throws Exception {
        Bill bill = billDAO.findBillById(billId);
        if (bill == null) {
            throw new IllegalArgumentException("Bill not found");
        }
        bill.setStatus(resolution);
        billDAO.updateBill(bill);
    }
}