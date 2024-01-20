package hsos.de.prog3.throwscorer.listener.model;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.model.CheckoutType;
import hsos.de.prog3.throwscorer.model.GameMultState;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * Interface for the GameListener
 */
public interface GameListener {

    public int getLeg();
    public boolean getIsDone();
    public int getWinner();
    public int getCurrentPlayersTurn();
    public int getNumPlayers();
    public int getPlayerScore(int player);
    public int getPlayerLegsWin(int player);
    public int getPlayerSetWin(int player);
    public String[] getPlayerNames();
    public ArrayList<String> getPlayerPoints(int player);
    public ArrayList<Integer> getCheckoutSuggestion();
    public GameMultState getGameMultState();
    public CheckoutType getCheckoutType();
    public GameSettings getGameSettings();
    public ArrayList<PlayerStats> getPlayerStats();

    public void setGameMultState(GameMultState state);

    /**
     * remove the last point of the board from the current player
     */
    public void removeLastBoardPoint();

    /**
     * concat the point to the board of the current player
     * @param point The point to concat
     */
    public void concatBoardPoints(int point);


}
