package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import Models.News;
import Utils.JsonParserArray;
import Utils.ObjectParserToJson;
import Utils.Request.DeleteRequest;
import Utils.Request.PostRequest;
import Utils.Request.PutRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NewsActivity extends AppCompatActivity {

    Button btn_backNews;
    TextView dailyNews;
    TextView monthNews;
    String actualUserString;
    Context context;
    LinearLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        mainContainer = findViewById(R.id.NewsContainet);

        actualUserString = this.getIntent().getStringExtra("ActualUser");
        context = this.getApplicationContext();
        GetNewsData();
    }

    private void GetNewsData() {
        String urlToNews = "http://10.0.2.1:5000/api/News/GetNews";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlToNews)
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
                        public void run() {
                            ResponseBody newsResponse = response.body();
                            String newsResp = "";
                            try {
                                newsResp = newsResponse.string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            News[] newsMass = JsonParserArray.fromJsonFromArray(newsResp, News[].class);

                            for (int i = 0; i < newsMass.length; i++) {

                                LinearLayout linear = new LinearLayout(context);
                                linear.setOrientation(LinearLayout.VERTICAL);
                                linear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linear.setPadding(30, 40, 30, 40);

                                TextView textH = new TextView(context);
                                textH.setText(newsMass[i].heading);
                                textH.setTextSize(40);
                                textH.setTypeface(Typeface.DEFAULT_BOLD);
                                linear.addView(textH);

                                TextView textV = new TextView(context);
                                textV.setText(newsMass[i].body);
                                textV.setTextSize(30);
                                linear.addView(textV);

                                LinearLayout childLL = new LinearLayout(context);
                                childLL.setOrientation(LinearLayout.HORIZONTAL);
                                linear.addView(childLL);

                                LinearLayout border = new LinearLayout(context);
                                border.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
                                border.setBackgroundColor(Color.RED);
                                linear.addView(border);

                                mainContainer.addView(linear);
                            }

                        }

                    });
                }
            }
        });
    }
}