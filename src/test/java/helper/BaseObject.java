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

    /**
     * Wait for seconds
     * @param seconds count of seconds
     */
    public void waitForSeconds(String seconds){
        try {
            Thread.sleep(Integer.parseInt(seconds) * 1000L);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Add API call response into test variables
     * @param response API call response, json format string
     */
    public static void addResponseToTestVariables(String response){
        JSONObject obj = new JSONObject(response);
        Iterator<String> itr = obj.keys();
        while (itr.hasNext()){
            String key = itr.next();
            setVariable(key, obj.get(key));
        }
    }

    /**
     * Parse json format string into Hashmap type
     * @param jsonString json format string
     * @return HashMap key value pairs
     */
    public static HashMap<String, Object> jsonToHashmap(String jsonString){
        HashMap<String, Object> retMap = new HashMap<>();
        JSONObject json = new JSONObject(jsonString);
        if(!json.equals(JSONObject.NULL)) {
            retMap = toMap(json);
        }
        return retMap;
    }

    /**
     * Parse JSONObject value to HashMap value
     * @param object JSONObject value
     * @return HashMap value
     * @throws JSONException
     */
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

    /**
     * Parse JSONArray value into List value
     * @param array JSONArray value
     * @return List value
     * @throws JSONException
     */
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

    /**
     * Get test values by key
     * @param key of the value
     * @return key mapping value
     */
    public static String getVariableByKey(String key){
        String value = variables.get(key).toString();
        logger.info("Get Test Value '"+key+"' : " + value);
        return value;
    }

    /**
     * Set test values, key value pairs
     * @param key key string
     * @param val value
     */
    public static void setVariable(String key, Object val){
        variables.put(key,val);
    }
}
