package hsos.de.prog3.throwscorer.listener.controller;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.room.entity.GameEntity;
import hsos.de.prog3.throwscorer.room.entity.PlayerStatsEntity;

/**
 * PersistensListener
 * Schnittstelle für die Persistente Speicherung
 * Autor: Lucius Weimer
 */
public interface PersistensListener {
    /**
     * Setzen des Contexts
     * @param context Context
     */
    public void setContext(Context context);

    /**
     * Speichern eines Spiels in der Datenbank
     * @param gameDatabase GameEntity Object
     */
    public void safeGame(GameDatabase gameDatabase);

    /**
     * Löschen eines Spiels
     * @param gameID ID des Spiels
     */
    public void deleteGame(String gameID);
    /**
     * Löschen aller Spiele und zugehöriger Daten
     */
    public void deleteAllGames();

    /**
     * Get Spiel
     * @param gameID ID des Spiels
     * @return GameDatabase
     */
    public LiveData<GameEntity> getGame(String gameID);

    /**
     * Get alle Spiele
     * @return List<GameDatabase> Liste von GameDatabase Objects
     */
    public LiveData<List<GameEntity>> getAllGames();

    /**
     * Get PlayerStats zu einem Spiel
     * @param gameID ID des Spiels
     * @return List<PlayerStats> List of PlayerStats Objects
     */
    public LiveData<List<PlayerStatsEntity>> getPlayerStats(String gameID);
}
