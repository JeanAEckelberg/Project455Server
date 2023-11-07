import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void main(String[] args) throws IOException {
        byte[] receive = new byte[65535];

        DatagramPacket DpReceive;
        // this will connect to a local db and create a new one if necessary
        if (Events.setUp())
            System.out.println("The Database is set up.");
        else{
            System.err.println("DB Failed to set up, closing");
            return;
        }

        // Opens the server to constantly listen for requests
        try (DatagramSocket socket = new DatagramSocket(6789)) {
            System.out.println("The UDP server is on. Your IP is " + InetAddress.getLocalHost().getHostAddress());

            while (true) {
                // Continuously waits for new connections and launches a new thread to handle the processing
                System.out.println("Waiting for connections");
                DpReceive = new DatagramPacket(receive, receive.length);
                socket.receive(DpReceive);
                System.out.println("Connection Accepted. IP is " + DpReceive.getAddress());
                new SocketThread(socket, new String(receive, StandardCharsets.UTF_8), DpReceive.getAddress(), DpReceive.getPort()).start();
            }
        }
    }
}