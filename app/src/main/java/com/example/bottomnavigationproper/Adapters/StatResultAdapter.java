package com.example.bottomnavigationproper.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.ChartType;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.R;

import java.util.ArrayList;
import java.util.List;


public class StatResultAdapter extends RecyclerView.Adapter<StatResultAdapter.StatResultsHolder> {
    private List<StatsView> results = new ArrayList<>();
    private Boolean singleStat;
    private Boolean singleFixture;
    private ChartType chartType;

    @NonNull
    @Override
    public StatResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stat_item_row, parent, false);

        return new StatResultsHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull StatResultsHolder holder, int position) {
        StatsView statsView = results.get(position);

        holder.statNameTV.setText(statsView.getStatName());
        holder.playerNameTV.setText(statsView.getFirstName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<StatsView> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void setSingleStat(Boolean selection){
        this.singleStat = selection;
    }

    public void setSingleFixture(Boolean selection){
        this.singleFixture = selection;
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
