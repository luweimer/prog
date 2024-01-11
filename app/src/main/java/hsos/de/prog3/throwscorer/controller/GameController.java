package hsos.de.prog3.throwscorer.controller;

import android.view.View;
import android.widget.Button;

import hsos.de.prog3.throwscorer.listener.activity.GameActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.ClickHandler;
import hsos.de.prog3.throwscorer.listener.model.GameListener;
import hsos.de.prog3.throwscorer.model.Game;
import hsos.de.prog3.throwscorer.model.GameMultState;
import hsos.de.prog3.throwscorer.model.GameSettings;

public class GameController implements ClickHandler {

    private GameActivityListener view;
    private GameListener model;

    public GameController(GameActivityListener view, GameSettings gameSettings){
        this.view = view;
        this.view.registerController(this);
        new Game(this, gameSettings);

        this.init();
    }

    private void registerModel(GameListener model){
        this.model = model;
    }

    private void init(){
        view.createPlayerHeader( this.model.getNumPlayers(), this.model.getPlayerNames()  );
        this.updatePlayerHeader();
    }

    private GameController handlePoints(int point){
        this.model.concatBoardPoints(point);
        return this;
    }

    private GameController resetBoardState(){
        model.setGameMultState(GameMultState.NORMAL);
        return this;
    }

    private GameController removeLastBoardPoint(){
        model.removeLastBoardPoint();
        return this;
    }

    private GameController handleSpecial(String buttonValue){
        switch(buttonValue){
            case "DOUBLE" : {
                model.setGameMultState(GameMultState.DOUBLE);
                break;
            }
            case "TRIPLE" : {
                model.setGameMultState(GameMultState.TRIPLE);
                break;
            }
            //Language specific
            case "B" :
            case "Z" : {
                this.removeLastBoardPoint();
                break;
            }
            default: {
                break;
            }
        }
        return this;
    }


    @Override
    public void registerController(Object controller) {
        if(controller instanceof GameListener){
            this.registerModel((GameListener) controller);
        }
    }

    @Override
    public void handleOnClick(View view) {
        Button button = (Button) view;
        String buttonValue = button.getText().toString();

        try{
            int point = Integer.parseInt( buttonValue );
            this.handlePoints(point).resetBoardState();
        } catch ( NumberFormatException e ){
            this.handleSpecial(buttonValue);
        }

        if( this.model.getIsDone() ){
            this.view.showWinner(this.model.getWinner(), this.model.getPlayerStats(), this.model.getGameSettings() );
        }

        //Update the view complete, because not much datas changed
        this.updateBoardState()
                .updatePlayerHeader()
                .updateActivePlayerHead()
                .updateSuggestion()
                .updateCurrentLegs();
    }

    private GameController updatePlayerHeader(){
        for(int i = 0; i < this.model.getNumPlayers(); i++){
            view.updatePlayerHeader(i, this.model.getPlayerScore(i), this.model.getPlayerPoints(i), this.model.getPlayerSetWin(i), this.model.getPlayerLegsWin(i));
        }
        return this;
    }

    private GameController updateActivePlayerHead(){
        view.updateActivePlayerHead(this.model.getCurrentPlayersTurn());
        return this;
    }

    private GameController updateBoardState(){
        view.updateGameState(this.model.getGameMultState());
        return this;
    }

    private GameController updateSuggestion(){
        this.view.updateSuggestions( this.model.getCheckoutSuggestion(), this.model.getCheckoutType() );
        return this;
    }

    private GameController updateCurrentLegs(){
        this.view.updateLegs( this.model.getLeg() );
        return this;
    }


}
