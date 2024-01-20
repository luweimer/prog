package hsos.de.prog3.throwscorer.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class GameSettings implements Parcelable {

    private int startScore;
    private int numPlayers;
    private int numLegs;
    private int numSets;
    private CheckoutType checkoutType;

    private String[] playerNames;

    public GameSettings(){
        this.startScore = 501;
        this.numPlayers = 2;
        this.numLegs = 3;
        this.numSets = 3;
        this.checkoutType = CheckoutType.DOUBLE;
        this.playerNames = new String[numPlayers];
        this.playerNames[0] = "Player 1";
        this.playerNames[1] = "Player 2";
    }

    public GameSettings(int startScore, int numPlayers, int numLegs, int numSets, CheckoutType checkoutType){
        this.startScore = startScore;
        this.numPlayers = numPlayers;
        this.numLegs = numLegs;
        this.numSets = numSets;
        this.playerNames = new String[numPlayers];
        this.checkoutType = checkoutType;
    }

    public int getStartScore(){
        return this.startScore;
    }
    public int getNumPlayers(){
        return this.numPlayers;
    }
    public int getNumLegs(){
        return this.numLegs;
    }
    public int getNumSets(){
        return this.numSets;
    }

    public String[] getPlayerNames(){
        return this.playerNames;
    }

    public void setPlayerNames(String[] playerNames){

        this.playerNames = playerNames;
    }

    public CheckoutType getCheckoutType(){
        return this.checkoutType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(this.startScore);
        parcel.writeInt(this.numPlayers);
        parcel.writeInt(this.numLegs);
        parcel.writeInt(this.numSets);
        parcel.writeSerializable(this.playerNames);
        parcel.writeSerializable(this.checkoutType);

    }

    public static final Creator<GameSettings> CREATOR = new Creator<GameSettings>() {
        @Override
        public GameSettings createFromParcel(Parcel in) {
            return new GameSettings(in);
        }

        @Override
        public GameSettings[] newArray(int size) {
            return new GameSettings[size];
        }
    };

    private GameSettings(Parcel in) {
        startScore = in.readInt();
        numPlayers = in.readInt();
        numLegs = in.readInt();
        numSets = in.readInt();
        playerNames = (String[]) in.readSerializable();
        checkoutType = (CheckoutType) in.readSerializable();
    }

}
