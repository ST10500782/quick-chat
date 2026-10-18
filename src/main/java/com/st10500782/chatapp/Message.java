/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.st10500782.chatapp;

import java.io.FileWriter;
import java.io.IOException;
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
    
    // check message ID 
    public boolean checkMessageId(String id) {
        return id != null && id.length() <= 10;
    }
    
    // check Recipient's cell number
    public String checkRecipientCell(String cellNumber) {
        if (cellNumber != null && cellNumber.startsWith("+") && cellNumber.length() <= 11) {
            return "Cellphone number successfully captured.";
        } else {
            return "Cellphone number is incorrectly formatted or does not contain an international code";
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
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                return "Message successfully sent";
            case 2:
                return "Press 0 to delete the message";
            case 3:
                storeMessageToJSON();
                return "Message successfully stored";
            default:
                return "Invalid choice.";
        }
    }
    
    // JSON Storage implementation with proper attribution
    private void storeMessageToJSON() {
        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write("{\"ID\":\"" + messageId + "\", \"Recipient\":\"" + recipient + "\", \"Message\":\"" + messageText + "\"}\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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
        int action = scanner.nextInt();
        scanner.nextLine();

        System.out.println(SentMessage(action));
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
