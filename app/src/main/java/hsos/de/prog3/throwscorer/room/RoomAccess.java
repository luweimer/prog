package hsos.de.prog3.throwscorer.room;

import static hsos.de.prog3.throwscorer.utility.Converter.BitmapToBase64;
import static hsos.de.prog3.throwscorer.utility.Converter.hashMapToJson;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.UUID;

import hsos.de.prog3.throwscorer.listener.controller.PersistensListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.room.entity.GameEntity;
import hsos.de.prog3.throwscorer.room.entity.PlayerStatsEntity;

public class RoomAccess implements PersistensListener {

    private AppDatabase appDatabase;

    @Override
    public void setContext(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "testOne").build();
    }

    @Override
    public void safeGame(GameDatabase gameDatabase) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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
            }
        }).start();
    }

    @Override
    public void deleteGame(String gameID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.gameDao().deleteGame(gameID);
                appDatabase.playerStatsDao().deletePlayerStats(gameID);
            }
        }).start();
    }

    @Override
    public void deleteAllGames() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Hier die Datenbankoperation durchführen
                appDatabase.clearAllTables();
            }
        }).start();
    }

    @Override
    public LiveData<GameEntity> getGame(String gameID) {
        return appDatabase.gameDao().getGameById(gameID);
    }

    @Override
    public LiveData<List<GameEntity>> getAllGames() {
        return appDatabase.gameDao().getAllGames();
    }

    @Override
    public LiveData<List<PlayerStatsEntity>> getPlayerStats(String gameID) {
        return appDatabase.playerStatsDao().getPlayerStatsByGameId(gameID);
    }


    private String createUUID(){
        return (UUID.randomUUID()).toString();
    }

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
