package com.example.bottomnavigationproper.Services;

import androidx.annotation.NonNull;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginService {

    APIInterface apiInterface;
    private boolean loginSuccess;

    public LoginService(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }


    public boolean login(Login login){
        loginSuccess = false;

        Call<User> call = apiInterface.login(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    User user = response.body();
                    String token = user.getAccessToken();
                    TokenSingleton.getInstance().setTokenString("Bearer " + token);
                    loginSuccess = true;
                }else{
//                    Toast.makeText(getApplicationContext(), "Login not correct :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                call.cancel();
//                Toast.makeText(getApplicationContext(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });
        return loginSuccess;

    }

}
