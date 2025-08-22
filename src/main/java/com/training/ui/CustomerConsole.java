package com.training.ui;

import com.training.model.Customer;
import com.training.model.Plan;
import com.training.model.Addon;
import com.training.model.Bill;
import com.training.model.Payment;
import com.training.model.PlanType;
import com.training.model.PaymentMethod;
import com.training.service.PlanService;
import com.training.service.PlanServiceImpl;
import com.training.service.BillingService;
import com.training.service.BillingServiceImpl;
import com.training.service.PaymentService;
import com.training.service.PaymentServiceImpl;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CustomerConsole {
    private final PlanService planService = new PlanServiceImpl();
    private final BillingService billingService = new BillingServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final Scanner scanner = new Scanner(System.in);
    private Customer loggedInCustomer;

    public void start(Customer customer) {
        loggedInCustomer = customer;
        while (true) {
            displayMenu();
            int choice = getChoice();
            try {
                switch (choice) {
                    case 1:
                        viewAvailablePlans();
                        break;
                    case 2:
                        subscribePlan();
                        break;
                    case 3:
                        addAddon();
                        break;
                    case 4:
                        viewBills();
                        break;
                    case 5:
                        makePayment();
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
        System.out.println("\n=== Customer Console ===");
        System.out.println("1. View Available Plans");
        System.out.println("2. Subscribe to Plan");
        System.out.println("3. Add Add-on");
        System.out.println("4. View Bills");
        System.out.println("5. Make Payment");
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

    private void viewAvailablePlans() throws Exception {
        System.out.println("Available Plans:");
        System.out.println("1. Prepaid Plans");
        System.out.println("2. Postpaid Plans");
        System.out.println("3. Add-ons");
        System.out.print("Select plan type (1-3): ");
        int typeChoice = Integer.parseInt(scanner.nextLine());
        PlanType planType = null;
        switch (typeChoice) {
            case 1:
                planType = PlanType.PREPAID;
                break;
            case 2:
                planType = PlanType.POSTPAID;
                break;
            case 3:
                planType = PlanType.ADDON;
                break;
            default:
                System.out.println("Invalid plan type.");
                return;
        }
        List<Plan> plans = planService.getAvailablePlans(planType);
        if (plans.isEmpty()) {
            System.out.println("No plans available for this type.");
            return;
        }
        for (Plan plan : plans) {
            System.out.println("ID: " + plan.getPlanId());
            System.out.println("Name: " + plan.getName());
            System.out.println("Price: $" + plan.getPrice());
            System.out.println("Validity: " + plan.getValidityDays() + " days");
            System.out.println("Data: " + plan.getDataLimit() + " MB");
            System.out.println("SMS: " + plan.getSmsLimit());
            System.out.println("Voice: " + plan.getVoiceLimit() + " minutes");
            System.out.println("Type: " + plan.getPlanType());
            System.out.println("-------------------");
        }
    }

    private void subscribePlan() throws Exception {
        System.out.print("Enter plan ID to subscribe: ");
        String planId = scanner.nextLine();
        List<Plan> plans = planService.getAvailablePlans(null);
        Plan selectedPlan = plans.stream()
                .filter(p -> p.getPlanId().equals(planId))
                .findFirst()
                .orElse(null);
        if (selectedPlan == null) {
            System.out.println("Plan not found.");
            return;
        }
        if (selectedPlan.getPlanType() == PlanType.ADDON) {
            System.out.println("Cannot subscribe to an add-on directly. Use 'Add Add-on' option.");
            return;
        }
        planService.subscribePlan(loggedInCustomer, selectedPlan);
        System.out.println("Successfully subscribed to plan: " + selectedPlan.getName());
    }

    private void addAddon() throws Exception {
        System.out.print("Enter add-on ID: ");
        String addonId = scanner.nextLine();
        List<Plan> plans = planService.getAvailablePlans(PlanType.ADDON);
        Addon selectedAddon = (Addon) plans.stream()
                .filter(p -> p.getPlanId().equals(addonId) && p.getPlanType() == PlanType.ADDON)
                .findFirst()
                .orElse(null);
        if (selectedAddon == null) {
            System.out.println("Add-on not found.");
            return;
        }
        planService.addAddon(loggedInCustomer, selectedAddon);
        System.out.println("Successfully added add-on: " + selectedAddon.getName());
    }

    private void viewBills() throws Exception {
        List<Bill> bills = billingService.getCustomerBills(loggedInCustomer.getCustomerId());
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
            return;
        }
        System.out.println("Your Bills:");
        for (Bill bill : bills) {
            System.out.println("Bill ID: " + bill.getBillId());
            System.out.println("Amount: $" + bill.getAmount());
            System.out.println("Generated Date: " + bill.getGeneratedDate());
            System.out.println("Due Date: " + bill.getDueDate());
            System.out.println("Status: " + bill.getStatus());
            System.out.println("-------------------");
        }
    }

    private void makePayment() throws Exception {
        System.out.print("Enter bill ID: ");
        String billId = scanner.nextLine();
        Bill bill = billingService.getBillById(billId);
        if (bill == null || !bill.getCustomerId().equals(loggedInCustomer.getCustomerId())) {
            System.out.println("Bill not found or does not belong to you.");
            return;
        }
        if (!"PENDING".equals(bill.getStatus())) {
            System.out.println("Bill is not pending payment.");
            return;
        }
        System.out.print("Enter payment amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Select payment method:");
        System.out.println("1. CARD");
        System.out.println("2. UPI");
        System.out.println("3. NETBANKING");
        System.out.println("4. CASH");
        System.out.print("Enter choice (1-4): ");
        int methodChoice = Integer.parseInt(scanner.nextLine());
        String paymentMethod;
        switch (methodChoice) {
            case 1:
                paymentMethod = "CARD";
                break;
            case 2:
                paymentMethod = "UPI";
                break;
            case 3:
                paymentMethod = "NETBANKING";
                break;
            case 4:
                paymentMethod = "CASH";
                break;
            default:
                System.out.println("Invalid payment method.");
                return;
        }
        String transactionId = UUID.randomUUID().toString();
        paymentService.processPayment(bill, amount, paymentMethod, transactionId);
        System.out.println("Payment processed successfully. Transaction ID: " + transactionId);
    }
}