package hsos.de.prog3.throwscorer.listener.view.zview;

import hsos.de.prog3.throwscorer.model.PlayerStats;

/**
 * EvaluationPlayerViewListener
 * Schnittstelle EvaluationPlayerView
 * @author Lucius Weimer
 */
public interface EvaluationPlayerViewListener {
    /**
     * Setzen der einzelnen Werte in der View
     * @param mame String - Name des Spielers
     * @param sumScore int - Summe der Punkte
     * @param avg double - Durchschnitt
     * @param sumThrough int - Anzahl der Wuerfe
     * @param bull int - Anzahl der erzielten Bulls
     * @param sBull int - Anzahl der erzielten Single Bulls
     * @param o180 int - Anzahl der erzielten 180er
     * @param o160 int - Anzahl der erzielten 160er
     * @param o140 int - Anzahl der erzielten 140er
     * @param o120 int - Anzahl der erzielten 120er
     * @param o100 int - Anzahl der erzielten 100er
     * @param singleThrow int - Anzahl der Single Wuerfe
     * @param doubleThrow int - Anzahl der Double Wuerfe
     * @param tripleThrow int - Anzahl der Triple Wuerfe
     */
    public void setViewValues(String mame, int sumScore, double avg, int sumThrough, int bull, int sBull, int o180, int o160, int o140, int o120, int o100, int singleThrow, int doubleThrow, int tripleThrow);

    /**
     * Setzt die Werte der View durch die Werte des PlayerStats Objekts
     * @param playerStats - PlayerStats
     */
    public void setViewValues(PlayerStats playerStats);
}
