
import com.st10500782.chatapp.Message;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
   @Test
    public void testMessageLengthSuccess() {
        Message msg = andGetInstance();
        String result = msg.checkMessageLength("Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testRecipientCellSuccess() {
        Message msg = new Message();
        String result = msg.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message();
        String hash = msg.createMessageHash("1234567890", 0, "Hi Tonight");
        assertEquals("12:0:HITONIGHT", hash);
    }

    @Test
    public void testSentMessageActions() {
        Message msg = new Message();
        assertEquals("Message successfully sent", msg.SentMessage(1));
        assertEquals("Press 0 to delete the message", msg.SentMessage(2));
        assertEquals("Message successfully stored", msg.SentMessage(3));
    }

    private Message andGetInstance() {
        return new Message();
    } 
    
}
