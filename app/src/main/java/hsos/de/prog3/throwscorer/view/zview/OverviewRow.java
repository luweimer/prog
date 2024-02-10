package hsos.de.prog3.throwscorer.view.zview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.listener.controller.OverviewControllerListener;
import hsos.de.prog3.throwscorer.listener.view.zview.OverviewRowListener;

/**
 * OverviewRow
 * View-Klasse fuer eine Zeile in der uebersicht um ein Spiel darzustellen
 * @author Lucius Weimer
 */
public class OverviewRow implements OverviewRowListener {

    private TableRow row;
    private TextView gameName;
    private ImageButton show;
    private ImageButton delete;

    private String id;

    private OverviewControllerListener controller;

    /**
     * Konstruktor fuer eine neue Zeile in der uebersicht
     * Setzt den Controller und fuegt die Zeile dem uebergeordneten TableLayout hinzu
     * @param context Kontext der App
     * @param tableLayout uebergeordnetes TableLayout
     * @param controller Controller der uebersicht
     */
    public OverviewRow(Context context, TableLayout tableLayout, OverviewControllerListener controller){
        this.row = (TableRow) LayoutInflater.from(context).inflate(R.layout.overview_row, tableLayout, false);
        this.controller = controller;
        tableLayout.addView(this.row);
        this.init();
    }

    /**
     * Initialisiert die View-Elemente und registriert die Listener
     */
    private void init(){
        this.registerViewElements()
                .registerListener();
    }

    /**
     * Registriert die View-Elemente
     * @return OverviewRow
     */
    private OverviewRow registerViewElements(){
        this.gameName = this.row.findViewById(R.id.tv_ov_game_name);
        this.show = this.row.findViewById(R.id.ib_ov_show);
        this.delete = this.row.findViewById(R.id.ib_ov_delete);
        return this;
    }

    /**
     * Registriert die Listener
     * @return OverviewRow
     */
    private OverviewRow registerListener(){
        this.show.setOnClickListener(v -> this.controller.showGame(this.id));
        this.delete.setOnClickListener(v -> this.controller.deleteGame(this.id));
        return this;
    }

    /**
     * Setzt den Namen des Spiels
     * @param name Name des Spiels
     */
    @Override
    public void setName(String name){
        this.gameName.setText(name);
    }

    /**
     * Setzt die ID des Spiels
     * @param id ID des Spiels
     */
    @Override
    public void setID(String id){
        this.id = id;
    }

    /**
     * Entfernt die Zeile aus dem uebergeordneten TableLayout
     */
    @Override
    public void destroy() {
        // Entferne die Zeile aus dem uebergeordneten TableLayout
        ((ViewGroup) row.getParent()).removeView(row);
    }





}
