package com.example.bottomnavigationproper.APIs;

import com.example.bottomnavigationproper.Models.Club;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Player;

import com.example.bottomnavigationproper.Models.Result;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Models.StatModel;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Models.Register;
import com.example.bottomnavigationproper.Models.Teamsheet;
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
import retrofit2.http.QueryMap;

public interface APIInterface {

    @POST("api/auth/login")
    Call<User> login(@Body Login login);

    @POST("api/auth/register")
    Call<User> register(@Body Register register);

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

    @GET("stats_view/countAllFixtureByPlayerStatName")
    Call<List<StatsView>> countAllFixtureByPlayerStatName(@Header("Authorization") String accessToken,
                                                          @QueryMap Map<String, String> params);

    @GET("stats_view/countAllPlayerByFixtureStatName")
    Call<List<StatsView>> countAllPlayerByFixtureStatName(@Header("Authorization") String accessToken,
                                                          @QueryMap Map<String, String> params);

    @GET("stats_view/countAllPlayerFixtureByStatName")
    Call<List<StatsView>> countAllPlayerFixtureByStatName(@Header("Authorization") String accessToken,
                                                          @QueryMap Map<String, String> params);

    @GET("stats_view/countAllPlayerStat")
    Call<List<StatsView>> countAllPlayerStat(@Header("Authorization") String accessToken);

    @GET("stats_view/countAllPlayerStatNameByFixtureDate")
    Call<List<StatsView>> countAllPlayerStatNameByFixtureDate(@Header("Authorization") String accessToken,
                                                              @QueryMap Map<String, String> params);
    @GET("stats_view/countAllStatNameFixtureByPlayer")
    Call<List<StatsView>> countAllStatNameFixtureByPlayer(@Header("Authorization") String accessToken,
                                                          @QueryMap Map<String, String> params);

    @GET("stats_view/countAllStatsByPlayerFixtureDate")
    Call<List<StatsView>> countAllStatsByPlayerFixtureDate(@Header("Authorization") String accessToken,
                                                           @QueryMap Map<String, String> params);

    @GET("stats_view/countStat")
    Call<List<StatsView>> countStat(@Header("Authorization") String accessToken,
                                    @QueryMap Map<String, String> params);



    @GET("stats_view/countAllFixtureByPlayerStatNameHeatMap")
    Call<List<StatsView>> countAllFixtureByPlayerStatNameHeatMap(@Header("Authorization") String accessToken,
                                                          @QueryMap Map<String, String> params);

    @GET("stats_view/countAllPlayerByFixtureStatNameHeatMap")
    Call<List<StatsView>> countAllPlayerByFixtureStatNameHeatMap(@Header("Authorization") String accessToken,
                                                          @QueryMap Map<String, String> params);

    @GET("stats_view/countAllPlayerFixtureByStatNameHeatMap")
    Call<List<StatsView>> countAllPlayerFixtureByStatNameHeatMap(@Header("Authorization") String accessToken,
                                                          @QueryMap Map<String, String> params);

    @GET("stats_view/countAllPlayerStatHeatMap")
    Call<List<StatsView>> countAllPlayerStatHeatMap(@Header("Authorization") String accessToken);

    @GET("stats_view/countAllPlayerStatNameByFixtureDateHeatMap")
    Call<List<StatsView>> countAllPlayerStatNameByFixtureDateHeatMap(@Header("Authorization") String accessToken,
                                                              @QueryMap Map<String, String> params);
    @GET("stats_view/countAllStatNameFixtureByPlayerHeatMap")
    Call<List<StatsView>> countAllStatNameFixtureByPlayerHeatMap(@Header("Authorization") String accessToken,
                                                          @QueryMap Map<String, String> params);

    @GET("stats_view/countAllStatsByPlayerFixtureDateHeatMap")
    Call<List<StatsView>> countAllStatsByPlayerFixtureDateHeatMap(@Header("Authorization") String accessToken,
                                                           @QueryMap Map<String, String> params);

    @GET("stats_view/countStatHeatMap")
    Call<List<StatsView>> countStatHeatMap(@Header("Authorization") String accessToken,
                                    @QueryMap Map<String, String> params);

    @GET("teamsheet/findPlayersByFixtureId")
    Call<List<Player>> getPlayersForFixture(@Header("Authorization") String accessToken,
                                            @QueryMap Map<String, String> params);


    @GET("stats_view/countAllPlayerStatNameByFixtureDateGroupSuccess")
    Call<List<StatsView>> countAllPlayerStatNameByFixtureDateGroupSuccess(@Header("Authorization") String accessToken,
                                                                          @QueryMap Map<String, String> params);

    @PUT("stats/add")
    Call<List<StatsView>> addStat(@Header("Authorization") String token, @Body StatModel statModel );

    @GET("stats/scoreByFixtureDate")
    Call<Result> getScoreByFixtureDate(@Header("Authorization") String token, @QueryMap Map<String, String> params );

    @GET("stats_view/getAvgStatsForWinsByOpponent")
    Call<List<StatsView>> getAvgStatsForWinsByOpponent(@Header("Authorization") String accessToken,
                                                                          @QueryMap Map<String, String> params);

    @GET("stats_view/getAvgStatsForLossesByOpponent")
    Call<List<StatsView>> getAvgStatsForLossesByOpponent(@Header("Authorization") String accessToken,
                                                       @QueryMap Map<String, String> params);

    @GET("stats_view/getStatsForLastFiveFixturesWon")
    Call<List<StatsView>> getStatsForLastFiveFixturesWon(@Header("Authorization") String accessToken);

    @GET("stats_view/getStatsForLastFiveFixturesLost")
    Call<List<StatsView>> getStatsForLastFiveFixturesLost(@Header("Authorization") String accessToken);

    @GET("stats_view/countStatsPlayerAnalysis")
    Call<List<StatsView>> countStatsPlayerAnalysis(@Header("Authorization") String accessToken,
                                                   @QueryMap Map<String, String> params);

    @GET("stats_view/countStatsAllPlayerAnalysis")
    Call<List<StatsView>> countStatsAllPlayerAnalysis(@Header("Authorization") String accessToken);

    @GET("player/findByEmail")
    Call<Player> getPlayerByEmail(@Header("Authorization") String accessToken,
                                  @QueryMap Map<String, String> params);

    @GET("teamsheets/findByFixtureId")
    Call<List<Teamsheet>> getTeamsheetsForFixture(@Header("Authorization") String accessToken,
                                                  @QueryMap Map<String, String> params);

    @POST("teamsheets/updateAll")
    Call<List<Teamsheet>> updateTeamsheets(@Header("Authorization") String accessToken,
                                           @Body List<Teamsheet> teamsheets);
}
