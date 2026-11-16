
import com.st10500782.chatapp.Message;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class MessageTest {
    private Message message;

    @BeforeEach
    public void setUp() {
        message = new Message();
        // Populate test data corresponding to POE guidelines
        message.SentMessage(1, "1234567890", "+27834557896", "Did you get the cake?", "12:0:DIDCAKE");
        message.SentMessage(3, "1234567891", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "12:1:WHEREETIME");
        message.SentMessage(2, "1234567892", "+27834484567", "Yohoooo, I am at your gate.", "12:2:YOHOOOGATE");
        message.SentMessage(1, "0838884567", "0838884567", "It is dinner time!", "08:3:ITTIME");
        message.SentMessage(3, "1234567894", "+27838884567", "Ok, I am leaving without you.", "12:4:OKYOU");
    }
    
   @Test
    public void testSentMessagesArrayPopulation() {
        List<String> sent = Message.getSentMessages();
        assertTrue(sent.contains("Did you get the cake?"));
        assertTrue(sent.contains("It is dinner time!"));
    }
    
    @Test
    public void testDisplayLongestMessage() {
        String longest = message.getLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }
    
    @Test
    public void testSearchByMessageID() {
        String result = message.searchByMessageID("0838884567");
        assertFalse(result.contains("It is dinner time!"));
    }
    
    @Test
    public void testSearchByRecipient() {
        List<String> recipientMsgs = message.searchByRecipient("+27838884567");
        assertTrue(recipientMsgs.contains("Where are you? You are late! I have asked you to be on time."));
        assertFalse(recipientMsgs.contains("Ok, I am leaving without you."));
    }
    
    @Test
    public void testDeleteByHash() {
        boolean deleted = message.deleteByHash("12:1:WHEREETIME");
        assertTrue(deleted);
    }

    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message();
        String result = msg.checkRecipientCell("+277186930021");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message();
        String hash = msg.createMessageHash("1234567890", 0, "Hi Tonight");
        assertEquals("12:0:HITONIGHT", hash);
    }
    private Message andGetInstance() {
        return new Message();
    } 
    
}
