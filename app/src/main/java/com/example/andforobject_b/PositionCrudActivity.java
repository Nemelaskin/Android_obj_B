package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Models.Positions;
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

public class PositionCrudActivity extends AppCompatActivity {
    String actualUserString;

    LinearLayout mainContainer;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_crud);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        mainContainer = findViewById(R.id.main_container);
        context = this.getApplicationContext();
        GetNewsData();
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
                            Positions[] mass = JsonParserArray.fromJsonFromArray(Response, Positions[].class);

                            for (int i = 0; i < mass.length; i++) {

                                LinearLayout linear = new LinearLayout(context); // создание основного блока родителя
                                linear.setOrientation(LinearLayout.VERTICAL);
                                linear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linear.setPadding(20, 20, 20, 20);
                                linear.setId(i);

                                EditText text1 = new EditText(context); // поля для ввода / изминения
                                text1.setText(mass[i].namePosition);
                                text1.setTextSize(20);
                                text1.setTypeface(Typeface.DEFAULT_BOLD);
                                linear.addView(text1);

                                EditText text2 = new EditText(context); // поля для ввода / изминения
                                text2.setText(String.valueOf(mass[i].salary));
                                text2.setTextSize(20);
                                text2.setTypeface(Typeface.DEFAULT_BOLD);
                                linear.addView(text2);

                                Button btn_delete = new Button(context); // функционал кнопки удалить
                                Button btn_edit = new Button(context);
                                btn_delete.setText("Delete");
                                int finalI = i;
                                btn_delete.setOnClickListener(v -> {
                                    String urlForDel = "http://10.0.2.1:5000/api/Positions/"+ mass[finalI].positionId;
                                    DeleteRequest.RequestToDelete(urlForDel, resp -> {
                                        Intent intent = new Intent(PositionCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "PositionCrudActivity");
                                        startActivity(intent);
                                    });
                                });

                                btn_edit.setText("Edit"); // функционал кнопки редактировать
                                int finalI1 = i;
                                btn_edit.setOnClickListener(v -> {
                                    Positions pos = new Positions(mass[finalI1].positionId, text1.getText().toString(), Double.parseDouble(text2.getText().toString())); // выбрать тип
                                    String json = ObjectParserToJson.fromObjectFromJson(pos);
                                    String urlForPut = "http://10.0.2.1:5000/api/Positions";
                                    PutRequest.RequestToPost(urlForPut, resp -> {
                                        Intent intent = new Intent(PositionCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "PositionCrudActivity");
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

                                EditText text1 = new EditText(context); // создание первого блока
                                text1.setTextSize(20);
                                text1.setTypeface(Typeface.DEFAULT_BOLD);
                                linearCreate.addView(text1);

                                EditText text2 = new EditText(context); // создание второго блока
                                text2.setTextSize(20);
                                text2.setTypeface(Typeface.DEFAULT_BOLD);
                                linearCreate.addView(text2);

                                Button btn_add = new Button(context);
                                btn_add.setText("Add");
                                btn_add.setOnClickListener(s->{
                                    String url = "http://10.0.2.1:5000/api/Positions";
                                    Positions pos = new Positions(text1.getText().toString(), Double.parseDouble(text2.getText().toString())); //выбрать тип
                                    String json = ObjectParserToJson.fromObjectFromJson(pos);
                                    PostRequest.RequestToPost(url, resp ->{
                                        Intent intent = new Intent(PositionCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "PositionCrudActivity");
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
        Intent intent = new Intent(PositionCrudActivity.this, MainAdminActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
}