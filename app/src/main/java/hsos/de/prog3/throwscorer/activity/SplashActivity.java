package hsos.de.prog3.throwscorer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import hsos.de.prog3.throwscorer.R;

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
                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
