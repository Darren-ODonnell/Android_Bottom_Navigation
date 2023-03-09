package com.example.bottomnavigationproper;

import com.example.bottomnavigationproper.Models.Player;

public class UserSingleton{

    private static UserSingleton instance;
    private User user;
    private Player player;

    private UserSingleton(){

    }

    public static UserSingleton getInstance(){
        instance = (instance == null) ? new UserSingleton(): instance;
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public String getRole(){
        if(user.getRoles().contains("ROLE_ADMIN"))
            return "ADMIN";
        else if(user.getRoles().contains("ROLE_USER")){
            return "USER";
        }
        return null;
    }


}
