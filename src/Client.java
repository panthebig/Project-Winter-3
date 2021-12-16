import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        //if (args.length==6 or 5 or 4 send them to inputarray in server thread)
        //readline push to server thread 4-5 <FN_ID> <username>
        try {
            // the first argument is the ip address of the server
            // while the second one is its port
            socket = new Socket(args[0], Integer.parseInt(args[1]));
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.err.println("Could not initiate a connection to server");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser=String.join(" ",args);
        out.println(fromUser);

        fromServer = in.readLine();

        if(args[2].equals("2") || args[2].equals("4")){
            List<String> usernameList= Arrays.asList(fromServer.split("\t"));       //TODO : unique line separator
            System.out.println(String.join("\n",usernameList));
        }else {
            System.out.println(fromServer);

        }

        /*
        while ((fromServer = in.readLine()) != null) {
            System.out.println(fromServer);
            fromServer = in.readLine();
            System.out.println(fromServer);

        }*/
        /*while ((fromServer = in.readLine()) != null) {
            fiboSeries.add(Integer.parseInt(fromServer));
            System.out.println("Server: " + fiboSeries.toString() + ". Continue?[y/n]");
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);
            }
            // if the user does not want to continue exit the main loop
            if (!fromUser.equals("y")) break;
        }*/

        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}