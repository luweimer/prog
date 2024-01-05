package hsos.de.prog3.throwscorer.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hsos.de.prog3.throwscorer.database.DatabaseAccess;
import hsos.de.prog3.throwscorer.listener.activity.EvaluationActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.EvaluationControllerListener;
import hsos.de.prog3.throwscorer.listener.controller.PersistentListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public class EvaluationController implements EvaluationControllerListener {

    private EvaluationActivityListener view;

    private GameDatabase gameDatabase;

    private PersistentListener persistent;

    public EvaluationController(EvaluationActivityListener view, GameDatabase gameDatabase) {
        this.view = view;
        view.registerController(this);
        this.gameDatabase = gameDatabase;
        this.persistent = new DatabaseController();
        this.persistent.setContext((Context) this.view);
        this.init();
    }

    private void init(){
        this.view.createPlayerViews( gameDatabase.getPlayerStats() );
        this.view.setWinnerText( gameDatabase.getWinnerName() );
        if(this.gameDatabase.getWinnerPic() != null) {
            this.view.setWinnerPic( gameDatabase.getWinnerPic() );
        }
    };


    @Override
    public void handleSave(String name, Bitmap pic) {
        if(name.isEmpty()) {
            this.view.showToast("Bitte gib einen Namen ein!");
            return;
        }
        if(pic == null) {
            this.view.showToast("Bitte wähle ein Bild aus!");
            return;
        }
        this.gameDatabase.setGameName(name);
        this.gameDatabase.setWinnerPic(pic);
        this.persistent.safeGame(this.gameDatabase);
        this.view.showToast("Spiel wurde erfolgreich hinzugefügt!");
        this.view.handleHome();
    }

    @Override
    public void shareWinner(){
        double avg = 0;
        List<String> against = new ArrayList<>();
        for (PlayerStats p : this.gameDatabase.getPlayerStats()) {
            Log.i("EvaluationController", "shareWinner: " + p.getPlayer() + " " + p.getWin());
            if(! p.getWin()){
                against.add(p.getPlayer());
            } else {
                avg = p.getAvg();
            }
        }
        this.view.shareWinner(avg, against);
    }
}
