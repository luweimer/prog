package hsos.de.prog3.throwscorer.database;

import static hsos.de.prog3.throwscorer.utility.Converter.Base64ToBitmap;
import static hsos.de.prog3.throwscorer.utility.Converter.BitmapToBase64;
import static hsos.de.prog3.throwscorer.utility.Converter.hashMapToJson;
import static hsos.de.prog3.throwscorer.utility.Converter.jsonToHashMap;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public class DatabaseAccess {

    private final DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAccess(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    @SuppressLint("Range")
    public List<GameDatabase> getAllGames(){
        String[] columns = {"GameID", "GameName", "WinnerInt", "WinnerName", "Picture"};
        List<GameDatabase> gameDatabaseList = new ArrayList<>();

        try(
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("Game", columns, null, null, null, null, null)
        ){
            while (cursor.moveToNext()) {
                String gameID = cursor.getString(cursor.getColumnIndex("GameID"));
                String gameName = cursor.getString(cursor.getColumnIndex("GameName"));
                int winnerInt = cursor.getInt(cursor.getColumnIndex("WinnerInt"));
                String winnerName = cursor.getString(cursor.getColumnIndex("WinnerName"));
                String picture = cursor.getString(cursor.getColumnIndex("Picture"));

                Bitmap pic = Base64ToBitmap(picture);

                GameDatabase gameDatabase = new GameDatabase(gameID, gameName, winnerInt, winnerName, pic);
                gameDatabaseList.add(gameDatabase);
            }

            cursor.close();
            db.close();
        }

        return gameDatabaseList;
    }

    @SuppressLint("Range")
    public GameDatabase getGame(String gameID){
        String[] columns = {"GameID", "GameName", "WinnerInt", "WinnerName", "Picture"};
        GameDatabase gameDatabase = null;
        try(
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("Game", columns, "GameID=?", new String[]{gameID}, null, null, null)
        ){
            while (cursor.moveToNext()) {
                String gameName = cursor.getString(cursor.getColumnIndex("GameName"));
                int winnerInt = cursor.getInt(cursor.getColumnIndex("WinnerInt"));
                String winnerName = cursor.getString(cursor.getColumnIndex("WinnerName"));
                String picture = cursor.getString(cursor.getColumnIndex("Picture"));

                Bitmap pic = Base64ToBitmap(picture);

                gameDatabase = new GameDatabase(gameID, gameName, winnerInt, winnerName, pic);
            }

            cursor.close();
            db.close();
        }

        return gameDatabase;
    }

    public void cleanDatabase(){
        try(SQLiteDatabase db = dbHelper.getWritableDatabase()){
            db.delete("Game", null, null);
            db.delete("PlayerStats", null, null);
            db.close();
        }
    }

    @SuppressLint("Range")
    public List<PlayerStats> getPlayerStats(String gameID){
        String[] columns = {"PlayerName", "Name", "WinLegs", "WinSets", "Win", "Stats"};
        List<PlayerStats> playerStatsList = new ArrayList<>();

        try(
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("PlayerStats", columns, "GameID=?", new String[]{gameID}, null, null, null)
        ){
            while (cursor.moveToNext()) {
                PlayerStats playerStats;
                String player = cursor.getString(cursor.getColumnIndex("PlayerName"));
                int name = cursor.getInt(cursor.getColumnIndex("Name"));
                int winLegs = cursor.getInt(cursor.getColumnIndex("WinLegs"));
                int winSets = cursor.getInt(cursor.getColumnIndex("WinSets"));
                boolean win = cursor.getInt(cursor.getColumnIndex("Win")) == 1; // 1 für true, 0 für false
                HashMap<String, Integer> stats = jsonToHashMap( cursor.getString( cursor.getColumnIndex("Stats") ) );

                playerStats = new PlayerStats(
                        player,
                        name,
                        winLegs,
                        winSets,
                        win,
                        stats
                );

                playerStatsList.add(playerStats);
            }

            cursor.close();
            db.close();
        }
        return playerStatsList;
    }

    public void addGameStats(GameDatabase gameDatabase){
        if(!checkValidGame(gameDatabase)){
            return;
        }

        String gameID = createUUID();
        String picture = BitmapToBase64(gameDatabase.getWinnerPic());

        this.addGame(gameID, gameDatabase.getGameName(), gameDatabase.getWinnerInt(), gameDatabase.getWinnerName(), picture);

        for (PlayerStats playerStat : gameDatabase.getPlayerStats()) {
            addPlayerStats(gameID, playerStat);
        }

    }

    public void deleteGame(String id){
        try(SQLiteDatabase db = dbHelper.getWritableDatabase()){
            db.delete("Game", "GameID=?", new String[]{id});
            db.delete("PlayerStats", "GameID=?", new String[]{id});
            db.close();
        }
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

    private void addGame(String gameID, String gameName, int winnerInt, String winnerName, String picture){
        ContentValues values = new ContentValues();
        values.put( "GameID", gameID );
        values.put( "GameName", gameName );
        values.put( "WinnerInt", winnerInt );
        values.put( "WinnerName", winnerName );
        values.put( "Picture", picture );
        Log.i("DB", gameID);
        database.insert("Game", null, values);
    }

    private void addPlayerStats(String gameID, PlayerStats playerStats){
        ContentValues values = new ContentValues();
        values.put( "GameID", gameID );
        values.put( "PlayerName", playerStats.getPlayer() );
        values.put( "Name", playerStats.getName() );
        values.put( "WinLegs", playerStats.getWinLegs() );
        values.put( "WinSets", playerStats.getWinSets() );
        values.put( "Win", playerStats.getWin() );
        Log.i("DB", String.valueOf(playerStats.getWin()));
        values.put( "Stats", hashMapToJson(playerStats.getStats()) );
        Log.i("DB", gameID);
        database.insert("PlayerStats", null, values);
    }


}
