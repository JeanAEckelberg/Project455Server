import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    // general response object to send to clients with json support
    public ResponseType responseType;
    public String responseBody;

    @JsonCreator
    public Response(@JsonProperty("ResponseType") ResponseType responseType, @JsonProperty("ResponseBody") String responseBody){
        this.responseType = responseType;
        this.responseBody = responseBody;
    }
}
