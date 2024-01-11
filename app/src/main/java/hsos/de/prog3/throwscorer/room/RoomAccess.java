package hsos.de.prog3.throwscorer.room;

import static hsos.de.prog3.throwscorer.utility.Converter.Base64ToBitmap;
import static hsos.de.prog3.throwscorer.utility.Converter.BitmapToBase64;
import static hsos.de.prog3.throwscorer.utility.Converter.hashMapToJson;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hsos.de.prog3.throwscorer.listener.controller.PersListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.room.entity.GameEntity;
import hsos.de.prog3.throwscorer.room.entity.PlayerStatsEntity;

public class RoomAccess implements PersListener {

    private AppDatabase appDatabase;

    @Override
    public void setContext(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "ThrowSafe").build();
    }

    @Override
    public void safeGame(GameDatabase gameDatabase) {
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
            appDatabase.playerStatsDao().insertPlayerStats(playerStatsEntity);
        }
    }

    @Override
    public void deleteGame(String gameID) {
        appDatabase.gameDao().deleteGame(gameID);
        appDatabase.playerStatsDao().deletePlayerStats(gameID);
    }

    @Override
    public void deleteAllGames() {
        appDatabase.clearAllTables();
    }

    @Override
    public GameDatabase getGame(String gameID) {
        GameEntity gameEntity = appDatabase.gameDao().getGameById(gameID).getValue();
        if(gameEntity == null){
            return null;
        }
        return this.convertGameEntityToGameDatabase(gameEntity);
    }

    @Override
    public List<GameDatabase> getAllGames() {
        List<GameEntity> games = appDatabase.gameDao().getAllGames().getValue();
        if(games == null){
            return null;
        }
        List<GameDatabase> gameDatabase= new ArrayList<>();
        games.forEach(gameEntity -> {
            gameDatabase.add(this.convertGameEntityToGameDatabase(gameEntity));
        });
        return gameDatabase;
    }

    @Override
    public List<PlayerStatsEntity> getPlayerStats(String gameID) {
        LiveData<List<PlayerStatsEntity>> playerStats = appDatabase.playerStatsDao().getPlayerStatsByGameId(gameID);
        return playerStats.getValue();
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

    private GameDatabase convertGameEntityToGameDatabase(GameEntity gameEntity){
        return new GameDatabase(
                gameEntity.gameID,
                gameEntity.gameName,
                gameEntity.winnerInt,
                gameEntity.winnerName,
                Base64ToBitmap(gameEntity.picture)
        );
    }
}
