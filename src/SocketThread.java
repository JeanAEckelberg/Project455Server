import java.net.*;

public class SocketThread extends Thread {
    DatagramSocket socket;
    String clientSentence;
    InetAddress IP;
    int port;

    public SocketThread(DatagramSocket socket, String data, InetAddress IP, int port) {
        this.socket = socket;
        this.clientSentence = data;
        this.IP = IP;
        this.port = port;
    }

    public void run() {
        try {
            // takes client input line until a \n character and creates a request object from the json string
            System.out.println("Input from client: " + clientSentence);
            Request request = Json.deserialize(clientSentence, Request.class);

            // creates a None request if no request is parsable
            if (request == null)
                request = new Request(RequestType.NONE, "");


            // sends the appropriate requests to the right handlers and returns a response object from them
            System.out.println("Request Type: " + request.requestType);
            Response returnResponse = request.requestType.handle(request.requestBody);

            // differentiates errors from standard logging and sends response to user
            if(returnResponse.responseType == ResponseType.ERROR){
                System.err.println("ERROR: " + returnResponse.responseBody);
            } else{
                System.out.println("RETURNING: " + returnResponse.responseBody);
            }
            byte[] bytes = (Json.serialize(returnResponse)+'\n').getBytes();

            DatagramPacket sendPacket = new DatagramPacket(bytes, bytes.length, IP, port);

            socket.send(sendPacket);
        } catch (Exception e) {
            // writes error in case of connection or critical failure
            System.err.println(e.getMessage());
        }
    }
}
