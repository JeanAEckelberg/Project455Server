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
        System.out.println("The TCP server is on.");

        while (true) {
            Socket connectionSocket = socket.accept();
            new SocketThread(connectionSocket).start();
        }
    }
}