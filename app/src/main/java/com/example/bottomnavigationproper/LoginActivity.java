package com.example.bottomnavigationproper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Services.LoginService;
import com.example.bottomnavigationproper.Services.PlayerRepository;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFromInput();
        returnToRegister();

    }

    private void returnToRegister() {
        findViewById(R.id.navigate_to_register).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    public void loginFromInput(){
        LoginService service = new LoginService();
        PlayerRepository playerRepository = new PlayerRepository();


        findViewById(R.id.login).setOnClickListener(v -> {

            String username = getTextFromEditText(R.id.name);
            String password = getTextFromEditText(R.id.password);

            Login loginObj = new Login(username, password);
            service.login(loginObj);

            storeToken();


        });


        //Check token validation

        // if token valid (response from endpoint not null)
        // startActivity(bottom_nav)
    }

    public void storeToken(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.jwt_token),(Context.MODE_PRIVATE));
        sharedPref.edit().putString("TOKEN", TokenSingleton.getInstance().getTokenString()).apply();
    }

    private String getTextFromEditText(int id){
        return ((EditText)findViewById(id)).getText().toString();
    }


}
