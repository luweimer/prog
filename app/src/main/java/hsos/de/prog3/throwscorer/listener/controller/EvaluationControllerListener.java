package hsos.de.prog3.throwscorer.listener.controller;

import android.graphics.Bitmap;

/**
 * EvaluationControllerListener
 * Schnittstelle fuer den EvaluationController
 */
public interface EvaluationControllerListener {
    /**
     * Speichern eines Spieles
     * @param name Name des Spiels
     * @param pic Siegesbild
     */
    public void handleSave(String name, Bitmap pic);

    /**
     * Teilen des Siegers
     */
    public void shareWinner();
}
