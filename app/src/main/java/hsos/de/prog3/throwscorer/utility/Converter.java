package hsos.de.prog3.throwscorer.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class Converter {

    public static String hashMapToJson(HashMap<String, Integer> hashMap){
        JSONObject json = new JSONObject(hashMap);
        return json.toString();
    }


    //Quelle: https://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
    public static HashMap<String, Integer> jsonToHashMap(String jsonStr){
        HashMap<String, Integer> hashMap = new HashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                int value = jsonObject.getInt(key);
                hashMap.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hashMap;
    }

}
