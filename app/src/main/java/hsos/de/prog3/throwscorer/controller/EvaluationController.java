package hsos.de.prog3.throwscorer.controller;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.listener.activity.EvaluationActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.EvaluationControllerListener;
import hsos.de.prog3.throwscorer.listener.controller.PersistensListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.room.RoomAccess;

/**
 * EvaluationController
 * Verwaltet die Anzeige und Interaktion der Spielergebnisse mit der Speicherung in der Datenbank
 * View: EvaluationActivityListener
 * Datenbank: PersistensListener
 * Autor: Lucius Weimer
 */
public class EvaluationController implements EvaluationControllerListener {

    private EvaluationActivityListener view;

    private GameDatabase gameDatabase;
    private PersistensListener persistent;

    public EvaluationController(EvaluationActivityListener view, GameDatabase gameDatabase) {
        this.view = view;
        view.registerController(this);
        this.gameDatabase = gameDatabase;
        this.persistent = new RoomAccess();
        this.persistent.setContext((Context) this.view);
        this.init();
    }

    /**
     * Initialisierung der View mit den Daten aus der Datenbank
     */
    private void init(){
        this.view.createPlayerViews( gameDatabase.getPlayerStats() );
        this.view.setWinnerText( gameDatabase.getWinnerName() );
        if(this.gameDatabase.getWinnerPic() != null) {
            this.view.setWinnerPic( gameDatabase.getWinnerPic() );
        }
    };

    /**
     * Speichern des Spiels in der Datenbank
     * Ausgabe eines Toast über die View bei fehlenden Daten
     * @param name Name des Spiels
     * @param pic Siegesbild
     */
    @Override
    public void handleSave(String name, Bitmap pic) {
        if(name.isEmpty()) {
            this.view.showToast("Please enter a name the game!");
            return;
        }
        if(pic == null) {
            this.view.showToast("Please choose a picture!");
            return;
        }
        this.gameDatabase.setGameName(name);
        this.gameDatabase.setWinnerPic(pic);
        this.persistent.safeGame(this.gameDatabase);
        this.view.showToast("Game was saved successfully!");
        this.view.handleHome();
    }

    /**
     * Teilen des Siegers über die View
     */
    @Override
    public void shareWinner(){
        double avg = 0;
        List<String> against = new ArrayList<>();
        for (PlayerStats p : this.gameDatabase.getPlayerStats()) {
            if(! p.getWin()){
                against.add(p.getPlayer());
            } else {
                avg = p.getAvg();
            }
        }
        this.view.shareWinner(avg, against);
    }
}
