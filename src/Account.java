import java.util.ArrayList;
import java.util.List;

public class Account {
    String username;
    int authToken;
    List<Message> messageBox = new ArrayList<Message>();
    public Account(String username){
        this.username = username;
        this.authToken = Shared.index++;
    }




}
