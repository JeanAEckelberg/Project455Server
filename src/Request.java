import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Request {
    // request object sent from the client to the server and give serialization/deserialization support
    public RequestType requestType;
    public String requestBody;

    @JsonCreator
    public Request(@JsonProperty("RequestType") RequestType type, @JsonProperty("RequestBody") String requestBody) {
        this.requestType = type;
        this.requestBody = requestBody;
    }

}
