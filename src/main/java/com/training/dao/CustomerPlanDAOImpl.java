package com.training.dao;

import com.training.model.CustomerPlan;
import com.training.model.Plan;
import com.training.utility.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerPlanDAOImpl implements CustomerPlanDAO {
    private final PlanDAO planDAO = new PlanDAOImpl();

    @Override
    public void createCustomerPlan(CustomerPlan customerPlan) throws Exception {
        String sql = "INSERT INTO CustomerPlan (customer_id, plan_id, start_date, end_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerPlan.getCustomerId());
            stmt.setString(2, customerPlan.getPlan().getPlanId());
            stmt.setDate(3, new java.sql.Date(customerPlan.getStartDate().getTime()));
            stmt.setDate(4, customerPlan.getEndDate() != null ? new java.sql.Date(customerPlan.getEndDate().getTime()) : null);
            stmt.setString(5, customerPlan.getStatus());
            stmt.executeUpdate();
        }
    }

    @Override
    public CustomerPlan findCustomerPlanById(String customerPlanId) throws Exception {
        String sql = "SELECT * FROM CustomerPlan WHERE customer_plan_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerPlanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CustomerPlan customerPlan = new CustomerPlan();
                customerPlan.setCustomerPlanId(rs.getString("customer_plan_id"));
                customerPlan.setCustomerId(rs.getString("customer_id"));
                customerPlan.setPlan(planDAO.findPlanById(rs.getString("plan_id")));
                customerPlan.setStartDate(rs.getDate("start_date"));
                customerPlan.setEndDate(rs.getDate("end_date"));
                customerPlan.setStatus(rs.getString("status"));
                return customerPlan;
            }
            return null;
        }
    }

    @Override
    public List<CustomerPlan> findCustomerPlansByCustomerId(String customerId) throws Exception {
        String sql = "SELECT * FROM CustomerPlan WHERE customer_id = ?";
        List<CustomerPlan> customerPlans = new ArrayList<>();
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CustomerPlan customerPlan = new CustomerPlan();
                customerPlan.setCustomerPlanId(rs.getString("customer_plan_id"));
                customerPlan.setCustomerId(rs.getString("customer_id"));
                customerPlan.setPlan(planDAO.findPlanById(rs.getString("plan_id")));
                customerPlan.setStartDate(rs.getDate("start_date"));
                customerPlan.setEndDate(rs.getDate("end_date"));
                customerPlan.setStatus(rs.getString("status"));
                customerPlans.add(customerPlan);
            }
            return customerPlans;
        }
    }
}