package hsos.de.prog3.throwscorer.listener.activity;

import android.graphics.Bitmap;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.listener.RegisterListener;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public interface OverviewActivityListener extends RegisterListener {

    public void createGameRows(String[] name, String[] id);

    public void updateGameRows(String name, String id);

    public void showGame(int player, ArrayList<PlayerStats> playerStats, Bitmap pic);



}
