package hsos.de.prog3.throwscorer.listener.view;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.model.Player;

public interface PlayerViewListener {

    public void updatePlayerName(String name);
    public void updateScore(int score);
    public void updatePoints(ArrayList<String> points);
    public void updateWin(int sets, int legs);
    public void updateActivePlayer(boolean active);

}
