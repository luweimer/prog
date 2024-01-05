package hsos.de.prog3.throwscorer.utility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.model.CheckoutType;

public class ConvertViewValues {
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

    public static String convertArrayList(ArrayList<String> list){
        StringBuilder output = new StringBuilder();
        list.forEach(p -> {
            output.append(p).append(" ");
        });

        return output.toString();
    }

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

    private static boolean convertSingle(StringBuilder output, int p){
        if(p < 20 && p > 0){
            output.append(p).append(" ");
            return true;
        }
        return false;
    }

    private static boolean convertDouble(StringBuilder output, int p){
        if( checkDouble( p )){
            output.append("D").append(p / 2).append(" ");
            return true;
        }
        return false;
    }

    private static boolean convertTriple(StringBuilder output, int p){
        if (checkTriple(p)) {
            output.append("T").append(p / 3).append(" ");
            return true;
        }
        return false;
    }

    private static boolean checkDouble(int point){
        return point % 2 == 0 && point <= 40;
    }
    private static boolean checkTriple(int point){
        return point % 3 == 0;
    }

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

}
