public class EventGetHandler {
    public static Response handle(String requestBody){
        WriteJsonObject json = new WriteJsonObject();
        try{
        EventRequest eventRequest = json.deserialize(requestBody, EventRequest.class);
        Event event = Events.getEvent(eventRequest.eventId);
        return new Response(ResponseType.EVENT, json.serialize(event));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
