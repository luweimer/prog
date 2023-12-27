package hsos.de.prog3.throwscorer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.controller.EvaluationController;
import hsos.de.prog3.throwscorer.listener.activity.EvaluationActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.EvaluationControllerListener;
import hsos.de.prog3.throwscorer.listener.view.EvaluationPlayerViewListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.view.EvaluationPlayerView;

public class EvaluationActivity extends AppCompatActivity implements EvaluationActivityListener {

    private TextView winner;
    private EditText gameName;

    private GridLayout statsFirst;
    private GridLayout statsSecond;

    private Button home;
    private Button saveGame;

    private EvaluationControllerListener controller;
    private EvaluationPlayerViewListener[] playerViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation);
        this.init();
        new EvaluationController(this, this.handleIncomingIntent());
        this.registerButton();
    }

    private void init(){
        this.registerViewElements();
    }

    private EvaluationActivity registerButton(){
        this.home.setOnClickListener(v -> this.handleHome());
        this.saveGame.setOnClickListener(v -> this.controller.handleSave(String.valueOf(this.gameName.getText())) );
        //TODO: Auslagern? Nur ein View Evenet?!
        this.home.setOnClickListener(v -> this.handleHome());
        return this;
    }

    private EvaluationActivity registerViewElements(){
        this.winner = findViewById(R.id.tv_eva_win);
        this.statsFirst = findViewById(R.id.gl_eva_stats_first);
        this.statsSecond = findViewById(R.id.gl_eva_stats_second);
        this.gameName = findViewById(R.id.et_eva_name);
        this.home = findViewById(R.id.btn_eva_home);
        this.saveGame = findViewById(R.id.btn_eva_save);

        return this;
    }

    @Override
    public void createPlayerViews(List<PlayerStats> playerStats){
        playerViews = new EvaluationPlayerViewListener[playerStats.size()];
        for(int i = 0; i < playerStats.size(); i++){
            if(i < 2){
                playerViews[i] = new EvaluationPlayerView(this, this.statsFirst);
            } else {
                playerViews[i] = new EvaluationPlayerView(this, this.statsSecond);
            }
            playerViews[i].setViewValues(playerStats.get(i));
        }
        this.statsFirst.invalidate();
        this.statsSecond.invalidate();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setWinnerText(String playerWinner){
        String text = this.winner.getText().toString();
        text = text + " " + playerWinner;
        this.winner.setText(text);
    }

    @Override
    public void handleHome(){
        Intent intent = new Intent(EvaluationActivity.this, HomeActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Nullable
    private GameDatabase handleIncomingIntent() {
        Intent intent_in = this.getIntent();
        int winner = intent_in.getIntExtra("winner", 0);
        List<PlayerStats> playerStats= new ArrayList<>();
        playerStats = intent_in.getParcelableArrayListExtra("PlayerStats");
        assert playerStats != null;
        String playerWinner = playerStats.get(winner).getPlayer();
        return new GameDatabase(winner, playerWinner, playerStats);
    }

    @Override
    public void registerController(Object controller) {
        if(controller instanceof EvaluationControllerListener){
            this.controller = (EvaluationControllerListener) controller;
        }
    }
}
