package hsos.de.prog3.throwscorer.view.activity;

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
import hsos.de.prog3.throwscorer.listener.controller.ClickHandler;
import hsos.de.prog3.throwscorer.listener.view.activity.GameActivityListener;
import hsos.de.prog3.throwscorer.listener.view.zview.PlayerViewListener;
import hsos.de.prog3.throwscorer.model.CheckoutType;
import hsos.de.prog3.throwscorer.model.GameMultState;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.view.zview.PlayerView;

/**
 * GameActivity
 * Activity fuer das Dartspiel
 * Unterscheidung zwischen Custom und Standard Buttons
 * Custom Buttons - double, triple, back
 * Standard Buttons - 1-20, 25, 50
 *
 * @author Lucius Weimer
 */
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

    /**
     * Initialisiert die Elemente der Activity
     */
    private void init() {
        this.registerViewElements();
    }

    /**
     * Registriert die Listener fuer die Buttons, wenn der Controller gesetzt ist
     */
    private void initController() {
        if (this.controller != null) {
            this.registerBtn()
                    .registerCustomBtn();
        }
    }

    /**
     * Registriert die Elemente der Activity
     *
     * @return GameActivity
     */
    private GameActivity registerViewElements() {
        this.playerHeader = findViewById(R.id.gl_game_playerheader);
        this.leg = findViewById(R.id.tv_game_leg);
        this.checkout = findViewById(R.id.tv_game_checkout);
        return this;
    }

    /**
     * Registriert die Standart Buttons der Activity
     *
     * @return GameActivity
     */
    private GameActivity registerBtn() {
        for (int i = 0; i <= 20; i++) {
            Button btn = this.getButtonById("btn_game_" + i);
            if (btn != null) {
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

    /**
     * Registriert die Custom Buttons der Activity
     *
     * @return GameActivity
     */
    private GameActivity registerCustomBtn() {
        String[] customBtns = {"double", "triple", "back"};

        Arrays.asList(customBtns).forEach(btnVal -> {
            Button btn = this.getButtonById("btn_game_" + btnVal);
            this.setButton(btn);
            this.boardBtnSpecial.put(btnVal, btn);
        });
        return this;
    }

    /**
     * Setzt den OnClickListener fuer den Button im Controller
     *
     * @param button Button
     * @return GameActivity
     */
    private GameActivity setButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller != null) {
                    controller.handleOnClick(view);
                }
            }
        });
        return this;
    }

    /**
     * Gibt den Button anhand der ID zurueck
     *
     * @param id String
     * @return Button
     */
    private Button getButtonById(String id) {
        Button btn = findViewById(getResources().getIdentifier(id, "id", getPackageName()));
        return btn;
    }

    /**
     * Setzt die Custom Buttons zurueck, auf den Standard Hintergrund
     */
    private void resetSpecialButton() {
        this.boardBtnSpecial.forEach((key, btn) -> {
            btn.setBackgroundResource(R.drawable.button_game);
        });
    }

    /**
     * Setzt die Farbe des Buttons
     *
     * @param btn   Button
     * @param color int
     */
    private void setBtnColor(Button btn, int color) {
        btn.setBackgroundColor(getResources().getColor(color));
    }

    /**
     * Erstellt die PlayerViews fuer die einzelnen PlayerHeader
     *
     * @param numPlayers int
     * @param player     String[]
     */
    @Override
    public void createPlayerHeader(int numPlayers, String[] player) {
        this.playerViews = new PlayerView[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            this.playerViews[i] = new PlayerView(this, this.playerHeader);
            this.playerViews[i].updatePlayerName(player[i]);
        }
        this.playerHeader.invalidate();
    }

    /**
     * Updated den gesamten PlayerHeader mit den neuen Werten
     *
     * @param player int
     * @param score  int
     * @param points ArrayList<String>
     * @param sets   int
     * @param legs   int
     */
    @Override
    public void updatePlayerHeader(int player, int score, ArrayList<String> points, int sets, int legs) {
        this.playerViews[player].updateScore(score);
        this.playerViews[player].updatePoints(points);
        this.playerViews[player].updateWin(sets, legs);
    }

    /**
     * Updated den aktiven Player im allen PlayerHeader
     *
     * @param player int - aktiver Player
     */
    @Override
    public void updateActivePlayerHead(int player) {
        for (int i = 0; i < this.playerViews.length; i++) {
            this.playerViews[i].updateActivePlayer(i == player);
        }
    }

    /**
     * Updated die Custom Buttons
     * Setzt alle Custom Buttons zurueck
     * Setzt den aktiven Custom Button
     *
     * @param state GameMultState - aktiver Custom Button
     */
    @Override
    public void updateGameState(GameMultState state) {
        this.resetSpecialButton();

        if (state == GameMultState.DOUBLE) {
            this.setBtnColor(Objects.requireNonNull(this.boardBtnSpecial.get("double")), R.color.primary);
        } else if (state == GameMultState.TRIPLE) {
            this.setBtnColor(Objects.requireNonNull(this.boardBtnSpecial.get("triple")), R.color.primary);
        }
    }

    /**
     * Updated das aktuelle Leg in der uebersicht
     *
     * @param legs int - aktuelles Leg
     */
    @Override
    public void updateLegs(int legs) {
        String text = getResources().getString(R.string.game_leg) + " " + legs;
        this.leg.setText(text);
    }

    /**
     * Updated die Checkout Suggestion in der uebersicht fuer den aktiven Spieler
     * Verwendung globaler Methode convertArrayListCheckout fuer Convertierung der ArrayList in String
     *
     * @param suggestions ArrayList<Integer>
     * @param checkout    CheckoutType
     */
    @Override
    public void updateSuggestions(ArrayList<Integer> suggestions, CheckoutType checkout) {
        String result = convertArrayListCheckout(suggestions, checkout);
        result = getResources().getString(R.string.game_checkout) + " " + result;
        this.checkout.setText(result);
    }

    /**
     * Startet die EvaluationActivity, wenn ein Spieler gewonnen hat
     *
     * @param player       int - Sieger
     * @param playerStats  ArrayList<PlayerStats> - Stats der Spieler
     * @param gameSettings GameSettings - Einstellungen des Spiels
     */
    @Override
    public void showGame(int player, ArrayList<PlayerStats> playerStats, GameSettings gameSettings) {
        startEvaluationActivity(this, player, playerStats, null, true);
    }


    @Override
    public void registerController(Object controller) {
        if (controller instanceof ClickHandler) {
            this.controller = (ClickHandler) controller;
        }
    }

}
