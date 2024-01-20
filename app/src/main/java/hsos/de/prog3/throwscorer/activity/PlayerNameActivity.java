package hsos.de.prog3.throwscorer.activity;

import static hsos.de.prog3.throwscorer.utility.Router.startGame;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.listener.view.PlayerNameViewListener;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.view.PlayerNameView;

public class PlayerNameActivity extends AppCompatActivity {

    private GridLayout playerNames;
    private Button submit;

    private PlayerNameViewListener[] playerName;

    private GameSettings gameSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playername);
        this.init();
        this.handleIncomingIntent();
        this.createPlayerViews();
    }

    private void init(){
        this.registerViewElements().registerBtn();
    }

    private PlayerNameActivity registerViewElements(){
        this.playerNames = findViewById(R.id.gl_pn_playername);
        this.submit = findViewById(R.id.btn_pn_submit);
        return this;
    }

    private PlayerNameActivity registerBtn(){
        this.submit.setOnClickListener(v -> this.handleSubmit());
        return this;
    }

    private void handleIncomingIntent() {
        this.gameSettings = getIntent().getParcelableExtra("GameSettings");
    }

    private void handleSubmit(){
        String[] names = new String[this.gameSettings.getNumPlayers()];
        for(int i = 0; i < playerName.length; i++){
            String name = playerName[i].getPlayerName();
            if(name.equals("")){
                Toast.makeText(getApplicationContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
                return;
            }
            if(name.length() > 5){
                Toast.makeText(getApplicationContext(), "Please enter a valid name - Length max. 5", Toast.LENGTH_SHORT).show();
                return;
            }
            names[i] = name;
        }
        this.gameSettings.setPlayerNames(names);
        startGame(this, this.gameSettings);
    }

    private void createPlayerViews(){
        this.playerName = new PlayerNameViewListener[this.gameSettings.getNumPlayers()];
        for(int i = 0; i < this.gameSettings.getNumPlayers(); i++){
            this.playerName[i] = new PlayerNameView(this, this.playerNames);
            this.playerName[i].setPlayerName(getResources().getString(R.string.pnt_playername) + " : " + i);
        }
    }


}
