import java.io.*;
import java.net.*;

public class SocketThread extends Thread {
    Socket csocket;
    String clientSentence;

    public SocketThread(Socket csocket) {
        this.csocket = csocket;
    }

    public void run() {
        try {

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(this.csocket.getInputStream()));

            WriteJsonObject json = new WriteJsonObject();

            DataOutputStream outToClient = new DataOutputStream(this.csocket.getOutputStream());

            while ((clientSentence = inFromClient.readLine()) != null) {
                System.out.println("Input from client: " + clientSentence);
                Request request = json.deserialize(clientSentence, Request.class);
                System.out.println("Request Type: " + request.requestType);
                Response returnResponse = switch (request.requestType){
                    case CREATE -> EventCreateHandler.handle(request.requestBody);
                    case EVENT -> EventGetHandler.handle(request.requestBody);
                    case EVENTS -> EventGetAllHandler.handle();
                    case DONATE -> DepositHandler.handle(request.requestBody);
                    case UPDATE -> EventUpdateHandler.handle(request.requestBody);
                    default -> new Response(ResponseType.ERROR, "No Handler present!!!");
                };
                if(returnResponse.responseType == ResponseType.ERROR){
                    System.err.println("ERROR: " + returnResponse.responseBody);
                } else{
                    System.out.println("RETURNING: " + returnResponse.responseBody);
                }
                outToClient.writeBytes(json.serialize(returnResponse)+'\n');
            }

            csocket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
