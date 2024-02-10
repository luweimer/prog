package hsos.de.prog3.throwscorer.listener.view.activity;

import android.graphics.Bitmap;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.listener.RegisterListener;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * OverviewActivity
 * Schnittstelle fuer die OverviewActivity
 * @author Lucius Weimer
 */
public interface OverviewActivityListener extends RegisterListener {
    /**
     * Erstellung der Tabellenzeilen (einzelne Spiele)
     * @param name Name der Spiele
     * @param id IDs der Spiele
     */
    public void createGameRows(String[] name, String[] id);

    /**
     * Anzeigen eines ausgewaehlten Spieles
     * @param player Nummer des Siegers
     * @param playerStats Liste aller Spieler
     * @param pic Siegesbild
     */
    public void showGame(int player, ArrayList<PlayerStats> playerStats, Bitmap pic);



}
