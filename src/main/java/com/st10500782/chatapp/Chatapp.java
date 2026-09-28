/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.st10500782.chatapp;

import java.util.Scanner;

/**
 *
 * @author TshepoMahudu
 */
public class Chatapp {

      /**
     * Prints a prompt and reads a line of input. If the input stream is
     * closed (no interactive console available), the application exits
     * gracefully instead of looping or crashing.
     */
    private static String readInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println("\nNo input available. Exiting application.");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Registration registration = new Registration();
        Login login = new Login(registration);

        System.out.println("===================================");
        System.out.println("        QUICKCHAT REGISTRATION     ");
        System.out.println("===================================");

        // Capture the user's personal details once for the welcome message.
        String firstName = readInput(scanner, "Please enter your first name: ");
        String lastName = readInput(scanner, "Please enter your last name: ");

        // Keep prompting until registration is successful.
        boolean registered = false;
        while (!registered) {
            String username = readInput(scanner, "Enter a username (must contain an underscore and be no more than five characters long): ");
            String password = readInput(scanner, "Enter a password (at least eight characters, a capital letter, a number and a special character): ");
            String cellPhoneNumber = readInput(scanner, "Enter your cell phone number (with the international country code, e.g. +27): ");

            String registrationMessage = registration.registerUser(firstName, lastName, username, password, cellPhoneNumber);
            System.out.println(registrationMessage);

            registered = registration.isRegistered();

            if (!registered) {
                System.out.println("Please correct the above detail(s) and try again.\n");
            }
        }

        System.out.println("\n===================================");
        System.out.println("             QUICKCHAT LOGIN       ");
        System.out.println("===================================");

        // Keep prompting until the user logs in successfully.
        boolean loggedIn = false;
        while (!loggedIn) {
            String loginUsername = readInput(scanner, "Enter your username: ");
            String loginPassword = readInput(scanner, "Enter your password: ");

            loggedIn = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.returnLoginStatus(loggedIn));

            if (!loggedIn) {
                System.out.println();
            }
        }

        scanner.close();
    }
}
