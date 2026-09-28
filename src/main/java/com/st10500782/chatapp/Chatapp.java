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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Registration registration = new Registration();
        // Login login = new Login(registration);

        System.out.println("===================================");
        System.out.println("        QUICKCHAT REGISTRATION     ");
        System.out.println("===================================");

        // Capture the user's personal details once for the welcome message.
        System.out.print("Please enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Please enter your last name: ");
        String lastName = scanner.nextLine();

        // Keep prompting until registration is successful.
        boolean registered = false;
        while (!registered) {
            System.out.print("Enter a username (must contain an underscore and be no more than five characters long): ");
            String username = scanner.nextLine();

            System.out.print("Enter a password (at least eight characters, a capital letter, a number and a special character): ");
            String password = scanner.nextLine();

            System.out.print("Enter your cell phone number (with the international country code, e.g. +27): ");
            String cellPhoneNumber = scanner.nextLine();

            String registrationMessage = registration.registerUser(firstName, lastName, username, password, cellPhoneNumber);
            System.out.println(registrationMessage);

            registered = registration.isRegistered();

            if (!registered) {
                System.out.println("Please correct the above detail(s) and try again.\n");
            }
        }
    }
}
