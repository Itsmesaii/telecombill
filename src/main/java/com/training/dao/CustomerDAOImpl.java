package com.training.dao;

import com.training.model.Customer;
import com.training.utility.DataBaseUtil;
import com.training.utility.PasswordUtil;
import com.training.exceptions.InvalidLoginException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public void createCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (name, phone_number, email, address, username, password_hash) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhoneNumber());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getUsername());
            stmt.setString(6, PasswordUtil.hashPassword(customer.getPasswordHash()));
            stmt.executeUpdate();
        }
    }

    @Override
    public Customer findCustomerById(String customerId) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getString("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setUsername(rs.getString("username"));
                customer.setPasswordHash(rs.getString("password_hash"));
                return customer;
            }
            return null;
        }
    }

    @Override
    public Customer findCustomerByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE username = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getString("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setUsername(rs.getString("username"));
                customer.setPasswordHash(rs.getString("password_hash"));
                return customer;
            }
            return null;
        }
    }

    @Override
    public boolean login(String username, String password) throws InvalidLoginException {
        try{
            Customer customer = findCustomerByUsername(username);

        if (customer == null || !PasswordUtil.verifyPassword(password, customer.getPasswordHash())) {
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
    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET name = ?, phone_number = ?, email = ?, address = ?, username = ?, password_hash = ? WHERE customer_id = ?";
        try (Connection conn = DataBaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhoneNumber());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getUsername());
            stmt.setString(6, PasswordUtil.hashPassword(customer.getPasswordHash()));
            stmt.setString(7, customer.getCustomerId());
            stmt.executeUpdate();
        }
    }
}