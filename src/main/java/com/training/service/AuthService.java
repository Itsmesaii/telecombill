package com.training.service;

import com.training.model.Customer;
import com.training.model.Admin;
import com.training.exceptions.InvalidLoginException;

public interface AuthService {
    boolean customerLogin(String username, String password) throws InvalidLoginException;
    boolean adminLogin(String username, String password) throws InvalidLoginException;
    void registerCustomer(Customer customer) throws Exception;
    void registerAdmin(Admin admin) throws Exception;
}