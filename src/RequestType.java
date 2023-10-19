import java.util.function.Function;

public enum RequestType {
    // request types enum for organization

    NONE((String) -> new Response(ResponseType.ERROR, "Invalid request format!")),
    EVENT(EventGetHandler::handle),
    EVENTS((String) -> EventGetAllHandler.handle()),
    CREATE(EventCreateHandler::handle),
    DONATE(DepositHandler::handle),
    UPDATE(EventUpdateHandler::handle);

    private final Function<String, Response> handler;

    RequestType(Function<String, Response> handler) {
        this.handler = handler;
    }

    public Response handle(String requestBody){
        return this.handler.apply(requestBody);
    }

}