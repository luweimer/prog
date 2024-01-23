package hsos.de.prog3.throwscorer.listener.controller;

/**
 * OverviewControllerListener
 * Schnittstelle für den OverviewController
 * Autor: Lucius Weimer
 */
public interface OverviewControllerListener{

    /**
     * Anzeige eines Spiels
     * @param id ID des Spiels
     */
    public void showGame(String id);

    /**
     * Löschen eines Spiels
     * @param id ID des Spiels
     */
    public void deleteGame(String id);

    /**
     * Löschen der Inhalte der Datenbank
     */
    public void cleanDatabase();
}
