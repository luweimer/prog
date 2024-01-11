package hsos.de.prog3.throwscorer.room.entity;

import androidx.room.*;

@Entity(tableName = "GameEntity")
public class GameEntity {
    @PrimaryKey
    public String gameID;

    @ColumnInfo(name = "GameName")
    public String gameName;

    @ColumnInfo(name = "WinnerInt")
    public int winnerInt;

    @ColumnInfo(name = "WinnerName")
    public String winnerName;

    @ColumnInfo(name = "Picture")
    public String picture; // Base64-String für das Bild gespeichert

    public GameEntity(String gameID, String gameName, int winnerInt, String winnerName, String picture) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.winnerInt = winnerInt;
        this.winnerName = winnerName;
        this.picture = picture;
    }
}
