package hsos.de.prog3.throwscorer.view.activity;

import static hsos.de.prog3.throwscorer.utility.Router.startPlayerNameActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.model.CheckoutType;
import hsos.de.prog3.throwscorer.model.GameSettings;

/**
 * IndividualGameActivity
 * Activity um Spieleinstellungen eines individuellen Spiels festzulegen
 * Parameter: StartScore, Spieleranzahl, Setanzahl, Leganzahl, CheckoutType
 * Autor: Lucius Weimer
 */
public class IndividualGameActivity extends AppCompatActivity {

    private EditText score;
    private EditText set;
    private EditText leg;
    private RadioGroup player;
    private RadioGroup checkoutType;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_game);
        this.init();
    }

    /**
     * Initialisiert die Elemente der Activity
     */
    private void init(){
        this.registerViewElements();
    }

    /**
     * Registriert die Elemente der Activity
     * @return IndividualGameActivity
     */
    private IndividualGameActivity registerViewElements(){
        this.score = findViewById(R.id.et_ind_score);
        this.set = findViewById(R.id.et_ind_set);
        this.leg = findViewById(R.id.et_ind_leg);
        this.player = findViewById(R.id.rg_ind_playerSize);
        this.checkoutType = findViewById(R.id.rg_ind_checkout);
        this.submit = findViewById(R.id.btn_ind_submit);
        this.submit.setOnClickListener(view -> handleSubmit());
        return this;
    }

    /**
     * Validiert die Eingaben und startet die PlayerNameActivity
     * Auslesen der TextViews und RadioButtons
     * Textviews werden in Integer geparst
     * RadioButtons werden in CheckoutType geparst
     */
    private void handleSubmit(){
        int score;
        int set;
        int leg;
        try {
            score = Integer.parseInt(this.score.getText().toString());
            set = Integer.parseInt(this.set.getText().toString());
            leg = Integer.parseInt(this.leg.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }
        int player = this.getPlayerSize();
        if(player == -1){
            return;
        }
        CheckoutType checkoutType = this.getCheckoutType();
        GameSettings gameSettings = new GameSettings(score, player, leg, set, checkoutType);
        startPlayerNameActivity(this, gameSettings);
    }

    /**
     * Liest die Spieleranzahl aus den RadioButtons aus
     * @return int - Spieleranzahl
     */
    private int getPlayerSize(){
        int playerSize;
        try{
            playerSize = Integer.parseInt(this.getRadioButtonText( this.player.getCheckedRadioButtonId() ));
        }catch (NumberFormatException e){
            Toast.makeText(this, "Please enter a valid player size", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return playerSize;
    }

    /**
     * Liest den CheckoutType aus den RadioButtons aus
     * @return CheckoutType
     */
    private CheckoutType getCheckoutType(){
        String checkoutType = this.getRadioButtonText( this.checkoutType.getCheckedRadioButtonId() );
        if(checkoutType.equals(this.getResources().getString(R.string.a_double))){
            return CheckoutType.DOUBLE;
        } else if(checkoutType.equals(this.getResources().getString(R.string.a_triple))){
            return CheckoutType.TRIPLE;
        } else {
            return CheckoutType.SINGLE;
        }
    }

    /**
     * Hilfsfunktion um RadioButtons auszulesen
     * @param id - RadioButton ID
     * @return String - RadioButton Text
     */
    private String getRadioButtonText(int id){
        RadioButton radioButton = (RadioButton) findViewById( id );
        return radioButton.getText().toString();
    }




}
