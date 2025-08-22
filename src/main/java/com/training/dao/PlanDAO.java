package com.training.dao;

import com.training.model.Plan;
import com.training.model.PlanType;

import java.util.List;

public interface PlanDAO {
    void createPlan(Plan plan) throws Exception;
    Plan findPlanById(String planId) throws Exception;
    List<Plan> findPlansByType(PlanType planType) throws Exception;
    void updatePlan(Plan plan) throws Exception;
    void deletePlan(String planId) throws Exception;
}