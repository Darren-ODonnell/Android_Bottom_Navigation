package com.example.bottomnavigationproper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Services.LoginRepository;
import com.example.bottomnavigationproper.ViewModels.GameViewModel;
import com.example.bottomnavigationproper.ViewModels.LoginViewModel;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "token_key";
    public static final String API_KEY = "jwt_token";
    public static final String USER = "logged_in_user";

    private static final boolean DEBUG_LOGIN_WITHOUT_JWT = false;


    LoginViewModel viewModel;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

        validateJWT();

    }

    public void validateJWT(){
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        viewModel.init();
        String token = retrieveToken();

        viewModel.getSingPlayerResponseLiveData().observe(this, new Observer<Player>() {
            @Override
            public void onChanged(Player player) {
                UserSingleton.getInstance().setPlayer(player);
                startActivity(new Intent(getApplicationContext(), BottomNavActivity.class));
            }
        });

        viewModel.getTokenValidityLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    TokenSingleton.getInstance().setTokenString(token);
                    UserSingleton.getInstance().setUser(retrieveUser());
                    if(!UserSingleton.getInstance().isAdminOrCoach()) {
                        viewModel.getPlayerByEmail(
                                UserSingleton.getInstance().getUser()
                        );
                    }else
                        startActivity(new Intent(getApplicationContext(), BottomNavActivity.class));
                    storeToken(getApplicationContext());
                }else{
                    buildRegisterLoginScreen();
                }
            }
        });

        if(DEBUG_LOGIN_WITHOUT_JWT){
            buildRegisterLoginScreen();
        }else{
            if(token != null){
                viewModel.validateJWT(token);
            }else{
                buildRegisterLoginScreen();
            }
        }

    }


    private String retrieveToken() {
        Context context = getApplicationContext();

        settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(API_KEY, null);
    }

    private User retrieveUser(){
        Context context = getApplicationContext();
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        Gson gson = new Gson();
        return gson.fromJson(settings.getString(USER, null), User.class);
    }

    public void buildRegisterLoginScreen(){
        setContentView(R.layout.register_login_screen);

        findViewById(R.id.navigate_to_login).setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.bottomnavigationproper.LoginActivity.class));
        });

        findViewById(R.id.navigate_to_register).setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.bottomnavigationproper.RegisterActivity.class));
        });
    }

    public void storeToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(API_KEY, TokenSingleton.getInstance().getTokenStr());
        // Commit the edits!
        editor.apply();
    }



    @Override
    protected void onStop() {
        if(viewModel.getSingPlayerResponseLiveData().hasObservers()){
            viewModel.getSingPlayerResponseLiveData().removeObservers(this);
        }

        viewModel.getTokenValidityLiveData().removeObservers(this);
        super.onStop();

    }
}
