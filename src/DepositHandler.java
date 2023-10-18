public class DepositHandler {
    public static Response handle(String requestBody){
        WriteJsonObject json = new WriteJsonObject();
        try{
            DonateRequest donateRequest = json.deserialize(requestBody, DonateRequest.class);
            Event event = Events.getEvent(donateRequest.eventid);
            event.deposit(donateRequest.amount);
            event = Events.updateEvent(event);
            return new Response(ResponseType.EVENT, json.serialize(event));
        } catch (Exception e){
            return new Response(ResponseType.ERROR, e.getMessage());
        }
    }
}
