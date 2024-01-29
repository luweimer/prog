package hsos.de.prog3.throwscorer.room;

import static hsos.de.prog3.throwscorer.utility.Converter.BitmapToBase64;
import static hsos.de.prog3.throwscorer.utility.Converter.hashMapToJson;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.UUID;

import hsos.de.prog3.throwscorer.listener.controller.PersistensListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.room.entity.GameEntity;
import hsos.de.prog3.throwscorer.room.entity.PlayerStatsEntity;

/**
 * RoomAccess
 * Zugriff auf die Room Datenbank
 * Autor: Lucius Weimer
 */
public class RoomAccess implements PersistensListener {

    private AppDatabase appDatabase;
    private Handler handler;
    private Context context;

    public RoomAccess(){
        this.handler = new Handler(Looper.getMainLooper());
    }


    /**
     * Setzen des Kontextes für die Datenbank
     * @param context Context
     */
    @Override
    public void setContext(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "testOne").build();
        this.context = context;
    }

    /**
     * Speichern eines Spiels in der Datenbank
     * Erstellt ein GameEntity Objekt von dem gameDatabase
     * Speicherung durch einen neuen Thread
     * @param gameDatabase GameEntity Object
     */
    @Override
    public void safeGame(GameDatabase gameDatabase) {
        this.DBOperation(() -> {
            if(!checkValidGame(gameDatabase)){
                return;
            }
            String gameID = createUUID();
            String picture = BitmapToBase64(gameDatabase.getWinnerPic());

            GameEntity gameEntity = new GameEntity(gameID, gameDatabase.getGameName(), gameDatabase.getWinnerInt(), gameDatabase.getWinnerName(), picture);
            appDatabase.gameDao().insertGame(gameEntity);

            for (PlayerStats playerStat : gameDatabase.getPlayerStats()) {
                PlayerStatsEntity playerStatsEntity = new PlayerStatsEntity(
                        gameID,
                        playerStat.getPlayer(),
                        playerStat.getPlayerNumber(),
                        playerStat.getWinLegs(),
                        playerStat.getWinSets(),
                        playerStat.getWin(),
                        hashMapToJson( playerStat.getStats() )
                );
                try{
                    appDatabase.playerStatsDao().insertPlayerStats(playerStatsEntity);
                } catch (Exception e){
                    Log.e("RoomAccess", "safeGame: " + e.getMessage() );
                }

            }
        }, "Game saved!");
    }

    /**
     * Löschen eines Spiels aus der Datenbank und die dazugehörigen PlayerStats
     * Löschen durch einen neuen Thread
     * @param gameID Der eindeutige Identifier des Spiels
     */
    @Override
    public void deleteGame(String gameID) {
        this.DBOperation(() -> {
            appDatabase.gameDao().deleteGame(gameID);
            appDatabase.playerStatsDao().deletePlayerStats(gameID);
        }, "Game deleted!");
    }

    /**
     * Löschen aller Spiele aus der Datenbank und die dazugehörigen PlayerStats
     * Löschen durch einen neuen Thread
     */
    @Override
    public void deleteAllGames() {
        Log.i("RoomAccess", "deleteAllGames: ");
        this.DBOperation(() -> {
            appDatabase.clearAllTables();
        }, "All games deleted!");
    }

    private void DBOperation(Runnable runnable, String output){
        Log.i("RoomAccess", "DBOperation: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                    Log.i("RoomAccess", "run: ");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context.getApplicationContext(), output, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context.getApplicationContext(), "Eingabe fehlgeschlagen!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e("RoomAccess", "deleteAllGames: " + e.getMessage() );
                }
            }
        }).start();
    }

    /**
     * Laden eines Spiels aus der Datenbank
     * @param gameID Der eindeutige Identifier des Spiels
     * @return LiveData<GameEntity>
     */
    @Override
    public LiveData<GameEntity> getGame(String gameID) {
        return appDatabase.gameDao().getGameById(gameID);
    }

    /**
     * Laden aller Spiele aus der Datenbank
     * @return LiveData<List<GameEntity>>
     */
    @Override
    public LiveData<List<GameEntity>> getAllGames() {
        return appDatabase.gameDao().getAllGames();
    }

    /**
     * Laden aller PlayerStats aus der Datenbank zu einem zugehörigen Spiel
     * @param gameID Der eindeutige Identifier des Spiels
     * @return LiveData<List<PlayerStatsEntity>>
     */
    @Override
    public LiveData<List<PlayerStatsEntity>> getPlayerStats(String gameID) {
        return appDatabase.playerStatsDao().getPlayerStatsByGameId(gameID);
    }

    /**
     * Erstellen eines eindeutigen Identifiers für ein Spiel
     * @return String
     */
    private String createUUID(){
        return (UUID.randomUUID()).toString();
    }

    /**
     * Prüfen ob ein GameDatabase Objekt valide bzw. vollständig ist
     * @param gameDatabase GameDatabase Objekt das geprüft werden soll
     * @return boolean, true wenn valide
     */
    private boolean checkValidGame(GameDatabase gameDatabase){
        if(gameDatabase.getGameName() == null || gameDatabase.getGameName().isEmpty()){
            return false;
        }
        if(gameDatabase.getWinnerInt() < 0){
            return false;
        }
        if(gameDatabase.getWinnerName() == null || gameDatabase.getWinnerName().isEmpty()){
            return false;
        }
        if(gameDatabase.getPlayerStats() == null || gameDatabase.getPlayerStats().isEmpty()){
            return false;
        }
        if(gameDatabase.getWinnerPic() == null){
            return false;
        }
        return true;
    }

}
