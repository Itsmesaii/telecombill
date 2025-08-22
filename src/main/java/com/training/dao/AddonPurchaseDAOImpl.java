package com.training.dao;

import com.training.model.Addon;
import com.training.utility.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddonPurchaseDAOImpl implements AddonPurchaseDAO {
    private final PlanDAO planDAO = new PlanDAOImpl();

    @Override
    public void createAddonPurchase(String customerId, Addon addon, java.util.Date purchaseDate, java.util.Date expiryDate) throws Exception {
        String sql = "INSERT INTO AddonPurchase (customer_id, plan_id, purchase_date, expiry_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            stmt.setString(2, addon.getPlanId());
            stmt.setDate(3, new java.sql.Date(purchaseDate.getTime()));
            stmt.setDate(4, expiryDate != null ? new java.sql.Date(expiryDate.getTime()) : null);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Addon> findAddonsByCustomerId(String customerId) throws Exception {
        String sql = "SELECT p.* FROM AddonPurchase ap JOIN Plan p ON ap.plan_id = p.plan_id WHERE ap.customer_id = ? AND p.plan_type = 'ADDON'";
        List<Addon> addons = new ArrayList<>();
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Addon addon = (Addon) planDAO.findPlanById(rs.getString("plan_id"));
                addons.add(addon);
            }
            return addons;
        }
    }
}