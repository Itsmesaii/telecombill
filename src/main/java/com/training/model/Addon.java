package com.training.model;

public class Addon extends Plan {
    public Addon() {
        super();
        setPlanType(PlanType.ADDON);
    }

    public Addon(String planId, String name, double price, int validityDays, int dataLimit,
                 int smsLimit, int voiceLimit) {
        super(planId, name, price, validityDays, dataLimit, smsLimit, voiceLimit, PlanType.ADDON);
    }

    @Override
    public double calculateCharge() {
        return getPrice();
    }
}