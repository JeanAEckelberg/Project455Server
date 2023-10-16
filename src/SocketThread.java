import java.io.*;
import java.net.*;

public class SocketThread extends Thread {
    Socket csocket;
    String clientSentence;
    String capitalizedSentence;

    public SocketThread(Socket csocket) {
        this.csocket = csocket;
    }

    public void run() {
        try {

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(csocket.getInputStream()));



            DataOutputStream outToClient = new DataOutputStream(csocket.getOutputStream());

            clientSentence = inFromClient.readLine();

            capitalizedSentence = clientSentence.toUpperCase() + '\n';

            outToClient.writeBytes(capitalizedSentence);
            csocket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
