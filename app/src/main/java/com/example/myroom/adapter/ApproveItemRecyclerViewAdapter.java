package com.example.myroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroom.APItools.APICallback;
import com.example.myroom.APItools.HitAPI;
import com.example.myroom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class ApproveItemRecyclerViewAdapter extends RecyclerView.Adapter<ApproveItemRecyclerViewAdapter.RecyclerViewHolder>  {
    private JSONArray transactions;
    private Context context;
    public ApproveItemRecyclerViewAdapter(JSONArray transactions , Context context) {
        this.transactions = transactions;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.approve_item_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        try {
            holder.amount.setText((CharSequence) ((JSONObject)transactions.get(position)).get("amount"));
            holder.description.setText((CharSequence) ((JSONObject)transactions.get(position)).get("description"));
            holder.done_by.setText((CharSequence) ((JSONObject)transactions.get(position)).get("done_by"));
            holder.timestamp.setText((CharSequence) ((JSONObject)transactions.get(position)).get("timestamp"));
            holder.transaction_id = Integer.parseInt(((JSONObject)transactions.get(position)).get("id").toString());
            JSONObject data = new JSONObject();
            data.put("transaction",holder.transaction_id);
            holder.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HitAPI h = new HitAPI();
                    h.sendJsonPostRequest("approveitem.php", context, data, new APICallback() {
                        @Override
                        public void response(JSONObject jsonobj) {
                            Toast.makeText(context, "Item Approved Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }, true);
                }
            });
            holder.deny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HitAPI h = new HitAPI();
                    h.sendJsonPostRequest("denyitem.php", context,data, new APICallback() {
                        @Override
                        public void response(JSONObject jsonobj) {
                            Toast.makeText(context, "Item Denied", Toast.LENGTH_SHORT).show();
                        }
                    }, true);
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return transactions.length();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        //create layout variables here
        public TextView amount,description,done_by,timestamp;
        public Button approve , deny;
        public int transaction_id;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amount);
            description = itemView.findViewById(R.id.description);
            done_by = itemView.findViewById(R.id.done_by);
            timestamp = itemView.findViewById(R.id.timestamp);
            approve = itemView.findViewById(R.id.approve);
            deny = itemView.findViewById(R.id.deny);
        }
    }
}
