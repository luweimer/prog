package hsos.de.prog3.throwscorer.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * PlayerStats
 * Speicherung der Statistiken eines Spielers
 * Implementiert Parcelable, um Objekt zwischen Activities zu uebergeben
 * @author Lucius Weimer
 */
public class PlayerStats implements Parcelable{

    private HashMap<String, Integer> stats;
    private List<GameMultState> lastStates;
    private int name;
    private int winLegs;
    private int winSets;
    private String player;
    private boolean win;

    /**
     * Standardkonstruktor
     * @param numPlayer Spielernummer
     * @param player Spielernamen
     * Initialisiert die HashMap mit den benoetigten Keys
     */
    public PlayerStats(int numPlayer, String player){
        this.stats = new HashMap<String, Integer>();
        this.player = player;
        this.name = numPlayer;
        this.lastStates = new ArrayList<GameMultState>();
        this.win = false;
        this.initMap();
    }

    /**
     * Konstruktor
     * @param player Spielernamen
     * @param name Spielernummer
     * @param winLegs Anzahl gewonnener Legs
     * @param winSets Anzahl gewonnener Sets
     * @param win Gewonnen oder nicht
     * @param stats HashMap mit den Statistiken
     */
    public PlayerStats(String player, int name, int winLegs, int winSets, boolean win, HashMap<String, Integer> stats){
        this.player = player;
        this.name = name;
        this.winLegs = winLegs;
        this.winSets = winSets;
        this.win = win;
        this.stats = stats;
    }

    /**
     * Initialisiert die HashMap mit den benoetigten Keys
     * Keys: 1-20, 25, 50, 180, 100, 120, 140, 160, COUNT_SUM, SUM, S, D, T
     */
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
    }

    /**
     * Aktualisiert die Statistiken
     * Berechnet die Summe der geworfenen Punkte und aktualisiert die Statistiken
     * @param boardPoints geworfene Punkte auf dem Board
     * @return PlayerStats
     */
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

    /**
     * Aktualisiert die Statistiken
     * Erhoeht den Wert des Keys um 1
     * @param key Key
     */
    public void updateStats(String key){
        if(this.stats.containsKey(key)){
            try {
                this.stats.put(key, this.stats.get(key) + 1);
                return;
            }catch (Exception e){
                Log.e("GameStats", "updateStats: " + key + " " + this.stats.get(key) );
            }
        }
        Log.e("GameStats", "updateStats: " + key + " " + this.stats.get(key) );
    }

    /**
     * Aktualisiert die Statistiken
     * @param key Key
     * @param diff Wert um den der Key erhoeht werden soll
     * @return PlayerStats
     */
    private PlayerStats updateStats(String key, int diff){
        if(this.stats.containsKey(key)){
            this.stats.put(key, this.stats.get(key) + diff);
        }
        return this;
    }

    /**
     * Aktualisiert die Statistiken
     * Erhoeht die Anzahl der geworfenen Punkte und die Summe der geworfenen Punkte
     * @param point geworfene Punkte
     * @return PlayerStats
     */
    public PlayerStats addPoint(int point){
        this.updateStats("COUNT_SUM");
        this.updateStats("SUM", point);
        return this;
    }

    /**
     * Aktualisiert die Statistiken
     * Erhoeht die Anzahl der geworfenen Modi (Single, Double, Triple)
     * @param state geworfener Modus
     * @return PlayerStats
     */
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

    /**
     * Aktualisiert die Statistiken
     * Loescht den Punkt aus der Summe der geworfenen Punkte und verringert die Anzahl der geworfenen Punkte
     * @param point
     * @return
     */
    public PlayerStats removePoint(int point){
        this.updateStats("COUNT_SUM", -1);
        this.updateStats("SUM", point * -1);
        return this;
    }

    /**
     * Aktualisiert die Statistiken
     * Loescht den Modus aus der Anzahl der geworfenen Modi
     * @return PlayerStats
     */
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

    /**
     * Setzt die Anzahl der gewonnenen Legs
     * @param legs Anzahl gewonnener Legs
     * @return PlayerStats
     */
    public PlayerStats setWinLegs(int legs){
        this.winLegs = legs;
        return this;
    }

    /**
     * Setzt die Anzahl der gewonnenen Sets
     * @param sets Anzahl gewonnener Sets
     * @return PlayerStats
     */
    public PlayerStats setWinSets(int sets){
        this.winSets = sets;
        return this;
    }

    /**
     * Setzt ob der Spieler gewonnen hat
     * @param win gewonnen oder nicht
     * @return PlayerStats
     */
    public PlayerStats setWin(boolean win){
        this.win = win;
        return this;
    }

    /**
     * Gibt den Wert des Keys zurueck
     * @param id Key
     * @return Wert des Keys
     */
    private int getValueFromHash(String id){
        try{
            return this.stats.get(id);
        } catch (Exception e){
            Log.e("PlayerStats", "Error get Value from Hash: " + id);
            return 0;
        }
    }

    /**
     * Gibt den Spielernamen zurueck
     * @return Spielernamen
     */
    public String getName(){
        return String.valueOf(this.name);
    }

    /**
     * Gibt die Spielernummer zurueck
     * @return Spielernummer
     */
    public int getPlayerNumber(){
        return this.name;
    }

    /**
     * Gibt die Anzahl der Summe der geworfenen Punkte zurueck
     * @return Summe der geworfenen Punkte
     */
    public int getSumScore(){
        return this.getValueFromHash("SUM");
    }

    /**
     * Gibt die Anzahl der geworfenen Punkte zurueck
     * @return Anzahl der geworfenen Punkte
     */
    public int getCountSum(){return this.getValueFromHash("COUNT_SUM");}

    /**
     * Gibt die Anzahl der Durchlaeufe, in denen der Spieler 180 geworfen hat zurueck
     * @return
     */
    public int get180(){
        return this.getValueFromHash("180");
    }

    /**
     * Gibt die Anzahl der Durchlaeufe, in denen der Spieler 160 geworfen hat zurueck
     * @return Anzahl der Durchlaeufe, in denen der Spieler 160 geworfen hat
     */
    public int get160(){
        return this.getValueFromHash("160");
    }

    /**
     * Gibt die Anzahl der Durchlaeufe, in denen der Spieler 140 geworfen hat zurueck
     * @return Anzahl der Durchlaeufe, in denen der Spieler 140 geworfen hat
     */
    public int get140(){
        return this.getValueFromHash("140");
    }

    /**
     * Gibt die Anzahl der Durchlaeufe, in denen der Spieler 120 geworfen hat zurueck
     * @return Anzahl der Durchlaeufe, in denen der Spieler 120 geworfen hat
     */
    public int get120(){
        return this.getValueFromHash("120");
    }

    /**
     * Gibt die Anzahl der Durchlaeufe, in denen der Spieler 100 geworfen hat zurueck
     * @return Anzahl der Durchlaeufe, in denen der Spieler 100 geworfen hat
     */
    public int get100(){
        return this.getValueFromHash("100");
    }

    /**
     * Gibt die Anzahl der geworfenen Bulls-Eye zurueck
     * @return Anzahl der geworfenen Bulls-Eye
     */
    public int getBull(){
        return this.getValueFromHash("50");
    }

    /**
     * Gibt die Anzahl der geworfenen Single-Bulls zurueck
     * @return Anzahl der geworfenen Single-Bulls
     */
    public int getsBull(){
        return this.getValueFromHash("25");
    }

    /**
     * Gibt die Anzahl der geworfenen Single zurueck
     * @return Anzahl der geworfenen Single
     */
    public int getSingleThrow(){
        return this.getValueFromHash("S");
    }

    /**
     * Gibt die Anzahl der geworfenen Double zurueck
     * @return Anzahl der geworfenen Double
     */
    public int getDoubleThrow(){
        return this.getValueFromHash("D");
    }

    /**
     * Gibt die Anzahl der geworfenen Triple zurueck
     * @return Anzahl der geworfenen Triple
     */
    public int getTripleThrow(){
        return this.getValueFromHash("T");
    }

    /**
     * Gibt den Spielernamen zurueck
     * @return Spielernamen
     */
    public String getPlayer(){
        return this.player;
    }

    /**
     * Gibt die Anzahl der gewonnenen Legs zurueck
     * @return Anzahl der gewonnenen Legs
     */
    public int getWinLegs(){
        return this.winLegs;
    }

    /**
     * Gibt die Anzahl der gewonnenen Sets zurueck
     * @return Anzahl der gewonnenen Sets
     */
    public int getWinSets(){
        return this.winSets;
    }

    /**
     * Gibt zurueck ob der Spieler gewonnen hat
     * @return gewonnen, true oder false
     */
    public boolean getWin() {
        return win;
    }

    /**
     * Gibt die HashMap mit den Statistiken zurueck
     * @return HashMap mit den Statistiken
     */
    public HashMap<String, Integer> getStats(){
        return this.stats;
    }

    /**
     * Gibt den Durchschnitt der geworfenen Punkte zurueck
     * @return Durchschnitt der geworfenen Punkte
     */
    public double getAvg(){
        try{
            int count = this.stats.get("COUNT_SUM");
            int points = this.stats.get("SUM");
            return (count == 0) ? 0 : (double) points / count;
        } catch (NullPointerException e){
            Log.e("PlayerStats", "Error get AVG" );
            return 0;
        }
    }

    /**
     * Gibt die Statistiken als String zurueck
     * @return Statistiken als String
     */
    @NonNull
    public String toString(){
        StringBuilder output = new StringBuilder();
        for(int j = 0; j < 3; j++) {
            String output2 = (j == 0) ? "" : (j == 1) ? "D" : "T";
            for (int i = 0; i <= 20; i++) {
                output.append(output2).append(i).append(": ").append(this.stats.get(output2 + i)).append("\n");
            }
        }
        output.append("25: ").append(this.stats.get("25")).append("\n");
        output.append("50: ").append(this.stats.get("50")).append("\n");
        output.append("180: ").append(this.stats.get("180")).append("\n");
        output.append("100: ").append(this.stats.get("100")).append("\n");
        output.append("120: ").append(this.stats.get("120")).append("\n");
        output.append("140: ").append(this.stats.get("140")).append("\n");
        output.append("160: ").append(this.stats.get("160")).append("\n");
        output.append("COUNT_SUM: ").append(this.stats.get("COUNT_SUM")).append("\n");
        output.append("SUM: ").append(this.stats.get("SUM")).append("\n");
        output.append("S: ").append(this.stats.get("S")).append("\n");
        output.append("D: ").append(this.stats.get("D")).append("\n");
        output.append("T: ").append(this.stats.get("T")).append("\n");
        output.append("AVG: ").append(this.getAvg()).append("\n");
        return output.toString();
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

    public static final Creator<PlayerStats> CREATOR = new Creator<PlayerStats>() {
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
