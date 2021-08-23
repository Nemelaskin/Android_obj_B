package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class templateActivity extends AppCompatActivity {
    String actualUserString;
    String fromActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        fromActivity = this.getIntent().getStringExtra("From");

        if(fromActivity.equals("RoleCrudActivity")) {
            Intent intent = new Intent(templateActivity.this, RoleCrudActivity.class);
            intent.putExtra("ActualUser", actualUserString);
            startActivity(intent);
        }
        if(fromActivity.equals("NewsCrudActivity")) {
            Intent intent = new Intent(templateActivity.this, NewsCrudActivity.class);
            intent.putExtra("ActualUser", actualUserString);
            startActivity(intent);
        }
        if(fromActivity.equals("PositionCrudActivity")) {
            Intent intent = new Intent(templateActivity.this, PositionCrudActivity.class);
            intent.putExtra("ActualUser", actualUserString);
            startActivity(intent);
        }
        if(fromActivity.equals("SensorCrudActivity")) {
            Intent intent = new Intent(templateActivity.this, SensorCrudActivity.class);
            intent.putExtra("ActualUser", actualUserString);
            startActivity(intent);
        }
        if(fromActivity.equals("RoomCrudActivity")) {
            Intent intent = new Intent(templateActivity.this, RoomCrudActivity.class);
            intent.putExtra("ActualUser", actualUserString);
            startActivity(intent);
        }
    }
}