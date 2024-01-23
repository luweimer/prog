package hsos.de.prog3.throwscorer.utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.model.CheckoutType;

/**
 * ConvertViewValues
 * Utility class um Datentypen für die Darstellung in der View zu konvertieren
 * ArrayList<Integer> - Checkout String
 * ArrayList<String> - String
 * Drawable - Bitmap
 */
public class ConvertViewValues {
    /**
     * Konvertiert eine ArrayList zu einem Checkout String
     * @param list ArrayList to convert
     * @param settingsCheckout CheckoutType
     * @return Checkout String
     */
    public static String convertArrayListCheckout(ArrayList<Integer> list, CheckoutType settingsCheckout){
        StringBuilder output = new StringBuilder();
        list.forEach(p -> {
            if(p == 0){
                return;
            }

            if (convertStandartPoints(output, p)) {
                return;
            }
            switch (settingsCheckout) {
                case DOUBLE: {
                    if (convertDouble(output, p)) {
                        return;
                    } else if (convertTriple(output, p)) {
                        return;
                    } else {
                        convertSingle(output, p);
                    }
                    break;
                }
                case TRIPLE: {
                    if (convertTriple(output, p)) {
                        return;
                    } else if (convertDouble(output, p)) {
                        return;
                    } else {
                        convertSingle(output, p);
                    }
                    break;
                }
            }
        });
        return output.toString();
    }

    /**
     * Konvertiert eine ArrayList zu einem String
     * @param list ArrayList to convert
     * @return String
     */
    public static String convertArrayList(ArrayList<String> list){
        StringBuilder output = new StringBuilder();
        list.forEach(p -> {
            output.append(p).append(" ");
        });

        return output.toString();
    }

    /**
     * Konvertiert ein Drawable zu einem Bitmap
     * @param drawable Drawable to convert
     * @return Bitmap
     */
    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        return bitmap;
    }

    /**
     * Konvertiert standart Punkte
     * B - Bulls Eye
     * S - Single Bull
     * @param output StringBuilder
     * @param point Punkte
     * @return true, wenn Konvertierung erfolgreich war
     */
    private static boolean convertStandartPoints(StringBuilder output, int point){
        if(point == 50){
            output.append("B").append(" ");
            return true;
        } else if(point == 25){
            output.append("S").append(" ");
            return true;
        }
        return false;
    }

    /**
     * Konvertiert Single Punkte
     * @param output StringBuilder
     * @param p Punkte
     * @return true, wenn Konvertierung erfolgreich war
     */
    private static boolean convertSingle(StringBuilder output, int p){
        if(p < 20 && p > 0){
            output.append(p).append(" ");
            return true;
        }
        return false;
    }

    /**
     * Konvertiert Double Punkte
     * @param output StringBuilder
     * @param p Punkte
     * @return true, wenn Konvertierung erfolgreich war
     */
    private static boolean convertDouble(StringBuilder output, int p){
        if( checkDouble( p )){
            output.append("D").append(p / 2).append(" ");
            return true;
        }
        return false;
    }

    /**
     * Konvertiert Triple Punkte
     * @param output StringBuilder
     * @param p Punkte
     * @return true, wenn Konvertierung erfolgreich war
     */
    private static boolean convertTriple(StringBuilder output, int p){
        if (checkTriple(p)) {
            output.append("T").append(p / 3).append(" ");
            return true;
        }
        return false;
    }

    /**
     * Prüft ob der Punkt ein Double-Punkt sind
     * @param point Punkt zum prüfen
     * @return true, wenn Punkt ein Double-Punkt ist
     */
    private static boolean checkDouble(int point){
        return point % 2 == 0 && point <= 40;
    }

    /**
     * Prüft ob der Punkt ein Triple-Punkt sind
     * @param point Punkt zum prüfen
     * @return true, wenn Punkt ein Triple-Punkt ist
     */
    private static boolean checkTriple(int point){
        return point % 3 == 0;
    }

}
