package com.example.myroom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myroom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>  {
    private JSONArray transactions;
    public RecyclerViewAdapter(JSONArray transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.transaction, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        try {
            holder.amount.setText((CharSequence) ((JSONObject)transactions.get(position)).get("amount"));
            holder.description.setText((CharSequence) ((JSONObject)transactions.get(position)).get("description"));
            holder.done_by.setText((CharSequence) ((JSONObject)transactions.get(position)).get("done_by"));
            holder.timestamp.setText((CharSequence) ((JSONObject)transactions.get(position)).get("timestamp"));
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
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amount);
            description = itemView.findViewById(R.id.description);
            done_by = itemView.findViewById(R.id.done_by);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }
}
