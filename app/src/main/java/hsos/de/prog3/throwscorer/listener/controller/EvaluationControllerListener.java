package hsos.de.prog3.throwscorer.listener.controller;

import android.graphics.Bitmap;

import hsos.de.prog3.throwscorer.listener.RegisterListener;

public interface EvaluationControllerListener {
    public void handleSave(String name, Bitmap pic);

    public void shareWinner();
}
