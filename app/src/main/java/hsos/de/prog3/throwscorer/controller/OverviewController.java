package hsos.de.prog3.throwscorer.controller;

import static hsos.de.prog3.throwscorer.room.Converter.convertGameEntityToGameDatabase;
import static hsos.de.prog3.throwscorer.room.Converter.convertPlayerStatsEntityToPlayerStats;

import android.content.Context;
import android.util.Pair;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;

import hsos.de.prog3.throwscorer.listener.activity.OverviewActivityListener;
import hsos.de.prog3.throwscorer.listener.controller.OverviewControllerListener;
import hsos.de.prog3.throwscorer.listener.controller.PersistensListener;
import hsos.de.prog3.throwscorer.model.GameDatabase;
import hsos.de.prog3.throwscorer.model.PlayerStats;
import hsos.de.prog3.throwscorer.room.RoomAccess;
import hsos.de.prog3.throwscorer.room.entity.GameEntity;
import hsos.de.prog3.throwscorer.room.entity.PlayerStatsEntity;

public class OverviewController implements OverviewControllerListener {

    private OverviewActivityListener view;

    private PersistensListener persistent;

    public OverviewController(OverviewActivityListener view) {
        this.view = view;
        view.registerController(this);

        this.persistent = new RoomAccess();
        this.persistent.setContext((Context) this.view);

        this.registerTableRows();
    }

    /**
     * Register all Table Rows
     */
    private void registerTableRows(){
        LiveData<List<GameEntity>> liveDataGames = this.persistent.getAllGames();
        liveDataGames.observe((LifecycleOwner) this.view, games -> {
            if (games == null || games.isEmpty()) {
                this.view.createGameRows(new String[]{}, new String[]{});
                return;
            }

            String[] names = new String[games.size()];
            String[] ids = new String[games.size()];

            for (int i = 0; i < games.size(); i++) {
                names[i] = games.get(i).gameName;
                ids[i] = games.get(i).gameID;
            }

            this.view.createGameRows(names, ids);
        });
    };

    @Override
    public void showGame(String id) {
        MediatorLiveData<Pair<GameEntity, List<PlayerStatsEntity>>> mediatorLiveData = new MediatorLiveData<>();

        LiveData<GameEntity> liveGame = this.persistent.getGame(id);
        LiveData<List<PlayerStatsEntity>> livePlayerStats = this.persistent.getPlayerStats(id);

        mediatorLiveData.addSource(liveGame, game -> {
            if (game != null) {
                mediatorLiveData.setValue(new Pair<>(game, livePlayerStats.getValue()));
            }
        });

        mediatorLiveData.addSource(livePlayerStats, playerStats -> {
            GameEntity currentGame = liveGame.getValue();
            if (currentGame != null) {
                mediatorLiveData.setValue(new Pair<>(currentGame, playerStats));
            }
        });

        mediatorLiveData.observe((LifecycleOwner) this.view, data -> {
            GameEntity game = data.first;
            List<PlayerStatsEntity> playerStats = data.second;

            if (game != null && playerStats != null) {
                GameDatabase gd = convertGameEntityToGameDatabase(game);
                List<PlayerStats> playerStatsList = new ArrayList<>();
                playerStats.forEach(ps -> {
                    playerStatsList.add(convertPlayerStatsEntityToPlayerStats(ps));
                });
                gd.setPlayerStats(playerStatsList);
                this.view.showGame(gd.getWinnerInt(), (ArrayList<PlayerStats>) gd.getPlayerStats(), gd.getWinnerPic());
            }
        });
    }

    @Override
    public void deleteGame(String id) {
        this.persistent.deleteGame(id);
    }

    @Override
    public void cleanDatabase() {
        this.persistent.deleteAllGames();
    }
}
