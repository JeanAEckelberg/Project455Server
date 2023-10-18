import java.util.ArrayList;

public class EventGetAllHandler {
    public static Response handle(){
        WriteJsonObject json = new WriteJsonObject();
        try{
            ArrayList<Event> events = Events.getEvents();
            return new Response(ResponseType.EVENTS, json.serialize(events));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
