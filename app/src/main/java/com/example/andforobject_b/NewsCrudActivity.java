package com.example.andforobject_b;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

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

public class NewsCrudActivity extends AppCompatActivity {
    String actualUserString;

    LinearLayout mainContainer;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_crud);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        mainContainer = findViewById(R.id.main_container);
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
                                linear.setPadding(20, 20, 20, 20);
                                linear.setId(i);

                                EditText textH = new EditText(context);
                                textH.setText(newsMass[i].heading );
                                textH.setTextSize(20);
                                textH.setTypeface(Typeface.DEFAULT_BOLD);
                                linear.addView(textH);

                                EditText textV = new EditText(context);
                                String tempBody = newsMass[i].body;
                                if (tempBody.length() > 100)
                                    tempBody = tempBody.substring(0, 100);
                                textV.setText(tempBody);
                                textV.setTextSize(20);
                                linear.addView(textV);

                                Button btn_delete = new Button(context);
                                Button btn_edit = new Button(context);
                                btn_delete.setText("Delete");
                                int finalI = i;
                                btn_delete.setOnClickListener(v -> {
                                    String urlForDel = "http://10.0.2.1:5000/api/News/"+ newsMass[finalI].newsId;
                                    DeleteRequest.RequestToDelete(urlForDel, resp -> {
                                        Intent intent = new Intent(NewsCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "NewsCrudActivity");

                                        startActivity(intent);
                                    });
                                });

                                btn_edit.setText("Edit");
                                int finalI1 = i;
                                btn_edit.setOnClickListener(v -> {
                                    News news = new News(newsMass[finalI1].newsId,textH.getText().toString(),textV.getText().toString());
                                    String json = ObjectParserToJson.fromObjectFromJson(news);
                                    String urlForPut = "http://10.0.2.1:5000/api/News";
                                    PutRequest.RequestToPost(urlForPut, resp -> {
                                        Log.i("de","eeeeee");
                                        Intent intent = new Intent(NewsCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "NewsCrudActivity");

                                        startActivity(intent);
                                    }, json);
                                });

                                LinearLayout childLL = new LinearLayout(context);
                                childLL.setOrientation(LinearLayout.HORIZONTAL);
                                childLL.addView(btn_delete);
                                childLL.addView(btn_edit);
                                linear.addView(childLL);

                                LinearLayout border = new LinearLayout(context);
                                border.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
                                border.setBackgroundColor(Color.RED);
                                linear.addView(border);

                                mainContainer.addView(linear);
                            }
                            Button btn_create = new Button(context);
                            btn_create.setText("Create");
                            btn_create.setOnClickListener(v ->{
                                LinearLayout linearCreate = new LinearLayout(context);
                                linearCreate.setOrientation(LinearLayout.VERTICAL);
                                linearCreate.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linearCreate.setPadding(20, 20, 20, 20);

                                EditText textH = new EditText(context);
                                textH.setTextSize(20);
                                textH.setTypeface(Typeface.DEFAULT_BOLD);
                                linearCreate.addView(textH);

                                EditText textV = new EditText(context);
                                textV.setTextSize(20);
                                linearCreate.addView(textV);

                                Button btn_add = new Button(context);
                                btn_add.setText("Add");
                                btn_add.setOnClickListener(s->{
                                    String url = "http://10.0.2.1:5000/api/News";
                                    News news = new News(textH.getText().toString(),textV.getText().toString());
                                    String json = ObjectParserToJson.fromObjectFromJson(news);
                                    PostRequest.RequestToPost(url,resp ->{
                                        Intent intent = new Intent(NewsCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "NewsCrudActivity");

                                        startActivity(intent);
                                    },json);
                                });
                                linearCreate.addView(btn_add);
                                mainContainer.addView(linearCreate);

                            });
                            mainContainer.addView(btn_create);
                        }

                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewsCrudActivity.this, MainAdminActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
}