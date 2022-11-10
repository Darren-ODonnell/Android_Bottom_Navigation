package com.example.bottomnavigationproper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Services.LoginRepository;
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
        LoginRepository service = new LoginRepository();
        PlayerRepository playerRepository = new PlayerRepository();


        findViewById(R.id.login).setOnClickListener(v -> {
            String username = getTextFromEditText(R.id.name);
            String password = getTextFromEditText(R.id.password);

            Login loginObj = new Login(username, password);
            service.login(loginObj);


            startActivity(new Intent(getApplicationContext(), BottomNavActivity.class));

        });


    }

    private String getTextFromEditText(int id){
        return ((EditText)findViewById(id)).getText().toString();
    }


}
