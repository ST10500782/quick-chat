/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.st10500782.chatapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class Message {
    private String messageId;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int totalMessagesSent = 0;
    
    // Parallel Arrays / Lists for Part 3
    private static List<String> sentMessages = new ArrayList<>();
    private static List<String> disregardedMessages = new ArrayList<>();
    private static List<String> storedMessages = new ArrayList<>();
    private static List<String> messageHashes = new ArrayList<>();
    private static List<String> messageIDs = new ArrayList<>();
    private static List<String> recipients = new ArrayList<>();
    
    // check message ID 
    public boolean checkMessageId(String id) {
        return id != null && id.length() <= 10;
    }
    
    
     // check Recipient's cell number
    public String checkRecipientCell(String cellNumber) {
        if (cellNumber != null && cellNumber.startsWith("+") && cellNumber.length() <= 11) {
            return "Cell phone number successfully captured.";
        } else if (cellNumber != null && cellNumber.matches("\\d{10}")) { // Allow local dev numbers like Test Data 4
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }
    
    // create Message Hash
    public String createMessageHash(String id, int index, String msg) {
        String prefix = id.length() >= 2 ? id.substring(0, 2) : "00";
        String[] words = msg.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";
        
        return prefix + ":" + index + ":" + (firstWord + lastWord).toUpperCase();
    } 
    
    // message Character length validation 
    public String checkMessageLength(String msg) {
        if (msg.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = msg.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }
    
     // 5. Store/Send Action Handler and JSON Storage
    public String SentMessage(int choice, String id, String recip, String text, String hash) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessages.add(text);
                messageIDs.add(id);
                recipients.add(recip);
                messageHashes.add(hash);
                return "Message successfully sent";
            case 2:
                disregardedMessages.add(text);
                return "Press 0 to delete the message";
            case 3:
                storedMessages.add(text);
                messageIDs.add(id);
                recipients.add(recip);
                messageHashes.add(hash);
                storeMessageToJSON(id, recip, text, hash);
                return "Message successfully stored";
            default:
                return "Invalid choice.";
        }
    }
    
    // JSON Storage implementation with proper attribution
    private void storeMessageToJSON(String id, String recip, String text, String hash) {
        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write("{\"ID\":\"" + id + "\", \"Recipient\":\"" + recip + "\", \"Hash\":\"" + hash + "\", \"Message\":\"" + text + "\"}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Read JSON file into Stored Messages array/list[cite: 17, 45]
    public static void loadStoredMessagesFromJson() {
        storedMessages.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("\"Message\":\"")) {
                    String msg = line.split("\"Message\":\"")[1].replace("\"}", "");
                    if (!storedMessages.contains(msg)) {
                        storedMessages.add(msg);
                    }
                }
            }
        } catch (IOException e) {
            // File may not exist yet if no messages stored
        }
    }
    
     // --- Part 3 Feature Requirements ---

    // a. Display sender/recipient and stored messages
    public void displayStoredSenderAndRecipient() {
        loadStoredMessagesFromJson();
        System.out.println("\n--- Stored Messages Sender & Recipient Report ---");
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        for (int i = 0; i < storedMessages.size(); i++) {
            System.out.println("Recipient: " + (i < recipients.size() ? recipients.get(i) : "N/A") + " | Message: " + storedMessages.get(i));
        }
    }
    
    // b. Display the longest stored message
    public String getLongestMessage() {
        loadStoredMessagesFromJson();
        // Check across sent and stored
        List<String> allMessages = new ArrayList<>(sentMessages);
        allMessages.addAll(storedMessages);

        if (allMessages.isEmpty()) return "No messages available.";
        
        String longest = allMessages.get(0);
        for (String msg : allMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest;
    }
    
    // c. Search for a message ID and display recipient and message[cite: 45]
    public String searchByMessageID(String searchID) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(searchID)) {
                return "Recipient: " + recipients.get(i) + " | Message: " + sentMessages.get(i) + (i < sentMessages.size() ? sentMessages.get(i) : storedMessages.get(i));
            }
        }
        return "Message ID not found.";
    }

    // d. Search all messages sent or stored for a particular recipient[cite: 45]
    public List<String> searchByRecipient(String targetRecipient) {
        List<String> results = new ArrayList<>();
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(targetRecipient)) {
                if (i < sentMessages.size()) {
                    results.add(sentMessages.get(i));
                } else {
                    results.add(storedMessages.get(i % storedMessages.size()));
                }
            }
        }
        return results;
    }
    
    // e. Delete a message using the message hash[cite: 45]
    public boolean deleteByHash(String targetHash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(targetHash)) {
                String msgContent = i < sentMessages.size() ? sentMessages.get(i) : "Message";
                messageHashes.remove(i);
                messageIDs.remove(i);
                recipients.remove(i);
                if (i < sentMessages.size()) {
                    sentMessages.remove(i);
                } else {
                    storedMessages.remove(i);
                }
                System.out.println("Message: \"" + msgContent + "\" successfully deleted.");
                return true;
            }
        }
        System.out.println("Message hash not found.");
        return false;
    }

    // f. Display a report listing full details of all stored/sent messages[cite: 45]
    public void displayFullReport() {
        System.out.println("\n=============================================");
        System.out.println("           QUICKCHAT MESSAGE REPORT          ");
        System.out.println("=============================================");
        for (int i = 0; i < messageIDs.size(); i++) {
            System.out.println("Message Hash : " + (i < messageHashes.size() ? messageHashes.get(i) : "N/A"));
            System.out.println("Recipient    : " + (i < recipients.size() ? recipients.get(i) : "N/A"));
            System.out.println("Message      : " + (i < sentMessages.size() ? sentMessages.get(i) : "Stored/Other"));
            System.out.println("---------------------------------------------");
        }
    }
    
    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // Getters for testing
    public static List<String> getSentMessages() { return sentMessages; }
    public static List<String> getStoredMessages() { return storedMessages; }
    public static List<String> getDisregardedMessages() { return disregardedMessages; }
    
    public void processMessage(Scanner scanner, int index) {
        Random rand = new Random();
        this.messageId = String.valueOf(1000000000L + rand.nextLong(900000000L));

        System.out.print("Enter recipient cell number: ");
        this.recipient = scanner.nextLine();
        System.out.println(checkRecipientCell(recipient));

        System.out.print("Enter your message (max 250 characters): ");
        this.messageText = scanner.nextLine();
        System.out.println(checkMessageLength(messageText));

        this.messageHash = createMessageHash(messageId, index, messageText);

        System.out.println("Choose action: 1) Send Message, 2) Disregard Message, 3) Store Message");
        int action = 0;
        try {
            action = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered.");
        }

        System.out.println(SentMessage(action, this.messageId, this.recipient, this.messageText, this.messageHash));
        printMessages();
    }
    // Print Message Details[cite: 13, 14]
    public void printMessages() {
        System.out.println("\n--- Message Details ---");
        System.out.println("Message ID: " + messageId);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageText);
    }
    
    
    //return total messages sent
    public int returnTotalMessages() {
        return totalMessagesSent;
    }
}
