package hsos.de.prog3.throwscorer.listener.controller;

import android.content.Context;

import java.util.List;

import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * Interface for Persistent
 * Example Database
 */
public interface PersistentListener {

    public void setContext(Context context);
    public void safeGame(GameDatabase gameDatabase);
    public void deleteGame(String gameID);
    public void deleteAllGames();

    public GameDatabase getGame(String gameID);

    public List<GameDatabase> getAllGames();

    public List<PlayerStats> getPlayerStats(String gameID);

}
