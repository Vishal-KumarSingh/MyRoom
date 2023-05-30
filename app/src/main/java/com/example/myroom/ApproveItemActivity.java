package com.example.myroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import com.example.myroom.APItools.APICallback;
import com.example.myroom.APItools.HitAPI;
import com.example.myroom.adapter.ApproveItemRecyclerViewAdapter;
import com.example.myroom.adapter.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApproveItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_item);
        recyclerView = findViewById(R.id.approve_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HitAPI h = new HitAPI();
        Context context=this;
        h.sendJsonPostRequest("getPendingitem.php", this, new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                try {
                    JSONArray transactions = jsonobj.getJSONArray("transactions");
                    recyclerView.setAdapter(new ApproveItemRecyclerViewAdapter(transactions , context));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, true);
    }
}