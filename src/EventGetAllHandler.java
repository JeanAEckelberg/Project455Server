import java.util.ArrayList;

public class EventGetAllHandler {
    public static Response handle(){
        // Handles getting all events
        try{
            ArrayList<Event> events = Events.getEvents();
            return new Response(ResponseType.EVENTS, Json.serialize(events));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
