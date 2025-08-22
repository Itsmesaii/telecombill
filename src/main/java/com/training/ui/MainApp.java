package com.training.ui;

import com.training.model.Customer;
import com.training.model.Admin;
import com.training.service.AuthService;
import com.training.service.AuthServiceImpl;
import com.training.exceptions.InvalidLoginException;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        AuthService authService = new AuthServiceImpl();
        Scanner scanner = new Scanner(System.in);
        CustomerConsole customerConsole = new CustomerConsole();
        AdminConsole adminConsole = new AdminConsole();

        while (true) {
            System.out.println("\n=== Telecom Management System ===");
            System.out.println("1. Customer Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Register Customer");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        handleCustomerLogin(authService, customerConsole, scanner);
                        break;
                    case 2:
                        handleAdminLogin(authService, adminConsole, scanner);
                        break;
                    case 3:
                        handleCustomerRegistration(authService, scanner);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void handleCustomerLogin(AuthService authService, CustomerConsole customerConsole, Scanner scanner) throws Exception {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authService.customerLogin(username, password)) {
            Customer customer = new Customer();
            customer.setUsername(username);
            customer.setCustomerId(java.util.UUID.randomUUID().toString());
            customerConsole.start(customer);
        } else {
            throw new InvalidLoginException("Customer login failed.");
        }
    }

    private static void handleAdminLogin(AuthService authService, AdminConsole adminConsole, Scanner scanner) throws Exception {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authService.adminLogin(username, password)) {
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setAdminId(java.util.UUID.randomUUID().toString());
            adminConsole.start(admin);
        } else {
            throw new InvalidLoginException("Admin login failed.");
        }
    }

    private static void handleCustomerRegistration(AuthService authService, Scanner scanner) throws Exception {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Customer customer = new Customer();
        customer.setCustomerId(java.util.UUID.randomUUID().toString());
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setUsername(username);
        customer.setPasswordHash(password);

        authService.registerCustomer(customer);
        System.out.println("Customer registered successfully.");
    }
}