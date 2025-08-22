package com.training.service;

import com.training.dao.BillDAO;
import com.training.dao.BillDAOImpl;
import com.training.dao.CustomerDAO;
import com.training.dao.CustomerDAOImpl;
import com.training.dao.CustomerPlanDAO;
import com.training.dao.CustomerPlanDAOImpl;
import com.training.dao.AddonPurchaseDAO;
import com.training.dao.AddonPurchaseDAOImpl;
import com.training.model.Bill;
import com.training.model.Customer;
import com.training.model.CustomerPlan;
import com.training.model.Addon;
import com.training.utility.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BillingServiceImpl implements BillingService {
    private final BillDAO billDAO = new BillDAOImpl();
    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final CustomerPlanDAO customerPlanDAO = new CustomerPlanDAOImpl();
    private final AddonPurchaseDAO addonPurchaseDAO = new AddonPurchaseDAOImpl();

    @Override
    public Bill generateBill(Customer customer) throws Exception {
        customer = customerDAO.findCustomerById(customer.getCustomerId());
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        List<CustomerPlan> plans = customerPlanDAO.findCustomerPlansByCustomerId(customer.getCustomerId());
        List<Addon> addons = addonPurchaseDAO.findAddonsByCustomerId(customer.getCustomerId());
        double totalAmount = 0.0;
        for (CustomerPlan plan : plans) {
            if ("ACTIVE".equals(plan.getStatus())) {
                totalAmount += plan.getPlan().calculateCharge();
            }
        }
        for (Addon addon : addons) {
            totalAmount += addon.calculateCharge();
        }
        Bill bill = new Bill();
        bill.setBillId(UUID.randomUUID().toString());
        bill.setCustomerId(customer.getCustomerId());
        bill.setAmount(totalAmount);
        bill.setGeneratedDate(DateUtil.getCurrentDate());
        bill.setDueDate(DateUtil.addDays(DateUtil.getCurrentDate(), 7));
        bill.setBillingCycleStart(DateUtil.getCurrentDate());
        bill.setBillingCycleEnd(DateUtil.addDays(DateUtil.getCurrentDate(), 30));
        bill.setStatus("PENDING");
        billDAO.createBill(bill);
        customer.getBills().add(bill);
        return bill;
    }

    @Override
    public Bill getBillById(String billId) throws Exception {
        return billDAO.findBillById(billId);
    }

    @Override
    public List<Bill> getCustomerBills(String customerId) throws Exception {
        return billDAO.findBillsByCustomerId(customerId);
    }

    @Override
    public void updateBillStatus(String billId, String status) throws Exception {
        Bill bill = billDAO.findBillById(billId);
        if (bill == null) {
            throw new IllegalArgumentException("Bill not found");
        }
        bill.setStatus(status);
        billDAO.updateBill(bill);
    }
}