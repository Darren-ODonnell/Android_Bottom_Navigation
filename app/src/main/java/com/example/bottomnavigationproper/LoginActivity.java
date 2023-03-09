package com.example.bottomnavigationproper;

import static com.example.bottomnavigationproper.MainActivity.API_KEY;

import android.app.UiAutomation;
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
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Services.LoginRepository;
import com.example.bottomnavigationproper.Services.PlayerRepository;
import com.example.bottomnavigationproper.ViewModels.LoginViewModel;
import com.example.bottomnavigationproper.ViewModels.StatViewModel;

import java.util.List;


public class LoginActivity extends AppCompatActivity {

    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
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

        });
    }

    private void loginFromInput(){
        storeToken(getApplicationContext());


        startActivity(new Intent(getApplicationContext(), BottomNavActivity.class));
    }

    private String getTextFromEditText(int id){
        return ((EditText)findViewById(id)).getText().toString();
    }

    public void storeToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(API_KEY, TokenSingleton.getInstance().getTokenStr());
        // Commit the edits!
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        storeToken(getApplicationContext());
    }
}
