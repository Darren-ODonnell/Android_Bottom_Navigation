package com.example.bottomnavigationproper.APIs;

import com.example.bottomnavigationproper.Models.Club;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Player;

import com.example.bottomnavigationproper.Models.StatView;
import com.example.bottomnavigationproper.Models.StatModel;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    Call<List<StatView>> countAllFixtureByPlayerStatName(@Header("Authorization") String accessToken,
                                                         @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("stats_view/countAllPlayerByFixtureStatName")
    Call<List<StatView>> countAllPlayerByFixtureStatName(@Header("Authorization") String accessToken,
                                                         @FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST("stats_view/countAllPlayerFixtureByStatName")
    Call<List<StatView>> countAllPlayerFixtureByStatName(@Header("Authorization") String accessToken,
                                                         @FieldMap Map<String, String> params);

    @GET("stats_view/countAllPlayerStat")
    Call<List<StatView>> countAllPlayerStat(@Header("Authorization") String accessToken);

    @FormUrlEncoded
    @POST("stats_view/countAllPlayerStatNameByFixtureDate")
    Call<List<StatView>> countAllPlayerStatNameByFixtureDate(@Header("Authorization") String accessToken,
                                                             @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("stats_view/countAllStatNameFixtureByPlayer")
    Call<List<StatView>> countAllStatNameFixtureByPlayer(@Header("Authorization") String accessToken,
                                                         @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("stats_view/countAllStatsByPlayerFixtureDate")
    Call<List<StatView>> countAllStatsByPlayerFixtureDate(@Header("Authorization") String accessToken,
                                                          @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("stats_view/countStat")
    Call<List<StatView>> countStat(@Header("Authorization") String accessToken,
                                   @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("teamsheet/findPlayersByFixtureId")
    Call<List<Player>> getPlayersForFixture(@Header("Authorization") String accessToken,
                                            @FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("stats_view/countAllPlayerStatNameByFixtureDateGroupSuccess")
    Call<List<StatView>> countAllPlayerStatNameByFixtureDateGroupSuccess(@Header("Authorization") String accessToken,
                                                                         @FieldMap Map<String, String> params);

    @PUT("stats/add")
    Call<List<StatView>> addStat(@Header("Authorization") String token, @Body StatModel statModel );
}
