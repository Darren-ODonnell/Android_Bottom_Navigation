package com.example.bottomnavigationproper.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Player;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerService {

    private APIInterface apiInterface;

    public PlayerService(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void getPlayers(String token){
        Call<List<Player>> call = apiInterface.getPlayers(token);

        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(@NonNull Call<List<Player>> call, @NonNull Response<List<Player>> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
//                        Toast.makeText(getApplicationContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                    for(Player p: response.body()){
                        String player = p.toString();
                        Log.d("PlayersObject", player);
                    }

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
