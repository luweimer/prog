package hsos.de.prog3.throwscorer.activity;

import static hsos.de.prog3.throwscorer.utility.Router.startGame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.model.GameSettings;

public class HomeActivity extends AppCompatActivity {

    private Button startGame;
    private Button individualGame;
    private Button rules;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        this.startGame = findViewById(R.id.btn_home_start);
        this.individualGame = findViewById(R.id.btn_home_ind);
        this.rules = findViewById(R.id.btn_home_rule);

        this.startGame.setOnClickListener(view -> handleStartGame());
        this.individualGame.setOnClickListener(v -> handleIndividualGame());
        this.rules.setOnClickListener(v -> handleRules());
    }

    /**
     * Handles the start of a new game.
     */
    private void handleStartGame(){
        Log.i("HomeActivity", "handleStartGame");
        GameSettings wmSettings = new GameSettings();
        startGame(this, wmSettings);
    }

    /**
     * Handles the start of a new individual game.
     */
    private void handleIndividualGame(){
        Log.i("HomeActivity", "handleIndividualGame");
        Intent intent = new Intent(this, IndividualGameActivity.class);
        startActivity(intent);
    }

    /**
     * Handles the display of the rules.
     */
    private void handleRules(){
        Log.i("HomeActivity", "handleRules");
        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }
}
