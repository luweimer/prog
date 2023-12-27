package hsos.de.prog3.throwscorer.utility;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.activity.EvaluationActivity;
import hsos.de.prog3.throwscorer.activity.GameActivity;
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

    public static void startEvaluationActivity(Context context, int winner, ArrayList<PlayerStats> playerStats){
        Intent intent = new Intent(context, EvaluationActivity.class);
        intent.putExtra("winner", winner);
        intent.putExtra("PlayerStats", new ArrayList<>(playerStats));
        //intent.putExtra("GameSettings", gameSettings);
        context.startActivity(intent);

    }
}
