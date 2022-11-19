//package com.example.bottomnavigationproper.ViewModels;
//
//import android.app.Application;
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import com.example.bottomnavigationproper.APIs.TokenSingleton;
//import com.example.bottomnavigationproper.Models.Player;
//import com.example.bottomnavigationproper.Services.PlayerRepository;
//
//import java.util.List;
//
//public class PlayerViewModel extends AndroidViewModel {
//    private PlayerRepository playerRepository;
//    private LiveData<List<Player>> playerResponseLiveData;
//
//    public PlayerViewModel(@NonNull Application application) {
//        super(application);
//    }
//
//    public void init(){
//        playerRepository = new PlayerRepository();
//        playerResponseLiveData = playerRepository.getPlayersResponseLiveData();
//    }
//
//    public void getPlayers() {
//
//        playerRepository.getPlayers(TokenSingleton.getInstance().getBearerTokenString());
//
//    }
//
//
//    public LiveData<List<Player>> getPlayerResponseLiveData() {
//        return playerResponseLiveData;
//    }
//}
