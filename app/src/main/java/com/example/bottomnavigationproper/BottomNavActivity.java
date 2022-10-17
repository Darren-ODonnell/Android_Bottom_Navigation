package com.example.bottomnavigationproper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottomnavigationproper.databinding.BottomNavBinding;

public class BottomNavActivity extends AppCompatActivity {
    BottomNavBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        onLogin();

    }

    private void onLogin(){
        binding = BottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.homeFragment:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.gameFragment:
                    replaceFragment(new GameFragment());
                    break;
                case R.id.statsFragment:
                    replaceFragment(new StatsFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView2,fragment);
        fragmentTransaction.commit();
    }
}
