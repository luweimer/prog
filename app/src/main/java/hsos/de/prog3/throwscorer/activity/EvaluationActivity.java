package hsos.de.prog3.throwscorer.activity;

import static hsos.de.prog3.throwscorer.utility.ConvertViewValues.convertDrawableToBitmap;
import static hsos.de.prog3.throwscorer.utility.Converter.BitmapToBase64;
import static hsos.de.prog3.throwscorer.utility.Router.startHomeActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.controller.EvaluationController;
import hsos.de.prog3.throwscorer.listener.activity.EvaluationActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.EvaluationControllerListener;
import hsos.de.prog3.throwscorer.listener.view.EvaluationPlayerViewListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.view.EvaluationPlayerView;

/**
 * EvaluationActivity
 * Übersicht über die Spielergebnisse
 * Autor: Lucius Weimer
 */
public class EvaluationActivity extends AppCompatActivity implements EvaluationActivityListener {
    private static final int CAMERA_PERMISSON_CODE = 100;
    private TextView winner;
    private EditText gameName;



    private GridLayout statsFirst;
    private GridLayout statsSecond;

    private Button home;
    private Button saveGame;
    private Button shareWinner;

    private Button winTakePic;

    private ImageView winPic;
    private Bitmap winPicBitmap;


    private EvaluationControllerListener controller;
    private EvaluationPlayerViewListener[] playerViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation);
        this.init();
        new EvaluationController(this, this.handleIncomingIntent());
        this.registerButton();
    }

    /**
     * Initialisiert die Elemente der Activity
     */
    private void init(){
        this.registerViewElements();
    }

    /**
     * Registriert die Buttons
     * @return EvaluationActivity
     */
    private EvaluationActivity registerButton(){
        this.home.setOnClickListener(v -> this.handleHome() );
        this.saveGame.setOnClickListener(v -> this.controller.handleSave(this.gameName.getText().toString(), this.getPicture() ) );
        this.shareWinner.setOnClickListener(v -> this.controller.shareWinner() );
        //TODO: Auslagern? Nur ein View Evenet?!
        this.home.setOnClickListener(v -> this.handleHome() );
        this.winTakePic.setOnClickListener(v ->  this.useCamera());
        this.winPic.setOnClickListener(v -> this.useCamera());

        return this;
    }

    /**
     * Registriert die View Elemente
     * @return EvaluationActivity
     */
    private EvaluationActivity registerViewElements(){
        this.winner = findViewById(R.id.tv_eva_win);
        this.statsFirst = findViewById(R.id.gl_eva_stats_first);
        this.statsSecond = findViewById(R.id.gl_eva_stats_second);
        this.gameName = findViewById(R.id.et_eva_name);
        this.home = findViewById(R.id.btn_eva_home);
        this.saveGame = findViewById(R.id.btn_eva_save);
        this.shareWinner = findViewById(R.id.btn_eva_share);
        this.winPic = findViewById(R.id.iv_eva_win_pic);
        this.winTakePic = findViewById(R.id.btn_eva_take_pic);

        return this;
    }

    /**
     * Managed das öffnen der Kamera und die Berechtigungsanfrage
     */
    private void useCamera(){
        if(ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            this.showToast("Camera access already granted");
            this.takePicture();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},
                    EvaluationActivity.CAMERA_PERMISSON_CODE
            );
        }
    }

    /**
     * Verarbeitet die Ergebnisse der Berechtigungsanfrage
     * @param requestCode The request code passed in
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case EvaluationActivity.CAMERA_PERMISSON_CODE:
                if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.showToast("Camera access newly granted");
                    this.takePicture();
                } else {
                    this.showToast("Camera access denied");
                }
                break;
            default:
                this.showToast("Unidentified permission request");
        }
    }

    /**
     * Startet die Kamera mit Intent
     */
    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageCaptureLauncher.launch(intent);
    }

    /**
     * Registriert den ActivityResultLauncher für die Kamera
     */
    private final ActivityResultLauncher<Intent> imageCaptureLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getExtras() != null) {
                        Bundle extras = data.getExtras();
                        if (extras.containsKey("data")) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            this.setWinnerPic(imageBitmap);
                        }
                    }
                }
            }
    );

    /**
     * Ruft das Bild aus der ImageView und wandelt es in ein Bitmap um
     * @return Bitmap
     */
    private Bitmap getPicture(){
        Drawable drawable = this.winPic.getDrawable();

        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            bitmap = convertDrawableToBitmap(drawable);
        }
        return bitmap;
    }

    /**
     * Setzt das Bild in der ImageView
     * @param imageBitmap Bitmap
     */
    @Override
    public void setWinnerPic(Bitmap imageBitmap){
        this.winPic.setImageBitmap(imageBitmap);
        this.winPicBitmap = imageBitmap;
        this.winPic.invalidate();
    }

    /**
     * Erstellt die einzelnen PlayerViews, welche die Spielergebnisse/ Statistiken anzeigen
     * @param playerStats The PlayerStats Object
     */
    @Override
    public void createPlayerViews(List<PlayerStats> playerStats){
        playerViews = new EvaluationPlayerViewListener[playerStats.size()];
        for(int i = 0; i < playerStats.size(); i++){
            if(i < 2){
                playerViews[i] = new EvaluationPlayerView(this, this.statsFirst);
            } else {
                playerViews[i] = new EvaluationPlayerView(this, this.statsSecond);
            }
            playerViews[i].setViewValues(playerStats.get(i));
        }
        this.statsFirst.invalidate();
        this.statsSecond.invalidate();
    }

    /**
     * Teilt den Gewinner über andere Apps mit
     * Verwendung Intent - Action Send
     * @param AVG Der Durchschnitt des gewonnenen Spielers
     * @param against Die Spieler gegen die der Gewinner gewonnen hat
     */
    @Override
    public void shareWinner(double AVG, List<String> against){
        Intent sendIntent = new Intent();
        StringBuilder text = new StringBuilder(getString(R.string.share_avg_win) + " " + AVG + " " + getString(R.string.share_against) + " ");
        for(int i = 0; i < against.size(); i++){
            if(i == against.size() - 1){
                text.append(against.get(i)).append(" ");
            } else {
                text.append(against.get(i)).append(", ");
            }
        }
        text.append(getString(R.string.share_win));
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text.toString());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    /**
     * Ausgabe von Toast durch den Controller
     * @param message Nachricht die angezeigt werden soll
     */
    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Setzt den Text für den Gewinner in der Überschrift
     * @param playerWinner Name des Siegers
     */
    @Override
    public void setWinnerText(String playerWinner){
        String text = this.winner.getText().toString();
        text = text + " " + playerWinner;
        this.winner.setText(text);
    }

    /**
     * Startet die HomeActivity
     */
    @Override
    public void handleHome(){
        startHomeActivity(this);
    }

    /**
     * Verarbeitung des Intent - winner, playerWinner, playerStats
     * @return GameDatabase Object
     */
    @Nullable
    private GameDatabase handleIncomingIntent() {
        Intent intent_in = this.getIntent();
        int winner = intent_in.getIntExtra("winner", 0);
        List<PlayerStats> playerStats= new ArrayList<>();
        playerStats = intent_in.getParcelableArrayListExtra("PlayerStats");
        Bitmap pic = intent_in.getParcelableExtra("winnerPic");
        if(playerStats == null){
            Log.e("EvaluationActivity", "handleIncomingIntent: playerStats is null");
        }
        String playerWinner = playerStats.get(winner).getPlayer();
        if(pic == null){
            return new GameDatabase(winner, playerWinner, playerStats);
        } else {
            return new GameDatabase(winner, playerWinner, playerStats, pic);
        }
    }

    @Override
    public void registerController(Object controller) {
        if(controller instanceof EvaluationControllerListener){
            this.controller = (EvaluationControllerListener) controller;
        }
    }
}
