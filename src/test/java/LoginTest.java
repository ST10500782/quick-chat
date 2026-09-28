
import com.st10500782.chatapp.Login;
import com.st10500782.chatapp.Registration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TshepoMahudu
 */
public class LoginTest {
    private Login login;

    @BeforeEach
    public void setUp() {
        Registration registration = new Registration();
        registration.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login = new Login(registration);
    }

    // -------------------- assertTrue / assertFalse tests --------------------

    @Test
    @DisplayName("Login successful returns true")
    public void testLoginUserSuccessful() {
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    @DisplayName("Login failed (wrong password) returns false")
    public void testLoginUserFailedWrongPassword() {
        assertFalse(login.loginUser("kyl_1", "wrongPassword1!"));
    }

    @Test
    @DisplayName("Login failed (wrong username) returns false")
    public void testLoginUserFailedWrongUsername() {
        assertFalse(login.loginUser("kyle!!!!!!!", "Ch&&sec@ke99!"));
    }

    // -------------------- assertEquals tests --------------------

    @Test
    @DisplayName("Username is correctly formatted - welcome message returned")
    public void testCorrectUsernameWelcomeMessage() {
        boolean loggedIn = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle, Smith it is great to see you again.",
                login.returnLoginStatus(loggedIn));
    }

    @Test
    @DisplayName("Failed login - error message returned")
    public void testFailedLoginMessage() {
        boolean loggedIn = login.loginUser("kyl_1", "wrongPassword1!");
        assertEquals("Username or password incorrect, please try again.",
                login.returnLoginStatus(loggedIn));
    }

    // ---------- POE-specified methods delegated to Registration ----------

    @Test
    @DisplayName("Login class still exposes the POE validation methods")
    public void testDelegatedValidationMethods() {
        assertTrue(login.checkUserName("kyl_1"));
        assertFalse(login.checkUserName("kyle!!!!!!!"));
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertFalse(login.checkPasswordComplexity("password"));
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}
