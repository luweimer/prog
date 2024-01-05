package hsos.de.prog3.throwscorer.listener.activity;

import android.graphics.Bitmap;

import androidx.core.content.ContextCompat;

import java.util.List;

import hsos.de.prog3.throwscorer.listener.RegisterListener;
import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * Listener for the EvaluationActivity
 */
public interface EvaluationActivityListener extends RegisterListener {

    /**
     * Handle Intent for Home Button
     */
    public void handleHome();

    /**
     * set the winner text
     * @param playerWinner The name of the winner
     */
    public void setWinnerText(String playerWinner);

    /**
     * Creates the PlayerViews Objects
     * @param playerStats The PlayerStats Object
     */
    public void createPlayerViews(List<PlayerStats> playerStats);

    /**
     * Shows a toast message on the screen
     * @param message The message to show
     */
    public void showToast(String message);

    /**
     *
     * @param AVG The average of the winner
     * @param against The list of the other players
     */
    public void shareWinner(double AVG, List<String> against);

    public void setWinnerPic(Bitmap pic);

}
