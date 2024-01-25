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
import hsos.de.prog3.throwscorer.activity.SplashActivity;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * Router
 * Utility class um Activities zu starten
 * Activity zum starten: GameActivity, PlayerNameActivity, EvaluationActivity, HomeActivity, OverviewActivity, IndividualGameActivity
 * Autor: Lucius Weimer
 */
public class Router {

    public static void startHome(Context context){
        Intent mainIntent = new Intent(context, HomeActivity.class);
        context.startActivity(mainIntent);
    }

    /**
     * Startet die GameActivity
     * @param context Context der aktuellen Activity
     * @param gameSettings GameSettings für das Spiel
     */
    public static void startGame(Context context, GameSettings gameSettings){
        Intent intent = new Intent(context, GameActivity.class);
        if (gameSettings == null) {
            Log.e("Router", "GameSettings is null");
            return;
        }
        intent.putExtra("GameSettings", gameSettings);
        context.startActivity(intent);
    }

    /**
     * Startet die PlayerNameActivity
     * @param context Context der aktuellen Activity
     * @param gameSettings GameSettings für das Spiel
     */
    public static void startPlayerNameActivity(Context context, GameSettings gameSettings){
        Intent intent = new Intent(context, PlayerNameActivity.class);
        if (gameSettings == null) {
            Log.e("Router", "GameSettings is null");
            return;
        }
        intent.putExtra("GameSettings", gameSettings);
        context.startActivity(intent);
    }

    /**
     * Startet die EvaluationActivity
     * @param context Context der aktuellen Activity
     * @param winner Gewinner des Spiels
     * @param playerStats Spielerstatistiken
     * @param winnerPic Siegesbild
     */
    public static void startEvaluationActivity(Context context, int winner, ArrayList<PlayerStats> playerStats, Bitmap winnerPic){
        Intent intent = new Intent(context, EvaluationActivity.class);
        if(playerStats == null || winnerPic == null || playerStats.isEmpty()){
            Log.e("Router", "Unable to start EvaluationActitvity");
            return;
        }
        intent.putExtra("winner", winner);
        intent.putExtra("PlayerStats", new ArrayList<>(playerStats));
        intent.putExtra("winnerPic", winnerPic);
        context.startActivity(intent);
    }

    /**
     * Startet die HomeActivity
     * @param context Context der aktuellen Activity
     */
    public static void startHomeActivity(Context context){
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Startet die OverviewActivity
     * @param context Context der aktuellen Activity
     */
    public static void startOverviewActivity(Context context){
        Intent intent = new Intent(context, OverviewActivity.class);
        context.startActivity(intent);
    }

    /**
     * Startet die IndividualGameActivity
     * @param context Context der aktuellen Activity
     */
    public static void startIndividualGame(Context context){
        Intent intent = new Intent(context, IndividualGameActivity.class);
        context.startActivity(intent);
    }
}
