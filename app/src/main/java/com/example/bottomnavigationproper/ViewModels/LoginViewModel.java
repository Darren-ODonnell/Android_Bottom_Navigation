//package com.example.bottomnavigationproper.ViewModels;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.bottomnavigationproper.Models.Login;
//import com.example.bottomnavigationproper.Services.LoginRepository;
//
//public class LoginViewModel {
//
//    private LoginRepository loginRepository;
//    private MutableLiveData<Boolean> tokenValidityLiveData;
//    private MutableLiveData<String> tokenLiveData;
//
//    public LoginViewModel(@NonNull Application application) {
//        super(application);
//    }
//
//    public void init(){
//        loginRepository = new LoginRepository();
//        tokenValidityLiveData = loginRepository.getTokenValidity();
//        tokenLiveData = loginRepository.getTokenString();
//    }
//
//    public void logIn(Login login) {
//        loginRepository.login(login);
//    }
//
//    public MutableLiveData<Boolean> getTokenValidityLiveData() {
//        return tokenValidityLiveData;
//    }
//
//    public MutableLiveData<String> getTokenLiveData() {
//        return tokenLiveData;
//    }
//
//
//
//}
//
//}
