package hsos.de.prog3.throwscorer.view.zview;

import static hsos.de.prog3.throwscorer.utility.ConvertViewValues.convertArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.listener.view.zview.PlayerViewListener;

/**
 * PlayerView
 * Stellt die einzelnen Spieler in der GameActivity dar.
 * @author Lucius Weimer
 */
public class PlayerView implements PlayerViewListener {

    private LinearLayout gamePlayer;
    private TextView tvGamePlayerScore;
    private TextView tvGamePlayerPoints;
    private TextView tvGamePlayerWin;
    private TextView tvPlayerName;

    /**
     * Konstruktor
     * Fuegt die View dem uebergeordneten GridLayout hinzu
     * @param context Kontext der App
     * @param playerHeader uebergeordnetes GridLayout
     */
    public PlayerView(Context context, GridLayout playerHeader){
        this.gamePlayer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.game_player, playerHeader, false);
        playerHeader.addView(this.gamePlayer);
        this.init();
    }

    /**
     * Initialisiert die View-Elemente
     */
    private void init(){
        this.registerViewElements();
    }

    /**
     * Registriert die View-Elemente
     * @return PlayerView
     */
    private PlayerView registerViewElements(){
        this.tvGamePlayerScore = this.gamePlayer.findViewById(R.id.tv_game_player_score);
        this.tvGamePlayerPoints = this.gamePlayer.findViewById(R.id.tv_game_player_points);
        this.tvGamePlayerWin = this.gamePlayer.findViewById(R.id.tv_game_player_win);
        this.tvPlayerName = this.gamePlayer.findViewById(R.id.tv_game_player_name);
        return this;
    }

    /**
     * Setzt den Namen des Spielers
     * @param name Name des Spielers
     */
    @Override
    public void updatePlayerName(String name) {
        this.tvPlayerName.setText(name);
    }

    /**
     * Setzt den Punktestand des Spielers
     * @param score Punktestand des Spielers
     */
    @Override
    public void updateScore(int score) {
        this.tvGamePlayerScore.setText(String.valueOf(score));
    }

    /**
     * Setzt die geworfenen Punkte des Spielers (Abbildung der 3 Wuerfe pro Runde)
     * @param points geworfene Punkte des Spielers
     */
    @Override
    public void updatePoints(ArrayList<String> points) {
        String result = convertArrayList(points);
        this.tvGamePlayerPoints.setText(result);
    }

    /**
     * Setzt die Anzahl der gewonnenen Legs und Sets des Spielers
     * @param sets Anzahl der gewonnenen Sets
     * @param legs Anzahl der gewonnenen Legs
     */
    @Override
    public void updateWin(int sets, int legs) {
        this.tvGamePlayerWin.setText("S:" + String.valueOf(sets) + " " + "L:" + String.valueOf(legs));
    }

    /**
     * Setzt die Hintergrundfarbe des Spielers, durch active
     * @param active true, wenn Spieler aktiv ist
     */
    @Override
    public void updateActivePlayer(boolean active) {
        if(active){
            this.gamePlayer.setBackgroundColor(this.gamePlayer.getResources().getColor(R.color.black));
        } else {
            this.gamePlayer.setBackgroundColor(this.gamePlayer.getResources().getColor(R.color.primary));
        }
    }
}
