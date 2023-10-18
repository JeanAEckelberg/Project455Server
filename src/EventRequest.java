import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EventRequest {
    // Request object to get an event
    public int eventId;

    @JsonCreator
    public EventRequest(@JsonProperty("id") int id) {
        this.eventId = id;
    }

}
