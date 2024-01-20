package hsos.de.prog3.throwscorer.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.listener.view.PlayerNameViewListener;

public class PlayerNameView implements PlayerNameViewListener {

    private LinearLayout playerLayout;
    private TextView playerName;
    private EditText player;


    public PlayerNameView(Context context, GridLayout playerName){
        this.playerLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.playername_player, playerName, false);
        playerName.addView(this.playerLayout);
        this.init();
    }

    private void init(){
        this.registerViewElements();
    }

    private PlayerNameView registerViewElements(){
        this.playerName = this.playerLayout.findViewById(R.id.tv_pn_playername);
        this.player = this.playerLayout.findViewById(R.id.et_pn_playername);
        return this;
    }

    public void setPlayerName(String name){
        this.playerName.setText(name);
    }

    @Override
    public String getPlayerName(){
        return this.player.getText().toString();
    }


}
