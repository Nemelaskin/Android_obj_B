package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    Button btnLog;
    TextView mTextViewResult;//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        mTextViewResult = (TextView)findViewById(R.id.textView);
    }

    public void addListenerOnButton(){
        btnLog = (Button)findViewById(R.id.btn_to_login);
        btnLog.setOnClickListener(v -> {
            //Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(intent);
        });
    }
}