package Utils;

import com.google.gson.Gson;

public class ObjectParserToJson {
    public static String fromObjectFromJson(Object obj){
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }
}
