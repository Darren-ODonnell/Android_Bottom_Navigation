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
import com.example.bottomnavigationproper.Services.PlayerRepository;
import com.example.bottomnavigationproper.Services.StatRepository;

import java.util.List;

public class StatViewModel extends AndroidViewModel {
    private StatRepository statRepository;
    private LiveData<List<Stat>> statResponseLiveData;

    public StatViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        statRepository = new StatRepository();
        statResponseLiveData = statRepository.getStatsResponseLiveData();
    }

    public void getStats() {

        statRepository.getStats(TokenSingleton.getInstance().getBearerTokenString());

    }

    public LiveData<List<Stat>> getStatResponseLiveData() {
        return statResponseLiveData;
    }


    public void getAllPlayerStatFixture() {
    }

    public void getAllPlayerFixture(StatName statName) {
    }

    public void getAllPlayerStat(Fixture fixture) {
    }

    public void getAllStatFixture(Player player) {
    }

    public void getAllPlayer(Fixture fixture, StatName statName) {
    }

    public void getAllFixture(Player player, StatName statName) {
    }

    public void getAllStats(Player player, Fixture fixture) {
    }

    public void getStat(Player player, Fixture fixture, StatName statName) {
    }


}
