package hsos.de.prog3.throwscorer.listener.controller;

import android.view.View;

import hsos.de.prog3.throwscorer.listener.RegisterListener;

/**
 * ClickHandler
 * Schnittstelle fuer Click-Handler
 * @author Lucius Weimer
 */
public interface ClickHandler extends RegisterListener {
    /**
     * Verarbeitet einen Klick auf ein View-Element
     * @param view das View-Element, das geklickt wurde
     */
    public void handleOnClick(View view);
}
