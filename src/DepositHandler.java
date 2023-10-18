public class DepositHandler {
    public static Response handle(String requestBody){
        // handles donations/deposits
        try{
            DonateRequest donateRequest = Json.deserialize(requestBody, DonateRequest.class);
            Event event = Events.getEvent(donateRequest.eventid);
            event.deposit(donateRequest.amount);
            event = Events.updateEvent(event);
            return new Response(ResponseType.EVENT, Json.serialize(event));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
