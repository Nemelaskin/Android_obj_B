package com.example.andforobject_b;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Models.Positions;
import Models.Sensors;
import Utils.JsonParserArray;
import Utils.Request.GetRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MapViewActivity extends AppCompatActivity {

    Button btnReload;
    String SensorseResponse;
    ImageView imageView;
    String input = "http://10.0.2.1:5000/chat";
    HubConnection hubConnection;
    private String actualUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        imageView = findViewById(R.id.imageView9);
        hubConnection = HubConnectionBuilder.create(input)
                .build();
        hubConnection.start();
        drawMap();
        addListenerOnButton();

        PEPE();
    }

    public void PEPE() {
        hubConnection.onClosed(exception -> {
            hubConnection.start();
            Log.i("test", "message");

        });

        hubConnection.on("ReceiveCoordinates", (message, user) -> {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            Canvas canvas = new Canvas(bitmap);
            Paint p = new Paint();
            p.setColor(Color.RED);
            Log.i("test", message);
            String[] sensor = message.split(":");

            canvas.drawCircle(Integer.parseInt(sensor[0]) * 30, Integer.parseInt(sensor[1]) + 340, 14, p);
            imageView.setImageBitmap(bitmap);

        }, String.class, String.class);
    }

    public void addListenerOnButton() {
        btnReload = findViewById(R.id.btn_reload);
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
                    canvas.drawCircle(Integer.parseInt(sensor[0]) + 900, Integer.parseInt(sensor[1]) + 340, 14, p);
                }
            });
            imageView.setImageBitmap(bitmap);
        });
    }

    void drawMap() {
        Picasso.get().load("http://10.0.2.1:5000/Maps/MainCompanyMap.png").resize(1600, 1400).into(imageView);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapViewActivity.this, homeActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
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
                    runOnUiThread(() -> {

                        ResponseBody TemplateResponse = response.body();
                        String Response = "";
                        try {
                            Response = TemplateResponse.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Positions[] mass = JsonParserArray.fromJsonFromArray(Response, Positions[].class);
                    });
                }
            }
        });
    }
}

