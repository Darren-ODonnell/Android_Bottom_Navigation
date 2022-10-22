package com.example.bottomnavigationproper.Services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Player;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerRepository {

    PlayerRepository service;
    private APIInterface apiInterface;
    private List<Player> players = new ArrayList<>();


    public PlayerRepository(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public PlayerRepository getInstance(){
        service = (service == null) ? new PlayerRepository(): service;
        return service;
    }

    public List<Player> getPlayers(String token){
        getPlayersFromDB(token);
        return players;
    }

    private void getPlayersFromDB(String token){
        Call<List<Player>> call = apiInterface.getPlayers(token);

        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(@NonNull Call<List<Player>> call, @NonNull Response<List<Player>> response) {
                if(response.isSuccessful()){
                    players = response.body();
                }else{
//                    Toast.makeText(getApplicationContext(), "Login not correct :(", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Player>> call, @NonNull Throwable t) {
                call.cancel();
//                Toast.makeText(getApplicationContext(), "error :(", Toast.LENGTH_SHORT).show();            }
            }
        });

    }
}
