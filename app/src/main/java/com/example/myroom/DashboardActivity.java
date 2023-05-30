package com.example.myroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myroom.APItools.APICallback;
import com.example.myroom.APItools.HitAPI;
import com.example.myroom.PermanantStorage.Session;
import com.example.myroom.adapter.MemberListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MemberListAdapter memberListAdapter;
    private TextView total , user , share , profit;
    private FloatingActionButton plusbtn , summarybtn , approvebtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        plusbtn = findViewById(R.id.floatingActionButton);
        total = findViewById(R.id.total);
        user = findViewById(R.id.user);
        share = findViewById(R.id.share);
        profit = findViewById(R.id.profit);
        summarybtn = findViewById(R.id.floatingActionButton2);
        approvebtn = findViewById(R.id.floatingActionButton3);
        recyclerView = findViewById(R.id.member_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HitAPI h2 = new HitAPI();
        final Context context = this;
        h2.sendJsonPostRequest("memberlist.php", this, new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {

                try {
                    recyclerView.setAdapter(new MemberListAdapter((JSONArray) jsonobj.get("members")));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, true);



        HitAPI h = new HitAPI();

        plusbtn.setOnClickListener(view -> {
            Intent i = new Intent(context , AddItemActivity.class);
            startActivity(i);
        });
        approvebtn.setOnClickListener(view -> {
            Intent i = new Intent(context , ApproveItemActivity.class);
            startActivity(i);
        });
        summarybtn.setOnClickListener(view -> {
            Intent i = new Intent(context , SummaryActivity.class);
            startActivity(i);
        });
        h.sendJsonPostRequest("dashboard.php", this, new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                try {
                    total.setText(jsonobj.getString("total"));
                    user.setText(jsonobj.getString("user"));
                    share.setText(jsonobj.getString("share"));
                    profit.setText(jsonobj.getString("profit"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, true);



    }
}