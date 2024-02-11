package hsos.de.prog3.throwscorer.listener.view.zview;

/**
 * PlayerNameViewListener
 * Schnittstelle PlayerNameView
 *
 * @author Lucius Weimer
 */
public interface PlayerNameViewListener {

    /**
     * Setzen des Spielernamens
     *
     * @param name Name des Spielers
     */
    public void setPlayerName(String name);

    /**
     * Auslesen des Spielernamens
     *
     * @return Name des Spielers
     */
    public String getPlayerName();
}
