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

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.RecyclerViewHolder>  {
    private JSONArray memberlist;
    public MemberListAdapter(JSONArray memberlist) {
        this.memberlist = memberlist;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.member_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        try {
            holder.amount.setText((CharSequence) ((JSONObject)memberlist.get(position)).get("amount"));
            holder.name.setText((CharSequence) ((JSONObject)memberlist.get(position)).get("email"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return memberlist.length();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        //create layout variables here
        public TextView amount,name;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amount);
            name = itemView.findViewById(R.id.email);

        }
    }
}
