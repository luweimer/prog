package hsos.de.prog3.throwscorer.listener.model;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.model.CheckoutType;
import hsos.de.prog3.throwscorer.model.GameMultState;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * GameListener
 * Shnitstelle fuer das Game
 *
 * @author Lucius Weimer
 */
public interface GameListener {

    /**
     * Get Leg
     *
     * @return Leg Nummer
     */
    public int getLeg();

    /**
     * Get isDone - Spiel ist beendet
     *
     * @return true wenn Spiel beendet, sonst false
     */
    public boolean getIsDone();

    /**
     * Get Winner
     *
     * @return int Nummer des Siegers
     */
    public int getWinner();

    /**
     * Get Current Player
     *
     * @return int Nummer des aktuellen Spielers
     */
    public int getCurrentPlayersTurn();

    /**
     * Get Num Players
     *
     * @return int Anzahl der Spieler
     */
    public int getNumPlayers();

    /**
     * Get Player Score
     *
     * @param player Nummer des Spielers
     * @return int Punktzahl des Spielers
     */
    public int getPlayerScore(int player);

    /**
     * Get Player Legs Win
     *
     * @param player Nummer des Spielers
     * @return int Anzahl der gewonnen Legs des Spielers
     */
    public int getPlayerLegsWin(int player);

    /**
     * Get Player Sets Win
     *
     * @param player Nummer des Spielers
     * @return int Anzahl der gewonnen Sets des Spielers
     */
    public int getPlayerSetWin(int player);

    /**
     * Get Player Name
     *
     * @return Namen aller Spieler
     */
    public String[] getPlayerNames();

    /**
     * Get Player Points
     *
     * @param player Nummer des Spielers
     * @return ArrayList<String> Liste der Punkte des Spielers
     */
    public ArrayList<String> getPlayerPoints(int player);

    /**
     * Get Checkout Suggestion
     *
     * @return ArrayList<Integer> Liste eines Checkout Vorschlags
     */
    public ArrayList<Integer> getCheckoutSuggestion();

    /**
     * Get Game Mult State
     *
     * @return GameMultState Aktueller Status (Multiplikator) des Boards
     */
    public GameMultState getGameMultState();

    /**
     * Get Checkout Type
     *
     * @return CheckoutType Checkout Typ
     */
    public CheckoutType getCheckoutType();

    /**
     * Get Game Settings
     *
     * @return GameSettings Einstellungen des Spiels
     */
    public GameSettings getGameSettings();

    /**
     * Get Player Stats
     *
     * @return ArrayList<PlayerStats> Liste der Statistiken aller Spieler
     */
    public ArrayList<PlayerStats> getPlayerStats();

    /**
     * Set Game Mult State
     *
     * @param state GameMultState Status (Multiplikator) des Boards
     */
    public void setGameMultState(GameMultState state);

    /**
     * Loescht den letzten Punkt des Boards des aktuellen Spielers
     */
    public void removeLastBoardPoint();

    /**
     * Fuegt einen Punkt zum Board des aktuellen Spielers hinzu
     *
     * @param point Punkt der hinzugefuegt werden soll
     */
    public void concatBoardPoints(int point);


}
