package hsos.de.prog3.throwscorer.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import hsos.de.prog3.throwscorer.room.dao.GameDao;
import hsos.de.prog3.throwscorer.room.dao.PlayerStatsDao;
import hsos.de.prog3.throwscorer.room.entity.GameEntity;
import hsos.de.prog3.throwscorer.room.entity.PlayerStatsEntity;

/**
 * AppDatabase
 * Room Database
 * Entity: GameEntity, PlayerStatsEntity
 *
 * @author Lucius Weimer
 */
@Database(entities = {
        GameEntity.class,
        PlayerStatsEntity.class
},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GameDao gameDao();

    public abstract PlayerStatsDao playerStatsDao();
}
