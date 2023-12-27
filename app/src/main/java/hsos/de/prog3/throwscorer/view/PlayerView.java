package hsos.de.prog3.throwscorer.view;

import static hsos.de.prog3.throwscorer.utility.ConvertView.convertArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.listener.view.PlayerViewListener;
import hsos.de.prog3.throwscorer.model.Player;

public class PlayerView implements PlayerViewListener {

    private LinearLayout gamePlayer;
    private TextView tvGamePlayerScore;
    private TextView tvGamePlayerPoints;
    private TextView tvGamePlayerWin;
    private TextView tvPlayerName;

    public PlayerView(Context context, GridLayout playerHeader){
        this.gamePlayer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.game_player, playerHeader, false);
        playerHeader.addView(this.gamePlayer);
        this.init();
    }

    private void init(){
        this.registerViewElements();
    }

    private PlayerView registerViewElements(){
        this.tvGamePlayerScore = this.gamePlayer.findViewById(R.id.tv_game_player_score);
        this.tvGamePlayerPoints = this.gamePlayer.findViewById(R.id.tv_game_player_points);
        this.tvGamePlayerWin = this.gamePlayer.findViewById(R.id.tv_game_player_win);
        this.tvPlayerName = this.gamePlayer.findViewById(R.id.tv_game_player_name);
        return this;
    }

    @Override
    public void updatePlayerName(String name) {
        this.tvPlayerName.setText(name);
    }

    @Override
    public void updateScore(int score) {
        this.tvGamePlayerScore.setText(String.valueOf(score));
    }

    @Override
    public void updatePoints(ArrayList<String> points) {
        String result = convertArrayList(points);
        this.tvGamePlayerPoints.setText(result);
    }

    @Override
    public void updateWin(int sets, int legs) {
        this.tvGamePlayerWin.setText("S:" + String.valueOf(sets) + " " + "L:" + String.valueOf(legs));
    }

    @Override
    public void updateActivePlayer(boolean active) {
        if(active){
            this.gamePlayer.setBackgroundColor(this.gamePlayer.getResources().getColor(R.color.black));
        } else {
            this.gamePlayer.setBackgroundColor(this.gamePlayer.getResources().getColor(R.color.primary));
        }
    }
}
