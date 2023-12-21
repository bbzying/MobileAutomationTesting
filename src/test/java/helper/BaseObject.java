package helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class BaseObject {
    private static final Logger logger = LogManager.getLogger(BaseObject.class);
    public static Properties props = new Properties();
    static {
        try {
            props.load(BaseObject.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static JSONObject variables = new JSONObject();

    public BaseObject() {
    }

    public void waitForSeconds(String seconds){
        try {
            Thread.sleep(Integer.parseInt(seconds) * 1000L);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addResponseToTestVariables(String response){
        JSONObject obj = new JSONObject(response);
        Iterator<String> itr = obj.keys();
        while (itr.hasNext()){
            String key = itr.next();
            setVariable(key, obj.get(key));
        }
    }

    public static HashMap<String, Object> jsonToHashmap(String jsonString){
        HashMap<String, Object> retMap = new HashMap<>();
        JSONObject json = new JSONObject(jsonString);
        if(!json.equals(JSONObject.NULL)) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static HashMap<String, Object> toMap(JSONObject object) throws JSONException {
        HashMap<String, Object> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


    public static String getVariableByKey(String key){
        String value = variables.get(key).toString();
        logger.info("Get Test Value '"+key+"' : " + value);
        return value;
    }

    public static void setVariable(String key, Object val){
        variables.put(key,val);
    }
}
