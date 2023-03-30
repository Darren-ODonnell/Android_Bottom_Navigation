package com.example.bottomnavigationproper.Services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Teamsheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerRepository {

    private APIInterface apiInterface;
    private MutableLiveData<List<Player>> playerResponseLiveData;
    private MutableLiveData<Player> singPlayerResponseLiveData;


    public PlayerRepository(){
        playerResponseLiveData = new MutableLiveData<>();
        singPlayerResponseLiveData = new MutableLiveData<>();

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void getPlayers(String token){
        apiInterface.getPlayers(token)
            .enqueue(new Callback<List<Player>>() {
                @Override
                public void onResponse(@NonNull Call<List<Player>> call, Response<List<Player>> response) {
                    if (response.body() != null) {
                        playerResponseLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Player>> call, Throwable t) {
                    playerResponseLiveData.postValue(null);

                }
            });
    }

    public void getPlayersForFixture(Fixture fixture, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("id", Long.toString(fixture.getId()));
        apiInterface.getPlayersForFixture(token, params)
                .enqueue(new Callback<List<Player>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Player>> call, Response<List<Player>> response) {
                        if (response.body() != null) {
                            playerResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Player>> call, Throwable t) {
                        playerResponseLiveData.postValue(null);

                    }
                });
    }



    public void getPlayerByEmail(String email, String token) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        apiInterface.getPlayerByEmail(token, params)
                .enqueue(new Callback<Player>() {
                    @Override
                    public void onResponse(@NonNull Call<Player> call, Response<Player> response) {
                        if (response.body() != null) {
                            singPlayerResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Player> call, Throwable t) {
                        singPlayerResponseLiveData.postValue(null);

                    }
                });
    }

    public LiveData<List<Player>> getPlayersResponseLiveData() {
        return playerResponseLiveData;
    }


    public LiveData<Player> getSingPlayerResponseLiveData() {
        return singPlayerResponseLiveData;
    }


}

