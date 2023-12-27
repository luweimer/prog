package hsos.de.prog3.throwscorer.listener.view;

import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public interface EvaluationPlayerViewListener {
    public void setViewValues(String mame, int sumScore, double avg, double checkout, int sumThrough, int bull, int sBull, int o180, int o160, int o140, int o120, int o100, int singleThrow, int doubleThrow, int tripleThrow);

    public void setViewValues(PlayerStats playerStats);



}
