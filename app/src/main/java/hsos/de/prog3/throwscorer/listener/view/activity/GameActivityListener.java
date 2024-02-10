package hsos.de.prog3.throwscorer.listener.view.activity;

import java.util.ArrayList;
import java.util.List;

import hsos.de.prog3.throwscorer.listener.RegisterListener;
import hsos.de.prog3.throwscorer.model.CheckoutType;
import hsos.de.prog3.throwscorer.model.GameMultState;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.Player;
import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * GameActivityListener
 * Schnittstelle fuer die GameActivity
 * @author Lucius Weimer
 */
public interface GameActivityListener extends RegisterListener {

    /**
     * Erstellung der PlayerHeader
     * @param numPlayers Anzahl Spieler
     * @param player Namen der Spieler
     */
    public void createPlayerHeader(int numPlayers, String[] player);

    /**
     * Setzen aller Spieler auf inaktiv ausser dem aktiven Spieler (uebergeben Spieler)
     * @param player Nummer des aktiven Spielers
     */
    public void updateActivePlayerHead(int player);

    /**
     * Aktualisierung des PlayerHeaders eines Spielers (player)
     * @param player Nummer des zu aktualisierenden Spieler
     * @param score Score des Spielers
     * @param points geworfene Punkte des Spielers
     * @param sets gewonnene Sets des Spielers
     * @param legs gewonnene Legs des Spielers
     */
    public void updatePlayerHeader(int player, int score, ArrayList<String> points, int sets, int legs);

    /**
     * Aktualisierung des GameStatus (Multiplikator)
     * @param state aktueller GameMultState
     */
    public void updateGameState(GameMultState state);

    /**
     * Aktualisierung des Scoreboards - Aktuelles Leg
     * @param legs aktuelles Leg
     */
    public void updateLegs(int legs);

    /**
     * Aktualisierung des Scoreboards - Aktuelle Checkout Suggestion
     * @param suggestions aktuelle Checkout Suggestion
     * @param checkout CheckoutType
     */
    public void updateSuggestions(ArrayList<Integer> suggestions, CheckoutType checkout);

    /**
     * Ausgabe des Siegers, bei Spielende
     * @param player Nummer des Siegers
     * @param playerStats Statistiken aller Spieler
     * @param gameSettings Einstellungen des Spiels
     */
    public void showGame(int player, ArrayList<PlayerStats> playerStats, GameSettings gameSettings);
}
