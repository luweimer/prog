package hsos.de.prog3.throwscorer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ThrowScorer.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Game (" +
                "GameID TEXT," +
                "GameName TEXT," +
                "WinnerInt INTEGER," +
                "WinnerName TEXT," +
                "Picture TEXT," +
                "PRIMARY KEY(GameID)" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS PlayerStats (" +
                "GameID TEXT, " +
                "PlayerName TEXT, " +
                "Name INTEGER, " +
                "WinLegs INTEGER, " +
                "WinSets INTEGER, " +
                "Win BOOLEAN," +
                "Stats TEXT," +
                "PRIMARY KEY(GameID, PlayerName),"+
                "FOREIGN KEY(GameID) REFERENCES Game(GameID)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
