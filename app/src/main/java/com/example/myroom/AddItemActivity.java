package com.example.myroom;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myroom.APItools.APICallback;
import com.example.myroom.APItools.HitAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {
    private EditText amount , desc;
    private Button addbtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        amount = findViewById(R.id.amount);
        desc = findViewById(R.id.desc);
        addbtn = findViewById(R.id.addbtn);
        final Context context = this;
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addbtn.setEnabled(false);
                String descfield = desc.getText().toString();
                String amountfield = amount.getText().toString();

                if(Objects.equals(descfield , "")){
                    Toast.makeText(context, " Description field is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Objects.equals(amountfield , "")){
                    Toast.makeText(context, "Amount Field is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                HitAPI h = new HitAPI();
                JSONObject jsn = new JSONObject();
                try {
                    jsn.put("desc", descfield);
                    jsn.put("amount" , amountfield);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                h.sendJsonPostRequest("additem.php", context, jsn, new APICallback() {
                    @Override
                    public void response(JSONObject jsonobj) {
                        try {
                            boolean status = (boolean)jsonobj.get("status");
                            if(status){
                                finish();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                } , true);
            }
        });
            }

    }
