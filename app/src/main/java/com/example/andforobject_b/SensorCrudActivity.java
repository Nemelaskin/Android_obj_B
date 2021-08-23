package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import Models.Positions;
import Models.Sensors;
import Models.Users;
import Utils.JsonParserArray;
import Utils.ObjectParserToJson;
import Utils.Request.DeleteRequest;
import Utils.Request.GetRequest;
import Utils.Request.PostRequest;
import Utils.Request.PutRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SensorCrudActivity extends AppCompatActivity {
    String actualUserString;
    String Response;
    LinearLayout mainContainer;
    Context context;
    Users[] users;
    String[] str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_crud);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        mainContainer = findViewById(R.id.main_container);
        context = this.getApplicationContext();

        String url = "http://10.0.2.1:5000/api/Users";
        GetRequest.RequestToGet(url, response -> {
             Response = response.body().string();
             users = JsonParserArray.fromJsonFromArray(Response, Users[].class);
             str = new String[users.length];
             GetNewsData();
        });

    }

    private void GetNewsData() { // запрос к серверу
        String url = "http://10.0.2.1:5000/api/Sensors";
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
                            Sensors[] mass = JsonParserArray.fromJsonFromArray(Response, Sensors[].class);

                            for (int i = 0; i < mass.length; i++) {

                                LinearLayout linear = new LinearLayout(context); // создание основного блока родителя
                                linear.setOrientation(LinearLayout.VERTICAL);
                                linear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                linear.setPadding(20, 20, 20, 20);
                                linear.setId(i);

                                EditText text1 = new EditText(context); // поля для ввода / изминения
                                text1.setText(mass[i].nameSensor);
                                text1.setTextSize(20);
                                text1.setTypeface(Typeface.DEFAULT_BOLD);
                                linear.addView(text1);

                                int checker = 0;
                                for(int j = 0; j < users.length; j++){
                                    str[j] = String.valueOf(users[j].userId);
                                    if(users[j].userId == mass[i].userId) checker = j;
                                }

                                Spinner spinner2 = new Spinner(context);// создание второго блока
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                        android.R.layout.simple_spinner_item, str);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(adapter);
                                spinner2.setSelection(checker);
                                linear.addView(spinner2);


                                EditText text3 = new EditText(context); // поля для ввода / изминения
                                text3.setText(String.valueOf(mass[i].coordinates));
                                text3.setTextSize(20);
                                text3.setTypeface(Typeface.DEFAULT_BOLD);
                                linear.addView(text3);

                                Button btn_delete = new Button(context); // функционал кнопки удалить
                                Button btn_edit = new Button(context);
                                btn_delete.setText("Delete");
                                int finalI = i;
                                btn_delete.setOnClickListener(v -> {
                                    String urlForDel = "http://10.0.2.1:5000/api/Sensors/"+ mass[finalI].sensorId;
                                    DeleteRequest.RequestToDelete(urlForDel, resp -> {
                                        Intent intent = new Intent(SensorCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "SensorCrudActivity");
                                        startActivity(intent);
                                    });
                                });

                                btn_edit.setText("Edit"); // функционал кнопки редактировать
                                int finalI1 = i;
                                btn_edit.setOnClickListener(v -> {
                                    Sensors sensor = new Sensors(mass[finalI1].sensorId, text1.getText().toString(), Integer.parseInt(spinner2.getSelectedItem().toString()),text3.getText().toString()); // выбрать тип
                                    String json = ObjectParserToJson.fromObjectFromJson(sensor);
                                    String urlForPut = "http://10.0.2.1:5000/api/Sensors";
                                    PutRequest.RequestToPost(urlForPut, resp -> {
                                        Intent intent = new Intent(SensorCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "SensorCrudActivity");
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

                                for(int j = 0; j < users.length; j++){
                                    str[j] = String.valueOf(users[j].userId);
                                }

                                Spinner spinner2 = new Spinner(context);// создание второго блока
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                        android.R.layout.simple_spinner_item, str);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner2.setAdapter(adapter);
                                linearCreate.addView(spinner2);

                                EditText text3 = new EditText(context); // создание второго блока
                                text3.setTextSize(20);
                                text3.setTypeface(Typeface.DEFAULT_BOLD);
                                linearCreate.addView(text3);

                                Button btn_add = new Button(context);
                                btn_add.setText("Add");
                                btn_add.setOnClickListener(s->{
                                    String url = "http://10.0.2.1:5000/api/Sensors";
                                    Sensors sensor = new Sensors( text1.getText().toString(), Integer.parseInt(spinner2.getSelectedItem().toString()) ,text3.getText().toString()); //выбрать тип
                                    String json = ObjectParserToJson.fromObjectFromJson(sensor);
                                    PostRequest.RequestToPost(url, resp ->{
                                        Intent intent = new Intent(SensorCrudActivity.this, templateActivity.class);
                                        intent.putExtra("ActualUser", actualUserString);
                                        intent.putExtra("From", "SensorCrudActivity");
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
        Intent intent = new Intent(SensorCrudActivity.this, MainAdminActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }
}