package com.example.bottomnavigationproper.Services;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.bottomnavigationproper.APIs.APIClient;
import com.example.bottomnavigationproper.APIs.APIInterface;
import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Login;
import com.example.bottomnavigationproper.Models.Register;
import com.example.bottomnavigationproper.User;
import com.example.bottomnavigationproper.UserSingleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private MutableLiveData<Boolean> validToken;
    private MutableLiveData<String> tokenLiveData;

    private APIInterface apiInterface;

    public LoginRepository(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
        validToken = new MutableLiveData<>();
        tokenLiveData = new MutableLiveData<>();

    }


    public void login(Login login){
        Call<User> call = apiInterface.login(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    User user = response.body();
                    String token = user.getAccessToken();
                    tokenLiveData.postValue(token);
                    validToken.postValue(true);
                    TokenSingleton.getInstance().setTokenString(token);
                    UserSingleton.getInstance().setUser(user);
                }else{
//                    Toast.makeText(getApplicationContext(), "Login not correct :(", Toast.LENGTH_SHORT).show();
                    validToken.postValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                validToken.postValue(false);
                call.cancel();
//                Toast.makeText(getApplicationContext(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void register(Register register){
        Call<User> call = apiInterface.register(register);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    User user = response.body();
                    String token = user.getAccessToken();
                    tokenLiveData.postValue(token);
                    validToken.postValue(true);
                    TokenSingleton.getInstance().setTokenString(token);
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

    }

    public void validateJWT(String token){
        validToken.postValue(false);
        Call<Boolean> call = apiInterface.checkToken(token);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(Boolean.TRUE.equals(response.body())){
                    validToken.postValue(true);
                    TokenSingleton.getInstance().setTokenString(token);
                    UserSingleton.getInstance().getUser();


                }else
                    validToken.postValue(false);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                validToken.postValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getTokenValidity(){
        return validToken;
    }



}
