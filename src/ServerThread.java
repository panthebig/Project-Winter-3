import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
    private Socket socket;
    //private Fibonacci fib;
    String clientIP;

    public ServerThread(Socket socket) {
        super();
        this.socket = socket;
        clientIP = socket.getInetAddress().getHostAddress();
        //Account acc = new Account();
        //fib = new Fibonacci();
    }

    private String handleInput(String input) {
        try {
            String[] inputArr = input.split(" ");
            switch (inputArr[2]){
                case "1":{

                    //check if user already exists
                    for (Account acc :
                            Shared.users) {
                        if (acc.username.equals(inputArr[3])){
                            return "Sorry, the user already exists.";
                        }
                    }

                    //check username validity
                    if(! inputArr[3].matches("(([a-zA-Z]+)|_)*")){
                        return "Invalid Username";
                    }

                    //create new account
                    Account acc = new Account(inputArr[3]);
                    Shared.users.add(acc);
                    return String.valueOf(acc.authToken);
                    //java client <ip> <port number> 1 <username>
                    //return authToken;
                }
                case "2":{

                    for (Account acc :
                            Shared.users) {
                        //check authtoken
                        if (acc.authToken == Integer.parseInt(inputArr[3])){
                            List<String> usernameList= new ArrayList<>();
                            String usernames;
                            int i=0;
                            for (Account acc1 :
                                    Shared.users) {
                                i++;
                                usernameList.add(i +". "+ acc1.username);
                            }
                            usernames = String.join("\t",usernameList);
                            System.out.println(usernames);
                            return usernames;
                        }
                    }
                    return "Invalid Auth Token";

                    //java client <ip> <port number> 2 <authToken>

                }
                case "3":{
                    boolean foundAuth=false;
                    String sender="";
                    String recipient = inputArr[3];
                    String message = inputArr[6];
                    for (Account acc :
                            Shared.users) {
                        if (acc.authToken == Integer.parseInt(inputArr[3])) {
                            foundAuth = true;
                            sender = acc.username;
                        }
                    }
                    if(!foundAuth){
                        return "Invalid Auth Token";
                    }

                    for (Account acc :
                            Shared.users) {

                        if(acc.username.equals(recipient)){
                            acc.messageBox.add( new Message(sender,recipient,message) );
                            return "OK";
                        }

                    }
                    return "User does not exist";
                }
                    //java client <ip> <port number> 3 <authToken> <recipient> <message_body>
                case "4":{

                    for (Account acc :
                            Shared.users) {
                        if (acc.authToken == Integer.parseInt(inputArr[3])) {

                            List<String> msgList= new ArrayList<>();
                            String messages;
                            String i;
                            for (Message msg :
                                    acc.messageBox) {
                                if(msg.isRead)
                                    i = "";
                                else
                                    i = "*";

                                msgList.add(msg.messageId +". from: "+ msg.sender + i );
                            }

                            messages = String.join("\t",msgList);
                            return messages;

                        }
                    }
                    return "Invalid Auth Token";




                }
                    //java client <ip> <port number> 4 <authToken>

                case "5":{


                    boolean foundAuth=false;
                    for (Account acc :
                            Shared.users) {
                        if (acc.authToken == Integer.parseInt(inputArr[3])) {
                            foundAuth = true;

                            for (Message msg :
                                    acc.messageBox) {

                                if (msg.messageId == Integer.parseInt(inputArr[4])) {
                                    return msg.body;
                                }
                            }


                        }
                    }
                    if(!foundAuth){
                        return "Invalid Auth Token";
                    }else {
                        return "Message ID does not exist";
                    }

                    //java client <ip> <port number> 5 <authToken> <message_id>
                }

                case "6":{

                    boolean foundAuth=false;
                    for (Account acc :
                            Shared.users) {
                        if (acc.authToken == Integer.parseInt(inputArr[3])) {
                            foundAuth = true;

                            for (Message msg :
                                    acc.messageBox) {

                                if (msg.messageId == Integer.parseInt(inputArr[4])) {
                                    acc.messageBox.remove(msg);
                                    return "OK";
                                }
                            }


                        }
                    }
                    if(!foundAuth){
                        return "Invalid Auth Token";
                    }else {
                        return "Message does not exist";
                    }


                    //java client <ip> <port number> 6 <authToken> <message_id>

                }

                default:
                    System.out.println("Make sure you used the correct FN_ID(1-6)");
                    return null;
            }
        }catch (Exception e){
            System.out.println("Please follow the correct input format.");
        }

        /*if (input.toLowerCase().equals("y")) {
            String nextNumber = String.valueOf(fib.getNext());
            return nextNumber;
        }*/
        return null;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));


            String input = in.readLine();
            String response = handleInput(input);

            /*
            String input = in.readLine();
            while (true) {
                String response = handleInput(input);
                // continue if the user wants to continue
                // or close the connection
                if (response != null ) out.println(response);
                else break;
            }*/

            out.println(response);

            // send the first number
            /*int nextFibNumber = fib.getNext();
            out.println(nextFibNumber);
            while (true) {
                String input = in.readLine();
                String response = handleInput(input);
                // continue if the user wants to continue
                // or close the connection
                if (response != null) out.println(response);
                else break;
            }*/
            out.close();
            in.close();
            socket.close();
            System.out.println("Terminating connection. Client: " + clientIP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}