package Serveur;

/**
 * Created by p1307887 on 13/03/2017.
 */
public class Message {

    private int messageId;
    private String messageTxt;

    public Message(int messageId, String messageTxt) {
        this.messageId = messageId;
        this.messageTxt = messageTxt;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMessageTxt() {
        return messageTxt;
    }
}
