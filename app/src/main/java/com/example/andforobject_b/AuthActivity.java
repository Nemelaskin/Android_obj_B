package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;


import Utils.JsonParserArray;
import Models.Users;
import Utils.ObjectParserToJson;
import Utils.Request.GetRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthActivity extends AppCompatActivity {
    Button btn_auth;
    EditText inputTextLogin;
    EditText inputTextPassword;
    TextView MessageStateLog;

    private String ResponseString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        inputTextLogin = (EditText)findViewById(R.id.inputEmail);
        inputTextPassword = (EditText)findViewById(R.id.inputPassword);
        MessageStateLog = (TextView)findViewById(R.id.stateLogin);

        String urlToRoles = "http://10.0.2.1:5000/api/Users";
        try{
            GetRequest.RequestToGet(urlToRoles, resp -> {
                String myResponse = resp.body().string();
                ResponseString = myResponse;
                addListenerOnButton();
            });
        } catch (Exception e){
            Log.e("Connect exception!","Don`t valid request!");
        }
    }

    public void addListenerOnButton(){
        btn_auth = (Button)findViewById(R.id.btn_auth);
        btn_auth.setOnClickListener(v -> {
            MessageStateLog.setVisibility(View.INVISIBLE);
            Users[] users;
            try {
                users = JsonParserArray.fromJsonFromArray(ResponseString, Users[].class);
                for (Users user: users) {
                    if(user.email.equals(inputTextLogin.getText().toString().trim()) &&
                            user.password.equals(inputTextPassword.getText().toString().trim())){
                        String actualUser = ObjectParserToJson.fromObjectFromJson(user);
                        MessageStateLog.setVisibility(View.VISIBLE);
                        MessageStateLog.setTextColor(Color.GREEN);
                        MessageStateLog.setText("Correct input!");
                        Intent intent = new Intent(AuthActivity.this, homeActivity.class);
                        intent.putExtra("ActualUser", actualUser);
                        startActivity(intent);
                        return;
                    }
                }
                MessageStateLog.setVisibility(View.VISIBLE);
                MessageStateLog.setTextColor(Color.RED);
                MessageStateLog.setText("Incorrect input!");
                Log.e("error","incorrect input data!");
            }
            catch (Exception e){
                Log.e("error", "failed auth!");
            }
        });
    }
}