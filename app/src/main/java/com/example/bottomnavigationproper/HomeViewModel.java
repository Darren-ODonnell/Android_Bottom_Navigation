package com.example.bottomnavigationproper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Services.StatRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private StatRepository statRepository;

    private LiveData<List<StatsView>> statResponseLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        statRepository = new StatRepository();
        statResponseLiveData = statRepository.getStatsResponseLiveData();
    }

    public void countStatByPlayer(Player player){
        statRepository.countStatByPlayer(player.getFirstname(), player.getLastname(), TokenSingleton.getInstance().getBearerTokenString());
    }

    public void countStatAllPlayer(){
        statRepository.countStatsAllPlayer(TokenSingleton.getInstance().getBearerTokenString());
    }

    public LiveData<List<StatsView>> getStatResponseLiveData(){
        return statResponseLiveData;
    }
}
