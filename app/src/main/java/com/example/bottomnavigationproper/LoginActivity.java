package com.example.bottomnavigationproper;

import static com.example.bottomnavigationproper.MainActivity.API_KEY;
import static com.example.bottomnavigationproper.MainActivity.USER;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.ViewModels.LoginViewModel;
import com.google.gson.Gson;


public class LoginActivity extends AppCompatActivity {

    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());

        setContentView(R.layout.activity_login);

        attemptLogin();
        returnToRegister();

    }

    private void returnToRegister() {
        findViewById(R.id.navigate_to_register).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    public void attemptLogin(){

        findViewById(R.id.login).setOnClickListener(v -> {

            viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
            viewModel.init();
            viewModel.getTokenValidityLiveData().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean valid) {

                    if (valid) {
                       loginFromInput();
                    }else{
                        Toast.makeText(getApplicationContext(), "User does not exist, please register", Toast.LENGTH_LONG).show();
                    }

                }
            });

            String username = getTextFromEditText(R.id.name);
            String password = getTextFromEditText(R.id.password);

            Login loginObj = new Login(username, password);
            viewModel.login(loginObj);

            viewModel.getSingPlayerResponseLiveData().observe(this, new Observer<Player>() {
                @Override
                public void onChanged(Player player) {
                    UserSingleton.getInstance().setPlayer(player);
                    startActivity(new Intent(getApplicationContext(), BottomNavActivity.class));
                }
            });


        });
    }

    private void loginFromInput(){
        storeTokenAndUser(getApplicationContext());
        if(!UserSingleton.getInstance().isAdminOrCoach()) {
            viewModel.getPlayerByEmail(
                    UserSingleton.getInstance().getUser()
            );
        }else{
            startActivity(new Intent(getApplicationContext(), BottomNavActivity.class));
        }


    }

    private String getTextFromEditText(int id){
        return ((EditText)findViewById(id)).getText().toString();
    }

    public void storeTokenAndUser(Context context) {
        SharedPreferences settings = context.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(API_KEY, TokenSingleton.getInstance().getTokenStr());

        Gson gson = new Gson();
        String jsonString = gson.toJson(UserSingleton.getInstance().getUser());
        editor.putString(USER, jsonString);
        // Commit the edits!
        editor.apply();
    }

    @Override
    protected void onStop() {
        if(viewModel.getSingPlayerResponseLiveData() != null) {
            viewModel.getSingPlayerResponseLiveData().removeObservers(this);
        }
        viewModel.getTokenValidityLiveData().removeObservers(this);
        storeTokenAndUser(getApplicationContext());
        super.onStop();

    }
}
