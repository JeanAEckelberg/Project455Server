import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    // json serialization/deserialization abstraction
    private static final ObjectMapper objMap = new ObjectMapper();

    public static String serialize(Object Object) {
        try {
            return objMap.writeValueAsString(Object);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T deserialize(String json, Class<T> obj) {
        try {
            return objMap.readValue(json, obj);
        } catch (Exception e) {
            return null;
        }
    }
}
