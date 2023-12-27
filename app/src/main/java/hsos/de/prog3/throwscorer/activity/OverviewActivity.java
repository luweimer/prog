package hsos.de.prog3.throwscorer.activity;

import static hsos.de.prog3.throwscorer.utility.Router.startEvaluationActivity;

import android.widget.Button;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.controller.OverviewController;
import hsos.de.prog3.throwscorer.listener.activity.OverviewActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.OverviewControllerListener;
import hsos.de.prog3.throwscorer.listener.view.OverviewRowListener;
import hsos.de.prog3.throwscorer.model.GameSettings;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.view.OverviewRow;

public class OverviewActivity extends AppCompatActivity implements OverviewActivityListener {

    private TableLayout gameTable;

    private OverviewControllerListener controller;

    private Button cleanup;

    private OverviewRowListener[] rows;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        this.init();
        new OverviewController(this);
        this.initController();
    }

    private void init(){
        this.registerViewElements();
    }

    private void initController(){
        if(this.controller == null){
            return;
        }
        this.cleanup.setOnClickListener(view -> this.controller.cleanDatabase());
    }

    private OverviewActivity registerViewElements(){
        this.gameTable = this.findViewById(R.id.tl_ov_games);
        this.cleanup = this.findViewById(R.id.btn_ov_clean);
        return this;
    }

    @Override
    public void registerController(Object controller) {
        if(controller instanceof OverviewControllerListener){
            this.controller = (OverviewControllerListener) controller;
        }
    }

    @Override
    public void createGameRows(String[] name, String[] id) {
        if(this.rows != null){
            for(OverviewRowListener row : this.rows){
                row.destroy();
            }
        }
        if(name.length != id.length){
            throw new IllegalArgumentException("Name and ID array must have the same length");
        }
        this.rows = new OverviewRowListener[name.length];
        for(int i = 0; i < name.length; i++){
            this.rows[i] = new OverviewRow(this, this.gameTable, this.controller);
            this.rows[i].setName(name[i]);
            this.rows[i].setID(id[i]);
        }

        this.gameTable.invalidate();
    }

    @Override
    public void updateGameRows(String name, String id) {

    }

    @Override
    public void showWinner(int player, ArrayList<PlayerStats> playerStats) {
        startEvaluationActivity(this, player, playerStats);
    }
}
