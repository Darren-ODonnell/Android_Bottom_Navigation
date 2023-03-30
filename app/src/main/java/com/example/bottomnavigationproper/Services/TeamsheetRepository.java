package com.example.bottomnavigationproper.Services;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Teamsheet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsheetRepository {
    private APIInterface apiInterface;
    private MutableLiveData<List<Teamsheet>> teamsheetsResponseLiveData;

    public TeamsheetRepository(){

        teamsheetsResponseLiveData = new MutableLiveData<>();

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }


    public void getTeamsheetsForFixture(Fixture fixture, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("id", Long.toString(fixture.getId()));
        apiInterface.getTeamsheetsForFixture(token, params)
                .enqueue(new Callback<List<Teamsheet>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Teamsheet>> call, Response<List<Teamsheet>> response) {
                        if (response.body() != null) {
                            teamsheetsResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Teamsheet>> call, Throwable t) {
                        teamsheetsResponseLiveData.postValue(null);

                    }
                });
    }


    public LiveData<List<Teamsheet>> getTeamsheetsResponseLiveData() {
        return teamsheetsResponseLiveData;
    }

    public void update(String token, List<Teamsheet> subsToChange) {
        
        apiInterface.updateTeamsheets(token, subsToChange)
                .enqueue(new Callback<List<Teamsheet>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Teamsheet>> call, Response<List<Teamsheet>> response) {
                        if (response.body() != null) {
                            teamsheetsResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Teamsheet>> call, Throwable t) {
                        teamsheetsResponseLiveData.postValue(null);

                    }
                });
    }
}
