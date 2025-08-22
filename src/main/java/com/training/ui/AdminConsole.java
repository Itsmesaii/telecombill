package com.training.ui;

import com.training.model.Admin;
import com.training.model.Customer;
import com.training.model.Plan;
import com.training.model.PlanType;
import com.training.service.AdminService;
import com.training.service.AdminServiceImpl;
import com.training.service.PlanService;
import com.training.service.PlanServiceImpl;
import com.training.exceptions.PlanNotFoundException;

import java.util.List;
import java.util.Scanner;

public class AdminConsole {
    private final AdminService adminService = new AdminServiceImpl();
    private final PlanService planService = new PlanServiceImpl();
    private final Scanner scanner = new Scanner(System.in);
    private Admin loggedInAdmin;

    public void start(Admin admin) {
        loggedInAdmin = admin;
        while (true) {
            displayMenu();
            int choice = getChoice();
            try {
                switch (choice) {
                    case 1:
                        createPlan();
                        break;
                    case 2:
                        updatePlan();
                        break;
                    case 3:
                        deletePlan();
                        break;
                    case 4:
                        viewCustomer();
                        break;
                    case 5:
                        resolveBillingIssue();
                        break;
                    case 6:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== Admin Console ===");
        System.out.println("1. Create Plan");
        System.out.println("2. Update Plan");
        System.out.println("3. Delete Plan");
        System.out.println("4. View Customer");
        System.out.println("5. Resolve Billing Issue");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
    }

    private int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void createPlan() throws Exception {
        System.out.print("Enter plan name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter validity days: ");
        int validityDays = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter data limit (MB): ");
        int dataLimit = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter SMS limit: ");
        int smsLimit = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter voice limit (minutes): ");
        int voiceLimit = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter plan type (PREPAID/POSTPAID/ADDON): ");
        String planTypeStr = scanner.nextLine().toUpperCase();
        PlanType planType;
        try {
            planType = PlanType.valueOf(planTypeStr);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid plan type.");
            return;
        }

        Plan plan;
        switch (planType) {
            case PREPAID:
                plan = new com.training.model.PrepaidPlan();
                break;
            case POSTPAID:
                plan = new com.training.model.PostpaidPlan();
                break;
            case ADDON:
                plan = new com.training.model.Addon();
                break;
            default:
                System.out.println("Invalid plan type.");
                return;
        }

        plan.setPlanId(java.util.UUID.randomUUID().toString());
        plan.setName(name);
        plan.setPrice(price);
        plan.setValidityDays(validityDays);
        plan.setDataLimit(dataLimit);
        plan.setSmsLimit(smsLimit);
        plan.setVoiceLimit(voiceLimit);
        plan.setPlanType(planType);

        adminService.createPlan(plan);
        System.out.println("Plan created successfully.");
    }

    private void updatePlan() throws Exception {
        System.out.print("Enter plan ID to update: ");
        String planId = scanner.nextLine();
        List<Plan> plans = planService.getAvailablePlans(null);
        Plan planToUpdate = plans.stream()
                .filter(p -> p.getPlanId().equals(planId))
                .findFirst()
                .orElse(null);

        if (planToUpdate == null) {
            System.out.println("Plan not found.");
            return;
        }

        System.out.print("Enter new plan name (current: " + planToUpdate.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) planToUpdate.setName(name);

        System.out.print("Enter new price (current: " + planToUpdate.getPrice() + "): ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isEmpty()) planToUpdate.setPrice(Double.parseDouble(priceStr));

        System.out.print("Enter new validity days (current: " + planToUpdate.getValidityDays() + "): ");
        String validityStr = scanner.nextLine();
        if (!validityStr.isEmpty()) planToUpdate.setValidityDays(Integer.parseInt(validityStr));

        System.out.print("Enter new data limit (MB) (current: " + planToUpdate.getDataLimit() + "): ");
        String dataLimitStr = scanner.nextLine();
        if (!dataLimitStr.isEmpty()) planToUpdate.setDataLimit(Integer.parseInt(dataLimitStr));

        System.out.print("Enter new SMS limit (current: " + planToUpdate.getSmsLimit() + "): ");
        String smsLimitStr = scanner.nextLine();
        if (!smsLimitStr.isEmpty()) planToUpdate.setSmsLimit(Integer.parseInt(smsLimitStr));

        System.out.print("Enter new voice limit (minutes) (current: " + planToUpdate.getVoiceLimit() + "): ");
        String voiceLimitStr = scanner.nextLine();
        if (!voiceLimitStr.isEmpty()) planToUpdate.setVoiceLimit(Integer.parseInt(voiceLimitStr));

        adminService.updatePlan(planToUpdate);
        System.out.println("Plan updated successfully.");
    }

    private void deletePlan() throws Exception {
        System.out.print("Enter plan ID to delete: ");
        String planId = scanner.nextLine();
        try {
            adminService.deletePlan(planId);
            System.out.println("Plan deleted successfully.");
        } catch (PlanNotFoundException e) {
            System.out.println("Plan not found.");
        }
    }

    private void viewCustomer() throws Exception {
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine();
        Customer customer = adminService.viewCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }
        System.out.println("Customer Details:");
        System.out.println("ID: " + customer.getCustomerId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Phone: " + customer.getPhoneNumber());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Address: " + customer.getAddress());
        System.out.println("Username: " + customer.getUsername());
    }

    private void resolveBillingIssue() throws Exception {
        System.out.print("Enter bill ID: ");
        String billId = scanner.nextLine();
        System.out.print("Enter resolution (e.g., WAIVED, ADJUSTED, PAID): ");
        String resolution = scanner.nextLine();
        try {
            adminService.resolveBillingIssue(billId, resolution);
            System.out.println("Billing issue resolved.");
        } catch (Exception e) {
            System.out.println("Error resolving billing issue: " + e.getMessage());
        }
    }
}