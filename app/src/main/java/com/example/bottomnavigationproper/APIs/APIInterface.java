package com.example.bottomnavigationproper.APIs;

import com.example.bottomnavigationproper.Models.Club;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Player;

import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.Models.StatModel;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @GET("club/list")
    Call<List<Club>> getClubs(@Header("Authorization") String accessToken);

    @GET("club/findByName")
    Call<Club> getClubByName(@Header("Authorization") String accessToken);

    @GET("statname/list")
    Call<List<StatName>> getStatNames(@Header("Authorization") String accessToken);

    @FormUrlEncoded
    @POST("stats_view/countAllFixtureByPlayerStatName")
    Call<List<Stat>> countAllFixtureByPlayerStatName(@Header("Authorization") String accessToken,
                                                     @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("stats_view/countAllPlayerByFixtureStatName")
    Call<List<Stat>> countAllPlayerByFixtureStatName(@Header("Authorization") String accessToken,
                                                     @FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("stats_view/countAllPlayerFixtureByStatName")
    Call<List<Stat>> countAllPlayerFixtureByStatName(@Header("Authorization") String accessToken,
                                                     @FieldMap Map<String, String> params);

    @GET("stats_view/countAllPlayerStat")
    Call<List<Stat>> countAllPlayerStat(@Header("Authorization") String accessToken);

    @FormUrlEncoded
    @POST("stats_view/countAllPlayerStatNameByFixtureDate")
    Call<List<Stat>> countAllPlayerStatNameByFixtureDate(@Header("Authorization") String accessToken,
                                                         @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("stats_view/countAllStatNameFixtureByPlayer")
    Call<List<Stat>> countAllStatNameFixtureByPlayer(@Header("Authorization") String accessToken,
                                                     @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("stats_view/countAllStatsByPlayerFixtureDate")
    Call<List<Stat>> countAllStatsByPlayerFixtureDate(@Header("Authorization") String accessToken,
                                                      @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("stats_view/countStat")
    Call<List<Stat>> countStat(@Header("Authorization") String accessToken,
                               @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("teamsheet/findPlayersByFixtureId")
    Call<List<Player>> getPlayersForFixture(@Header("Authorization") String accessToken,
                                            @FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("stats_view/countAllPlayerStatNameByFixtureDateGroupSuccess")
    Call<List<Stat>> countAllPlayerStatNameByFixtureDateGroupSuccess(@Header("Authorization") String accessToken,
                                                         @FieldMap Map<String, String> params);

    @PUT("stats/add")
    Call<List<Stat>> addStat(@Header("Authorization") String token, @Body StatModel statModel );
}
