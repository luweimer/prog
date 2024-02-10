package hsos.de.prog3.throwscorer.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * GameDatabase
 * Speicherung aller Informationen eines Spiels unabhaengig von tatsaechlichen Speicherung
 * Autor: Lucius Weimer
 */
public class GameDatabase {

    private String GameID;
    private String gameName;
    private int winnerInt;
    private String winnerName;
    private Bitmap winnerPic;

    private List<PlayerStats> playerStats;

    /**
     * Konstruktor
     * @param GameID ID des Spiels
     * @param gameName Name des Spiels
     * @param winnerInt Nummer des Gewinners
     * @param winnerName Name des Gewinners
     * @param winnerPic Siegesbild
     */
    public GameDatabase(String GameID, String gameName, int winnerInt, String winnerName, Bitmap winnerPic){
        this.playerStats = new ArrayList<>();
        this.GameID = GameID;
        this.gameName = gameName;
        this.winnerInt = winnerInt;
        this.winnerName = winnerName;
        this.winnerPic = winnerPic;
    }

    /**
     * Konstruktor
     * @param winnerInt Nummer des Gewinners
     * @param winnerName Name des Gewinners
     * @param playerStats Liste der Spielerstatistiken fuer alle Spieler
     * @param winnerPic Siegesbild
     */
    public GameDatabase(int winnerInt, String winnerName, List<PlayerStats> playerStats, Bitmap winnerPic){
        this.playerStats = new ArrayList<>();
        this.winnerInt = winnerInt;
        this.winnerName = winnerName;
        this.playerStats = playerStats;
        this.winnerPic = winnerPic;
        this.GameID = null;
    }

    /**
     * Konstruktor
     * @param winnerInt Nummer des Gewinners
     * @param winnerName Name des Gewinners
     * @param playerStats Liste der Spielerstatistiken fuer alle Spieler
     */
    public GameDatabase(int winnerInt, String winnerName, List<PlayerStats> playerStats){
        this.playerStats = new ArrayList<>();
        this.winnerInt = winnerInt;
        this.winnerName = winnerName;
        this.playerStats = playerStats;
        this.GameID = null;
    }

    /**
     * Getter fuer WinnerInt
     * @return winnerInt - Nummer des Gewinners
     */
    public int getWinnerInt(){
        return winnerInt;
    }

    /**
     * Getter fuer WinnerName
     * @return winnerName - Name des Gewinners
     */
    public String getWinnerName(){
        return winnerName;
    }

    /**
     * Getter fuer PlayerStats
     * @return playerStats - Liste der Spielerstatistiken fuer alle Spieler
     */
    public List<PlayerStats> getPlayerStats(){
        return playerStats;
    }

    /**
     * Getter fuer GameName
     * @return gameName - Name des Spiels
     */
    public String getGameName(){
        return gameName;
    }

    /**
     * Getter fuer Siegesbild
     * @return winnerPic - Siegesbild
     */
    public Bitmap getWinnerPic(){
        return winnerPic;
    }

    /**
     * Setter fuer GameName
     * @param name Name des Spiels
     */
    public void setGameName(String name){
        this.gameName = name;
    }

    /**
     * Setter fuer Siegesbild
     * @param pic - Siegesbild
     */
    public void setWinnerPic(Bitmap pic){
        this.winnerPic = pic;
    }

    /**
     * Setter fuer PlayerStats
     * @param playerStats - Liste der Spielerstatistiken fuer alle Spieler
     */
    public void setPlayerStats(List<PlayerStats> playerStats){
        this.playerStats = playerStats;
    }


}
