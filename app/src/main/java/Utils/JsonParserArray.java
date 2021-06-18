package Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParserArray {
    public static <T> T fromJsonFromArray(String json, Class<T> classOfT) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, classOfT);
    }
}
