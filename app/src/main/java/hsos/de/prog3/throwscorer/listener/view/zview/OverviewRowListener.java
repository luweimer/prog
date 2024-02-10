package hsos.de.prog3.throwscorer.listener.view.zview;

/**
 * OverviewRowListener
 * Schnittstelle OverviewRow
 * Autor: Lucius Weimer
 */
public interface OverviewRowListener {

    /**
     * Setzen des Namen des Spiels
     * @param name Name des Spiels
     */
    public void setName(String name);

    /**
     * Setzen der ID des Spiels
     * @param id ID des Spiels
     */
    public void setID(String id);

    /**
     * Loeschen des Elements von der View
     */
    public void destroy();
}
