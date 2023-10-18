import com.fasterxml.jackson.databind.ObjectMapper;

public class WriteJsonObject {
    private ObjectMapper objMap = new ObjectMapper();

    public String serialize(Object Object) {
        try {
            return this.objMap.writeValueAsString(Object);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T deserialize(String json, Class<T> obj) {
        try {
            return this.objMap.readValue(json, obj);
        } catch (Exception e) {
            return null;
        }
    }
}
