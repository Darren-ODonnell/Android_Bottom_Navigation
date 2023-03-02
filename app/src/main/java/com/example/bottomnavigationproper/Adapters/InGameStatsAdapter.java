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
    private int colour;
    private List<String> averages = new ArrayList<>();
    private boolean countAsPercent;
    private int max;

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
        double width;
        String countText;
        if (countAsPercent) {
            double percent = Double.parseDouble(percents.get(position));
            width = getPercentWidth(percent);
            countText = percent + "%";
        } else {
            String num = averages.get(position);
            width = getAverageWidth(Integer.parseInt(num));
            countText = num;
        }
        holder.statTV.setText(stat);
        holder.countTV.setText(countText);
        holder.countTV.setBackgroundColor(colour);

        ViewGroup.LayoutParams parms = holder.countTV.getLayoutParams();
        parms.width = (int) Math.round(width);

    }

    private double getAverageWidth(int value) {
        int maxPixels = 500;
        return maxPixels * (double)value / max;
    }


    private double getPercentWidth(double percent){
        double maxPixels = 550.0;

        return Math.round(maxPixels * (percent / 100));

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
        this.countAsPercent = true;
        notifyDataSetChanged();
    }

    public void setAverages(List<String> averages, int max){
        this.averages = averages;
        this.countAsPercent = false;
        this.max = max;
        notifyDataSetChanged();
    }

    public void setColour(int colour){
        this.colour = colour;
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
