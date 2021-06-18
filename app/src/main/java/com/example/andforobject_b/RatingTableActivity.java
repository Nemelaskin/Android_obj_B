package com.example.andforobject_b;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Models.RatingTModel;
import Models.RatingTable;
import Utils.JsonParserArray;
import Utils.RatingTableAdapter;
import Utils.Request.GetRequest;

public class RatingTableActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    RatingTableAdapter adapter;
    RatingTModel[] ratingTableModel;
    String ratingTableResponse;
    private String actualUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_table);
        actualUserString = this.getIntent().getStringExtra("ActualUser");
        recycler_view = findViewById(R.id.recycler_view);
        String urlToRating = "http://10.0.2.1:5000/api/RatingTable/index";
        GetRequest.RequestToGet(urlToRating, resp -> {
            ratingTableResponse = resp.body().string();
            SetRecyclerView();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RatingTableActivity.this, homeActivity.class);
        intent.putExtra("ActualUser", actualUserString);
        startActivity(intent);
    }

    private void SetRecyclerView() {
        recycler_view.setHasFixedSize(true);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RatingTableAdapter(this, getList());
        recycler_view.setAdapter(adapter);
    }

    private List<RatingTable> getList() {
        List<RatingTable> ratingTableList = new ArrayList<>();
        RatingTModel[] ratingTablesArray = JsonParserArray.fromJsonFromArray(ratingTableResponse, RatingTModel[].class);
        for (RatingTModel table : ratingTablesArray) {
            ratingTableList.add(new RatingTable(table.position, table.name.trim(), table.surName.trim()));
        }
        return ratingTableList;
    }
}