package com.training.dao;

import com.training.model.Admin;
import com.training.exceptions.InvalidLoginException;
import java.sql.SQLException;

public interface AdminDAO {
    void createAdmin(Admin admin) throws SQLException;
    Admin findAdminById(String adminId) throws SQLException;
    Admin findAdminByUsername(String username) throws SQLException;
    boolean login(String username, String password) throws InvalidLoginException;
    void updateAdmin(Admin admin) throws SQLException;
}