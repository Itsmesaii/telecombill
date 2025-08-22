package com.training.model;

public class PostpaidPlan extends Plan {
    public PostpaidPlan() {
        super();
        setPlanType(PlanType.POSTPAID);
    }

    public PostpaidPlan(String planId, String name, double price, int validityDays, int dataLimit,
                        int smsLimit, int voiceLimit) {
        super(planId, name, price, validityDays, dataLimit, smsLimit, voiceLimit, PlanType.POSTPAID);
    }

    @Override
    public double calculateCharge() {
        return getPrice();
    }
}
