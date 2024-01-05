package hsos.de.prog3.throwscorer.listener.view;

public interface OverviewRowListener {

    public void setName(String name);
    public void setID(String id);

    /**
     * Destroys the view
     */
    public void destroy();
}
