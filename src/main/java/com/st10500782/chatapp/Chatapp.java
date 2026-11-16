/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.st10500782.chatapp;

import java.util.List;
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
        
        // ==============================
        // part 2: Welcome message and numberic menu.
        // ==============================
        System.out.println("\n===================================");
        System.out.println(" Welcome to QuickChat."); 
        System.out.println("=====================================");
        Message messageHandler = new Message();
        boolean messagingRunning = true;
        while (messagingRunning) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages"); //[cite: 11]
            System.out.println("2) Show recently sent messages"); //[cite: 11]
            System.out.println("3) Quit"); //[cite: 11]
            
            String choiceStr = readInput(scanner, "Choose an option (1-3): ");
            
            int choice;
            try {
                choice = Integer.parseInt(choiceStr.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Prompt user for number of messages and run loop
                    String numMsgStr = readInput(scanner, "Enter the number of messages you wish to send: "); //[cite: 11]
                    int numMessages;
                    try {
                        numMessages = Integer.parseInt(numMsgStr.trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format. Returning to menu.");
                        break;
                    }

                    for (int i = 0; i < numMessages; i++) {
                        System.out.println("\n--- Processing Message " + (i + 1) + " of " + numMessages + " ---");
                        messageHandler.processMessage(scanner, i);
                    }
                    
                    System.out.println("\nTotal messages processed: " + messageHandler.returnTotalMessages()); //[cite: 13]
                    break;

                case 2:
                    messageHandler.displayRecentlySentMessages();
                    break;

                case 3:
                    System.out.println("Exiting QuickChat. Goodbye!");
                    messagingRunning = false;
                    break;
                    
                 case 4:
    boolean part3Running = true;
    Message messageManager = new Message();
    while (part3Running) {
        System.out.println("\n--- Stored Messages & Report Menu ---");
        System.out.println("a) Display sender and recipient of all stored messages");
        System.out.println("b) Display the longest stored/sent message");
        System.out.println("c) Search for a message ID");
        System.out.println("d) Search messages by recipient");
        System.out.println("e) Delete message using message hash");
        System.out.println("f) Display full message report");
        System.out.println("g) Return to Main Menu");

        String subChoice = readInput(scanner, "Choose an option (a-g): ").trim().toLowerCase();

        switch (subChoice) {
            case "a":
                messageManager.displayStoredSenderAndRecipient();
                break;
            case "b":
                System.out.println("\nLongest Message: \"" + messageManager.getLongestMessage() + "\"");
                break;
            case "c":
                String searchId = readInput(scanner, "Enter Message ID to search: ");
                System.out.println(messageManager.searchByMessageID(searchId));
                break;
            case "d":
                String searchRecip = readInput(scanner, "Enter recipient number to search: ");
                List<String> foundMsgs = messageManager.searchByRecipient(searchRecip);
                System.out.println("\nMessages for " + searchRecip + ": " + foundMsgs);
                break;
            case "e":
                String delHash = readInput(scanner, "Enter Message Hash to delete: ");
                messageManager.deleteByHash(delHash);
                break;
            case "f":
                messageManager.displayFullReport();
                break;
            case "g":
                part3Running = false;
                break;
            default:
                System.out.println("Invalid option. Choose between a and g.");
        }
    }
break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 3.");
            }
        }

        scanner.close();
    }
}
