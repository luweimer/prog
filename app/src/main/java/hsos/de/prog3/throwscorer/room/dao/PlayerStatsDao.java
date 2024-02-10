package hsos.de.prog3.throwscorer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import hsos.de.prog3.throwscorer.room.entity.PlayerStatsEntity;

/**
 * PlayerStatsDao
 * Data Access Object fuer PlayerStatsEntity
 * @author Lucius Weimer
 */
@Dao
public interface PlayerStatsDao {
    @Query("SELECT * FROM PlayerStats WHERE gameID = :gameID")
    LiveData<List<PlayerStatsEntity>> getPlayerStatsByGameId(String gameID);

    @Insert
    void insertPlayerStats(PlayerStatsEntity playerStatsEntity);

    @Query("DELETE FROM PlayerStats WHERE gameID = :gameID")
    void deletePlayerStats(String gameID);

}
