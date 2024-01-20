package hsos.de.prog3.throwscorer.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.activity.EvaluationActivity;
import hsos.de.prog3.throwscorer.activity.GameActivity;
import hsos.de.prog3.throwscorer.activity.HomeActivity;
import hsos.de.prog3.throwscorer.activity.IndividualGameActivity;
import hsos.de.prog3.throwscorer.activity.OverviewActivity;
import hsos.de.prog3.throwscorer.activity.PlayerNameActivity;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public class Router {

    public static void startGame(Context context, GameSettings gameSettings){
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra("GameSettings", gameSettings);
        context.startActivity(intent);
    }

    public static void startPlayerNameActivity(Context context, GameSettings gameSettings){
        Intent intent = new Intent(context, PlayerNameActivity.class);
        intent.putExtra("GameSettings", gameSettings);
        context.startActivity(intent);
    }

    public static void startEvaluationActivity(Context context, int winner, ArrayList<PlayerStats> playerStats, Bitmap winnerPic){
        Intent intent = new Intent(context, EvaluationActivity.class);
        intent.putExtra("winner", winner);
        intent.putExtra("PlayerStats", new ArrayList<>(playerStats));
        intent.putExtra("winnerPic", winnerPic);
        context.startActivity(intent);
    }

    public static void startHomeActivity(Context context){
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startOverviewActivity(Context context){
        Intent intent = new Intent(context, OverviewActivity.class);
        context.startActivity(intent);
    }

    public static void startIndividualGame(Context context){
        Intent intent = new Intent(context, IndividualGameActivity.class);
        context.startActivity(intent);
    }
}
