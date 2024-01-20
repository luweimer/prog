package hsos.de.prog3.throwscorer.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import hsos.de.prog3.throwscorer.controller.GameController;
import hsos.de.prog3.throwscorer.listener.model.GameListener;

public class Game implements GameListener {

    private GameMultState state;
    private int legs;
    private int currentPlayMove;
    private int currentPlayersTurn;
    private boolean isDone;
    private int winner;
    private GameSettings gameSettings;
    private final Player[] players;

    public Game(GameController gameController, GameSettings gameSettings){
        this.gameSettings = gameSettings;
        gameController.registerController(this);
        this.players = new Player[gameSettings.getNumPlayers()];
        this.winner = -1;
        this.isDone = false;
        this.currentPlayersTurn = 0;
        this.restart();
        this.initPlayerObjects();
    }

    private void restart(){
        this.currentPlayMove = 0;
        this.state = GameMultState.NORMAL;
    }

    private void initPlayerObjects(){
        for(int i = 0; i < gameSettings.getNumPlayers(); i++){
            this.players[i] = new Player(gameSettings.getStartScore(), gameSettings.getCheckoutType(), i, gameSettings.getPlayerNames()[i]);
        }
    }

    private void restartLeg(){
        this.restart();
        this.resetPlayers();
    }

    private void resetPlayers(){
        Arrays.asList(this.players).forEach(player -> {
            player.reset(this.gameSettings.getStartScore());
        });
    }

    @Override
    public int getLeg(){
        return this.legs;
    }
    @Override
    public int getPlayerScore(int player){
        return this.players[player].getScore();
    }
    @Override
    public int getPlayerLegsWin(int player){
        return this.players[player].getLegsWin();
    }
    @Override
    public int getPlayerSetWin(int player){
        return this.players[player].getSetsWin();
    }
    @Override
    public String[] getPlayerNames(){
        return this.gameSettings.getPlayerNames();
    }

    @Override
    public ArrayList<String> getPlayerPoints(int player) {
        return this.players[player].getBoardPointsOutput();
    }

    @Override
    public void setGameMultState(GameMultState state) {
        if(this.state == state){
            this.state = GameMultState.NORMAL;
        } else {
            this.state = state;
        }
    }

    @Override
    public GameMultState getGameMultState() {
        return this.state;
    }

    @Override
    public CheckoutType getCheckoutType() {
        return this.gameSettings.getCheckoutType();
    }

    @Override
    public GameSettings getGameSettings() {
        return this.gameSettings;
    }

    @Override
    public ArrayList<PlayerStats> getPlayerStats() {
        ArrayList<PlayerStats> playerStats = new ArrayList<PlayerStats>();
        Arrays.asList(this.players).forEach(player -> {
            playerStats.add(player.getPlayerStats());
        });
        return playerStats;
    }

    @Override
    public void concatBoardPoints(int point) {
        if( !( this.players[this.currentPlayersTurn].addPoint(point, this.state) ) ){
            this.switchPlayer();
            return;
        }

        this.currentPlayMove++;

        if(this.isCheckoutPossible()){
            this.checkPartialWin();
            this.checkWin();
        }


        this.checkCurrentPlayMove();
    }

    @Override
    public void removeLastBoardPoint(){
        this.players[this.currentPlayersTurn].removePoint();
        if(this.currentPlayMove > 0){
            this.currentPlayMove--;
        }
    }

    @Override
    public int getNumPlayers(){
        return gameSettings.getNumPlayers();
    }

    @Override
    public boolean getIsDone(){
        return this.isDone;
    }

    @Override
    public int getWinner(){
        return this.winner;
    }

    @Override
    public ArrayList<Integer> getCheckoutSuggestion(){
        return this.players[this.currentPlayersTurn].getCheckoutSuggestion();
    }

    @Override
    public int getCurrentPlayersTurn(){
        return this.currentPlayersTurn;
    }

    private void checkPartialWin(){
        if(this.players[this.currentPlayersTurn].addPartialWin( this.gameSettings.getNumLegs() )){
            this.restartLeg();
            this.legs++;
        }
    }
    private void checkWin(){
        Arrays.asList(this.players).forEach(player -> {
            if(player.checkWin( this.gameSettings.getNumSets()) ){
                this.isDone = true;
                this.winner = player.getPlayerNumber();
            }
        });

        if(this.isDone){
            Arrays.asList(this.players).forEach(Player::serialize);
        }
    }

    private boolean isCheckoutPossible(){
        return this.players[this.currentPlayersTurn].isCheckoutPossible();
    }

    private void checkCurrentPlayMove(){
        if(this.currentPlayMove >= 3){
            Log.i("Game", "switch player HIER");
            this.switchPlayer();
        }
    }

    private void switchPlayer(){
        this.currentPlayersTurn++;
        this.currentPlayMove = 0;
        if(this.currentPlayersTurn >= this.gameSettings.getNumPlayers()){
            this.currentPlayersTurn = 0;
        }
    }




}

