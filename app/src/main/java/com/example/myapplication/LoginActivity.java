package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText UserName,PassWord;
    Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserName=findViewById(R.id.userNameEditText);
        PassWord=findViewById(R.id.passwordEditText);
        Login=findViewById(R.id.loginButton);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(UserName.getText().toString()) || TextUtils.isEmpty(PassWord.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Username/Password Required", Toast.LENGTH_LONG).show();
                } else {
                    login();
                }
            }
        });
    }
    public void login(){
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setUsername(UserName.getText().toString());
        loginRequest.setPassword(PassWord.getText().toString());

        Call<LoginResponse> loginResponseCall=ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }
}