package hsos.de.prog3.throwscorer.listener.controller;

/**
 * OverviewControllerListener
 * Schnittstelle fuer den OverviewController
 * @author Lucius Weimer
 */
public interface OverviewControllerListener{

    /**
     * Anzeige eines Spiels
     * @param id ID des Spiels
     */
    public void showGame(String id);

    /**
     * Loeschen eines Spiels
     * @param id ID des Spiels
     */
    public void deleteGame(String id);

    /**
     * Loeschen der Inhalte der Datenbank
     */
    public void cleanDatabase();
}
