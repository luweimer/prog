package hsos.de.prog3.throwscorer.controller;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hsos.de.prog3.throwscorer.database.DatabaseAccess;
import hsos.de.prog3.throwscorer.listener.activity.OverviewActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.OverviewControllerListener;
import hsos.de.prog3.throwscorer.listener.controller.PersistentListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public class OverviewController implements OverviewControllerListener {

    private OverviewActivityListener view;

    private PersistentListener persistent;

    public OverviewController(OverviewActivityListener view) {
        this.view = view;
        view.registerController(this);

        this.persistent = new DatabaseController();
        this.persistent.setContext((Context) this.view);

        this.registerTableRows();
    }

    /**
     * Register all Table Rows
     */
    private void registerTableRows(){
        List<GameDatabase> games = this.persistent.getAllGames();
        String[] name = new String[games.size()];
        String[] id = new String[games.size()];
        for(int i = 0; i < games.size(); i++){
            name[i] = games.get(i).getGameName();
            id[i] = games.get(i).getGameID();
        }

        this.view.createGameRows( name, id );

    };

    @Override
    public void showGame(String id) {

        GameDatabase gd = this.persistent.getGame(id);
        List<PlayerStats> playerStats = this.persistent.getPlayerStats(id);

        this.view.showWinner(gd.getWinnerInt(), (ArrayList<PlayerStats>) playerStats);
    }

    @Override
    public void deleteGame(String id) {
        this.persistent.deleteGame(id);

        this.registerTableRows();
    }

    @Override
    public void cleanDatabase() {
        this.persistent.deleteAllGames();

        this.registerTableRows();
    }
}
