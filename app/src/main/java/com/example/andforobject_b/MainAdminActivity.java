package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainAdminActivity extends AppCompatActivity {
    String actualUserString;
    Button btn_to_user;
    Button btn_to_role;
    Button btn_to_position;
    Button btn_to_news;
    Button btn_to_room;
    Button btn_to_sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        actualUserString = this.getIntent().getStringExtra("ActualUser");

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainAdminActivity.this, homeActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
        btn_to_user = (Button) findViewById(R.id.btn_to_user_crud);
        btn_to_role = (Button) findViewById(R.id.btn_to_role_crud);
        btn_to_position = (Button) findViewById(R.id.btn_to_position_crud);
        btn_to_news = (Button) findViewById(R.id.btn_to_news_crud);
        btn_to_room = (Button) findViewById(R.id.btn_to_room_crud);
        btn_to_sensor = (Button) findViewById(R.id.btn_to_sensor_crud);
    }

    public void onNewsClick(View v){
        Intent intent = new Intent(MainAdminActivity.this, NewsCrudActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
    public void onRolesClick(View v){
        Intent intent = new Intent(MainAdminActivity.this, RoleCrudActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
    public void onPositionsClick(View v){
        Intent intent = new Intent(MainAdminActivity.this, PositionCrudActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
    public void onRoomsClick(View v){
        Intent intent = new Intent(MainAdminActivity.this, RoomCrudActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
    public void onSensorsClick(View v){
        Intent intent = new Intent(MainAdminActivity.this, SensorCrudActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
}