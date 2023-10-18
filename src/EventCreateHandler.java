public class EventCreateHandler {
    public static Response handle(String requestBody) {
        // handles creation of new events
        try {
            CreateEventRequest EventInfo = Json.deserialize(requestBody, CreateEventRequest.class);
            Event event = new Event(
                    0,
                    EventInfo.title,
                    EventInfo.description,
                    EventInfo.target,
                    EventInfo.balance,
                    EventInfo.deadline
            );
            event = Events.createEvent(event);
            return new Response(ResponseType.EVENT, Json.serialize(event));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
