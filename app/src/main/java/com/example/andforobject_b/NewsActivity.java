package com.example.andforobject_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewsActivity extends AppCompatActivity {

    Button btn_backNews;
    TextView dailyNews;
    TextView monthNews;
    String actualUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        dailyNews = (TextView)findViewById(R.id.dailyNewsText);
        monthNews = (TextView)findViewById(R.id.monthNewsText);
        btn_backNews = (Button) findViewById(R.id.btn_backNews);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        addListenerOnButton();
        setDailyNews();
        setMonthNews();
    }
    public void addListenerOnButton(){
        btn_backNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, homeActivity.class);
                intent.putExtra("ActualUser", actualUserString);
                startActivity(intent);
            }
        });
    }
    public void setDailyNews(){
        //some code for request
        dailyNews.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim.");
    }
    public void setMonthNews(){
        //some code fo request
        monthNews.setText("Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, ");
    }
}