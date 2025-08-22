package com.training.dao;

import com.training.model.Plan;
import com.training.model.PlanType;
import com.training.model.PrepaidPlan;
import com.training.model.PostpaidPlan;
import com.training.model.Addon;
import com.training.utility.DataBaseUtil;
import com.training.exceptions.PlanNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlanDAOImpl implements PlanDAO {
    @Override
    public void createPlan(Plan plan) throws Exception {
        String sql = "INSERT INTO Plan (name, price, validity_days, data_limit, sms_limit, voice_limit, plan_type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plan.getName());
            stmt.setDouble(2, plan.getPrice());
            stmt.setInt(3, plan.getValidityDays());
            stmt.setInt(4, plan.getDataLimit());
            stmt.setInt(5, plan.getSmsLimit());
            stmt.setInt(6, plan.getVoiceLimit());
            stmt.setString(7, plan.getPlanType().name());
            stmt.executeUpdate();
        }
    }

    @Override
    public Plan findPlanById(String planId) throws Exception {
        String sql = "SELECT * FROM Plan WHERE plan_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, planId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return createPlanFromResultSet(rs);
            }
            throw new PlanNotFoundException("Plan with ID " + planId + " not found");
        }
    }

    @Override
    public List<Plan> findPlansByType(PlanType planType) throws Exception {
        String sql = "SELECT * FROM Plan WHERE plan_type = ?";
        List<Plan> plans = new ArrayList<>();
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, planType.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                plans.add(createPlanFromResultSet(rs));
            }
            return plans;
        }
    }

    @Override
    public void updatePlan(Plan plan) throws Exception {
        String sql = "UPDATE Plan SET name = ?, price = ?, validity_days = ?, data_limit = ?, sms_limit = ?, voice_limit = ?, plan_type = ? WHERE plan_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plan.getName());
            stmt.setDouble(2, plan.getPrice());
            stmt.setInt(3, plan.getValidityDays());
            stmt.setInt(4, plan.getDataLimit());
            stmt.setInt(5, plan.getSmsLimit());
            stmt.setInt(6, plan.getVoiceLimit());
            stmt.setString(7, plan.getPlanType().name());
            stmt.setString(8, plan.getPlanId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deletePlan(String planId) throws Exception {
        String sql = "DELETE FROM Plan WHERE plan_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, planId);
            stmt.executeUpdate();
        }
    }

    private Plan createPlanFromResultSet(ResultSet rs) throws Exception {
        String planType = rs.getString("plan_type");
        Plan plan;
        switch (PlanType.valueOf(planType)) {
            case PREPAID:
                plan = new PrepaidPlan();
                break;
            case POSTPAID:
                plan = new PostpaidPlan();
                break;
            case ADDON:
                plan = new Addon();
                break;
            default:
                throw new IllegalArgumentException("Unknown plan type: " + planType);
        }
        plan.setPlanId(rs.getString("plan_id"));
        plan.setName(rs.getString("name"));
        plan.setPrice(rs.getDouble("price"));
        plan.setValidityDays(rs.getInt("validity_days"));
        plan.setDataLimit(rs.getInt("data_limit"));
        plan.setSmsLimit(rs.getInt("sms_limit"));
        plan.setVoiceLimit(rs.getInt("voice_limit"));
        plan.setPlanType(PlanType.valueOf(planType));
        return plan;
    }
}