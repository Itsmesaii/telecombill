package com.training.dao;

import com.training.model.Admin;
import com.training.model.AdminRole;
import com.training.utility.DataBaseUtil;
import com.training.utility.PasswordUtil;
import com.training.exceptions.InvalidLoginException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AdminDAOImpl implements AdminDAO {
    @Override
    public void createAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO Admin (username, password_hash, name, email, role, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, PasswordUtil.hashPassword(admin.getPasswordHash()));
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getRole().name());
            stmt.setTimestamp(6, new Timestamp(admin.getCreatedAt().getTime()));
            stmt.executeUpdate();
        }
    }

    @Override
    public Admin findAdminById(String adminId) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE admin_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, adminId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getString("admin_id"));
                admin.setUsername(rs.getString("username"));
                admin.setPasswordHash(rs.getString("password_hash"));
                admin.setName(rs.getString("name"));
                admin.setEmail(rs.getString("email"));
                admin.setRole(AdminRole.valueOf(rs.getString("role")));
                admin.setCreatedAt(rs.getTimestamp("created_at"));
                return admin;
            }
            return null;
        }
    }

    @Override
    public Admin findAdminByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Admin WHERE username = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getString("admin_id"));
                admin.setUsername(rs.getString("username"));
                admin.setPasswordHash(rs.getString("password_hash"));
                admin.setName(rs.getString("name"));
                admin.setEmail(rs.getString("email"));
                admin.setRole(AdminRole.valueOf(rs.getString("role")));
                admin.setCreatedAt(rs.getTimestamp("created_at"));
                return admin;
            }
            return null;
        }
    }

    @Override
    public boolean login(String username, String password) throws InvalidLoginException {
        try {
            Admin admin = findAdminByUsername(username);
            if (admin == null || !PasswordUtil.verifyPassword(password, admin.getPasswordHash())) {
                throw new InvalidLoginException("Invalid username or password");
            }
            return true;
        } catch (SQLException e) {
            throw new InvalidLoginException("Database error during login: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new InvalidLoginException("Invalid password data: " + e.getMessage());
        }
    }

    @Override
    public void updateAdmin(Admin admin) throws SQLException {
        String sql = "UPDATE Admin SET username = ?, password_hash = ?, name = ?, email = ?, role = ? WHERE admin_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, PasswordUtil.hashPassword(admin.getPasswordHash()));
            stmt.setString(3, admin.getName());
            stmt.setString(4, admin.getEmail());
            stmt.setString(5, admin.getRole().name());
            stmt.setString(6, admin.getAdminId());
            stmt.executeUpdate();
        }
    }
}