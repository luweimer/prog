package hsos.de.prog3.throwscorer.listener.activity;

import java.util.List;

import hsos.de.prog3.throwscorer.listener.RegisterListener;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public interface EvaluationActivityListener extends RegisterListener {

    public void handleHome();

    public void setWinnerText(String playerWinner);

    public void createPlayerViews(List<PlayerStats> playerStats);

    public void showToast(String message);

}
