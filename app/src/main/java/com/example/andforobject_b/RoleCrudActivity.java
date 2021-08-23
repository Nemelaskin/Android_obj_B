package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

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

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Models.News;
import Models.Roles;
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

public class RoleCrudActivity extends AppCompatActivity {
    String actualUserString;

    LinearLayout mainContainer;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_crud);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        mainContainer = findViewById(R.id.main_container);
        context = this.getApplicationContext();
        GetNewsData();
    }

    private void GetNewsData() { // запрос к серверу
        String url = "http://10.0.2.1:5000/api/Roles";
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
                            Roles[] mass = JsonParserArray.fromJsonFromArray(Response, Roles[].class);

                            for (int i = 0; i < mass.length; i++) {

                                LinearLayout linear = new LinearLayout(context); // создание основного блока родителя
                                linear.setOrientation(LinearLayout.VERTICAL);
                                linear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linear.setPadding(20, 20, 20, 20);
                                linear.setId(i);

                                EditText textH = new EditText(context); // поля для ввода / изминения
                                textH.setText(mass[i].nameRole );
                                textH.setTextSize(20);
                                textH.setTypeface(Typeface.DEFAULT_BOLD);
                                linear.addView(textH);

                                Button btn_delete = new Button(context); // функционал кнопки удалить
                                Button btn_edit = new Button(context);
                                btn_delete.setText("Delete");
                                int finalI = i;
                                btn_delete.setOnClickListener(v -> {
                                    String urlForDel = "http://10.0.2.1:5000/api/Roles/"+ mass[finalI].roleId;
                                    DeleteRequest.RequestToDelete(urlForDel, resp -> {
                                        Intent intent = new Intent(RoleCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "RoleCrudActivity");
                                        startActivity(intent);
                                    });
                                });

                                btn_edit.setText("Edit"); // функционал кнопки редактировать
                                int finalI1 = i;
                                btn_edit.setOnClickListener(v -> {
                                    Roles news = new Roles(mass[finalI1].roleId, textH.getText().toString()); // выбрать тип
                                    String json = ObjectParserToJson.fromObjectFromJson(news);
                                    String urlForPut = "http://10.0.2.1:5000/api/Roles";
                                    PutRequest.RequestToPost(urlForPut, resp -> {
                                        Intent intent = new Intent(RoleCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "RoleCrudActivity");
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

                                EditText textH = new EditText(context); // создание первого блока
                                textH.setTextSize(20);
                                textH.setTypeface(Typeface.DEFAULT_BOLD);
                                linearCreate.addView(textH);

                                Button btn_add = new Button(context);
                                btn_add.setText("Add");
                                btn_add.setOnClickListener(s->{
                                    String url = "http://10.0.2.1:5000/api/Roles";
                                    Roles news = new Roles(textH.getText().toString()); //выбрать тип
                                    String json = ObjectParserToJson.fromObjectFromJson(news);
                                    PostRequest.RequestToPost(url, resp ->{
                                        Intent intent = new Intent(RoleCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "RoleCrudActivity");
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
        Intent intent = new Intent(RoleCrudActivity.this, MainAdminActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
}