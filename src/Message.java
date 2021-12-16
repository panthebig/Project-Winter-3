public class Message {
    boolean isRead = false;
    String sender;
    String receiver;
    String body;
    int messageId = Shared.messageId++;

    public Message(String sender , String receiver , String body ){
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
    }
}
