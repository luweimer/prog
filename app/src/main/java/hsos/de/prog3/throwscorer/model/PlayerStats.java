package hsos.de.prog3.throwscorer.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerStats implements Parcelable{

    private HashMap<String, Integer> stats;
    private List<GameMultState> lastStates;
    private int name;
    private int winLegs;
    private int winSets;
    private String player;
    private boolean win;

    public PlayerStats(int numPlayer, String player){
        this.stats = new HashMap<String, Integer>();
        this.player = player;
        this.name = numPlayer;
        this.lastStates = new ArrayList<GameMultState>();
        this.win = false;
        this.initMap();
    }

    public PlayerStats(String player, int name, int winLegs, int winSets, boolean win, HashMap<String, Integer> stats){
        this.player = player;
        this.name = name;
        this.winLegs = winLegs;
        this.winSets = winSets;
        this.win = win;
        this.stats = stats;
    }

    private void initMap(){
        for(int j = 0; j < 3; j++) {
            String output = (j == 0) ? "" : (j == 1) ? "D" : "T";
            for (int i = 0; i <= 20; i++) {
                this.stats.put(output + i, 0);
            }
        }
        this.stats.put("25", 0);
        this.stats.put("50", 0);
        this.stats.put("180", 0);
        this.stats.put("100", 0);
        this.stats.put("120", 0);
        this.stats.put("140", 0);
        this.stats.put("160", 0);
        this.stats.put("COUNT_SUM", 0);
        this.stats.put("SUM", 0);
        this.stats.put("S", 0);
        this.stats.put("D", 0);
        this.stats.put("T", 0);
        this.stats.put("CHECKOUT", 0);
    }

    public PlayerStats updatePoints( ArrayList<Integer> boardPoints ){
        int points = boardPoints.stream()
                .mapToInt(Integer::intValue)
                .sum();
        if(points == 180){
            this.updateStats("180");
        }
        if(points >= 160){
            this.updateStats("160");
        }
        if(points >= 140){
            this.updateStats("140");
        }
        if(points >= 120){
            this.updateStats("120");
        }
        if(points >= 100){
            this.updateStats("100");
        }

        boardPoints.forEach(p -> {
            String key = (p == 25) ? "25" : (p == 50) ? "50" : "" + p;
            this.updateStats(key);
        });

        this.lastStates.clear();

        return this;
    }
    public void updateStats(String key){
        if(this.stats.containsKey(key)){
            try {
                this.stats.put(key, this.stats.get(key) + 1);
            }catch (Exception e){
                Log.e("GameStats", "updateStats: " + key + " " + this.stats.get(key) );
            }
            Log.e("GameStats", "updateStats: " + key + " " + this.stats.get(key) );
        }
    }
    private PlayerStats updateStats(String key, int diff){
        if(this.stats.containsKey(key)){
            this.stats.put(key, this.stats.get(key) + diff);
        }
        return this;
    }

    public PlayerStats addCheckout(){
        this.updateStats("CHECKOUT");
        return this;
    }
    public PlayerStats addPoint(int point){
        this.updateStats("COUNT_SUM");
        this.updateStats("SUM", point);
        return this;
    }
    public PlayerStats addState(GameMultState state){
        this.lastStates.add(state);
        switch (state){
            case NORMAL: {
                this.updateStats("S");
                break;
            }
            case DOUBLE : {
                this.updateStats("D");
                break;
            }
            case TRIPLE : {
                this.updateStats("T");
                break;
            }
        }
        return this;
    }

    public PlayerStats removePoint(int point){
        this.updateStats("COUNT_SUM", -1);
        this.updateStats("SUM", point * -1);
        return this;
    }
    public PlayerStats removeState(){
        GameMultState state = this.lastStates.get(this.lastStates.size() - 1);
        switch (state){
            case NORMAL: {
                this.updateStats("S", -1);
                break;
            }
            case DOUBLE : {
                this.updateStats("D", -1);
                break;
            }
            case TRIPLE : {
                this.updateStats("T", -1);
                break;
            }
        }
        return this;
    }
    public PlayerStats setWinLegs(int legs){
        this.winLegs = legs;
        return this;
    }

    public PlayerStats setWinSets(int sets){
        this.winSets = sets;
        return this;
    }
    public PlayerStats setWin(boolean win){
        this.win = win;
        return this;
    }

    private int getValueFromHash(String id){
        try{
            return this.stats.get(id);
        } catch (Exception e){
            return -1;
        }
    }

    public String getName(){
        return String.valueOf(this.name);
    }
    public int getSumScore(){
        return this.getValueFromHash("SUM");
    }
    public int getCountSum(){return this.getValueFromHash("COUNT_SUM");}
    public int get180(){
        return this.getValueFromHash("180");
    }
    public int get160(){
        return this.getValueFromHash("160");
    }
    public int get140(){
        return this.getValueFromHash("140");
    }
    public int get120(){
        return this.getValueFromHash("120");
    }
    public int get100(){
        return this.getValueFromHash("100");
    }
    public int getBull(){
        return this.getValueFromHash("50");
    }
    public int getsBull(){
        return this.getValueFromHash("25");
    }
    public int getSingleThrow(){
        return this.getValueFromHash("S");
    }
    public int getDoubleThrow(){
        return this.getValueFromHash("D");
    }
    public int getTripleThrow(){
        return this.getValueFromHash("T");
    }

    public String getPlayer(){
        return this.player;
    }

    public int getWinLegs(){
        return this.winLegs;
    }
    public int getWinSets(){
        return this.winSets;
    }
    public boolean getWin() {
        return win;
    }

    public HashMap<String, Integer> getStats(){
        return this.stats;
    }

    public double getAvg(){
        try{
            int count = this.stats.get("COUNT_SUM");
            int points = this.stats.get("SUM");
            return (count == 0) ? 0 : (double) points / count;
        } catch (Exception e){
            return 0;
        }
    }
    public double getCheckout(){
        try{
            int checkout = this.stats.get("CHECKOUT");
            int points = this.stats.get("SUM");
            return (checkout == 0) ? 0 : (double) points / checkout;
        } catch (Exception e){
            return 0;
        }
    }

    public String toString(){
        String output = "";
        for(int j = 0; j < 3; j++) {
            String output2 = (j == 0) ? "" : (j == 1) ? "D" : "T";
            for (int i = 0; i <= 20; i++) {
                output += output2 + i + ": " + this.stats.get(output2 + i) + "\n";
            }
        }
        output += "25: " + this.stats.get("25") + "\n";
        output += "50: " + this.stats.get("50") + "\n";
        output += "180: " + this.stats.get("180") + "\n";
        output += "100: " + this.stats.get("100") + "\n";
        output += "120: " + this.stats.get("120") + "\n";
        output += "140: " + this.stats.get("140") + "\n";
        output += "160: " + this.stats.get("160") + "\n";
        output += "COUNT_SUM: " + this.stats.get("COUNT_SUM") + "\n";
        output += "SUM: " + this.stats.get("SUM") + "\n";
        output += "S: " + this.stats.get("S") + "\n";
        output += "D: " + this.stats.get("D") + "\n";
        output += "T: " + this.stats.get("T") + "\n";
        output += "CHECKOUT: " + this.stats.get("CHECKOUT") + "\n";
        output += "AVG: " + this.getAvg() + "\n";
        return output;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(this.name);
        parcel.writeInt(this.winLegs);
        parcel.writeInt(this.winSets);
        parcel.writeString(this.player);
        parcel.writeByte((byte) (this.win ? 1 : 0));
        parcel.writeSerializable((Serializable) this.stats);
    }

    public static final Parcelable.Creator<PlayerStats> CREATOR = new Parcelable.Creator<PlayerStats>() {
        @Override
        public PlayerStats createFromParcel(Parcel in) {
            return new PlayerStats(in);
        }

        @Override
        public PlayerStats[] newArray(int size) {
            return new PlayerStats[size];
        }
    };

    private PlayerStats(Parcel in) {
        this.name = in.readInt();
        this.winLegs = in.readInt();
        this.winSets = in.readInt();
        this.player = in.readString();
        this.win = in.readByte() != 0;
        this.stats = (HashMap<String, Integer>) in.readSerializable();
    }





}
