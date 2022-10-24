package com.example.bottomnavigationproper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.bottomnavigationproper.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login_screen);

        findViewById(R.id.navigate_to_login).setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.bottomnavigationproper.LoginActivity.class));
        });


//        findViewById(R.id.navigate_to_register).setOnClickListener(v -> {
//            startActivity(new Intent(this, RegisterActivity.class));
//        });
//        findViewById(R.id.login).setOnClickListener(v -> onLogin());

    }


}