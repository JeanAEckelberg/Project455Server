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
            // Sets up reader for client input, the json serializer and deserializer, and the outbound writer
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(this.csocket.getInputStream()));


            DataOutputStream outToClient = new DataOutputStream(this.csocket.getOutputStream());

            while ((clientSentence = inFromClient.readLine()) != null) {
                // takes client input line until a \n character and creates a request object from the json string
                System.out.println("Input from client: " + clientSentence);
                Request request = Json.deserialize(clientSentence, Request.class);

                // creates a None request if no request is parsable
                if (request == null)
                    request = new Request(RequestType.NONE, "");


                // sends the appropriate requests to the right handlers and returns a response object from them
                System.out.println("Request Type: " + request.requestType);
                Response returnResponse = switch (request.requestType){
                    case CREATE -> EventCreateHandler.handle(request.requestBody);
                    case EVENT -> EventGetHandler.handle(request.requestBody);
                    case EVENTS -> EventGetAllHandler.handle();
                    case DONATE -> DepositHandler.handle(request.requestBody);
                    case UPDATE -> EventUpdateHandler.handle(request.requestBody);
                    case NONE -> new Response(ResponseType.ERROR, "Invalid request format!");
                };

                // differentiates errors from standard logging and sends response to user
                if(returnResponse.responseType == ResponseType.ERROR){
                    System.err.println("ERROR: " + returnResponse.responseBody);
                } else{
                    System.out.println("RETURNING: " + returnResponse.responseBody);
                }
                outToClient.writeBytes(Json.serialize(returnResponse)+'\n');
            }

            csocket.close();
        } catch (Exception e) {
            // writes error in case of connection or critical failure
            System.err.println(e.getMessage());
        }
    }
}
