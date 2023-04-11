//package com.example.bottomnavigationproper.Services;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.bottomnavigationproper.APIs.APIClient;
//import com.example.bottomnavigationproper.APIs.APIInterface;
//import com.example.bottomnavigationproper.Models.Club;
//import com.example.bottomnavigationproper.Models.Fixture;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ClubRepository {
//
//    private APIInterface apiInterface;
//    private MutableLiveData<List<Club>> clubResponseLiveData;
//
//
//    public ClubRepository(){
//        clubResponseLiveData = new MutableLiveData<>();
//
//        apiInterface = APIClient.getClient().create(APIInterface.class);
//    }
//
//    public void getClubs(String token){
//        apiInterface.getClubs(token).enqueue(new Callback<List<Club>>() {
//            @Override
//            public void onResponse(Call<List<Club>> call, Response<List<Club>> response) {
//                if (response.body() != null) {
//                    clubResponseLiveData.postValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Club>> call, Throwable t) {
//                clubResponseLiveData.postValue(null);
//            }
//        });
//
//    }
//
//    public void getClubsByName(String name, String token){
//        apiInterface.getClubByName(token).enqueue(new Callback<List<Club>>() {
//            @Override
//            public void onResponse(Call<List<Club>> call, Response<List<Club>> response) {
//                if (response.body() != null) {
//                    clubResponseLiveData.postValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Club>> call, Throwable t) {
//                clubResponseLiveData.postValue(null);
//            }
//        });
//    }
//    public LiveData<List<Club>> getClubsResponseLiveData() {
//        return clubResponseLiveData;
//    }
//
//
//}
