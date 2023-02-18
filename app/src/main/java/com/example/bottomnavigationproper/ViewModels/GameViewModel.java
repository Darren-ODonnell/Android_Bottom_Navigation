package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Result;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Services.FixtureRepository;
import com.example.bottomnavigationproper.Services.PlayerRepository;
import com.example.bottomnavigationproper.Services.ScoreRepository;
import com.example.bottomnavigationproper.Services.StatRepository;

import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private FixtureRepository fixtureRepository;
    private LiveData<List<Fixture>> fixtureResponseLiveData;

    private PlayerRepository playerRepository;
    private LiveData<List<Player>> playerResponseLiveData;

    private StatRepository statRepository;
    private LiveData<List<StatName>> statNameLiveData;
    private LiveData<Result> scoreLiveData;

    private LiveData<List<StatsView>> statsLiveData;


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
        statsLiveData = statRepository.getStatsResponseLiveData();

        scoreLiveData = statRepository.getScoreLiveData();
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

    public void getStats(Fixture fixture){
        statRepository.countAllPlayerStatNameByFixtureDateGroupSuccess(TokenSingleton.getInstance().getBearerTokenString(), fixture.getFixtureDate());
    }

    public void getScore(Fixture fixture){
        statRepository.getScoreForFixture(TokenSingleton.getInstance().getBearerTokenString(), fixture.getFixtureDate());
    }

    public LiveData<Result> getScoreLiveData() {
        return scoreLiveData;
    }

    public LiveData<List<Fixture>> getFixturesResponseLiveData() {
        return fixtureResponseLiveData;
    }
    public LiveData<List<Player>> getPlayerResponseLiveData() {
        return playerResponseLiveData;
    }
    public LiveData<List<StatName>> getStatNameLiveData(){ return statNameLiveData;}
    public LiveData<List<StatsView>> getStatsLiveData(){ return statsLiveData;}



}
