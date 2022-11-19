package com.example.bottomnavigationproper.APIs;

import com.example.bottomnavigationproper.Models.Club;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Player;

import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("api/auth/login")
    Call<User> login(@Body Login login);

    @GET("api/auth/checkToken")
    Call<Boolean> checkToken(@Query("token") String token);

    @GET("player/list")
    Call<List<Player>> getPlayers(@Header("Authorization") String accessToken);

    @GET("fixture/findByClub")
    Call<List<Fixture>> getFixtures(@Header("Authorization") String accessToken, @Query("name") String name);

    @GET("stats_view/countByStatNameFixtureDate2")
    Call<List<Stat>> getStats(@Header("Authorization") String accessToken);

    @GET("club/list")
    Call<List<Club>> getClubs(@Header("Authorization") String accessToken);

    @GET("club/findByName")
    Call<Club> getClubByName(@Header("Authorization") String accessToken);

    @GET("statname/list")
    Call<List<StatName>> getStatNames(@Header("Authorization") String accessToken);



}
