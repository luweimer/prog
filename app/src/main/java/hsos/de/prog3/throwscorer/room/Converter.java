package hsos.de.prog3.throwscorer.room;

import static hsos.de.prog3.throwscorer.utility.Converter.Base64ToBitmap;
import static hsos.de.prog3.throwscorer.utility.Converter.jsonToHashMap;

import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.room.entity.GameEntity;
import hsos.de.prog3.throwscorer.room.entity.PlayerStatsEntity;

/**
 * Converter
 * Konvertiert die Entitys der Datenbank zu unabhaengigen Datentypen von Throwscorer
 * GameEntity -> GameDatabase
 * PlayerStatsEntity -> PlayerStats
 * Autor: Lucius Weimer
 */
public class Converter {
    /**
     * Konvertiert GameEntity zu GameDatabase
     * @param gameEntity GameEntity
     * @return GameDatabase
     */
    public static GameDatabase convertGameEntityToGameDatabase(GameEntity gameEntity){
        return new GameDatabase(
                gameEntity.gameID,
                gameEntity.gameName,
                gameEntity.winnerInt,
                gameEntity.winnerName,
                Base64ToBitmap(gameEntity.picture)
        );
    }

    /**
     * Konvertiert PlayerStatsEntity zu PlayerStats
     * @param playerStatsEntity
     * @return
     */
    public static PlayerStats convertPlayerStatsEntityToPlayerStats(PlayerStatsEntity playerStatsEntity){
        return new PlayerStats(
                playerStatsEntity.playerName,
                playerStatsEntity.name,
                playerStatsEntity.winLegs,
                playerStatsEntity.winSets,
                playerStatsEntity.win,
                jsonToHashMap(playerStatsEntity.stats)
        );
    }
}
