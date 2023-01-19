package com.example.bottomnavigationproper.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.StatView;
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
    private MutableLiveData<List<StatView>> statResponseLiveData;
    private MutableLiveData<List<StatName>> statNameLiveData;
    private MutableLiveData<Boolean> singleFixtureLiveData;
    private MutableLiveData<Boolean> singleStatLiveData;


    public StatRepository(){
        statResponseLiveData = new MutableLiveData<>();
        statNameLiveData = new MutableLiveData<>();
        singleFixtureLiveData = new MutableLiveData<>();
        singleStatLiveData = new MutableLiveData<>();

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
    public LiveData<List<StatView>> getStatsResponseLiveData() {
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
                .enqueue(new Callback<List<StatView>>() {
                    @Override
                    public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayer(String token, String fixtureDate, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        params.put("statName", statName);

        apiInterface.countAllPlayerByFixtureStatName(token, params)
                .enqueue(new Callback<List<StatView>>() {
                    @Override
                    public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerFixture(String token, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("statName", statName);
        apiInterface.countAllPlayerFixtureByStatName(token, params)
                .enqueue(new Callback<List<StatView>>() {
                    @Override
                    public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerStatFixture(String token) {
        apiInterface.countAllPlayerStat(token)
                .enqueue(new Callback<List<StatView>>() {
                    @Override
                    public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(false);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }


    public void countAllPlayerStatNameByFixtureDateGroupSuccess(String token, String fixtureDate) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        apiInterface.countAllPlayerStatNameByFixtureDateGroupSuccess(token, params)
                .enqueue(new Callback<List<StatView>>() {
                    @Override
                    public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerStat(String token, String fixtureDate) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        apiInterface.countAllPlayerStatNameByFixtureDate(token, params)
                .enqueue(new Callback<List<StatView>>() {
                    @Override
                    public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleFixtureLiveData.postValue(true);
                            singleStatLiveData.postValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllStatFixture(String token, String firstname, String lastname) {
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);

        apiInterface.countAllStatNameFixtureByPlayer(token, params)
                .enqueue(new Callback<List<StatView>>() {
                    @Override
                    public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(false);
                            singleFixtureLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatView>> call, Throwable t) {
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
                .enqueue(new Callback<List<StatView>>() {
                    @Override
                    public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleFixtureLiveData.postValue(true);
                            singleStatLiveData.postValue(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatView>> call, Throwable t) {
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
                .enqueue(new Callback<List<StatView>>() {
                    @Override
                    public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                            singleStatLiveData.postValue(true);
                            singleFixtureLiveData.postValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<StatView>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }


    public void persistStat(String token, StatModel stat) {
        //TODO create put/post request for persisting stat

        apiInterface.addStat(token,stat)
                .enqueue(new Callback<List<StatView>>(){

            @Override
            public void onResponse(Call<List<StatView>> call, Response<List<StatView>> response) {
                if (response.body() != null) {
                    statResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<StatView>> call, Throwable t) {
                statResponseLiveData.postValue(null);
            }
        });
    }
}

