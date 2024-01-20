package hsos.de.prog3.throwscorer.listener.activity;

import java.util.ArrayList;
import java.util.List;

import hsos.de.prog3.throwscorer.listener.RegisterListener;
import hsos.de.prog3.throwscorer.model.CheckoutType;
import hsos.de.prog3.throwscorer.model.GameMultState;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.Player;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public interface GameActivityListener extends RegisterListener {

    public void createPlayerHeader(int numPlayers, String[] player);
    public void updateActivePlayerHead(int player);

    public void updatePlayerHeader(int player, int score, ArrayList<String> points, int sets, int legs);

    public void updateGameState(GameMultState state);
    public void updateLegs(int legs);

    public void updateSuggestions(ArrayList<Integer> suggestions, CheckoutType checkout);

    public void showWinner(int player, ArrayList<PlayerStats> playerStats, GameSettings gameSettings);

    //void updatePlayerWin(int player, List<GameStats> gameStats, Settings settings);


}
