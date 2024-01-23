package hsos.de.prog3.throwscorer.listener.view;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.model.Player;

/**
 * PlayerViewListener
 * Schnittstelle PlayerView
 * Autor: Lucius Weimer
 */
public interface PlayerViewListener {

    /**
     * Aktualisieren des Spielernamen
     * @param name Name des Spielers
     */
    public void updatePlayerName(String name);

    /**
     * Aktualisieren der Punkte
     * @param score Punkte
     */
    public void updateScore(int score);

    /**
     * Aktualisieren der Würfe
     * @param points Würfe
     */
    public void updatePoints(ArrayList<String> points);

    /**
     * Aktualisieren der Anzahl der gewonnenen Spiele
     * @param sets Anzahl der gewonnenen Sets
     * @param legs Anzahl der gewonnenen Legs
     */
    public void updateWin(int sets, int legs);

    /**
     * Aktualisierung des aktiven Spielers
     * @param active boolean - true wenn aktiv, false wenn nicht aktiv
     */
    public void updateActivePlayer(boolean active);

}
