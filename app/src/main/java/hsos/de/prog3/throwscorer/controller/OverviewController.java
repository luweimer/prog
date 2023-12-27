package hsos.de.prog3.throwscorer.controller;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hsos.de.prog3.throwscorer.database.DatabaseAccess;
import hsos.de.prog3.throwscorer.listener.activity.OverviewActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.OverviewControllerListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public class OverviewController implements OverviewControllerListener {

    private OverviewActivityListener view;

    public OverviewController(OverviewActivityListener view) {
        this.view = view;
        view.registerController(this);

        this.registerTableRows();
    }

    private OverviewController registerTableRows(){
        DatabaseAccess dbAccess = new DatabaseAccess((Context) this.view);
        dbAccess.open();
        List<GameDatabase> games = dbAccess.getAllGames();
        dbAccess.close();
        String[] name = new String[games.size()];
        String[] id = new String[games.size()];
        for(int i = 0; i < games.size(); i++){
            name[i] = games.get(i).getGameName();
            id[i] = games.get(i).getGameID();
        }

        this.view.createGameRows( name, id );

        return this;
    };


    @Override
    public void showGame(String id) {
        DatabaseAccess dbAccess = new DatabaseAccess((Context) this.view);
        dbAccess.open();
        GameDatabase gd = dbAccess.getGame(id);
        List<PlayerStats> playerStats = dbAccess.getPlayerStats(id);
        dbAccess.close();

        this.view.showWinner(gd.getWinnerInt(), (ArrayList<PlayerStats>) playerStats);
    }

    @Override
    public void deleteGame(String id) {
        DatabaseAccess dbAccess = new DatabaseAccess((Context) this.view);
        dbAccess.open();
        dbAccess.deleteGame(id);
        dbAccess.close();

        this.registerTableRows();
    }

    @Override
    public void cleanDatabase() {
        DatabaseAccess dbAccess = new DatabaseAccess((Context) this.view);
        dbAccess.open();
        dbAccess.cleanDatabase();
        dbAccess.close();

        this.registerTableRows();
    }
}
