package hsos.de.prog3.throwscorer.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public void reset(int score){
        this.score = score;
        this.partialWinFlag = false;
        this.resetBoardPoints();
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
     * @param point current point
     * @param state current state
     * @return Boolean if the point was added, false if Overthrow
     */
    public Boolean addPoint(int point, GameMultState state){

        int multPoint = multScore(point, state);

        if( this.checkUnderZero(multPoint, state) ){
            Log.i("Player", "addPoint: Overthrow");
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
            //Lamda?
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
     * Condition must be fulfilled for function call
     * this.calculateCurrentScore() == 0
     * @return true if win
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
        this.allLegsWin++;
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


    public void removePoint(){
        if(this.boardPoints.size() == 0){
            return;
        }

        int removePoint = this.boardPoints.remove(this.boardPoints.size() - 1);
        this.playerStats.removePoint(removePoint).removeState();
        this.boardPointsOutput.remove(this.boardPointsOutput.size() - 1);
    }


    /**
     * @param point current point
     * @param state current state
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
     * Prof if checkout with one throw is possible
     * @return ArrayList<Integer> with one throw checkout
     */
    private ArrayList<Integer> oneThrowOut(){
        ArrayList<Integer> checkout = new ArrayList<>();
        int currentScore = this.calculateCurrentScore();
        Log.i("Player", "oneThrowOut: " + currentScore);
        Log.i("Player", "oneThrowOut: " + this.checkout);

        if(currentScore == 50){
            checkout.add(50);
            return checkout;
        }

        switch (this.checkout){
            case SINGLE: {
                if(currentScore < 20){
                    Log.d("Player", "Single");
                    checkout.add(currentScore);
                }
                break;
            }
            case DOUBLE: {
                if(currentScore % 2 == 0 && currentScore < 40){
                    Log.d("Player", "Double");
                    checkout.add(currentScore);
                }
                break;
            }
            case TRIPLE: {
                if(currentScore % 3 == 0 && currentScore < 60){
                    Log.d("Player", "Triple");
                    checkout.add(currentScore);
                }
                break;
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
     * @param state current state
     * @param multPoint current point
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

    private void resetBoardPoints(){
        this.boardPoints.clear();
        this.boardPointsOutput.clear();
    }

    public void serialize(){
        Log.i("Player", "serialize: Legs: " + this.legsWin + " Sets: " + this.setsWin + " Win: " + this.win + " Name: " + this.playerName);
        this.playerStats
                .setWinLegs(this.allLegsWin)
                .setWinSets(this.setsWin)
                .setWin(this.win);

    }
}
