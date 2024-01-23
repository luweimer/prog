package hsos.de.prog3.throwscorer.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Converter
 * Utility class um Datentypen zu konvertieren
 * HaspMap - JSON
 * Bitmap - Base64
 * Autor: Lucius Weimer
 */
public class Converter {

    /**
     * Konvertiert eine HashMap zu einem JSON String
     * @param hashMap HashMap to convert
     * @return JSON String
     */
    public static String hashMapToJson(HashMap<String, Integer> hashMap){
        JSONObject json = new JSONObject(hashMap);
        return json.toString();
    }

    /**
     * Konvertiert einen JSON String zu einer HashMap
     * Quelle: https://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
     * @param jsonStr JSON String to convert
     * @return HashMap
     */
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

    /**
     * Konvertiert eine Bitmap zu einem Base64 String
     * @param bitmap Bitmap to convert
     * @return Base64 String
     */
    public static String BitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Konvertiert ein base64 String zu einer Bitmap
     * @param base64 base64 String
     * @return Bitmap
     */
    public static Bitmap Base64ToBitmap(String base64){
        byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
