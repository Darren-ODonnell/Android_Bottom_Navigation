package com.example.bottomnavigationproper.Services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Player;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerRepository {

    private APIInterface apiInterface;
    private MutableLiveData<List<Player>> playerResponseLiveData;


    public PlayerRepository(){
        playerResponseLiveData = new MutableLiveData<>();

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
    public LiveData<List<Player>> getPlayersResponseLiveData() {
        return playerResponseLiveData;
    }
}

