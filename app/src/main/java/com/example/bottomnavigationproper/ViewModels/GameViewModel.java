package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Services.FixtureRepository;
import com.example.bottomnavigationproper.Services.PlayerRepository;
import com.example.bottomnavigationproper.Services.StatRepository;

import java.util.List;
import java.util.function.ToDoubleBiFunction;

public class GameViewModel extends AndroidViewModel {
    private FixtureRepository fixtureRepository;
    private LiveData<List<Fixture>> fixtureResponseLiveData;

    private PlayerRepository playerRepository;
    private LiveData<List<Player>> playerResponseLiveData;

    private StatRepository statRepository;
    private LiveData<List<StatName>> statNameLiveData;


    /**
     * TODO
     * Add repo for current teamsheet
     * Add repo for current score
     *
     */

    public GameViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        fixtureRepository = new FixtureRepository();
        fixtureResponseLiveData = fixtureRepository.getFixturesResponseLiveData();

        playerRepository = new PlayerRepository();
        playerResponseLiveData = playerRepository.getPlayersResponseLiveData();

        statRepository = new StatRepository();
        statNameLiveData = statRepository.getStatNameLiveData();


    }

    public void getFixtures() {
        fixtureRepository.getFixtures(TokenSingleton.getInstance().getBearerTokenString());
    }

    public void getPlayers(Fixture fixture) {
        playerRepository.getPlayersForFixture(fixture, TokenSingleton.getInstance().getBearerTokenString());
    }

    public void getStatNames(){
        statRepository.getStatNames(TokenSingleton.getInstance().getBearerTokenString());
    }

    public LiveData<List<Fixture>> getFixturesResponseLiveData() {
        return fixtureResponseLiveData;
    }
    public LiveData<List<Player>> getPlayerResponseLiveData() {
        return playerResponseLiveData;
    }
    public LiveData<List<StatName>> getStatNameLiveData(){ return statNameLiveData;}


}
