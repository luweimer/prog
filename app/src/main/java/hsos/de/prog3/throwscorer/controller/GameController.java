package hsos.de.prog3.throwscorer.controller;

import android.view.View;
import android.widget.Button;

import hsos.de.prog3.throwscorer.listener.view.activity.GameActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.ClickHandler;
import hsos.de.prog3.throwscorer.listener.model.GameListener;
import hsos.de.prog3.throwscorer.model.Game;
import hsos.de.prog3.throwscorer.model.GameMultState;
import hsos.de.prog3.throwscorer.model.GameSettings;

/**
 * GameController
 * Verwaltet die Anzeige und Interaktion des Spiels
 * View: GameActivityListener
 * Model: GameListener
 * Autor: Lucius Weimer
 */
public class GameController implements ClickHandler {

    private GameActivityListener view;
    private GameListener model;

    public GameController(GameActivityListener view, GameSettings gameSettings){
        this.view = view;
        this.view.registerController(this);
        new Game(this, gameSettings);
        this.init();
    }

    /**
     * Registrieren des Models am Controller
     * @param model GameListener
     */
    private void registerModel(GameListener model){
        this.model = model;
    }

    /**
     * Initialisierung der View mit den Daten aus dem Model
     * Erstellung der Spielerübersichten
     */
    private void init(){
        view.createPlayerHeader( this.model.getNumPlayers(), this.model.getPlayerNames()  );
        this.updatePlayerHeader()
                .updateActivePlayerHead();
    }

    /**
     * Verarbeitung der Punkte
     * @param point Punktewert
     */
    private GameController handlePoints(int point){
        this.model.concatBoardPoints(point);
        return this;
    }

    /**
     * Zurücksetzen des BoardStates (Spezialfelder) im Model
     * @return GameController
     */
    private GameController resetBoardState(){
        model.setGameMultState(GameMultState.NORMAL);
        return this;
    }

    /**
     * Entfernen des letzten BoardPoints am Model
     * @return GameController
     */
    private GameController removeLastBoardPoint(){
        model.removeLastBoardPoint();
        return this;
    }

    /**
     * Verarbeitung der Spezialfelder
     * Weiterleitung des Inputs an das Model
     * @param buttonValue Wert des Buttons (Double, Triple, B || Z)
     * @return GameController
     */
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


    /**
     * Verarbeitung des Klicks auf einen Button
     * Weiterleitung des Inputs an die entsprechende Methode
     * Überprüfung, ob das Spiel beendet ist
     * Gesamte Aktualisierung der View
     * @param view View, welche angeklickt wurde (Button)
     */
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
            this.view.showGame(this.model.getWinner(), this.model.getPlayerStats(), this.model.getGameSettings() );
        }

        //Update the view complete, because not much datas changed
        this.updateBoardState()
                .updatePlayerHeader()
                .updateActivePlayerHead()
                .updateSuggestion()
                .updateCurrentLegs();
    }

    /**
     * Aktualisierung der Spielerübersichten
     * Abfragen der Daten aus dem Model und Weiterleitung an die View
     * @return GameController
     */
    private GameController updatePlayerHeader(){
        for(int i = 0; i < this.model.getNumPlayers(); i++){
            view.updatePlayerHeader(i, this.model.getPlayerScore(i), this.model.getPlayerPoints(i), this.model.getPlayerSetWin(i), this.model.getPlayerLegsWin(i));
        }
        return this;
    }

    /**
     * Aktualisierung der aktiven Spielerübersicht
     * Abfragen der Daten aus dem Model und Weiterleitung an die View
     * @return GameController
     */
    private GameController updateActivePlayerHead(){
        view.updateActivePlayerHead(this.model.getCurrentPlayersTurn());
        return this;
    }

    /**
     * Aktualisierung des BoardStates (Spezialfelder)
     * Abfragen der Daten aus dem Model und Weiterleitung an die View
     * @return GameController
     */
    private GameController updateBoardState(){
        view.updateGameState(this.model.getGameMultState());
        return this;
    }

    /**
     * Aktualisierung der Checkout Vorschläge für den aktuellen Spieler
     * Abfragen der Daten aus dem Model und Weiterleitung an die View
     * @return GameController
     */
    private GameController updateSuggestion(){
        this.view.updateSuggestions( this.model.getCheckoutSuggestion(), this.model.getCheckoutType() );
        return this;
    }

    /**
     * Aktualisierung der Anzahl Legs
     * Abfragen der Daten aus dem Model und Weiterleitung an die View
     * @return GameController
     */
    private GameController updateCurrentLegs(){
        this.view.updateLegs( this.model.getLeg() );
        return this;
    }

    @Override
    public void registerController(Object controller) {
        if(controller instanceof GameListener){
            this.registerModel((GameListener) controller);
        }
    }


}
