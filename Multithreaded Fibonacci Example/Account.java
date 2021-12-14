import java.util.ArrayList;
import java.util.List;

public class Account {
    String username;
    int authToken;
    List<Message> messageBox = new ArrayList<Message>();
    public Account(){

    }



    public int createAccount(String username , int authToken){
        this.username = username;
        this.authToken = authToken;
        return authToken;
    }


}
