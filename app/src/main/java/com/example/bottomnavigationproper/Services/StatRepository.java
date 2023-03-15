package com.example.bottomnavigationproper.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Result;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Models.StatModel;
import com.example.bottomnavigationproper.Models.StatName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatRepository {

    private APIInterface apiInterface;
    private MutableLiveData<List<StatsView>> statResponseLiveData;
    private MutableLiveData<List<StatName>> statNameLiveData;
    private MutableLiveData<Boolean> singleFixtureLiveData;
    private MutableLiveData<Boolean> singleStatLiveData;
    private MutableLiveData<Result> scoreResponseLiveData;
    private MutableLiveData<List<StatsView>> pregameWinAnalysisLiveData;
    private MutableLiveData<List<StatsView>> pregameLossAnalysisLiveData;



    public StatRepository(){
        statResponseLiveData = new MutableLiveData<>();
        statNameLiveData = new MutableLiveData<>();
        singleFixtureLiveData = new MutableLiveData<>();
        singleStatLiveData = new MutableLiveData<>();
        scoreResponseLiveData = new MutableLiveData<>();
        pregameWinAnalysisLiveData = new MutableLiveData<>();
        pregameLossAnalysisLiveData = new MutableLiveData<>();


        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

//    public void getStats(String token){
//        apiInterface.getStats(token)
//            .enqueue(new Callback<List<Stat>>() {
//                @Override
//                public void onResponse(Call<List<Stat>> call, Response<List<Stat>> response) {
//                    if (response.body() != null) {
//                        statResponseLiveData.postValue(response.body());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<Stat>> call, Throwable t) {
//                    statResponseLiveData.postValue(null);
//
//                }
//            });
//    }



    public void getStatNames(String token){
        apiInterface.getStatNames(token)
                .enqueue(new Callback<List<StatName>>() {
                    @Override
                    public void onResponse(Call<List<StatName>> call, Response<List<StatName>> response) {
                        if(response.body() != null)
                            statNameLiveData.postValue(response.body());
                            singleStatLiveData.postValue(false);
                            singleFixtureLiveData.postValue(false);

                    }

                    @Override
                    public void onFailure(Call<List<StatName>> call, Throwable t) {
                        statResponseLiveData.postValue(null);
                    }
                });

    }
    public LiveData<List<StatsView>> getStatsResponseLiveData() {
        return statResponseLiveData;
    }

    public LiveData<List<StatName>> getStatNameLiveData() {return statNameLiveData;}

    public LiveData<Boolean> getSingleStatLiveData() {
        return singleStatLiveData;
    }

    public LiveData<Boolean> getSingleFixtureLiveData() {
        return singleFixtureLiveData;
    }

    public void getCountAllFixture(String token, String firstname, String lastname, String statName){
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("statName", statName);

        apiInterface.countAllFixtureByPlayerStatName(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayer(String token, String fixtureDate, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        params.put("statName", statName);

        apiInterface.countAllPlayerByFixtureStatName(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerFixture(String token, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("statName", statName);
        apiInterface.countAllPlayerFixtureByStatName(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerStatFixture(String token) {
        apiInterface.countAllPlayerStat(token)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(false);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }


    public void countAllPlayerStatNameByFixtureDateGroupSuccess(String token, String fixtureDate) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        apiInterface.countAllPlayerStatNameByFixtureDateGroupSuccess(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerStat(String token, String fixtureDate) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        apiInterface.countAllPlayerStatNameByFixtureDate(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleFixtureLiveData.postValue(true);
                            singleStatLiveData.postValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllStatFixture(String token, String firstname, String lastname) {
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);

        apiInterface.countAllStatNameFixtureByPlayer(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(false);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });

    }

    public void getCountAllStats(String token, String firstname, String lastname, String fixtureDate) {
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("fixtureDate", fixtureDate);

        apiInterface.countAllStatsByPlayerFixtureDate(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleFixtureLiveData.postValue(true);
                            singleStatLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountStat(String token, String firstname, String lastname, String fixtureDate, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("fixtureDate", fixtureDate);
        params.put("statName", statName);

        apiInterface.countStat(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }


    public void getCountAllFixtureHeatMap(String token, String firstname, String lastname, String statName){
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("statName", statName);

        apiInterface.countAllFixtureByPlayerStatNameHeatMap(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerHeatMap(String token, String fixtureDate, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        params.put("statName", statName);

        apiInterface.countAllPlayerByFixtureStatNameHeatMap(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerFixtureHeatMap(String token, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("statName", statName);
        apiInterface.countAllPlayerFixtureByStatNameHeatMap(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerStatFixtureHeatMap(String token) {
        apiInterface.countAllPlayerStatHeatMap(token)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(false);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerStatHeatMap(String token, String fixtureDate) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        apiInterface.countAllPlayerStatNameByFixtureDateHeatMap(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleFixtureLiveData.postValue(true);
                            singleStatLiveData.postValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllStatFixtureHeatMap(String token, String firstname, String lastname) {
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);

        apiInterface.countAllStatNameFixtureByPlayerHeatMap(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(false);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });

    }

    public void getCountAllStatsHeatMap(String token, String firstname, String lastname, String fixtureDate) {
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("fixtureDate", fixtureDate);

        apiInterface.countAllStatsByPlayerFixtureDateHeatMap(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleFixtureLiveData.postValue(true);
                            singleStatLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountStatHeatMap(String token, String firstname, String lastname, String fixtureDate, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("fixtureDate", fixtureDate);
        params.put("statName", statName);

        apiInterface.countStatHeatMap(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }


    public void persistStat(String token, StatModel stat, String fixtureDate) {
        //TODO create put/post request for persisting stat

        apiInterface.addStat(token,stat)
                .enqueue(new Callback<List<StatsView>>(){

            @Override
            public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                if (response.body() != null) {
                    statResponseLiveData.postValue(response.body());
                    countAllPlayerStatNameByFixtureDateGroupSuccess(token, fixtureDate);
                }
            }

            @Override
            public void onFailure(Call<List<StatsView>> call, Throwable t) {
                statResponseLiveData.postValue(null);
            }
        });
    }

    public void getScoreForFixture(String token, String fixtureDate) {

        Map<String, String> params = new HashMap<>();
        params.put("fixture_date", fixtureDate);

        apiInterface.getScoreByFixtureDate(token, params)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.body() != null)
                            scoreResponseLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        scoreResponseLiveData.postValue(null);
                    }

                });
    }
    public LiveData<Result> getScoreLiveData() {return scoreResponseLiveData;}

    public void getAvgStatsForWinsByOpponent(String token, String clubName) {

        Map<String, String> params = new HashMap<>();
        params.put("club_name", clubName);

        apiInterface.getAvgStatsForWinsByOpponent(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null)
                            pregameWinAnalysisLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        pregameWinAnalysisLiveData.postValue(null);
                    }

                });
    }

    public void getAvgStatsForLossesByOpponent(String token, String clubName) {

        Map<String, String> params = new HashMap<>();
        params.put("club_name", clubName);

        apiInterface.getAvgStatsForLossesByOpponent(token, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null)
                            pregameLossAnalysisLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        pregameLossAnalysisLiveData.postValue(null);
                    }

                });
    }

    public void getAvgStatsForLastFiveFixturesWon(String token) {
        apiInterface.getStatsForLastFiveFixturesWon(token)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null)
                            pregameWinAnalysisLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        pregameWinAnalysisLiveData.postValue(null);
                    }

                });
    }

    public void getAvgStatsForLastFiveFixturesLost(String token) {

        apiInterface.getStatsForLastFiveFixturesLost(token)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null)
                            pregameLossAnalysisLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        pregameLossAnalysisLiveData.postValue(null);
                    }

                });
    }
    public LiveData<List<StatsView>> getPregameWinAnalysisLiveData() {return pregameWinAnalysisLiveData;}
    public LiveData<List<StatsView>> getPregameLossAnalysisLiveData() {return pregameLossAnalysisLiveData;}


    public void countStatByPlayer(String firstname, String lastname, String bearerTokenString) {
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        apiInterface.countStatsPlayerAnalysis(bearerTokenString, params)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null)
                            statResponseLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);
                    }

                });
    }

    public void countStatsAllPlayer(String bearerTokenString) {
        apiInterface.countStatsAllPlayerAnalysis(bearerTokenString)
                .enqueue(new Callback<List<StatsView>>() {
                    @Override
                    public void onResponse(Call<List<StatsView>> call, Response<List<StatsView>> response) {
                        if (response.body() != null)
                            statResponseLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<StatsView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);
                    }

                });
    }
}

