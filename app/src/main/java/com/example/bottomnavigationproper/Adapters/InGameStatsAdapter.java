package com.example.bottomnavigationproper.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.R;

import java.util.ArrayList;
import java.util.List;


public class InGameStatsAdapter extends RecyclerView.Adapter<InGameStatsAdapter.InGameStatsHolder> {
    private List<String> results = new ArrayList<>();
    private List<String> percents = new ArrayList<>();

    @NonNull
    @Override
    public InGameStatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.in_game_stats_row, parent, false);

        return new InGameStatsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InGameStatsHolder holder, int position) {
        String stat = results.get(position);
        double percent = Double.parseDouble(percents.get(position));
        int width = getPercentWidth(percent);
        String percentStr = percent + "%";
        holder.statTV.setText(stat);
        holder.countTV.setText(percentStr);


        ViewGroup.LayoutParams parms = holder.countTV.getLayoutParams();
        parms.width = width;

    }

    private int getPercentWidth(double percent){
        double maxPixels = 550.0;

        return (int) Math.round(maxPixels * (percent / 100));

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<String> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public void setPercents(List<String> percents) {
        this.percents = percents;
        notifyDataSetChanged();
    }

    class InGameStatsHolder extends RecyclerView.ViewHolder {
        private TextView statTV;
        private TextView countTV;


        public InGameStatsHolder(@NonNull View itemView) {
            super(itemView);

            statTV = itemView.findViewById(R.id.tvStatName);
            countTV = itemView.findViewById(R.id.tvStatCount);
        }
    }
}
