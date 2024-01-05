package hsos.de.prog3.throwscorer.activity;

import static hsos.de.prog3.throwscorer.utility.ConvertViewValues.convertArrayListCheckout;
import static hsos.de.prog3.throwscorer.utility.Router.startEvaluationActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.controller.GameController;
import hsos.de.prog3.throwscorer.listener.activity.GameActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.ClickHandler;
import hsos.de.prog3.throwscorer.listener.view.PlayerViewListener;
import hsos.de.prog3.throwscorer.model.CheckoutType;
import hsos.de.prog3.throwscorer.model.GameMultState;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.view.PlayerView;

public class GameActivity extends AppCompatActivity implements GameActivityListener {

    private GridLayout playerHeader;
    private Map<String, Button> boardBtnSpecial = new HashMap<String, Button>();
    private ArrayList<Button> gamePoints = new ArrayList<Button>();

    private PlayerViewListener[] playerViews;

    private ClickHandler controller;

    private TextView checkout;
    private TextView leg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        GameSettings gameSettings = getIntent().getParcelableExtra("GameSettings");
        this.init();
        new GameController(this, gameSettings);
        this.initController();

    }

    private void init(){
        this.registerViewElements();
    }

    private void initController(){
        if(this.controller != null){
                this.registerBtn()
                        .registerCustomBtn();
        }
    }


    private GameActivity registerViewElements(){
        this.playerHeader = findViewById(R.id.gl_game_playerheader);
        this.leg = findViewById(R.id.tv_game_leg);
        this.checkout = findViewById(R.id.tv_game_checkout);
        return this;
    }

    private GameActivity registerBtn(){
        for(int i = 0; i <= 20; i++){
            Button btn = this.getButtonById("btn_game_" + i);
            if(btn != null){
                this.setButton(btn);
                this.gamePoints.add(btn);
            }
        }

        Button btnSingleBull = this.getButtonById("btn_game_25");
        this.setButton(btnSingleBull);
        this.gamePoints.add(btnSingleBull);
        Button btnBull = this.getButtonById("btn_game_50");
        this.setButton(btnBull);
        this.gamePoints.add(btnBull);
        return this;
    }

    private GameActivity registerCustomBtn(){
        String[] customBtns = {"double", "triple", "back"};

        Arrays.asList(customBtns).forEach(btnVal -> {
            Button btn = this.getButtonById("btn_game_" + btnVal);
            this.setButton(btn);
            this.boardBtnSpecial.put(btnVal, btn);
        });
        return this;
    }

    private GameActivity setButton(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(controller != null){
                    controller.handleOnClick(view);
                }
            }
        });
        return this;
    }

    private Button getButtonById(String id){
        Button btn = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
        return btn;
    }

    private void resetSpecialButton(){
        this.boardBtnSpecial.forEach((key, btn) -> {
            btn.setBackgroundResource( R.drawable.button_game );
        });
    }

    private void setBtnColor(Button btn, int color){
        btn.setBackgroundColor(getResources().getColor( color ));
    }

    @Override
    public void createPlayerHeader(int numPlayers, String[] player){
        this.playerViews = new PlayerView[numPlayers];
        for(int i = 0; i < numPlayers; i++){
            this.playerViews[i] = new PlayerView(this, this.playerHeader);
            this.playerViews[i].updatePlayerName(player[i]);
        }
        this.playerHeader.invalidate();
    }

    @Override
    public void updatePlayerHeader(int player, int score, ArrayList<String> points, int sets, int legs) {
        this.playerViews[player].updateScore(score);
        this.playerViews[player].updatePoints(points);
        this.playerViews[player].updateWin(sets, legs);
    }

    @Override
    public void updateActivePlayerHead(int player){
        for(int i = 0; i < this.playerViews.length; i++){
            this.playerViews[i].updateActivePlayer(i == player);
        }
    }

    @Override
    public void updateGameState(GameMultState state) {
        this.resetSpecialButton();

        if(state == GameMultState.DOUBLE){
            this.setBtnColor(Objects.requireNonNull(this.boardBtnSpecial.get("double")), R.color.primary );
        } else if(state == GameMultState.TRIPLE){
            this.setBtnColor(Objects.requireNonNull(this.boardBtnSpecial.get("triple")), R.color.primary );
        }
    }

    @Override
    public void updateLegs(int legs) {
        String text = getResources().getString(R.string.game_leg) + " " + legs;
        this.leg.setText(text);
    }

    @Override
    public void updateSuggestions(ArrayList<Integer> suggestions, CheckoutType checkout) {
        String result = convertArrayListCheckout(suggestions, checkout);
        result = getResources().getString(R.string.game_checkout) + " " + result;
        this.checkout.setText(result);
    }

    @Override
    public void showWinner(int player, ArrayList<PlayerStats> playerStats, GameSettings gameSettings) {
        startEvaluationActivity(this, player, playerStats);
    }


    @Override
    public void registerController(Object controller) {
        if(controller instanceof ClickHandler){
            this.controller = (ClickHandler) controller;
        }
    }
}
