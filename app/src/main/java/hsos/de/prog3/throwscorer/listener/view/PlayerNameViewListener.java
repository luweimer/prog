package hsos.de.prog3.throwscorer.listener.view;

/**
 * PlayerNameViewListener
 * Schnittstelle PlayerNameView
 * Autor: Lucius Weimer
 */
public interface PlayerNameViewListener {

    /**
     * Setzen des Spielernamens
     * @param name Name des Spielers
     */
    public void setPlayerName(String name);

    /**
     * Auslesen des Spielernamens
     * @return Name des Spielers
     */
    public String getPlayerName();
}
