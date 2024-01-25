package hsos.de.prog3.throwscorer.view.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.utility.Router;

/**
 * SplashActivity
 * Activity für den Splashscreen
 * Autor: Lucius Weimer
 */
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;

    /**
     * Startet die HomeActivity nach einer Verzögerung
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Router.startHome(SplashActivity.this);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
