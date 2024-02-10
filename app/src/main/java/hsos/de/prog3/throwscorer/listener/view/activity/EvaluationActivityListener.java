package hsos.de.prog3.throwscorer.listener.view.activity;

import android.graphics.Bitmap;

import java.util.List;

import hsos.de.prog3.throwscorer.listener.RegisterListener;
import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * EvaluationActivity
 * Schnittstelle fuer die EvaluationActivity
 * @author Lucius Weimer
 */
public interface EvaluationActivityListener extends RegisterListener {

    /**
     * Verarbeitung des Klicks auf den Home-Button
     */
    public void handleHome();

    /**
     * Setzen des Sieger Textes
     * @param playerWinner Name des Siegers
     */
    public void setWinnerText(String playerWinner);

    /**
     * Erstellung PlayerViews
     * @param playerStats PlayerStats
     */
    public void createPlayerViews(List<PlayerStats> playerStats);

    /**
     * Anzeige eines Toasts in der View
     * @param message Nachricht
     */
    public void showToast(String message);

    /**
     * Trigger fuer das Teilen des Siegers
     * @param AVG durchscnittliche Punktzahl des Siegers
     * @param against Liste aller anderen Spieler
     */
    public void shareWinner(double AVG, List<String> against);

    /**
     * Setzen des Sieger Bildes
     * @param pic Bitmap
     */
    public void setWinnerPic(Bitmap pic);

}
