package com.training.service;

import com.training.dao.CustomerDAO;
import com.training.dao.CustomerDAOImpl;
import com.training.dao.AdminDAO;
import com.training.dao.AdminDAOImpl;
import com.training.model.Customer;
import com.training.model.Admin;
import com.training.exceptions.InvalidLoginException;

public class AuthServiceImpl implements AuthService {
    private final CustomerDAO customerDAO = new CustomerDAOImpl();
    private final AdminDAO adminDAO = new AdminDAOImpl();

    @Override
    public boolean customerLogin(String username, String password) throws InvalidLoginException {
        return customerDAO.login(username, password);
    }

    @Override
    public boolean adminLogin(String username, String password) throws InvalidLoginException {
        return adminDAO.login(username, password);
    }

    @Override
    public void registerCustomer(Customer customer) throws Exception {
        customerDAO.createCustomer(customer);
    }

    @Override
    public void registerAdmin(Admin admin) throws Exception {
        adminDAO.createAdmin(admin);
    }
}