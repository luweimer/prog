package hsos.de.prog3.throwscorer.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * GameDatabase
 * Speicherung aller Informationen eines Spiels unabhängig von tatsächlichen Speicherung
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
     * @param playerStats Liste der Spielerstatistiken für alle Spieler
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
     * @param playerStats Liste der Spielerstatistiken für alle Spieler
     */
    public GameDatabase(int winnerInt, String winnerName, List<PlayerStats> playerStats){
        this.playerStats = new ArrayList<>();
        this.winnerInt = winnerInt;
        this.winnerName = winnerName;
        this.playerStats = playerStats;
        this.GameID = null;
    }

    /**
     * Getter für WinnerInt
     * @return winnerInt - Nummer des Gewinners
     */
    public int getWinnerInt(){
        return winnerInt;
    }

    /**
     * Getter für WinnerName
     * @return winnerName - Name des Gewinners
     */
    public String getWinnerName(){
        return winnerName;
    }

    /**
     * Getter für PlayerStats
     * @return playerStats - Liste der Spielerstatistiken für alle Spieler
     */
    public List<PlayerStats> getPlayerStats(){
        return playerStats;
    }

    /**
     * Getter für GameName
     * @return gameName - Name des Spiels
     */
    public String getGameName(){
        return gameName;
    }

    /**
     * Getter für Siegesbild
     * @return winnerPic - Siegesbild
     */
    public Bitmap getWinnerPic(){
        return winnerPic;
    }

    /**
     * Setter für GameName
     * @param name Name des Spiels
     */
    public void setGameName(String name){
        this.gameName = name;
    }

    /**
     * Setter für Siegesbild
     * @param pic - Siegesbild
     */
    public void setWinnerPic(Bitmap pic){
        this.winnerPic = pic;
    }

    /**
     * Setter für PlayerStats
     * @param playerStats - Liste der Spielerstatistiken für alle Spieler
     */
    public void setPlayerStats(List<PlayerStats> playerStats){
        this.playerStats = playerStats;
    }


}
