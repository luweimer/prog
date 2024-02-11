package hsos.de.prog3.throwscorer.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import hsos.de.prog3.throwscorer.controller.GameController;
import hsos.de.prog3.throwscorer.listener.model.GameListener;

//@TODO: SingleTon implementieren?

/**
 * Game
 * Model class fuer das Spiel
 * Verwendung der Folgender Klassen fuer die Abbildung des gesamten Spiels:
 * - GameSettings
 * - Player
 * - PlayerStats
 * Verwendung der Folgenden Enum Klassen:
 * - GameMultState
 * - CheckoutType
 */
public class Game implements GameListener {

    private GameMultState state;
    private int legs;
    private int currentPlayMove;
    private int currentPlayersTurn;
    private boolean isDone;
    private int winner;
    private GameSettings gameSettings;
    private final Player[] players;

    /**
     * Konstruktor fuer das Spiel
     * Startet das Spiel mit GameSettings
     * Startet den Aufruf fuer die Instanziierung der Player Objekte
     * Setzt Listener fuer den Controller fuer Aktualisierungen
     *
     * @param gameController GameController
     * @param gameSettings   GameSettings
     */
    public Game(GameController gameController, GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        gameController.registerController(this);
        this.players = new Player[gameSettings.getNumPlayers()];
        this.winner = -1;
        this.isDone = false;
        this.currentPlayersTurn = 0;
        this.restart();
        this.initPlayerObjects();
    }

    /**
     * Startet das Spiel
     * Zuruecksetzen der aktuellen Runde
     */
    private void restart() {
        this.currentPlayMove = 0;
        this.state = GameMultState.NORMAL;
    }

    /**
     * Initialisiert die Player Objekte
     */
    private void initPlayerObjects() {
        for (int i = 0; i < gameSettings.getNumPlayers(); i++) {
            this.players[i] = new Player(gameSettings.getStartScore(), gameSettings.getCheckoutType(), i, gameSettings.getPlayerNames()[i]);
        }
    }

    /**
     * Startet ein neues Leg
     */
    private void restartLeg() {
        this.restart();
        this.resetPlayers();
    }

    /**
     * Setzt den Startscore der Player Objekte zurueck
     */
    private void resetPlayers() {
        Arrays.asList(this.players).forEach(player -> {
            player.reset(this.gameSettings.getStartScore());
        });
    }

    /**
     * Gibt die Anzahl der gesamten Legs zurueck
     *
     * @return int
     */
    @Override
    public int getLeg() {
        return this.legs;
    }

    /**
     * Gibt den Score des uebergebenen Spielers zurueck
     *
     * @param player int
     * @return int - Score
     */
    @Override
    public int getPlayerScore(int player) {
        return this.players[player].getScore();
    }

    /**
     * Gibt die Anzahl der gewonnenen Legs des uebergebenen Spielers zurueck
     *
     * @param player int
     * @return int - gewonnene Legs
     */
    @Override
    public int getPlayerLegsWin(int player) {
        return this.players[player].getLegsWin();
    }

    /**
     * Gibt die Anzahl der gewonnenen Sets des uebergebenen Spielers zurueck
     *
     * @param player int
     * @return int - gewonnene Sets
     */
    @Override
    public int getPlayerSetWin(int player) {
        return this.players[player].getSetsWin();
    }

    /**
     * Gibt die Namen der Spieler zurueck
     *
     * @return String[] - Namen der Spieler
     */
    @Override
    public String[] getPlayerNames() {
        return this.gameSettings.getPlayerNames();
    }

    /**
     * Gibt die Punkte des uebergebenen Spielers zurueck
     *
     * @param player int
     * @return ArrayList<String> - Punkte des Spielers
     */
    @Override
    public ArrayList<String> getPlayerPoints(int player) {
        return this.players[player].getBoardPointsOutput();
    }

    /**
     * Setzt den Multiplikator des Spiels
     *
     * @param state GameMultState
     */
    @Override
    public void setGameMultState(GameMultState state) {
        if (this.state == state) {
            this.state = GameMultState.NORMAL;
        } else {
            this.state = state;
        }
    }

    /**
     * Gibt den Multiplikator des Spiels zurueck
     *
     * @return GameMultState
     */
    @Override
    public GameMultState getGameMultState() {
        return this.state;
    }

    /**
     * Gibt den Checkout Typ des Spiels zurueck
     *
     * @return CheckoutType
     */
    @Override
    public CheckoutType getCheckoutType() {
        return this.gameSettings.getCheckoutType();
    }

    /**
     * Gibt die GameSettings des Spiels zurueck
     *
     * @return GameSettings
     */
    @Override
    public GameSettings getGameSettings() {
        return this.gameSettings;
    }

    /**
     * Gibt die PlayerStats aller Spieler zurueck
     *
     * @return ArrayList<PlayerStats>
     */
    @Override
    public ArrayList<PlayerStats> getPlayerStats() {
        ArrayList<PlayerStats> playerStats = new ArrayList<PlayerStats>();
        Arrays.asList(this.players).forEach(player -> {
            playerStats.add(player.getPlayerStats());
        });
        return playerStats;
    }

    /**
     * Fuegt dem aktuellen Spieler den uebergebenen Punkt hinzu
     * Prueft ob ein Checkout des Spielers moeglich ist
     * Prueft ob der Spieler gewonnen hat
     * Prueft ob der Spieler gewechselt werden muss
     *
     * @param point Der Punkt der hinzugefuegt werden soll
     */
    @Override
    public void concatBoardPoints(int point) {
        if (!(this.players[this.currentPlayersTurn].addPoint(point, this.state))) {
            this.switchPlayer();
            return;
        }

        this.currentPlayMove++;

        if (this.isCheckoutPossible()) {
            this.checkPartialWin();
            this.checkWin();
        }
        this.checkCurrentPlayMove();
    }

    /**
     * Entfernt den letzten Punkt des aktuellen Spielers
     */
    @Override
    public void removeLastBoardPoint() {
        this.players[this.currentPlayersTurn].removePoint();
        if (this.currentPlayMove > 0) {
            this.currentPlayMove--;
        }
    }

    /**
     * Gibt die Anzahl der Spieler zurueck
     *
     * @return int - Anzahl der Spieler
     */
    @Override
    public int getNumPlayers() {
        return gameSettings.getNumPlayers();
    }

    /**
     * Gibt zurueck ob das Spiel beendet ist
     *
     * @return boolean, true wenn das Spiel beendet ist
     */
    @Override
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Gibt den Gewinner des Spiels zurueck
     *
     * @return int - Gewinner des Spiels, wenn Wert -1 dann ist das Spiel noch nicht beendet
     */
    @Override
    public int getWinner() {
        return this.winner;
    }

    /**
     * Gibt den Checkout Vorschlag des aktuellen Spielers zurueck
     *
     * @return ArrayList<Integer> - Checkout Vorschlag
     */
    @Override
    public ArrayList<Integer> getCheckoutSuggestion() {
        return this.players[this.currentPlayersTurn].getCheckoutSuggestion();
    }

    /**
     * Gibt den aktuellen Spieler zurueck
     *
     * @return int - aktueller Spieler
     */
    @Override
    public int getCurrentPlayersTurn() {
        return this.currentPlayersTurn;
    }

    /**
     * Prueft ob der aktuelle Spieler ein Leg gewonnen hat
     */
    private void checkPartialWin() {
        if (this.players[this.currentPlayersTurn].addPartialWin(this.gameSettings.getNumLegs())) {
            this.restartLeg();
            this.legs++;
        }
    }

    /**
     * Prueft ob ein Spieler das Spiel gewonnen hat
     * Vergleich von gewonnenen Sets mit der Anzahl der benoetigten Sets
     * Setzt ggf. das Spiel auf beendet (isDone)
     */
    private void checkWin() {
        Arrays.asList(this.players).forEach(player -> {
            if (player.checkWin(this.gameSettings.getNumSets())) {
                this.isDone = true;
                this.winner = player.getPlayerNumber();
            }
        });

        if (this.isDone) {
            Arrays.asList(this.players).forEach(Player::serialize);
        }
    }

    /**
     * Prueft ob beim aktuellen Spieler ein Checkout moeglich ist
     *
     * @return boolean, true wenn ein Checkout moeglich ist
     */
    private boolean isCheckoutPossible() {
        return this.players[this.currentPlayersTurn].isCheckoutPossible();
    }

    /**
     * Prueft ob der aktuelle Spieler gewechselt werden muss
     * Wenn der aktuelle Spieler 3 mal geworfen hat, wird der Spieler gewechselt
     */
    private void checkCurrentPlayMove() {
        if (this.currentPlayMove >= 3) {
            Log.i("Game", "switch player HIER");
            this.switchPlayer();
        }
    }

    /**
     * Wechselt den aktuellen Spieler
     * Setzt den aktuellen Spielzug auf 0
     */
    private void switchPlayer() {
        this.currentPlayersTurn++;
        this.currentPlayMove = 0;
        if (this.currentPlayersTurn >= this.gameSettings.getNumPlayers()) {
            this.currentPlayersTurn = 0;
        }
    }


}

