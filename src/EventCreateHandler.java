import java.text.ParseException;

public class EventCreateHandler {
    public static Response handle(String requestBody) {
        System.out.println("RequestBody: " + requestBody);
        WriteJsonObject json = new WriteJsonObject();
        try {
            CreateEventRequest EventInfo = json.deserialize(requestBody, CreateEventRequest.class);
            Event event = new Event(
                    0,
                    EventInfo.title,
                    EventInfo.description,
                    EventInfo.target,
                    EventInfo.balance,
                    EventInfo.deadline
            );
            event = Events.createEvent(event);
            return new Response(ResponseType.EVENT, json.serialize(event));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
