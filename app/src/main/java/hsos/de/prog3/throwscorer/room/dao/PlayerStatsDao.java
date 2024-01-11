package hsos.de.prog3.throwscorer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import hsos.de.prog3.throwscorer.room.entity.PlayerStatsEntity;

@Dao
public interface PlayerStatsDao {
    @Query("SELECT * FROM PlayerStats WHERE gameID = :gameID")
    LiveData<List<PlayerStatsEntity>> getPlayerStatsByGameId(String gameID);

    @Insert
    void insertPlayerStats(PlayerStatsEntity playerStatsEntity);

    @Delete
    void deletePlayerStats(String id);

}
