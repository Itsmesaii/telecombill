package com.training.model;

public abstract class Plan {
    private String planId;
    private String name;
    private double price;
    private int validityDays;
    private int dataLimit;
    private int smsLimit;
    private int voiceLimit;
    private PlanType planType;

    public Plan() {}

    public Plan(String planId, String name, double price, int validityDays, int dataLimit,
                int smsLimit, int voiceLimit, PlanType planType) {
        this.planId = planId;
        this.name = name;
        this.price = price;
        this.validityDays = validityDays;
        this.dataLimit = dataLimit;
        this.smsLimit = smsLimit;
        this.voiceLimit = voiceLimit;
        this.planType = planType;
    }

    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getValidityDays() { return validityDays; }
    public void setValidityDays(int validityDays) { this.validityDays = validityDays; }
    public int getDataLimit() { return dataLimit; }
    public void setDataLimit(int dataLimit) { this.dataLimit = dataLimit; }
    public int getSmsLimit() { return smsLimit; }
    public void setSmsLimit(int smsLimit) { this.smsLimit = smsLimit; }
    public int getVoiceLimit() { return voiceLimit; }
    public void setVoiceLimit(int voiceLimit) { this.voiceLimit = voiceLimit; }
    public PlanType getPlanType() { return planType; }
    public void setPlanType(PlanType planType) { this.planType = planType; }

    public abstract double calculateCharge();
}