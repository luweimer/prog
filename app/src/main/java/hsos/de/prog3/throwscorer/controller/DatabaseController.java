package hsos.de.prog3.throwscorer.controller;

import android.content.Context;

import java.util.List;

import hsos.de.prog3.throwscorer.database.DatabaseAccess;
import hsos.de.prog3.throwscorer.listener.controller.PeListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public class DatabaseController implements PeListener {


    private Context context;
    private DatabaseAccess dbAccess;

    @Override
    public void setContext(Context context){
        this.context = context;

        //Close Database if open
        if(this.dbAccess != null) {
            dbAccess.close();
        }

        this.dbAccess = new DatabaseAccess(this.context);
    }

    @Override
    public void safeGame(GameDatabase gameDatabase) {
        if(this.context == null) return;

        this.dbAccess.open();
        this.dbAccess.addGameStats(gameDatabase);
        this.dbAccess.close();
    }

    @Override
    public void deleteGame(String gameID) {
        if(this.context == null) return;

        this.dbAccess.open();
        this.dbAccess.deleteGame(gameID);
        this.dbAccess.close();
    }

    @Override
    public void deleteAllGames() {
        if(this.context == null) return;

        this.dbAccess.open();
        this.dbAccess.cleanDatabase();
        this.dbAccess.close();
    }

    @Override
    public GameDatabase getGame(String gameID) {
        if(this.context == null) return null;

        this.dbAccess.open();
        GameDatabase gd = this.dbAccess.getGame(gameID);
        this.dbAccess.close();

        return gd;
    }

    @Override
    public List<GameDatabase> getAllGames() {
        if(this.context == null) return null;

        this.dbAccess.open();
        List<GameDatabase> games = this.dbAccess.getAllGames();
        this.dbAccess.close();

        return games;
    }

    @Override
    public List<PlayerStats> getPlayerStats(String gameID) {
        if(this.context == null) return null;

        this.dbAccess.open();
        List<PlayerStats> playerStats = this.dbAccess.getPlayerStats(gameID);
        this.dbAccess.close();

        return playerStats;
    }
}
