//package com.example.bottomnavigationproper.ViewModels;
//
//import android.app.Application;
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import com.example.bottomnavigationproper.APIs.TokenSingleton;
//import com.example.bottomnavigationproper.Models.Fixture;
//import com.example.bottomnavigationproper.Services.FixtureRepository;
//
//import java.util.List;
//
//public class FixtureViewModel extends AndroidViewModel {
//    private FixtureRepository fixtureRepository;
//    private LiveData<List<Fixture>> fixtureResponseLiveData;
//
//    public FixtureViewModel(@NonNull Application application) {
//        super(application);
//    }
//
//    public void init(){
//        fixtureRepository = new FixtureRepository();
//        fixtureResponseLiveData = fixtureRepository.getFixturesResponseLiveData();
//    }
//
//    public void getFixtures() {
//        fixtureRepository.getFixtures(TokenSingleton.getInstance().getBearerTokenString());
//
//    }
//
//    private String getToken() {
//        SharedPreferences settings = getApplication().getSharedPreferences("api_key",
//                Context.MODE_PRIVATE);
//        String token = settings.getString("token", null);
//        return token;
//    }
//
//    public LiveData<List<Fixture>> getFixturesResponseLiveData() {
//        return fixtureResponseLiveData;
//    }
//}
