package hsos.de.prog3.throwscorer.controller;

import android.content.Context;
import android.util.Log;

import java.util.List;

import hsos.de.prog3.throwscorer.database.DatabaseAccess;
import hsos.de.prog3.throwscorer.listener.activity.EvaluationActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.EvaluationControllerListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;

public class EvaluationController implements EvaluationControllerListener {

    private EvaluationActivityListener view;

    private GameDatabase gameDatabase;

    public EvaluationController(EvaluationActivityListener view, GameDatabase gameDatabase) {
        this.view = view;
        view.registerController(this);
        this.gameDatabase = gameDatabase;
        this.init();
    }

    private void init(){
        this.view.createPlayerViews( gameDatabase.getPlayerStats() );
        this.view.setWinnerText( gameDatabase.getWinnerName() );
    };

    @Override
    public void handleSave(String name) {
        if(name.isEmpty()) {
            this.view.showToast("Bitte gib einen Namen ein!");
            return;
        }
        this.gameDatabase.setGameName(name);

        DatabaseAccess dbAccess = new DatabaseAccess((Context) this.view);
        dbAccess.open();
        dbAccess.addGameStats(this.gameDatabase);

        this.view.showToast("Spiel wurde erfolgreich hinzugefügt!");
        List<GameDatabase> gd = dbAccess.getAllGames();
        Log.e("DB", gd.toString());
        this.view.handleHome();

        dbAccess.close();


    }
}
