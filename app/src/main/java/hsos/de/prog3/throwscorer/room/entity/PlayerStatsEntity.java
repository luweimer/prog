package hsos.de.prog3.throwscorer.room.entity;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(tableName = "PlayerStats", primaryKeys = {"GameID", "PlayerName"})
public class PlayerStatsEntity {
    @NonNull
    @ColumnInfo(name = "GameID")
    public String gameID;

    @NonNull
    @ColumnInfo(name = "PlayerName")
    public String playerName;

    @ColumnInfo(name = "Name")
    public int name;

    @ColumnInfo(name = "WinLegs")
    public int winLegs;

    @ColumnInfo(name = "WinSets")
    public int winSets;

    @ColumnInfo(name = "Win")
    public boolean win;

    @ColumnInfo(name = "Stats")
    public String stats; //JSON-String für die Stats gespeichert

    public PlayerStatsEntity(String gameID, String playerName, int name, int winLegs, int winSets, boolean win, String stats) {
        this.gameID = gameID;
        this.playerName = playerName;
        this.name = name;
        this.winLegs = winLegs;
        this.winSets = winSets;
        this.win = win;
        this.stats = stats;
    }


}
