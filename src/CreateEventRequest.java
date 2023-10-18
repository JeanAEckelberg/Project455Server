import java.util.Date;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateEventRequest {
    public String title;
    public String description;
    public double target;
    public String deadline;
    public double balance;

    @JsonCreator
    public CreateEventRequest(@JsonProperty("title") String title,
                              @JsonProperty("description") String description,
                              @JsonProperty("target") double target,
                              @JsonProperty("deadline") String deadline) {

        this.title = title;
        this.description = description;
        if (target > 0)
            this.target = target;
        else
            this.target = Math.abs(target);

        this.deadline = deadline;

        this.balance = 0.0;

    }
}