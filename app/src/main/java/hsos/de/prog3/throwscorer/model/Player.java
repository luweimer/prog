package hsos.de.prog3.throwscorer.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Player
 * Speichert die Daten eines Spielers
 * Verwaltet PlayerStats-Objekt
 * @author Lucius Weimer
 */
public class Player {

    private final int playerName;
    private final String player;
    private final CheckoutType checkout;
    private final ArrayList<Integer> boardPoints;
    private final ArrayList<String> boardPointsOutput;
    private final PlayerStats playerStats;

    private int score;
    private boolean partialWinFlag;
    private int legsWin;
    private int allLegsWin;
    private int setsWin;
    private boolean win;


    /**
     * Konstruktor
     * @param score Startscore
     * @param checkout CheckoutType
     * @param numPlayer Spielernummer
     * @param player Spielernamen
     */
    public Player(int score, CheckoutType checkout, int numPlayer, String player){
        this.playerName = numPlayer;
        this.player = player;
        this.boardPoints = new ArrayList<>();
        this.boardPointsOutput = new ArrayList<>();
        this.playerStats = new PlayerStats(numPlayer, player);
        this.score = score;
        this.checkout = checkout;
        this.legsWin = 0;
        this.allLegsWin = 0;
        this.setsWin = 0;
        this.partialWinFlag = false;
    }

    /**
     * Zuruecksetzen des Spielers
     * Startscore, partialWinFlag, Reset der BoardPoints
     * @param score
     */
    public void reset(int score){
        this.score = score;
        this.partialWinFlag = false;
        this.resetBoardPoints();
    }

    /**
     * Getter fuer den aktuellen Score
     * @return aktueller Score
     */
    public int getScore(){
        return this.calculateCurrentScore();
    }

    /**
     * Getter fuer die Anzahl gewonnener Legs
     * @return Anzahl gewonnener Legs
     */
    public int getLegsWin(){
        return this.legsWin;
    }

    /**
     * Getter fuer die Anzahl gewonnener Sets
     * @return Anzahl gewonnener Sets
     */
    public int getSetsWin(){
        return this.setsWin;
    }

    /**
     * Getter fuer den Spielernamen
     * @return Spielernamen
     */
    public String getPlayer(){
        return this.player;
    }

    /**
     * Getter fuer die Spielerstatistiken
     * @return Spielerstatistiken
     */
    public PlayerStats getPlayerStats(){
        return this.playerStats;
    }

    /**
     * Getter fuer die BoardPoints
     * @return BoardPoints
     */
    public ArrayList<Integer> getBoardPoints(){
        return this.boardPoints;
    }

    /**
     * Getter fuer die BoardPointsOutput
     * @return BoardPointsOutput
     */
    public ArrayList<String> getBoardPointsOutput(){
        return this.boardPointsOutput;
    }

    /**
     * Fuegt einen Punkt dem Spieler hinzu
     * Prueft auf Overthrow
     * Aktualisiert die BoardPoints
     * Aktuelisiert den Score bei 3 BoardPoints und leert die BoardPoints
     * Aktualisiert die PlayerStats bei 3 BoardPoints
     * @param point aktuell geworfener Punkt
     * @param state aktueller State (Multiplikator)
     * @return boolean, true wenn der Punkt hinzugefuegt wurde, false wenn Overthrow
     */
    public Boolean addPoint(int point, GameMultState state){

        int multPoint = multScore(point, state);

        if( this.checkUnderZero(multPoint, state) ){
            Log.i("Player", "addPoint: Overthrow");
            //Case - Overthrow
            this.removeLastBoardPoints();
            return false;
        }
        Log.e("Player", "Multpoint:" + multPoint + " Current: " + this.calculateCurrentScore());
        this.playerStats.addPoint(multPoint).addState(state);
        this.addPointOutput(point, state);

        this.boardPoints.add( multPoint );

        if(this.boardPoints.size() == 3) {
            this.playerStats.updatePoints(boardPoints);
            this.boardPoints.forEach(p -> {
                this.score -= p;
            });
            //Lamda?
            this.boardPointsOutput.forEach(p ->{
                this.playerStats.updateStats(p);
            });
            this.resetBoardPoints();

        }

        return true;
    }

    /**
     * Fuegt einen Punkt dem Output Array hinzu
     * Fuegt das Vorzeichen hinzu (D, T)
     * Fuegt S oder B hinzu bei 25 oder 50 Punkten
     * @param point aktuell geworfener Punkt
     * @param state aktueller State
     */
    private void addPointOutput(int point, GameMultState state){

        if(point == 25){
            this.boardPointsOutput.add("S");
            return;
        } else if(point == 50){
            this.boardPointsOutput.add("B");
            return;
        }

        switch(state){
            case DOUBLE : {
                String output = "D" + point;
                this.boardPointsOutput.add(output);
                break;
            }
            case TRIPLE : {
                String output = "T" + point;
                this.boardPointsOutput.add(output);
                break;
            }
            default : {
                String output = String.valueOf(point);
                this.boardPointsOutput.add(output);
            }
        }
    }

    /**
     * Prueft ob ein PartialWin moeglich ist - Leg
     * Setzt die winFlag
     * @param state aktueller State
     * @return true wenn PartialWin moeglich, false wenn nicht
     */
    public boolean checkPartialWin(GameMultState state){
        if(this.checkout == CheckoutType.SINGLE){
            this.partialWinFlag = true;
            return true;
        } else if(this.checkout == CheckoutType.DOUBLE && state == GameMultState.DOUBLE){
            this.partialWinFlag = true;
            return true;
        } else if(this.checkout == CheckoutType.TRIPLE && state == GameMultState.TRIPLE){
            this.partialWinFlag = true;
            return true;
        }
        return false;
    }

    /**
     * Fuegt einen PartialWin hinzu, wenn die winFlag gesetzt ist
     * @param numLegs Anzahl Legs, die fuer ein Set notwendig sind
     * @return true wenn PartialWin hinzugefuegt wurde, false wenn nicht
     */
    public boolean addPartialWin(int numLegs){
        if(!this.partialWinFlag){
            return false;
        }
        this.legsWin++;
        this.allLegsWin++;
        Log.i("Player", "addPartialWin: " + this.legsWin);
        if(this.legsWin >= numLegs){
            this.legsWin = 0;
            this.setsWin++;
            Log.i("Player", "addPartialWin: " + this.setsWin);
        }

        return true;
    }

    /**
     * Prueft ob der Spieler gewonnen hat - Vergleich mit der Anzahl der Sets
     * @param numSets Anzahl Sets, die fuer den Sieg notwendig sind
     * @return true wenn gewonnen, false wenn nicht
     */
    public boolean checkWin(int numSets){
        this.win = this.setsWin >= numSets;
        return this.win;
    }

    /**
     * Getter fuer die Spielernummer
     * @return Spielernummer
     */
    public int getPlayerNumber(){
        return this.playerName;
    }

    /**
     * Entfernt den letzten Punkt aus den BoardPoints
     * Entfernt den letzten Punkt aus den BoardPointsOutput
     * Aktualisiert die PlayerStats
     */
    public void removePoint(){
        if(this.boardPoints.size() == 0){
            return;
        }

        int removePoint = this.boardPoints.remove(this.boardPoints.size() - 1);
        this.playerStats.removePoint(removePoint).removeState();
        this.boardPointsOutput.remove(this.boardPointsOutput.size() - 1);
    }


    /**
     * Multipliziert den Punkt mit dem State
     * Double -> * 2
     * Triple -> * 3
     * Single -> return point
     * @param point current point
     * @param state current state
     * @return int multPoint
     */
    private int multScore(int point, GameMultState state){
        switch(state){
            case DOUBLE : {
                return point * 2;
            }
            case TRIPLE : {
                return point * 3;
            }
            default : {
                return point;
            }
        }
    }

    /**
     * Prueft ob ein Checkout moeglich ist und berechnet diesen
     * @return ArrayList<Integer> mit Checkout, wenn nicht moeglich leer
     */
    public ArrayList<Integer> getCheckoutSuggestion(){
        if( this.isCheckoutPossible() ) {
            return this.calculateCheckout();
        }
        return new ArrayList<>();
    }

    /**
     * Berechnet den Checkout
     * Prueft ob ein Checkout mit einem Wurf moeglich ist
     * @return ArrayList<Integer> mit Checkout, wenn nicht moeglich leer
     */
    private ArrayList<Integer> calculateCheckout(){
        int pendingDarts = 3 - this.boardPoints.size();
        if(pendingDarts == 0){
            return new ArrayList<>();
        }

        ArrayList<Integer> checkout = new ArrayList<>();
        int currentPoints = this.calculateCurrentScore();
        checkout = this.oneThrowOut();
        if(checkout.size() == 1){
            return checkout;
        }

        List<Integer> possiblePoints = this.getPossiblePoints();


        for(int f : possiblePoints){
            if(f == currentPoints && this.checkPossibleCheckout(f)){
                checkout.add(f);
                return checkout;
            }
            if(pendingDarts == 1) continue;
            for(int s : possiblePoints){
                if(f + s == currentPoints && this.checkPossibleCheckout(s)){
                    checkout.add(f);
                    checkout.add(s);
                    return checkout;
                }
                if(pendingDarts == 2) continue;
                for(int t : possiblePoints){
                    if(f + s + t == currentPoints && this.checkPossibleCheckout(t)){
                        checkout.add(f);
                        checkout.add(s);
                        checkout.add(t);
                        return checkout;
                    }
                }
            }
        }


        return new ArrayList<>();
    }

    /**
     * Prueft ob ein Checkout mit einem Wurf moeglich ist
     * @return ArrayList<Integer> mit Checkout, wenn nicht moeglich leer
     */
    private ArrayList<Integer> oneThrowOut(){
        ArrayList<Integer> checkout = new ArrayList<>();
        int currentScore = this.calculateCurrentScore();

        if(currentScore == 50){
            checkout.add(50);
            return checkout;
        }

        switch (this.checkout){
            case SINGLE: {
                if(currentScore <= 20){
                    Log.d("Player", "Single");
                    checkout.add(currentScore);
                }
                break;
            }
            case DOUBLE: {
                if(currentScore % 2 == 0 && currentScore <= 40){
                    Log.d("Player", "Double");
                    checkout.add(currentScore);
                }
                break;
            }
            case TRIPLE: {
                if(currentScore % 3 == 0 && currentScore <= 60){
                    Log.d("Player", "Triple");
                    checkout.add(currentScore);
                }
                break;
            }
        }

        return checkout;
    }

    /**
     * Prueft ob ein Checkout moeglich ist
     * @param lastPoint letzter Punkt bzw. Wurf
     * @return true wenn moeglich, false wenn nicht
     */
    private boolean checkPossibleCheckout(int lastPoint){

        if(lastPoint == 0 || lastPoint == 25) return false;

        //Bulls Eye always possible
        if(lastPoint == 50) return true;

        switch (this.checkout){
            case SINGLE : {
                return true;
            }
            case DOUBLE : {
                return lastPoint < 40 && lastPoint % 2 == 0;
            }
            case TRIPLE : {
                return lastPoint < 60 && lastPoint % 3 == 0;
            }
            default : {
                return false;
            }
        }
    }

    /**
     * Gibt eine Liste mit moeglichen Punkten zurueck, welche geworfen werden koennen
     * @return List<Integer> mit moeglichen Punkten
     */
    private List<Integer> getPossiblePoints() {
        List<Integer> unPossiblePoints = Arrays.asList(
                23, 29, 31, 35, 37, 41, 43, 44, 46, 47, 49, 50, 52, 53, 55, 56, 58, 59
        );

        return IntStream.rangeClosed(1, 60)
                .filter(i -> !unPossiblePoints.contains(i))
                .boxed()
                .collect(Collectors.toList());
    }
    /**
     * Prueft ob ein Checkout moeglich ist
     * @return true wenn moeglich, false wenn nicht
     */
    public boolean isCheckoutPossible(){
        int currentScore = this.calculateCurrentScore();

        if(currentScore == 50){
            return true;
        }

        int pendingDarts = 3 - this.boardPoints.size();
        int multPendingDarts = Math.max(0, pendingDarts - 1);

        switch (this.checkout){
            case SINGLE : {
                return ( currentScore - (multPendingDarts * 60 + 20) ) <= 0;
            }
            case DOUBLE : {
                return ( currentScore - (multPendingDarts * 60 + 40) ) <= 0;
            }
            case TRIPLE : {
                return ( currentScore - (multPendingDarts * 60 + 60) ) <= 0;
            }
        }
        return false;
    }

    /**
     * Berechnet den aktuellen Score, nach jedem Wurf
     * @return int currentScore
     */
    private int calculateCurrentScore(){
        int currentScore = this.score;

        for(int i = 0; i < this.boardPoints.size(); i++){
            currentScore = currentScore - this.boardPoints.get(i);
        }
        return currentScore;
    }

    /**
     * @param multPoint aktueller Punkt (bereits multipliziert)
     * @param state aktueller State
     * @return true wenn der Score unter 0 ist, false wenn nicht
     */
    private boolean checkUnderZero(int multPoint, GameMultState state){
        int currentScore = this.calculateCurrentScore();
        int tempScore = currentScore - multPoint;
        Log.i("Player", "checkUnderZero: " + tempScore + " multPoint" + multPoint);
        if( tempScore == 0 ) {
            if(multPoint == 50) {
                this.partialWinFlag = true;
                return false; //Bulls Eye always win
            }
            boolean checkWin = this.checkPartialWin(state);
            return !checkWin;
        }
        switch (this.checkout){
            case SINGLE: {
                return ( ( tempScore ) < 0 );
            }
            case DOUBLE: {
                return ( ( tempScore ) <= 1 );
            }
            case TRIPLE: {
                return ( ( tempScore ) <= 2 );
            }
            default: return false;
        }
    }

    /**
     * Entfernt die letzten BoardPoints
     */
    private void removeLastBoardPoints(){
        int size = boardPoints.size();
        if(size != this.boardPointsOutput.size()){
            Log.e("Player", "removeLastBoardPoints: Size not equal");
            return;
        }

        for(int i = 0; i < size; i++){
            this.removePoint();
        }

    }

    /**
     * Leert die Liste der BoardPoints
     */
    private void resetBoardPoints(){
        this.boardPoints.clear();
        this.boardPointsOutput.clear();
    }

    /**
     * Serialisiert die Daten des Spielers in die PlayerStats, wenn das Spiel beendet ist
     */
    public void serialize(){
        Log.i("Player", "serialize: Legs: " + this.legsWin + " Sets: " + this.setsWin + " Win: " + this.win + " Name: " + this.playerName);
        this.playerStats
                .setWinLegs(this.allLegsWin)
                .setWinSets(this.setsWin)
                .setWin(this.win);

    }
}
