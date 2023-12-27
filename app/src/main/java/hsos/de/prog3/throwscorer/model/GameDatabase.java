package hsos.de.prog3.throwscorer.model;

import java.util.ArrayList;
import java.util.List;

public class GameDatabase {

    private String GameID;
    private String gameName;
    private int winnerInt;
    private String winnerName;

    private List<PlayerStats> playerStats;

    public GameDatabase(String GameID, String gameName, int winnerInt, String winnerName){
        this.playerStats = new ArrayList<>();
        this.GameID = GameID;
        this.gameName = gameName;
        this.winnerInt = winnerInt;
        this.winnerName = winnerName;
    }

    public GameDatabase(int winnerInt, String winnerName, List<PlayerStats> playerStats){
        this.playerStats = new ArrayList<>();
        this.winnerInt = winnerInt;
        this.winnerName = winnerName;
        this.playerStats = playerStats;
        this.GameID = null;
    }

    public String getGameID(){
        return GameID;
    }

    public int getWinnerInt(){
        return winnerInt;
    }

    public String getWinnerName(){
        return winnerName;
    }

    public List<PlayerStats> getPlayerStats(){
        return playerStats;
    }

    public String getGameName(){
        return gameName;
    }

    public void setGameName(String name){
        this.gameName = name;
    }



}
