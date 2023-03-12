package com.example.bottomnavigationproper;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Services.PlayerRepository;

public class UserSingleton {

    private static UserSingleton instance;
    private User user;
    private Player associatedPlayer;

    private UserSingleton(){

    }

    public static UserSingleton getInstance(){
        instance = (instance == null) ? new UserSingleton(): instance;
        return instance;
    }

    public void setUser(User user){
        this.user = user;

        PlayerRepository repository = new PlayerRepository();

        repository.getSingPlayerResponseLiveData().observeForever(new Observer<Player>() {
            @Override
            public void onChanged(Player player) {
                associatedPlayer = player;
            }
        });

        if(user.getFellow().getFellowType().equalsIgnoreCase("player")){
            repository.getPlayerByEmail(user.getEmail(), TokenSingleton.getInstance().getBearerTokenString());
        }

        System.out.println("this");
    }

    public String getRole(){
        if(user.getRoles().contains("ROLE_ADMIN"))
            return "ADMIN";
        else if(user.getRoles().contains("ROLE_COACH"))
            return "COACH";
        else if(user.getRoles().contains("ROLE_PLAYER"))
            return "USER";

        return null;
    }


    public Player getPlayer() {
        return associatedPlayer;
    }
}
