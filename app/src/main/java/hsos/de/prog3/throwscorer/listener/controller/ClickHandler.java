package hsos.de.prog3.throwscorer.listener.controller;

import android.view.View;

import hsos.de.prog3.throwscorer.listener.RegisterListener;

/**
 * Interface for a ClickHandler
 */
public interface ClickHandler extends RegisterListener {
    /**
     * Handles the onClick event of the buttons
     * @param view The view that was clicked
     */
    public void handleOnClick(View view);
}
