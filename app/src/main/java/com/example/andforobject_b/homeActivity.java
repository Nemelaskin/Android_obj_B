package com.example.andforobject_b;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Models.Users;
import Utils.JsonParserArray;

public class homeActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    String actualUserString;
    Users actualUser;
    Button btn_to_cabinet;
    Button btn_to_RatingTable;
    Button btn_to_News;
    Button btn_to_Map;
    TextView textWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addListenerOnButton();
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        textWelcome = (TextView) findViewById(R.id.textWelcome);

        actualUser = JsonParserArray.fromJsonFromArray(actualUserString, Users.class);
        textWelcome.setText("Welcome " + actualUser.firstName + "!");
    }

    public void addListenerOnButton() {

        btn_to_cabinet = (Button) findViewById(R.id.buttonToKabinet);
        btn_to_News = (Button) findViewById(R.id.btnNews);
        btn_to_Map = (Button) findViewById(R.id.btn_to_Map);
        btn_to_Map.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, MapViewActivity.class);
            intent.putExtra("ActualUser", actualUserString);
            startActivity(intent);
        });
        btn_to_News.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, NewsActivity.class);
            intent.putExtra("ActualUser", actualUserString);
            startActivity(intent);
        });
        btn_to_RatingTable = (Button) findViewById(R.id.btnRatingTable);
        btn_to_RatingTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, RatingTableActivity.class);
                intent.putExtra("ActualUser", actualUserString);
                startActivity(intent);
            }
        });
        btn_to_cabinet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, CabinetActivity.class);
                intent.putExtra("ActualUser", actualUserString);
                startActivity(intent);
            }
        });
    }

}