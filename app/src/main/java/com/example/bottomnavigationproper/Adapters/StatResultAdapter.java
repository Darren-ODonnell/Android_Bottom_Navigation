package com.example.bottomnavigationproper.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.R;

import java.util.ArrayList;
import java.util.List;


public class StatResultAdapter extends RecyclerView.Adapter<StatResultAdapter.StatResultsHolder> {
    private List<Stat> results = new ArrayList<>();

    @NonNull
    @Override
    public StatResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_item_row, parent, false);

        return new StatResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatResultsHolder holder, int position) {
        Stat stat = results.get(position);

        holder.statNameTV.setText(stat.getStatName());
        holder.playerNameTV.setText(stat.getFirstName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Stat> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class StatResultsHolder extends RecyclerView.ViewHolder {
        private TextView statNameTV, playerNameTV;


        public StatResultsHolder(@NonNull View itemView) {
            super(itemView);

            statNameTV = itemView.findViewById(R.id.statNameTV);
            playerNameTV = itemView.findViewById(R.id.playerNameTV);
           }
    }
}
