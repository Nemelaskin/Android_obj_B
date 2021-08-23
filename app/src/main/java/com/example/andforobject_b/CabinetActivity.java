package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Models.Positions;
import Models.RatingTModel;

import Models.Roles;
import Models.Users;
import Models.Visits;
import Utils.JsonParserArray;
import Utils.ObjectParserToJson;
import Utils.Request.DeleteRequest;
import Utils.Request.GetRequest;
import Utils.Request.PostRequest;
import Utils.Request.PutRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CabinetActivity extends AppCompatActivity {
    TextView FirstName;
    TextView SecondName;
    TextView Email;
    TextView PhoneNumber;
    TextView position;
    TextView ratingState;
    Button btnToBack;
    Positions[] positionsArray;
    String actualUserString;
    String RatingResponse;
    Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        user = JsonParserArray.fromJsonFromArray(actualUserString, Users.class);

        String urlToRating = "http://10.0.2.1:5000/api/RatingTable/index";
        GetRequest.RequestToGet(urlToRating, resp -> {
            RatingResponse = resp.body().string();
            GetNewsData();
        });

    }

    public void SetUserInfo(){
        FirstName = (TextView)findViewById(R.id.FirstName);
        SecondName = (TextView)findViewById(R.id.SecondName);
        Email = (TextView)findViewById(R.id.EmailText);
        PhoneNumber = (TextView)findViewById(R.id.PhoneText);
        position = (TextView)findViewById(R.id.positionText);
        ratingState = (TextView)findViewById(R.id.ratingText);
        btnToBack = (Button)findViewById(R.id.btnBack);
        FirstName.setText(user.firstName);
        SecondName.setText(user.secondName);
        Email.setText(user.email);
        PhoneNumber.setText(user.phone);


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

    private void GetNewsData() { // запрос к серверу
        String url = "http://10.0.2.1:5000/api/Positions";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() { // начало генерации круда
                            ResponseBody TemplateResponse = response.body();
                            String Response = "";
                            try {
                                Response = TemplateResponse.string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            positionsArray = JsonParserArray.fromJsonFromArray(Response, Positions[].class);

                            SetUserInfo();
                            addListenerOnButton();
                        }
                    });
                }
            }
        });
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
