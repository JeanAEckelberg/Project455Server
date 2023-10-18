public class EventUpdateHandler {
    public static Response handle(String requestBody){
        WriteJsonObject json = new WriteJsonObject();
        try{
            Event event = json.deserialize(requestBody, Event.class);
            event = Events.updateEvent(event);
            return new Response(ResponseType.EVENT, json.serialize(event));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
