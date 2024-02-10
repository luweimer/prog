package hsos.de.prog3.throwscorer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import hsos.de.prog3.throwscorer.room.entity.GameEntity;

/**
 * GameDao
 * Data Access Object fuer GameEntity
 * Autor: Lucius Weimer
 */
@Dao
public interface GameDao {
    @Query("SELECT * FROM GameEntity")
    LiveData<List<GameEntity>> getAllGames();

    @Query("SELECT * FROM GameEntity WHERE gameID = :gameID")
    LiveData<GameEntity> getGameById(String gameID);

    @Insert
    void insertGame(GameEntity gameEntity);

    @Query("DELETE FROM GameEntity WHERE gameID = :gameID")
    void deleteGame(String gameID);
}
