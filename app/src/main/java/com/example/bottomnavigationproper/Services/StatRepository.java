package com.example.bottomnavigationproper.Services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.Models.StatName;

import java.util.List;

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

    public void getStats(String token){
        apiInterface.getStats(token)
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

}

