package com.training.model;

public class PrepaidPlan extends Plan {
    public PrepaidPlan() {
        super();
        setPlanType(PlanType.PREPAID);
    }

    public PrepaidPlan(String planId, String name, double price, int validityDays, int dataLimit,
                       int smsLimit, int voiceLimit) {
        super(planId, name, price, validityDays, dataLimit, smsLimit, voiceLimit, PlanType.PREPAID);
    }

    @Override
    public double calculateCharge() {
        return getPrice();
    }
}
