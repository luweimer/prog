package hsos.de.prog3.throwscorer.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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

    public static String BitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap Base64ToBitmap(String base64){
        byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
