package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import Models.Positions;
import Models.Sensors;
import Models.Users;
import Utils.CustomView;
import Utils.JsonParserArray;
import Utils.Request.GetRequest;
import okhttp3.ResponseBody;

public class MapViewActivity extends AppCompatActivity {

    Button btnReload;
    String SensorseResponse;
    private String actualUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        drawMap();
        addListenerOnButton();
    }

    public void addListenerOnButton(){
        btnReload = (Button)findViewById(R.id.btn_reload);
        btnReload.setOnClickListener(v -> {
            drawMap();
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapViewActivity.this, homeActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
    void drawMap(){

        ImageView imageView = (ImageView) findViewById(R.id.imageView9);
        Display display = getWindowManager().getDefaultDisplay();
        int displayWidth = display.getWidth();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inMutable = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.maket2,
                options);
        int width = options.outWidth;
        if (width > displayWidth)
            options.inSampleSize = 2;
        options.inJustDecodeBounds = false;

        String urlToMap = "http://10.0.2.1:5000/api/Map/ViewMap?nameCompany=TestCompany1";
        GetRequest.RequestToGet(urlToMap, response -> {
            ResponseBody MapResponse = response.body();
            byte[] bytesImage = MapResponse.bytes();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            imageView.setImageBitmap(bitmap);
            Canvas canvas = new Canvas(bitmap);
            Paint p = new Paint();
            p.setColor(Color.RED);
            String urlToSensors = "http://10.0.2.1:5000/api/Sensors";
            GetRequest.RequestToGet(urlToSensors, responseSecond -> {
                SensorseResponse = responseSecond.body().string();
                Sensors[] sensors = JsonParserArray.fromJsonFromArray(SensorseResponse, Sensors[].class);
                for(Sensors senr : sensors) {
                    String[] sensor = senr.coordinates.split("\\.");
                    canvas.drawCircle(Integer.parseInt(sensor[0]), Integer.parseInt(sensor[1]), 13, p);
                }
            });
            imageView.setImageBitmap(bitmap);
        });
    }

}