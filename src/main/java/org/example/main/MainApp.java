package org.example.main;

import org.example.config.AppConfig;
import org.example.model.User;
import org.example.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var service = context.getBean(UserService.class);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== User Management System ===");

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Create User");
            System.out.println("2. View All Users");
            System.out.println("3. View User by ID");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    User newUser = new User(0, name, email);
                    service.createUser(newUser);
                    System.out.println("User created with ID: " + newUser.getId());
                }
                case 2 -> {
                    List<User> users = service.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("No users found.");
                    } else {
                        System.out.println("All Users:");
                        for (User u : users) {
                            System.out.println("ID: " + u.getId() + ", Name: " + u.getName() + ", Email: " + u.getEmail());
                        }
                    }
                }
                case 3 -> {
                    System.out.print("Enter user ID: ");
                    int id = scanner.nextInt();
                    User user = service.getUser(id);
                    if (user != null) {
                        System.out.println("User Found: ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter ID of user to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // clear newline
                    User userToUpdate = service.getUser(id);
                    if (userToUpdate != null) {
                        System.out.print("Enter new name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter new email: ");
                        String email = scanner.nextLine();
                        userToUpdate.setName(name);
                        userToUpdate.setEmail(email);
                        service.updateUser(userToUpdate);
                        System.out.println("User updated.");
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case 5 -> {
                    System.out.print("Enter ID of user to delete: ");
                    int id = scanner.nextInt();
                    User userToDelete = service.getUser(id);
                    if (userToDelete != null) {
                        service.deleteUser(id);
                        System.out.println("🗑️ User deleted.");
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case 0 -> {
                    running = false;
                    System.out.println("Exiting...");
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
        context.close();
    }
}