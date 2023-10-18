import java.io.*;
import java.net.*;


public class Main {
    public static void main(String[] args) throws IOException {
        if (Events.setUp())
            System.out.println("The Database is set up.");
        else{
            System.err.println("DB Failed to set up, closing");
            return;
        }

        ServerSocket socket = new ServerSocket(6789);
        System.out.println("The TCP server is on. Your IP is " + InetAddress.getLocalHost().getHostAddress());


        while (true) {
            System.out.println("Waiting for connections");
            Socket connectionSocket = socket.accept();
            System.out.println("Connection Accepted. IP is " + connectionSocket.getInetAddress().getHostAddress());
            new SocketThread(connectionSocket).start();
        }
    }
}