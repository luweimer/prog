package hsos.de.prog3.throwscorer.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import hsos.de.prog3.throwscorer.R;
import hsos.de.prog3.throwscorer.listener.view.EvaluationPlayerViewListener;
import hsos.de.prog3.throwscorer.model.PlayerStats;

public class EvaluationPlayerView implements EvaluationPlayerViewListener {

    private LinearLayout gamePlayer;
    private TextView name;
    private TextView sumScore;
    private TextView avg;
    private TextView checkout;
    private TextView sumThrough;
    private TextView bull;
    private TextView sBull;
    private TextView o180;
    private TextView o160;
    private TextView o140;
    private TextView o120;
    private TextView o100;
    private TextView singleThrow;
    private TextView doubleThrow;
    private TextView tripleThrow;

    public EvaluationPlayerView(Context context, GridLayout playerHeader){
        this.gamePlayer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.evaluation_player, playerHeader, false);
        playerHeader.addView(this.gamePlayer);
        this.init();
    }

    private void init(){
        this.registerViewElements();
    }

    private EvaluationPlayerView registerViewElements(){
        this.name = this.gamePlayer.findViewById(R.id.tv_evap_player_name);
        this.sumScore = this.gamePlayer.findViewById(R.id.tv_evap_sum_score);
        this.avg = this.gamePlayer.findViewById(R.id.tv_evap_avg);
        this.checkout = this.gamePlayer.findViewById(R.id.tv_evap_checkout);
        this.sumThrough = this.gamePlayer.findViewById(R.id.tv_evap_sum_through);
        this.bull = this.gamePlayer.findViewById(R.id.tv_evap_bull);
        this.sBull = this.gamePlayer.findViewById(R.id.tv_evap_sbull);
        this.o180 = this.gamePlayer.findViewById(R.id.tv_evap_o180);
        this.o160 = this.gamePlayer.findViewById(R.id.tv_evap_o160);
        this.o140 = this.gamePlayer.findViewById(R.id.tv_evap_o140);
        this.o120 = this.gamePlayer.findViewById(R.id.tv_evap_o120);
        this.o100 = this.gamePlayer.findViewById(R.id.tv_evap_o100);
        this.singleThrow = this.gamePlayer.findViewById(R.id.tv_evap_single);
        this.doubleThrow = this.gamePlayer.findViewById(R.id.tv_evap_double);
        this.tripleThrow = this.gamePlayer.findViewById(R.id.tv_evap_triple);
        return this;
    }

    private double roundTwoDecimal(double val){
        return Math.round(val * 100.0) / 100.0;
    }

    @Override
    public void setViewValues(String mame, int sumScore, double avg, double checkout, int sumThrough, int bull, int sBull,int o180, int o160, int o140, int o120, int o100, int singleThrow, int doubleThrow, int tripleThrow) {
        this.name.setText(mame);
        this.sumScore.setText(String.valueOf(sumScore));
        this.avg.setText(String.valueOf( this.roundTwoDecimal( avg ) ));
        this.checkout.setText(String.valueOf( this.roundTwoDecimal( checkout) ));
        this.sumThrough.setText(String.valueOf(sumThrough));
        this.bull.setText(String.valueOf(bull));
        this.sBull.setText(String.valueOf(sBull));
        this.o180.setText(String.valueOf(o180));
        this.o160.setText(String.valueOf(o160));
        this.o140.setText(String.valueOf(o140));
        this.o120.setText(String.valueOf(o120));
        this.o100.setText(String.valueOf(o100));
        this.singleThrow.setText(String.valueOf(singleThrow));
        this.doubleThrow.setText(String.valueOf(doubleThrow));
        this.tripleThrow.setText(String.valueOf(tripleThrow));
    }

    @Override
    public void setViewValues(PlayerStats playerStats) {
        this.setViewValues(
                playerStats.getPlayer(),
                playerStats.getSumScore(),
                playerStats.getAvg(),
                playerStats.getCheckout(),
                playerStats.getCountSum(),
                playerStats.getBull(),
                playerStats.getsBull(),
                playerStats.get180(),
                playerStats.get160(),
                playerStats.get140(),
                playerStats.get120(),
                playerStats.get100(),
                playerStats.getSingleThrow(),
                playerStats.getDoubleThrow(),
                playerStats.getTripleThrow()
        );
    }


}
