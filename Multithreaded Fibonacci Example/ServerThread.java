import java.net.*;
import java.io.*;
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
                case "1":
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
                case "2":
                    //java client <ip> <port number> 1 <username>
                    break;
                case "3":
                    //java client <ip> <port number> 1 <username>
                    break;
                case "4":
                    //java client <ip> <port number> 1 <username>
                    break;
                case "5":
                    //java client <ip> <port number> 1 <username>
                    break;
                case "6":
                    //java client <ip> <port number> 1 <username>
                    break;
                default:
                    System.out.println("Make sure you used the correct FN_ID");
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