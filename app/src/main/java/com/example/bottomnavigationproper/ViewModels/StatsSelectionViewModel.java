package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Services.FixtureRepository;
import com.example.bottomnavigationproper.Services.PlayerRepository;
import com.example.bottomnavigationproper.Services.StatRepository;

import java.util.List;

public class StatsSelectionViewModel extends AndroidViewModel {
    private FixtureRepository fixtureRepository;
    private LiveData<List<Fixture>> fixtureResponseLiveData;

    private PlayerRepository playerRepository;
    private LiveData<List<Player>> playerResponseLiveData;

    private StatRepository statRepository;
    private LiveData<List<StatName>> statNameLiveData;

    private LiveData<List<StatsView>> statResponseLiveData;
    private LiveData<Boolean> singleStatLiveData;
    private LiveData<Boolean> singleFixtureLiveData;

    public StatsSelectionViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        fixtureRepository = new FixtureRepository();
        fixtureResponseLiveData = fixtureRepository.getFixturesResponseLiveData();

        playerRepository = new PlayerRepository();
        playerResponseLiveData = playerRepository.getPlayersResponseLiveData();

        statRepository = new StatRepository();
        statNameLiveData = statRepository.getStatNameLiveData();

        statResponseLiveData = statRepository.getStatsResponseLiveData();
        singleStatLiveData = statRepository.getSingleStatLiveData();
        singleFixtureLiveData = statRepository.getSingleFixtureLiveData();
    }

    public void getFixtures() {
        fixtureRepository.getFixtures(TokenSingleton.getInstance().getBearerTokenString());
    }

    public void getPlayers() {
        playerRepository.getPlayers(TokenSingleton.getInstance().getBearerTokenString());
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


    public LiveData<Boolean> getSingleFixtureLiveData() {
        return singleFixtureLiveData;
    }


    public LiveData<Boolean> getSingleStatLiveData() {
        return singleStatLiveData;
    }


    public LiveData<List<StatsView>> getStatResponseLiveData() {
        return statResponseLiveData;
    }


    public void getAllPlayerStatFixtureHeatMap() {
        statRepository.getCountAllPlayerStatFixtureHeatMap(
                TokenSingleton.getInstance().getBearerTokenString());
    }

    public void getAllPlayerFixtureHeatMap(StatName statName) {
        statRepository.getCountAllPlayerFixtureHeatMap(
                TokenSingleton.getInstance().getBearerTokenString(), statName.getName());
    }

    public void getAllPlayerStatHeatMap(Fixture fixture) {
        statRepository.getCountAllPlayerStatHeatMap(
                TokenSingleton.getInstance().getBearerTokenString(), fixture.getFixtureDate());
    }

    public void getAllStatFixtureHeatMap(Player player) {
        statRepository.getCountAllStatFixtureHeatMap(
                TokenSingleton.getInstance().getBearerTokenString(), player.getFirstname(), player.getLastname());
    }

    public void getAllPlayerHeatMap(Fixture fixture, StatName statName) {
        statRepository.getCountAllPlayerHeatMap(
                TokenSingleton.getInstance().getBearerTokenString(), fixture.getFixtureDate(), statName.getName());
    }

    public void getAllFixtureHeatMap(Player player, StatName statName) {
        statRepository.getCountAllFixtureHeatMap(
                TokenSingleton.getInstance().getBearerTokenString(),player.getFirstname(),
                player.getLastname(), statName.getName());
    }

    public void getAllStatsHeatMap(Player player, Fixture fixture) {
        statRepository.getCountAllStatsHeatMap(
                TokenSingleton.getInstance().getBearerTokenString(), player.getFirstname(), player.getLastname(), fixture.getFixtureDate());
    }

    public void getStatHeatMap(Player player, Fixture fixture, StatName statName) {
        statRepository.getCountStatHeatMap(
                TokenSingleton.getInstance().getBearerTokenString(), player.getFirstname(), player.getLastname(), fixture.getFixtureDate(), statName.getName());
    }
}
