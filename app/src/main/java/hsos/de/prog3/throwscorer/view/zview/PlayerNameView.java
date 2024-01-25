package hsos.de.prog3.throwscorer.view.zview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.listener.view.PlayerNameViewListener;

/**
 * PlayerNameView
 * Stellt die einzelnen Eingabe-Felder für die Spielernamen dar
 * Autor: Lucius Weimer
 */
public class PlayerNameView implements PlayerNameViewListener {

    private LinearLayout playerLayout;
    private TextView playerName;
    private EditText player;

    /**
     * Konstruktor
     * Fügt die View dem übergeordneten GridLayout hinzu
     * @param context Kontext der App
     * @param playerName übergeordnetes GridLayout
     */
    public PlayerNameView(Context context, GridLayout playerName){
        this.playerLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.playername_player, playerName, false);
        playerName.addView(this.playerLayout);
        this.init();
    }

    /**
     * Initialisiert die View-Elemente
     */
    private void init(){
        this.registerViewElements();
    }

    /**
     * Registriert die View-Elemente
     * @return PlayerNameView
     */
    private PlayerNameView registerViewElements(){
        this.playerName = this.playerLayout.findViewById(R.id.tv_pn_playername);
        this.player = this.playerLayout.findViewById(R.id.et_pn_playername);
        return this;
    }

    /**
     * Setzt die Überschrift für den jeweiligen Spieler
     * @param name Name des Spielers
     */
    public void setPlayerName(String name){
        this.playerName.setText(name);
    }

    /**
     * Gibt den Namen des eingebenen Spielers zurück
     * @return Name des Spielers
     */
    @Override
    public String getPlayerName(){
        return this.player.getText().toString();
    }


}
