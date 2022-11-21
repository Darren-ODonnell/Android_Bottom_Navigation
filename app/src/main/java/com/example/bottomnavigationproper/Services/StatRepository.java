package com.example.bottomnavigationproper.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.Models.StatName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatRepository {

    private APIInterface apiInterface;
    private MutableLiveData<List<Stat>> statResponseLiveData;
    private MutableLiveData<List<StatName>> statNameLiveData;


    public StatRepository(){
        statResponseLiveData = new MutableLiveData<>();
        statNameLiveData = new MutableLiveData<>();

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
                    }

                    @Override
                    public void onFailure(Call<List<StatName>> call, Throwable t) {
                        statResponseLiveData.postValue(null);
                    }
                });

    }
    public LiveData<List<Stat>> getStatsResponseLiveData() {
        return statResponseLiveData;
    }

    public LiveData<List<StatName>> getStatNameLiveData() {return statNameLiveData;}

    public void getCountAllFixture(String token, String firstname, String lastname, String statName){
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("statName", statName);

        apiInterface.countAllFixtureByPlayerStatName(token, params)
                .enqueue(new Callback<List<Stat>>() {
                    @Override
                    public void onResponse(Call<List<Stat>> call, Response<List<Stat>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stat>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayer(String token, String fixtureDate, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        params.put("statName", statName);

        apiInterface.countAllPlayerByFixtureStatName(token, params)
                .enqueue(new Callback<List<Stat>>() {
                    @Override
                    public void onResponse(Call<List<Stat>> call, Response<List<Stat>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stat>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerFixture(String token, String statName) {
        Map<String, String> params = new HashMap<>();
        params.put("statName", statName);
        apiInterface.countAllPlayerFixtureByStatName(token, params)
                .enqueue(new Callback<List<Stat>>() {
                    @Override
                    public void onResponse(Call<List<Stat>> call, Response<List<Stat>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stat>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerStatFixture(String token) {
        apiInterface.countAllPlayerStatFixture(token)
                .enqueue(new Callback<List<Stat>>() {
                    @Override
                    public void onResponse(Call<List<Stat>> call, Response<List<Stat>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stat>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllPlayerStat(String token, String fixtureDate) {
        Map<String, String> params = new HashMap<>();
        params.put("fixtureDate", fixtureDate);
        apiInterface.countAllPlayerStatNameByFixtureDate(token, params)
                .enqueue(new Callback<List<Stat>>() {
                    @Override
                    public void onResponse(Call<List<Stat>> call, Response<List<Stat>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stat>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }

    public void getCountAllStatFixture(String token, String firstname, String lastname) {
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);

        apiInterface.countAllStatNameFixtureByPlayer(token, params)
                .enqueue(new Callback<List<Stat>>() {
                    @Override
                    public void onResponse(Call<List<Stat>> call, Response<List<Stat>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stat>> call, Throwable t) {
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
                .enqueue(new Callback<List<Stat>>() {
                    @Override
                    public void onResponse(Call<List<Stat>> call, Response<List<Stat>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stat>> call, Throwable t) {
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
                .enqueue(new Callback<List<Stat>>() {
                    @Override
                    public void onResponse(Call<List<Stat>> call, Response<List<Stat>> response) {
                        if (response.body() != null) {
                            statResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stat>> call, Throwable t) {
                        statResponseLiveData.postValue(null);

                    }
                });
    }
}

