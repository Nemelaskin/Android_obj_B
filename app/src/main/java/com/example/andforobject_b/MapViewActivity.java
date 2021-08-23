package com.example.andforobject_b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import Models.Sensors;
import Utils.JsonParserArray;
import Utils.Request.GetRequest;

public class MapViewActivity extends AppCompatActivity {

    Button btnReload;
    String SensorseResponse;
    ImageView imageView;
    private String actualUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        imageView = (ImageView) findViewById(R.id.imageView9);

        drawMap();
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        btnReload = (Button) findViewById(R.id.btn_reload);
        btnReload.setOnClickListener(v -> {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            Canvas canvas = new Canvas(bitmap);
            Paint p = new Paint();
            p.setColor(Color.RED);
            String urlToSensors = "http://10.0.2.1:5000/api/Sensors";
            GetRequest.RequestToGet(urlToSensors, responseSecond -> {
                SensorseResponse = responseSecond.body().string();
                Sensors[] sensors = JsonParserArray.fromJsonFromArray(SensorseResponse, Sensors[].class);
                for (Sensors senr : sensors) {
                    String[] sensor = senr.coordinates.split("\\.");
                    canvas.drawCircle(Integer.parseInt(sensor[0])+900, Integer.parseInt(sensor[1])+340, 14, p);
                }
            });
            imageView.setImageBitmap(bitmap);
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapViewActivity.this, homeActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }

    void drawMap() {

        String urlToMap = "http://10.0.2.1:5000/Maps/MainCompanyMap.png";

        Picasso.get().load("http://10.0.2.1:5000/Maps/MainCompanyMap.png").resize(2440, 1400).into(imageView);


    }
}

