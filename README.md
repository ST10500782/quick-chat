# QuickChat Console Application (PROG5121 POE)

**Student Number:** ST10500782  
**Module:** Programming 1A (PROG5121)  
**Assessment:** Portfolio of Evidence (Parts 1, 2, and 3)  

---

## Overview

QuickChat is a Java Maven console-based messaging application built across three iterative development phases. It provides secure user registration and authentication, interactive message creation with automated hash generation, JSON persistence, array-based message searching and reporting, and automated JUnit 5 testing via GitHub Actions.

---

## Features Implemented

### Part 1: Registration & Login
* **Account Registration (`Registration.java`, `User.java`):**
  * Validates that usernames contain an underscore (`_`) and are no more than 5 characters long.
  * Enforces password complexity rules (minimum 8 characters, at least one uppercase letter, one number, and one special character).
  * Validates South African cell phone numbers with international country code formatting (`+27` followed by 9 digits) using regular expressions.
* **User Authentication (`Login.java`):**
  * Verifies stored credentials against login input and gates access to the messaging system behind a successful login session.

### Part 2: Sending & Managing Messages
* **Interactive Console Menu (`Chatapp.java`):**
  * Displays the `"Welcome to QuickChat."` banner upon login and runs a continuous loop-driven numeric menu until the user exits.
  * Prompts the user to specify how many messages they wish to process per session.
* **Message Processing (`Message.java`):**
  * **Unique Message ID:** Randomly generates a 10-digit tracking ID (`checkMessageId`).
  * **Recipient Validation:** Verifies international South African phone number formatting (`checkRecipientCell`).
  * **Payload Validation:** Ensures messages do not exceed 250 characters and reports the exact excess character count if exceeded (`checkMessageLength`).
  * **Automated Message Hash:** Generates uppercase hashes formatted as `[ID_Prefix]:[Index]:[FIRSTWORD][LASTWORD]` (e.g., `00:0:HITONIGHT`).
  * **Message Actions:** Allows users to **Send**, **Disregard**, or **Store** messages, persisting stored messages to `messages.json`.

### Part 3: Data Persistence, Parallel Arrays & Reporting
* **Synchronized Data Structures:**
  * Populates separate tracking lists for **Sent Messages**, **Disregarded Messages**, and **Stored Messages**, alongside synchronized parallel lists for **Message IDs**, **Recipients**, **Message Hashes**, and **Statuses**.
* **JSON File Reading:**
  * Reads stored messages back from `messages.json` into memory (`loadStoredMessagesFromJson`).
* **Recently Sent Messages (Option 2) & Stored Messages Submenu (Option 4):**
  * **Option 2:** Displays full details of all messages sent during the session.
  * **Option 4a:** Displays sender and recipient details for all stored messages.
  * **Option 4b:** Finds and displays the longest sent or stored message.
  * **Option 4c:** Searches for a message by its unique Message ID and displays the recipient and message text.
  * **Option 4d:** Retrieves all messages sent or stored for a specific recipient phone number.
  * **Option 4e:** Deletes a message record across all parallel lists using its Message Hash.
  * **Option 4f:** Generates a formatted QuickChat Message Report displaying all recorded message details.

---

## Project Structure

```text
chatapp/
├── .github/
│   └── workflows/
│       └── maven.yml                 # Automated CI/CD test workflow
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/st10500782/chatapp/
│   │           ├── Chatapp.java      # Main entry point and console menu controller
│   │           ├── Login.java        # Login verification and status messaging
│   │           ├── Message.java      # Message creation, validation, JSON I/O, and reporting
│   │           ├── Registration.java # User input validation and registration logic
│   │           └── User.java         # User entity model
│   └── test/
│       └── java/
│           ├── LoginTest.java        # JUnit tests for login functionality
│           ├── RegistrationTest.java # JUnit tests for registration rules
│           └── Part3Test.java        # JUnit tests for messaging, arrays, search, and deletion
├── messages.json                     # Local JSON storage file for saved messages
└── pom.xml                           # Maven project configuration and JUnit dependencies
```

---

## Prerequisites & Running the Application

* **Java Development Kit (JDK):** JDK 17 or higher
* **Build Tool:** Apache Maven 3.8+

### Build and Run Tests
Run all JUnit 5 unit tests via Maven:
```bash
mvn clean test
```

### Run the Console Application
Compile and execute the main class:
```bash
mvn compile exec:java -Dexec.mainClass="com.st10500782.chatapp.Chatapp"
```

---

## Continuous Integration (GitHub Actions)

This repository includes a GitHub Actions workflow (`.github/workflows/maven.yml`) that automatically builds the Maven project and runs all JUnit test suites on every `push` and `pull_request` to ensure code stability across feature branches.

---

## References & Code Attribution

* Oracle, 2026. *Class Pattern (Java SE 21 & JDK 21)*. [online] Available at: <https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/regex/Pattern.html> [Accessed 2026].
* Oracle, 2026. *Class FileWriter (Java SE 21 & JDK 21)*. [online] Available at: <https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/FileWriter.html> [Accessed 2026].
* Oracle, 2026. *Class BufferedReader (Java SE 21 & JDK 21)*. [online] Available at: <https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/BufferedReader.html> [Accessed 2026].
* QuickBlox, 2026. *Beginner's Guide to Chat App Architecture*. [online] Available at: <https://quickblox.com/blog/beginners-guide-to-chat-app-architecture/> [Accessed 2026].