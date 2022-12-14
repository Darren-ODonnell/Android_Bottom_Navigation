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

public class StatsSelectionViewModel extends AndroidViewModel {
    private FixtureRepository fixtureRepository;
    private LiveData<List<Fixture>> fixtureResponseLiveData;

    private PlayerRepository playerRepository;
    private LiveData<List<Player>> playerResponseLiveData;

    private StatRepository statRepository;
    private LiveData<List<StatName>> statNameLiveData;

    private LiveData<List<Stat>> statResponseLiveData;
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


    public LiveData<List<Stat>> getStatResponseLiveData() {
        return statResponseLiveData;
    }


    public void getAllPlayerStatFixture() {
        statRepository.getCountAllPlayerStatFixture(
                TokenSingleton.getInstance().getBearerTokenString());
    }

    public void getAllPlayerFixture(StatName statName) {
        statRepository.getCountAllPlayerFixture(
                TokenSingleton.getInstance().getBearerTokenString(), statName.getName());
    }

    public void getAllPlayerStat(Fixture fixture) {
        statRepository.getCountAllPlayerStat(
                TokenSingleton.getInstance().getBearerTokenString(), fixture.getFixtureDate());
    }

    public void getAllStatFixture(Player player) {
        statRepository.getCountAllStatFixture(
                TokenSingleton.getInstance().getBearerTokenString(), player.getFirstname(), player.getLastname());
    }

    public void getAllPlayer(Fixture fixture, StatName statName) {
        statRepository.getCountAllPlayer(
                TokenSingleton.getInstance().getBearerTokenString(), fixture.getFixtureDate(), statName.getName());
    }

    public void getAllFixture(Player player, StatName statName) {
        statRepository.getCountAllFixture(
                TokenSingleton.getInstance().getBearerTokenString(),player.getFirstname(),
                player.getLastname(), statName.getName());
    }

    public void getAllStats(Player player, Fixture fixture) {
        statRepository.getCountAllStats(
                TokenSingleton.getInstance().getBearerTokenString(), player.getFirstname(), player.getLastname(), fixture.getFixtureDate());
    }

    public void getStat(Player player, Fixture fixture, StatName statName) {
        statRepository.getCountStat(
                TokenSingleton.getInstance().getBearerTokenString(), player.getFirstname(), player.getLastname(), fixture.getFixtureDate(), statName.getName());
    }
}
