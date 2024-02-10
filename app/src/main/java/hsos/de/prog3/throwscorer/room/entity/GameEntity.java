package hsos.de.prog3.throwscorer.room.entity;

import androidx.annotation.NonNull;
import androidx.room.*;

/**
 * GameEntity
 * Entity fuer Game
 * Column: gameID (String), gameName (String), winnerInt (int), winnerName (String), picture (String)
 * PrimaryKey: gameID
 * @author Lucius Weimer
 */
@Entity(tableName = "GameEntity")
public class GameEntity {
    @PrimaryKey
    @NonNull
    public String gameID;

    @ColumnInfo(name = "GameName")
    public String gameName;

    @ColumnInfo(name = "WinnerInt")
    public int winnerInt;

    @ColumnInfo(name = "WinnerName")
    public String winnerName;

    @ColumnInfo(name = "Picture")
    public String picture;

    public GameEntity(String gameID, String gameName, int winnerInt, String winnerName, String picture) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.winnerInt = winnerInt;
        this.winnerName = winnerName;
        this.picture = picture;
    }
}
