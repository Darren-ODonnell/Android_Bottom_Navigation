package com.example.bottomnavigationproper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.bottomnavigationproper.databinding.RegisterLoginScreenBinding;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_login_screen);

        findViewById(R.id.navigate_to_login).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

//        findViewById(R.id.navigate_to_register).setOnClickListener(v -> {
//            startActivity(new Intent(this, RegisterActivity.class));
//        });
//        findViewById(R.id.login).setOnClickListener(v -> onLogin());

    }


}