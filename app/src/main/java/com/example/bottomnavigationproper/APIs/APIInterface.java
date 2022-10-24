package com.example.bottomnavigationproper.APIs;

import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.PlayerResponse;
import com.example.bottomnavigationproper.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("api/auth/login")
    Call<User> login(@Body Login login);

    @GET("player/list")
    Call<List<Player>> getPlayers(@Header("Authorization") String accessToken);
//    Call<ResponseBody> getPlayers();

}
