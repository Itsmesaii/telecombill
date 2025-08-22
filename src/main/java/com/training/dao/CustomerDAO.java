package com.training.dao;

import com.training.model.Customer;
import com.training.exceptions.InvalidLoginException;
import java.sql.SQLException;

public interface CustomerDAO {
    void createCustomer(Customer customer) throws SQLException;
    Customer findCustomerById(String customerId) throws SQLException;
    Customer findCustomerByUsername(String username) throws SQLException;
    boolean login(String username, String password) throws InvalidLoginException;
    void updateCustomer(Customer customer) throws SQLException;
}
