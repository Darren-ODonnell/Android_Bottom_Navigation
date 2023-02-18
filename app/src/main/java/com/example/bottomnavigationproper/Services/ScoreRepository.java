package com.example.bottomnavigationproper.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Result;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Models.StatsView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoreRepository {

    private APIInterface apiInterface;
    private MutableLiveData<Result> scoreResponseLiveData;


    public ScoreRepository() {
        scoreResponseLiveData = new MutableLiveData<>();


        apiInterface = APIClient.getClient().create(APIInterface.class);
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

}
