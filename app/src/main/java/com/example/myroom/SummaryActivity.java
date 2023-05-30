package com.example.myroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.myroom.APItools.APICallback;
import com.example.myroom.APItools.HitAPI;
import com.example.myroom.adapter.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {
     private RecyclerView recyclerView;
     private RecyclerViewAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        recyclerView = findViewById(R.id.transaction_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HitAPI h = new HitAPI();
        h.sendJsonPostRequest("summary.php", this, new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                try {
                    JSONArray transactions = jsonobj.getJSONArray("transactions");
                    recyclerView.setAdapter(new RecyclerViewAdapter(transactions));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, true);



    }
}