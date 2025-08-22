package com.training.model;

import java.util.Date;

public class CustomerPlan {
    private String customerPlanId;
    private String customerId;
    private Plan plan;
    private Date startDate;
    private Date endDate;
    private String status;

    public CustomerPlan() {}

    public CustomerPlan(String customerPlanId, String customerId, Plan plan, Date startDate,
                        Date endDate, String status) {
        this.customerPlanId = customerPlanId;
        this.customerId = customerId;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getCustomerPlanId() { return customerPlanId; }
    public void setCustomerPlanId(String customerPlanId) { this.customerPlanId = customerPlanId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
