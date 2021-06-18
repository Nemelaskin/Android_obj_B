package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Models.Positions;
import Models.RatingTModel;

import Models.Users;
import Models.Visits;
import Utils.JsonParserArray;
import Utils.Request.GetRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CabinetActivity extends AppCompatActivity {
    TextView firstName;
    TextView secondName;
    TextView email;
    TextView phoneNumber;
    TextView position;
    TextView ratingState;
    Button btnToBack;

    String actualUserString;
    String RatingResponse;
    String PositionsResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet);

        actualUserString = this.getIntent().getStringExtra("ActualUser");

        firstName = (TextView)findViewById(R.id.firstName);
        secondName = (TextView)findViewById(R.id.secondName);
        email = (TextView)findViewById(R.id.emailText);
        phoneNumber = (TextView)findViewById(R.id.phoneText);
        position = (TextView)findViewById(R.id.positionText);
        ratingState = (TextView)findViewById(R.id.ratingText);
        btnToBack = (Button)findViewById(R.id.btnBack);

        String urlToRating = "http://10.0.2.1:5000/api/RatingTable/index";
        GetRequest.RequestToGet(urlToRating, resp -> {
            RatingResponse = resp.body().string();
            String urlToPosition = "http://10.0.2.1:5000/api/Positions";
            GetRequest.RequestToGet(urlToPosition, response -> {
                PositionsResponse = response.body().string();
                SetUserInfo();
                addListenerOnButton();
            });
        });

    }

    public void SetUserInfo(){
        Users user = JsonParserArray.fromJsonFromArray(actualUserString, Users.class);
        firstName.setText(user.firstName);
        secondName.setText(user.secondName);
        email.setText(user.email);
        phoneNumber.setText(user.phone);

        Positions[] positionsArray = JsonParserArray.fromJsonFromArray(PositionsResponse, Positions[].class);
        String actualPosition ="";
        for(Positions pos : positionsArray) {
            if (pos.positionId == user.positionId)
                actualPosition = pos.namePosition;
        }
        position.setText(actualPosition);
        RatingTModel[] ratingTablesArray =  JsonParserArray.fromJsonFromArray(RatingResponse, RatingTModel[].class);
        int actualRating =0;
        for(RatingTModel table : ratingTablesArray) {
            if (table.email.equals(user.email) && table.name.equals(user.firstName))
                actualRating = table.position;
        }
        ratingState.setText(String.valueOf(actualRating));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CabinetActivity.this, homeActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }

    public void addListenerOnButton(){

        btnToBack.setOnClickListener(v -> {
            Intent intent = new Intent(CabinetActivity.this, homeActivity.class);
            intent.putExtra("ActualUser", actualUserString);
            startActivity(intent);
        });
    }

}
