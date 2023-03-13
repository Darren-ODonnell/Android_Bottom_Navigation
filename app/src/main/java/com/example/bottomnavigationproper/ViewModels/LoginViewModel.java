package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;
import android.media.session.MediaSession;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Services.LoginRepository;
import com.example.bottomnavigationproper.Services.PlayerRepository;
import com.example.bottomnavigationproper.User;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;
    private LiveData<Boolean> tokenValidityLiveData;

    private PlayerRepository playerRepository;
    private LiveData<Player> playerLiveData;





    /**
     * TODO
     * Add repo for current teamsheet
     * Add repo for current score
     *
     */

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        loginRepository = new LoginRepository();
        tokenValidityLiveData = loginRepository.getTokenValidity();

        playerRepository= new PlayerRepository();
        playerLiveData = playerRepository.getSingPlayerResponseLiveData();


    }

    public void login(Login login) {
        loginRepository.login(login);
    }

    public LiveData<Boolean> getTokenValidityLiveData() {
        return tokenValidityLiveData;
    }

    public LiveData<Player> getSingPlayerResponseLiveData(){
        return playerLiveData;
    }

    public void getPlayerByEmail(User user){
        playerRepository.getPlayerByEmail(user.getEmail(), TokenSingleton.getInstance().getBearerTokenString());
    }

    public void validateJWT(String token) {
        loginRepository.validateJWT(token);
    }
}
