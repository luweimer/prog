package hsos.de.prog3.throwscorer.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Player {

    private int score;

    private ArrayList<Integer> boardPoints;

    private ArrayList<String> boardPointsOutput;

    private CheckoutType checkout;
    private PlayerStats playerStats;
    private boolean partialWinFlag;

    private int legsWin;
    private int setsWin;

    private boolean win;
    private int playerName;

    private String player;

    public Player(int score, CheckoutType checkout, int numPlayer, String player){
        this.playerName = numPlayer;
        this.player = player;
        this.boardPoints = new ArrayList<Integer>();
        this.boardPointsOutput = new ArrayList<String>();
        this.playerStats = new PlayerStats(numPlayer, player);
        this.score = score;
        this.checkout = checkout;
        this.legsWin = 0;
        this.setsWin = 0;
        this.partialWinFlag = false;
    }

    public Player reset(int score){
        this.score = score;
        this.partialWinFlag = false;
        this.resetBoardPoints();
        return this;
    }

    public int getScore(){
        return this.calculateCurrentScore();
    }
    public int getLegsWin(){
        return this.legsWin;
    }
    public int getSetsWin(){
        return this.setsWin;
    }

    public String getPlayer(){
        return this.player;
    }

    public PlayerStats getPlayerStats(){
        return this.playerStats;
    }

    public ArrayList<Integer> getBoardPoints(){
        return this.boardPoints;
    }
    public ArrayList<String> getBoardPointsOutput(){
        return this.boardPointsOutput;
    }

    /**
     * @param point
     * @param state
     * @return Boolean if the point was added, false if Overthrow
     */
    public Boolean addPoint(int point, GameMultState state){

        int multPoint = multScore(point, state);

        if( this.checkUnderZero(multPoint, state) ){
            //Case - Overthrow
            this.removeLastBoardPoints();
            return false;
        }
        this.playerStats.addPoint(multPoint).addState(state);
        this.addPointOutput(point, state);

        this.boardPoints.add( multPoint );

        if(this.boardPoints.size() == 3) {
            this.playerStats.updatePoints(boardPoints);
            this.boardPoints.forEach(p -> {
                this.score -= p;
            });
            this.boardPointsOutput.forEach(p ->{
                this.playerStats.updateStats(p);
            });
            if(this.calculateCurrentScore() != 0){
                this.resetBoardPoints();
            } else {
                //Player win one leg
            }

        }

        return true;
    }

    private Player addPointOutput(int point, GameMultState state){

        if(point == 25){
            this.boardPointsOutput.add("S");
            return this;
        } else if(point == 50){
            this.boardPointsOutput.add("B");
            return this;
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
                String output = "" + point;
                this.boardPointsOutput.add(output);
            }
        }
        return this;
    }

    /**
     * Condition must be fulfilled for function call
     * this.calculateCurrentScore() == 0
     * @return
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

    //Example:
    //Legs: Win 501 Points
    //Sets: Win 2 Sets
    public boolean addPartialWin(int numLegs){
        if(!this.partialWinFlag){
            return false;
        }
        this.legsWin++;
        Log.i("Player", "addPartialWin: " + this.legsWin);
        if(this.legsWin >= numLegs){
            this.legsWin = 0;
            this.setsWin++;
            Log.i("Player", "addPartialWin: " + this.setsWin);
        }

        return true;
    }

    public boolean checkWin(int numSets){
        this.win = this.setsWin >= numSets;
        return this.win;
    }

    public int getPlayerNumber(){
        return this.playerName;
    }


    public Player removePoint(){
        if(this.boardPoints.size() == 0){
            return this;
        }

        int removePoint = this.boardPoints.remove(this.boardPoints.size() - 1);
        this.playerStats.removePoint(removePoint).removeState();
        this.boardPointsOutput.remove(this.boardPointsOutput.size() - 1);
        return this;
    }


    /**
     * @param point
     * @param state
     * @return int
     *
     * This method will multiply the point by the current state.
     *
     * This method will return the multiplied point.
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

    public ArrayList<Integer> getCheckoutSuggestion(){
        if( this.isCheckoutPossible() ) {
            return this.calculateCheckout();
        }
        return new ArrayList<>();
    }

    private ArrayList<Integer> calculateCheckout(){
        int pendingDarts = 3 - this.boardPoints.size();
        if(pendingDarts == 0){
            return new ArrayList<>();
        }

        ArrayList<Integer> checkout = new ArrayList<>();
        checkout = this.oneThrowOut();
        if(checkout.size() > 0){
            this.playerStats.addCheckout();
            return checkout;
        }

        List<Integer> possiblePoints = this.getPossiblePoints();
        int currentPoints = this.calculateCurrentScore();

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
     * Prof if checkout with one throw is possible
     * @return
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
                if(currentScore < 20){
                    checkout.add(currentScore);
                    break;
                }
            }
            case DOUBLE: {
                if(currentScore % 2 == 0 && currentScore < 40){
                    checkout.add(currentScore);
                    break;
                }
            }
            case TRIPLE: {
                if(currentScore % 3 == 0 && currentScore < 60){
                    checkout.add(currentScore);
                    break;
                }
            }
        }

        return checkout;
    }


    private boolean checkPossibleCheckout(int lastPoint){

        if(lastPoint == 0 || lastPoint == 25) return false;

        //Bulls Eye always possible
        if(lastPoint == 50) return true;

        switch (this.checkout){
            case SINGLE : {
                return true;
            }
            case DOUBLE : {
                if(lastPoint < 40 && lastPoint % 2 == 0){
                    return true;
                } else {
                    return false;
                }
            }
            case TRIPLE : {
                if(lastPoint < 60 && lastPoint % 3 == 0){
                    return true;
                } else {
                    return false;
                }
            }
            default : {
                return false;
            }
        }
    }


    private List<Integer> getPossiblePoints() {
        List<Integer> unPossiblePoints = Arrays.asList(
                23, 29, 31, 35, 37, 41, 43, 44, 46, 47, 49, 50, 52, 53, 55, 56, 58, 59
        );

        return IntStream.rangeClosed(1, 60)
                .filter(i -> !unPossiblePoints.contains(i))
                .boxed()
                .collect(Collectors.toList());
    }
    public boolean isCheckoutPossible(){
        return this.calculateCurrentScore() <= 170;
    }
    private int calculateCurrentScore(){
        int currentScore = this.score;

        for(int i = 0; i < this.boardPoints.size(); i++){
            currentScore = currentScore - this.boardPoints.get(i);
        }
        return currentScore;
    }

    /**
     *
     * @param
     * @return true under zero -> false over zero
     */
    private boolean checkUnderZero(int multPoint, GameMultState state){
        int currentScore = this.calculateCurrentScore();
        int tempScore = currentScore - multPoint;
        Log.i("Player", "checkUnderZero: " + tempScore);
        if( ( tempScore ) == 0 ) {
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

    private Player removeLastBoardPoints(){
        int size = boardPoints.size();
        if(size != this.boardPointsOutput.size()){
            Log.e("Player", "removeLastBoardPoints: Size not equal");
            return this;
        }

        for(int i = 0; i < size; i++){
            this.removePoint();
        }

        return this;
    }

    private Player resetBoardPoints(){
        this.boardPoints.clear();
        this.boardPointsOutput.clear();
        return this;
    }

    public Player serialize(){
        this.playerStats
                .setWinLegs(this.legsWin)
                .setWinSets(this.setsWin)
                .setWin(this.win);

        return this;
    }
}
