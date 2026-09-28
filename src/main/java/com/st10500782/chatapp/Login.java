/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.st10500782.chatapp;

/**
 *
 * @author TshepoMahudu
 */
public class Login {
    public static final String LOGIN_FAILURE = "Username or password incorrect, please try again.";

    private final Registration registration;

    public Login() {
        this.registration = new Registration();
    }

    public Login(Registration registration) {
        this.registration = registration;
    }

    /**
     * Delegates to {@link Registration#checkUserName(String)}.
     */
    public boolean checkUserName(String username) {
        return registration.checkUserName(username);
    }

    /**
     * Delegates to {@link Registration#checkPasswordComplexity(String)}.
     */
    public boolean checkPasswordComplexity(String password) {
        return registration.checkPasswordComplexity(password);
    }

    /**
     * Delegates to {@link Registration#checkCellPhoneNumber(String)}.
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return registration.checkCellPhoneNumber(cellPhoneNumber);
    }

    /**
     * Delegates to {@link Registration#registerUser(String, String, String, String, String)}.
     */
    public String registerUser(String firstName, String lastName, String username,
                               String password, String cellPhoneNumber) {
        return registration.registerUser(firstName, lastName, username, password, cellPhoneNumber);
    }

    /**
     * Verifies that the login details entered match the details stored when
     * the user registered.
     *
     * @param username the username entered at login
     * @param password the password entered at login
     * @return true if the details match the registered user, otherwise false
     */
    public boolean loginUser(String username, String password) {
        User registeredUser = registration.getRegisteredUser();
        return registeredUser != null
                && registeredUser.getUsername().equals(username)
                && registeredUser.getPassword().equals(password);
    }

    /**
     * Returns the login status message for a successful or failed login.
     *
     * @param loginSuccessful the result of loginUser()
     * @return a welcome message for a successful login, or an error message
     *         for a failed login
     */
    public String returnLoginStatus(boolean loginSuccessful) {
        User registeredUser = registration.getRegisteredUser();
        if (loginSuccessful && registeredUser != null) {
            return "Welcome " + registeredUser.getFirstName() + ", "
                    + registeredUser.getLastName() + " it is great to see you again.";
        }
        return LOGIN_FAILURE;
    }

    /**
     * @return the registration object backing this login
     */
    public Registration getRegistration() {
        return registration;
    }
    
}
