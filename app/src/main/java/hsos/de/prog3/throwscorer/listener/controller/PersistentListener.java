package hsos.de.prog3.throwscorer.listener.controller;

import android.content.Context;

import java.util.List;

import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * Interface to store data persistently
 * Example DatabaseController
 */
public interface PersistentListener {

    /**
     * Set Context
     * @param context Context
     */
    public void setContext(Context context);

    /**
     * Safe Game
     * @param gameDatabase GameDatabase Object
     */
    public void safeGame(GameDatabase gameDatabase);

    /**
     * Delete Game
     * @param gameID The unique identifier of the game.
     */
    public void deleteGame(String gameID);
    /**
     * Delete all Games
     */
    public void deleteAllGames();

    /**
     * Get Game
     * @param gameID The unique identifier of the game.
     * @return GameDatabase
     */
    public GameDatabase getGame(String gameID);

    /**
     * Get all Games
     * @return List<GameDatabase> List of GameDatabase Objects
     */
    public List<GameDatabase> getAllGames();

    /**
     * Get PlayerStats
     * @param gameID The unique identifier of the game.
     * @return List<PlayerStats> List of PlayerStats Objects
     */
    public List<PlayerStats> getPlayerStats(String gameID);

}
