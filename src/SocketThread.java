import java.io.*;
import java.net.*;

public class SocketThread extends Thread {
    Socket csocket;
    String clientSentence;
    String capitalizedSentence;

    public SocketThread(Socket csocket) {
        this.csocket = csocket;
    }

    private void menu(BufferedReader in, DataOutputStream out){
        try {
            out.writeBytes("Welcome to FundMeNow!\n\n1. Create an event\n2. List Events\n3. Select Event\n4. Quit\n\nEnter a number corresponding to a selection:\n");
            String selectedOption = in.readLine();
        }  catch (java.io.IOException e){
            System.err.println(e);
        }
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
