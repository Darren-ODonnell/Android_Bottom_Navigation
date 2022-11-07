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
import com.example.bottomnavigationproper.Services.FixtureRepository;
import com.example.bottomnavigationproper.Services.PlayerRepository;

import java.util.List;

public class StatsSelectionViewModel extends AndroidViewModel {
    private FixtureRepository fixtureRepository;
    private LiveData<List<Fixture>> fixtureResponseLiveData;

    private PlayerRepository playerRepository;
    private LiveData<List<Player>> playerResponseLiveData;

    public StatsSelectionViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        fixtureRepository = new FixtureRepository();
        fixtureResponseLiveData = fixtureRepository.getFixturesResponseLiveData();

        playerRepository = new PlayerRepository();
        playerResponseLiveData = playerRepository.getPlayersResponseLiveData();
    }

    public void getFixtures() {
//        SharedPreferences preferences = getApplication().getSharedPreferences("api_key", Context.MODE_PRIVATE);
//        playerRepository.getPlayers(preferences.getString("token", ""));
        fixtureRepository.getFixtures(TokenSingleton.getInstance().getTokenString());

    }

    public void getPlayers() {
//        SharedPreferences preferences = getApplication().getSharedPreferences("api_key", Context.MODE_PRIVATE);
//        playerRepository.getPlayers(preferences.getString("token", ""));
        playerRepository.getPlayers(TokenSingleton.getInstance().getTokenString());

    }

    private String getToken() {
        SharedPreferences settings = getApplication().getSharedPreferences("api_key",
                Context.MODE_PRIVATE);
        String token = settings.getString("token", null);
        return token;
    }

    public LiveData<List<Fixture>> getFixturesResponseLiveData() {
        return fixtureResponseLiveData;
    }
    public LiveData<List<Player>> getPlayerResponseLiveData() {
        return playerResponseLiveData;
    }
}
