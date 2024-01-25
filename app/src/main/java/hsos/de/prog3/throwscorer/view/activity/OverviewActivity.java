package hsos.de.prog3.throwscorer.view.activity;

import static hsos.de.prog3.throwscorer.utility.Router.startEvaluationActivity;
import static hsos.de.prog3.throwscorer.utility.Router.startHomeActivity;

import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.controller.OverviewController;
import hsos.de.prog3.throwscorer.listener.view.activity.OverviewActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.OverviewControllerListener;
import hsos.de.prog3.throwscorer.listener.view.zview.OverviewRowListener;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.view.zview.OverviewRow;

/**
 * OverviewActivity
 * Activity die eine Übersicht über alle gespeicherten Spiele darstellt
 * Hauptfunktionen: Datenbank aufräumen, zur HomeActivity zurückkehren
 * Funktionen pro Spiel: Spiele löschen, Spiel auswählen
 */
public class OverviewActivity extends AppCompatActivity implements OverviewActivityListener {

    private TableLayout gameTable;

    private OverviewControllerListener controller;

    private Button cleanup;

    private Button home;

    private OverviewRowListener[] rows;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        this.init();
        new OverviewController(this);
        this.initController();
    }

    /**
     * Initialisiert die Elemente der Activity
     */
    private void init(){
        this.registerViewElements();
    }

    /**
     * Registriert die Hauptfunktionen
     * Listener für die Buttons, wenn der Controller gesetzt ist
     */
    private void initController(){
        if(this.controller != null){
            this.cleanup.setOnClickListener(view -> this.controller.cleanDatabase());
            this.home.setOnClickListener(view -> startHomeActivity(this));
        }
    }

    /**
     * Registriert die View-Elemente der Activity
     */
    private void registerViewElements(){
        this.gameTable = this.findViewById(R.id.tl_ov_games);
        this.cleanup = this.findViewById(R.id.btn_ov_clean);
        this.home = this.findViewById(R.id.btn_ov_home);
    }

    /**
     * Erstellt die Zeilen für die einzelnen Spiele
     * Löscht die alten Zeilen
     * @param name - Name der Spiele
     * @param id - ID der Spiele
     */
    @Override
    public void createGameRows(String[] name, String[] id) {
        if(this.rows != null){
            for(OverviewRowListener row : this.rows){
                row.destroy();
            }
        }
        if(name.length != id.length){
            throw new IllegalArgumentException("Name and ID array must have the same length");
        }
        this.rows = new OverviewRowListener[name.length];
        for(int i = 0; i < name.length; i++){
            this.rows[i] = new OverviewRow(this, this.gameTable, this.controller);
            this.rows[i].setName(name[i]);
            this.rows[i].setID(id[i]);
        }

        this.gameTable.invalidate();
    }

    /**
     * Startet die EvaluationActivity für ein ausgewähltes Spiel
     * @param player - Sieger des Spiels
     * @param playerStats - Statistiken der Spieler
     * @param pic - Siegesbild des Spiels
     */
    @Override
    public void showGame(int player, ArrayList<PlayerStats> playerStats, Bitmap pic) {
        startEvaluationActivity(this, player, playerStats, pic);
    }

    @Override
    public void registerController(Object controller) {
        if(controller instanceof OverviewControllerListener){
            this.controller = (OverviewControllerListener) controller;
        }
    }
}
