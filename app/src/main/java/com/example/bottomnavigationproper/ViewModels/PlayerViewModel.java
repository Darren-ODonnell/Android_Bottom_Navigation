package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Services.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

public class PlayerViewModel extends AndroidViewModel {
    private PlayerRepository playerRepository;
    private LiveData<List<Player>> playerResponseLiveData;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        playerRepository = new PlayerRepository();
        playerResponseLiveData = playerRepository.getPlayersResponseLiveData();
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

    public LiveData<List<Player>> getPlayerResponseLiveData() {
        return playerResponseLiveData;
    }
}
