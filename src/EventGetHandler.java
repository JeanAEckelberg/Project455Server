public class EventGetHandler {
    public static Response handle(String requestBody){
        // handles getting an event
        try{
            EventRequest eventRequest = Json.deserialize(requestBody, EventRequest.class);
            Event event = Events.getEvent(eventRequest.eventId);
            return new Response(ResponseType.EVENT, Json.serialize(event));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
