# QuickChat — PROG5121 PoE

A console-based Java chat application developed for the **Programming 1A (PROG5121)** Portfolio of Evidence.
This repository currently contains **Part 1 — Registration and Login Feature**, built as a Maven project
with JUnit 5 unit tests and GitHub Actions CI.

> **Note:** This is a console application only. No GUI (and no `JOptionPane`) is used, as required by the PoE brief.

---

## Table of Contents

- [Overview](#overview)
- [Features (Part 1)](#features-part-1)
- [Project Structure](#project-structure)
- [Class Design (OOP)](#class-design-oop)
- [Validation Rules](#validation-rules)
- [System Messages](#system-messages)
- [Requirements](#requirements)
- [How to Run](#how-to-run)
  - [In NetBeans](#in-netbeans)
  - [From the Command Line](#from-the-command-line)
- [How to Run the Tests](#how-to-run-the-tests)
- [Unit Test Coverage](#unit-test-coverage)
- [Continuous Integration (GitHub Actions)](#continuous-integration-github-actions)
- [Attribution / References](#attribution--references)
- [Roadmap (Parts 2 & 3)](#roadmap-parts-2--3)

---

## Overview

QuickChat is a chat application built incrementally across three parts of the PoE:

1. **Part 1 (this submission):** user registration and login.
2. **Part 2:** sending, storing and disregarding messages (with JSON storage).
3. **Part 3:** message arrays, searching, deleting and reporting.

In Part 1, a user creates an account by entering a **username**, **password** and **South African
cell phone number**. Each input is validated against strict rules, and the system replies with the
exact feedback messages specified in the PoE. The user then logs in with the same username and
password and receives a personalised welcome message.

## Features (Part 1)

- **Registration**
  - Username validation: must contain an underscore (`_`) and be no more than **5 characters** long.
  - Password complexity validation: at least **8 characters**, one **capital letter**, one
    **number**, and one **special character**.
  - Cell phone validation: a **regular expression** checks that the number contains an
    **international country code** followed by a number of no more than **10 digits**
    (see [Attribution](#attribution--references)).
  - A `User` object is only created and stored when **all three** details are valid; otherwise the
    user is re-prompted.
- **Login**
  - Verifies entered credentials against the registered user.
  - Successful login: `Welcome <first name>, <last name> it is great to see you again.`
  - Failed login: `Username or password incorrect, please try again.` (user may retry)
- **Graceful exit** when no interactive input is available.
- **16 JUnit 5 unit tests** covering every validation rule, message and login path using the exact
  test data from the PoE specification.

## Project Structure

```
quickchat/
├── pom.xml                          # Maven build file (JUnit 5, Java 17)
├── nbactions.xml                    # NetBeans Run/Debug/Profile actions
├── .gitignore
├── README.md
├── .github/
│   └── workflows/
│       └── TestJava.yml             # GitHub Actions: runs mvn test on every push
└── src/
    ├── main/java/com/mycompany/quickchat/
    │   ├── Main.java                # Console entry point (orchestration)
    │   ├── Registration.java        # Validation + registration logic
    │   ├── Login.java               # Authentication logic (POE Login class)
    │   └── User.java                # Model class for a registered user
    └── test/java/com/mycompany/quickchat/
        ├── RegistrationTest.java    # 10 tests: validation rules + messages
        └── LoginTest.java           # 6 tests: login + login status messages
```

## Class Design (OOP)

The project follows a clear object-oriented design with separated responsibilities:

| Class | Responsibility |
|---|---|
| `User` | Immutable **model** — stores first name, last name, username, password and cell phone number. Private final fields, getters only. |
| `Registration` | **Registration** — owns the validation rules (`checkUserName`, `checkPasswordComplexity`, `checkCellPhoneNumber`), the registration messages, and `registerUser()` which creates and stores a `User` only when every check passes. |
| `Login` | **Authentication** — `loginUser()` compares entered credentials against the registered `User`; `returnLoginStatus()` builds the welcome/error message. Keeps the six methods required by the PoE specification, delegating validation to its `Registration` instance. |
| `Main` | **Orchestration** — creates the `Registration` object, injects it into `Login` (constructor injection), and drives the console input/output flow using `Scanner`. |

OOP principles demonstrated:

- **Encapsulation** — all fields are private; access is via methods only.
- **Composition** — `Login` *has-a* `Registration`; `Registration` *has-a* `User`.
- **Constructor injection** — `new Login(registration)` wires the dependency explicitly.
- **Single responsibility** — validation, authentication, modelling and I/O each live in their own class.

## Validation Rules

| Input | Rule | Example (valid) | Example (invalid) |
|---|---|---|---|
| Username | Contains `_` and length ≤ 5 | `kyl_1` | `kyle!!!!!!!` |
| Password | ≥ 8 chars, ≥ 1 capital, ≥ 1 digit, ≥ 1 special char | `Ch&&sec@ke99!` | `password` |
| Cell phone | `+`, country code (1–3 digits), then ≤ 10 digits | `+27838968976` | `08966553` |

The cell phone rule is enforced with the regular expression `^\\+\\d{1,3}\\d{1,10}$`
(see [Attribution](#attribution--references)).

## System Messages

The application returns the exact messages required by the PoE:

| Condition | Message |
|---|---|
| Username valid | `Username successfully captured.` |
| Username invalid | `Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.` |
| Password valid | `Password successfully captured.` |
| Password invalid | `Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.` |
| Cell phone valid | `Cell phone number successfully added.` |
| Cell phone invalid | `Cell phone number incorrectly formatted or does not contain international code.` |
| Login successful | `Welcome <first name>, <last name> it is great to see you again.` |
| Login failed | `Username or password incorrect, please try again.` |

## Requirements

- **JDK 17** or newer (project targets Java 17 via `maven.compiler.release`)
- **Apache Maven 3.6+**
- **NetBeans** (optional — any IDE with Maven support works)

## How to Run

### In NetBeans

1. **File → Open Project…** and select the `quickchat` folder (it has the Maven coffee-cup icon).
2. Press **F6** (Run Project).
3. The app runs in the **Output window** at the bottom — click into it, type your answers and press
   Enter after each one.

> The Run button uses the `exec:java` Maven goal (configured in `nbactions.xml`), which runs the app
> inside Maven's JVM so that keyboard input typed in the Output window reaches the application.

### From the Command Line

```powershell
cd quickchat
mvn compile
java -cp target\classes com.mycompany.quickchat.Main
```

Or via the Maven exec plugin:

```powershell
mvn process-classes org.codehaus.mojo:exec-maven-plugin:3.1.0:java "-Dexec.mainClass=com.mycompany.quickchat.Main"
```

### Example session

```
===================================
        QUICKCHAT REGISTRATION
===================================
Please enter your first name: Kyle
Please enter your last name: Smith
Enter a username (...): kyl_1
Enter a password (...): Ch&&sec@ke99!
Enter your cell phone number (...): +27838968976
Username successfully captured.
Password successfully captured.
Cell phone number successfully added.

===================================
             QUICKCHAT LOGIN
===================================
Enter your username: kyl_1
Enter your password: Ch&&sec@ke99!
Welcome Kyle, Smith it is great to see you again.
```

## How to Run the Tests

- **NetBeans:** right-click the project → **Test** (Alt+F6), or right-click a single test class →
  **Test File**.
- **Command line:**

```powershell
mvn test
```

## Unit Test Coverage

16 tests, all passing, using the exact test data prescribed by the PoE:

**`RegistrationTest`** (10 tests)

| Test | Data | Expected |
|---|---|---|
| Username valid | `kyl_1` | `true` |
| Username invalid | `kyle!!!!!!!` | `false` |
| Password valid | `Ch&&sec@ke99!` | `true` |
| Password invalid | `password` | `false` |
| Cell phone valid | `+27838968976` | `true` |
| Cell phone invalid | `08966553` | `false` |
| All details valid | POE test data | All 3 success messages; `User` stored |
| Username invalid message | `kyle!!!!!!!` | Exact PoE error message |
| Password invalid message | `password` | Exact PoE error message |
| Cell phone invalid message | `08966553` | Exact PoE error message |

**`LoginTest`** (6 tests)

| Test | Data | Expected |
|---|---|---|
| Login successful | `kyl_1` / `Ch&&sec@ke99!` | `true` |
| Login failed (wrong password) | `kyl_1` / `wrongPassword1!` | `false` |
| Login failed (wrong username) | `kyle!!!!!!!` / `Ch&&sec@ke99!` | `false` |
| Welcome message | successful login | `Welcome Kyle, Smith it is great to see you again.` |
| Failed login message | failed login | `Username or password incorrect, please try again.` |
| PoE methods on `Login` | POE test data | Delegated validations behave identically |

## Continuous Integration (GitHub Actions)

[.github/workflows/TestJava.yml](.github/workflows/TestJava.yml) automatically runs the full test
suite on every push and pull request to `main`:

1. Checks out the repository
2. Sets up JDK 17 (Temurin)
3. Runs `mvn -B test`

A green ✔ on the commit means all 16 tests passed in the cloud.

## Attribution / References

The regular expression used in `Registration.checkCellPhoneNumber()`:

```java
private static final String CELL_PHONE_REGEX = "^\\+\\d{1,3}\\d{1,10}$";
```

> OpenAI (2026) *ChatGPT* [Large language model]. Available at: https://chat.openai.com/
> (Accessed: 28 September 2026). Prompted to generate a regular expression that checks that a cell
> phone number contains an international country code followed by a number that is no more than ten
> characters long.

Additional background reading on chat application architecture:
> QuickBlox (n.d.) *Beginner's guide to chat app architecture*. Available at:
> https://quickblox.com/blog/beginners-guide-to-chat-app-architecture/

## Roadmap (Parts 2 & 3)

- **Part 2 — Sending Messages:** "Welcome to QuickChat." menu (Send / Show recent / Quit), message
  objects with auto-generated 10-digit Message IDs, 250-character limit, Message Hashes
  (e.g. `00:0:HITHANKS`), Send/Store/Disregard options, JSON file storage, and extended unit tests.
- **Part 3 — Store Data & Reports:** populated arrays (sent, disregarded, stored, hashes, IDs),
  stored-messages menu (display, longest message, search by ID/recipient, delete by hash, full
  report), and final unit tests.
