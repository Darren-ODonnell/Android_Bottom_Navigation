package com.example.bottomnavigationproper;

import static com.example.bottomnavigationproper.MainActivity.API_KEY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Register;
import com.example.bottomnavigationproper.Services.LoginRepository;
import com.example.bottomnavigationproper.Services.PlayerRepository;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());


        setContentView(R.layout.activity_register);

        loginFromInput();
//        returnToRegister();
//
    }

//    private void returnToRegister() {
//        findViewById(R.id.navigate_to_register).setOnClickListener(v -> {
//            startActivity(new Intent(this, MainActivity.class));
//        });
//    }

    public void loginFromInput(){
        LoginRepository service = new LoginRepository();

        findViewById(R.id.btn_register).setOnClickListener(v -> {
            String email = getTextFromEditText(R.id.email_reg);
            String username = getTextFromEditText(R.id.name_reg);
            String password = getTextFromEditText(R.id.password_reg);
            String passwordConfirm = getTextFromEditText(R.id.password_conf_reg);



            Register regObj = new Register();
            regObj.setEmail(email);
            regObj.setUsername(username);
            regObj.setPassword(password);
            regObj.setPasswordConfirm(passwordConfirm);
            service.register(regObj);
            storeToken(getApplicationContext());

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        });
    }

    private String getTextFromEditText(int id){
        return ((EditText)findViewById(id)).getText().toString();
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
        storeToken(getApplicationContext());
        super.onStop();

    }
}
