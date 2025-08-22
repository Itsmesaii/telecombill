package com.training.dao;

import com.training.model.Addon;

import java.util.List;

public interface AddonPurchaseDAO {
    void createAddonPurchase(String customerId, Addon addon, java.util.Date purchaseDate, java.util.Date expiryDate) throws Exception;
    List<Addon> findAddonsByCustomerId(String customerId) throws Exception;
}