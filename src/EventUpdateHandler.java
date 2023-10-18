public class EventUpdateHandler {
    public static Response handle(String requestBody){
        try{
            Event event = Json.deserialize(requestBody, Event.class);
            event = Events.updateEvent(event);
            return new Response(ResponseType.EVENT, Json.serialize(event));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
