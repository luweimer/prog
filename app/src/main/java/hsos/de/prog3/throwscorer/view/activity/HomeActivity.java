package hsos.de.prog3.throwscorer.view.activity;

import static hsos.de.prog3.throwscorer.utility.Router.startGame;
import static hsos.de.prog3.throwscorer.utility.Router.startIndividualGame;
import static hsos.de.prog3.throwscorer.utility.Router.startOverviewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.model.GameSettings;

/**
 * HomeActivity
 * Startet als erste Activity der Anwendung
 * Übersicht über alle Möglichkeiten des Spiels
 * Autor: Lucius Weimer
 */
public class HomeActivity extends AppCompatActivity {

    private Button startGame;
    private Button individualGame;
    private Button rules;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        //Registrierung der Buttons
        this.startGame = findViewById(R.id.btn_home_start);
        this.individualGame = findViewById(R.id.btn_home_ind);
        this.rules = findViewById(R.id.btn_home_rule);
        //Registrierung der Button Listener in der Activity
        this.startGame.setOnClickListener(view -> handleStartGame());
        this.individualGame.setOnClickListener(v -> handleIndividualGame());
        this.rules.setOnClickListener(v -> handleOverview());
    }

    /**
     * Startet ein neues Spiel mit den übergebenen Einstellungen.
     * Einstellung - WM standart Einstellungen
     */
    private void handleStartGame(){
        Log.i("HomeActivity", "handleStartGame");
        GameSettings wmSettings = new GameSettings();
        startGame(this, wmSettings);
    }

    /**
     * Startet ein individuelles Spiel
     */
    private void handleIndividualGame(){
        Log.i("HomeActivity", "handleIndividualGame");
        startIndividualGame(this);
    }

    /**
     * Startet die Übersichts Activity, über alle gespeicherten Spiele
     */
    private void handleOverview(){
        Log.i("HomeActivity", "handleRules");
        startOverviewActivity(this);
    }
}
