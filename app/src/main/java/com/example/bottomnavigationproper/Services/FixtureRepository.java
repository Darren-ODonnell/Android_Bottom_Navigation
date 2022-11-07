package com.example.bottomnavigationproper.Services;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FixtureRepository {

    private APIInterface apiInterface;
    private MutableLiveData<List<Fixture>> fixtureResponseLiveData;


    public FixtureRepository(){
        fixtureResponseLiveData = new MutableLiveData<>();

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void getFixtures(String token){
        apiInterface.getFixtures(token)
            .enqueue(new Callback<List<Fixture>>() {
                @Override
                public void onResponse(@NonNull Call<List<Fixture>> call, Response<List<Fixture>> response) {

                    Log.d("fixtureName", "hello");
                    if (response.body() != null) {
                        fixtureResponseLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Fixture>> call, Throwable t) {
                    fixtureResponseLiveData.postValue(null);

                }
            });
    }
    public LiveData<List<Fixture>> getFixturesResponseLiveData() {
        return fixtureResponseLiveData;
    }
}

