/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.st10500782.chatapp;

/**
 *
 * @author TshepoMahudu
 */
public class Registration {
     // Registration messages.
    public static final String USERNAME_SUCCESS = "Username successfully captured.";
    public static final String USERNAME_ERROR = "Username is not correctly formatted; "
            + "please ensure that your username contains an underscore and is no "
            + "more than five characters in length.";

    public static final String PASSWORD_SUCCESS = "Password successfully captured.";
    public static final String PASSWORD_ERROR = "Password is not correctly formatted; "
            + "please ensure that the password contains at least eight characters, "
            + "a capital letter, a number, and a special character.";

    public static final String CELL_SUCCESS = "Cell phone number successfully added.";
    public static final String CELL_ERROR = "Cell phone number incorrectly formatted "
            + "or does not contain international code.";

    /*
     * Regular expression that validates an international cell phone number:
     * a leading "+" followed by an international country code (1-3 digits),
     *
     * Reference: OpenAI (2026) ChatGPT [Large language model].
     * Available at: https://chat.openai.com/ (Accessed: 28 September 2026).
     * Prompted to generate a regular expression that checks that a cell phone
     * number contains an international country code followed by a number that
     * is no more than ten characters long.
     */
    private static final String CELL_PHONE_REGEX = "^\\+\\d{1,3}\\d{1,10}$";

    private User registeredUser;

    /**
     * Checks that a username contains an underscore (_) and is no more than
     * five characters long.
     *
     * @param username the username to validate
     * @return true if the username is correctly formatted, otherwise false
     */
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    /**
     * Checks that a password meets the complexity rules: at least eight
     * characters long, contains a capital letter, a number and a special
     * character.
     *
     * @param password the password to validate
     * @return true if the password meets the complexity rules, otherwise false
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);
            if (Character.isUpperCase(character)) {
                hasCapital = true;
            } else if (Character.isDigit(character)) {
                hasNumber = true;
            } else if (!Character.isLetter(character)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Checks that a cell phone number contains the international country code
     * followed by a number that is no more than ten characters long, using a
     * regular expression (see CELL_PHONE_REGEX for the reference).
     *
     * @param cellPhoneNumber the cell phone number to validate
     * @return true if the cell phone number is correctly formatted, otherwise
     *         false
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber != null && cellPhoneNumber.matches(CELL_PHONE_REGEX);
    }

    /**
     * Attempts to register a user with the given details. Each detail is
     * validated and the appropriate success or error message is returned.
     * A {@link User} object is only created and stored when all three
     * details are valid.
     *
     * @param firstName       the user's first name
     * @param lastName        the user's last name
     * @param username        the chosen username
     * @param password        the chosen password
     * @param cellPhoneNumber the user's cell phone number
     * @return the registration messaging indicating the result of each check
     */
    public String registerUser(String firstName, String lastName, String username,
                               String password, String cellPhoneNumber) {
        boolean usernameValid = checkUserName(username);
        boolean passwordValid = checkPasswordComplexity(password);
        boolean cellPhoneValid = checkCellPhoneNumber(cellPhoneNumber);

        StringBuilder registrationMessage = new StringBuilder();
        registrationMessage.append(usernameValid ? USERNAME_SUCCESS : USERNAME_ERROR).append("\n");
        registrationMessage.append(passwordValid ? PASSWORD_SUCCESS : PASSWORD_ERROR).append("\n");
        registrationMessage.append(cellPhoneValid ? CELL_SUCCESS : CELL_ERROR);

        if (usernameValid && passwordValid && cellPhoneValid) {
            registeredUser = new User(firstName, lastName, username, password, cellPhoneNumber);
        }

        return registrationMessage.toString();
    }

    /**
     * @return the registered user, or null if no user has registered yet
     */
    public User getRegisteredUser() {
        return registeredUser;
    }

    /**
     * @return true if a user has been successfully registered
     */
    public boolean isRegistered() {
        return registeredUser != null;
    }    
    
}
