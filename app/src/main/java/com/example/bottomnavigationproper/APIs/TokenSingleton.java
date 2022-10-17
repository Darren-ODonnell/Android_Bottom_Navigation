package com.example.bottomnavigationproper.APIs;

public class TokenSingleton {

    private static TokenSingleton tokenInstance;
    private String tokenStr;

    private TokenSingleton(){

    }

    public static TokenSingleton getInstance(){
        tokenInstance = (tokenInstance == null) ? new TokenSingleton(): tokenInstance;
        return  tokenInstance;
    }

    public void setTokenString(String tokenStr){
        this.tokenStr = tokenStr;
    }

    public String getTokenString(){
        return tokenStr;
    }

}
