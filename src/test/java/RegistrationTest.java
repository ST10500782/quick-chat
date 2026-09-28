
import com.st10500782.chatapp.Registration;
import com.st10500782.chatapp.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
public class RegistrationTest {
     private Registration registration;

    @BeforeEach
    public void setUp() {
        registration = new Registration();
    }

    // -------------------- assertTrue / assertFalse tests --------------------

    @Test
    @DisplayName("Username correctly formatted returns true")
    public void testCheckUserNameValid() {
        assertTrue(registration.checkUserName("kyl_1"));
    }

    @Test
    @DisplayName("Username incorrectly formatted returns false")
    public void testCheckUserNameInvalid() {
        assertFalse(registration.checkUserName("kyle!!!!!!!"));
    }

    @Test
    @DisplayName("Password meeting complexity requirements returns true")
    public void testCheckPasswordComplexityValid() {
        assertTrue(registration.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    @DisplayName("Password not meeting complexity requirements returns false")
    public void testCheckPasswordComplexityInvalid() {
        assertFalse(registration.checkPasswordComplexity("password"));
    }

    @Test
    @DisplayName("Cell phone number correctly formatted returns true")
    public void testCheckCellPhoneNumberValid() {
        assertTrue(registration.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    @DisplayName("Cell phone number incorrectly formatted returns false")
    public void testCheckCellPhoneNumberInvalid() {
        assertFalse(registration.checkCellPhoneNumber("08966553"));
    }

    // -------------------- assertEquals tests --------------------

    @Test
    @DisplayName("All details valid - success messages returned and user stored")
    public void testRegistrationSuccessMessages() {
        String message = registration.registerUser("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username successfully captured.\nPassword successfully captured."
                + "\nCell phone number successfully added.", message);
        assertTrue(registration.isRegistered());

        User user = registration.getRegisteredUser();
        assertNotNull(user);
        assertEquals("Kyle", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("kyl_1", user.getUsername());
        assertEquals("Ch&&sec@ke99!", user.getPassword());
        assertEquals("+27838968976", user.getCellPhoneNumber());
    }

    @Test
    @DisplayName("Username incorrectly formatted - error message returned")
    public void testIncorrectUsernameMessage() {
        String message = registration.registerUser("Kyle", "Smith", "kyle!!!!!!!",
                "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted; please ensure that your "
                + "username contains an underscore and is no more than five characters "
                + "in length.\nPassword successfully captured.\nCell phone number "
                + "successfully added.", message);
        assertNull(registration.getRegisteredUser());
    }

    @Test
    @DisplayName("Password does not meet complexity requirements - error message returned")
    public void testPasswordErrorMessage() {
        String message = registration.registerUser("Kyle", "Smith", "kyl_1",
                "password", "+27838968976");
        assertEquals("Username successfully captured.\nPassword is not correctly "
                + "formatted; please ensure that the password contains at least eight "
                + "characters, a capital letter, a number, and a special character."
                + "\nCell phone number successfully added.", message);
        assertNull(registration.getRegisteredUser());
    }

    @Test
    @DisplayName("Cell phone incorrectly formatted - error message returned")
    public void testCellPhoneErrorMessage() {
        String message = registration.registerUser("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "08966553");
        assertEquals("Username successfully captured.\nPassword successfully captured."
                + "\nCell phone number incorrectly formatted or does not contain "
                + "international code.", message);
        assertNull(registration.getRegisteredUser());
    }
}
