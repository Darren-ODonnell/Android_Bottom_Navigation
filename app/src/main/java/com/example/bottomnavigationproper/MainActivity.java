package com.example.bottomnavigationproper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Services.LoginRepository;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "token_key";
    public static final String API_KEY = "token";
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        validateJWT();

        setContentView(R.layout.register_login_screen);



        findViewById(R.id.navigate_to_login).setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.bottomnavigationproper.LoginActivity.class));
        });

//        findViewById(R.id.navigate_to_register).setOnClickListener(v -> {
//            startActivity(new Intent(this, RegisterActivity.class));
//        });
//        findViewById(R.id.login).setOnClickListener(v -> onLogin());

    }

    public void validateJWT(){
        Context context = getApplicationContext();

        settings = context.getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("jwt_token", null);
        LoginRepository service = new LoginRepository();

        if(token != null){
            service.validateJWT(token);
        }

        service.getTokenValidity().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    startActivity(new Intent(getApplicationContext(), BottomNavActivity.class));
                    storeToken(getApplicationContext());
                }else{
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });

    }
    public void storeToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("jwt_token", TokenSingleton.getInstance().getTokenStr());
        // Commit the edits!
        editor.commit();
    }

    public void displayPref(boolean silent){
        Toast.makeText(this, "Silent Mode " + silent ,
                Toast.LENGTH_LONG).show();
    }
}
