package com.example.bottomnavigationproper.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.ChartType;
import com.example.bottomnavigationproper.Models.Stat;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.example.bottomnavigationproper.R;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiViewTypeAdapter extends RecyclerView.Adapter{
    private List<Stat> results = new ArrayList<>();
    private HashMap<String, List<Stat>> playerStats = new HashMap<>();
    private HashMap<Integer, List<Stat>> intPlayerStats = new HashMap<>();
    private Boolean singleStat;
    private Boolean singleFixture;
//    private ChartType chartType;

    /**
    public static class BasicViewHolder extends RecyclerView.ViewHolder {

        TextView statName;
        TextView responseVal;

        public BasicViewHolder(View itemView) {
            super(itemView);

            this.statName = (TextView) itemView.findViewById(R.id.type);
            this.responseVal = (TextView) itemView.findViewById(R.id.card_view);
        }
    }

     public static class LineChartViewHolder extends RecyclerView.ViewHolder {

     TextView statName;
     LineChart lineChart;

     public LineChartViewHolder(View itemView) {
     super(itemView);

     this.statName = (TextView) itemView.findViewById(R.id.type);
     this.lineChart = (LineChart) itemView.findViewById(R.id.background);

     }
     }
     */

    public static class BarChartViewHolder extends RecyclerView.ViewHolder {

        TextView statName;
        BarChart barChart;

        public BarChartViewHolder(View itemView) {
            super(itemView);

            this.statName = (TextView) itemView.findViewById(R.id.bar_player);
            this.barChart = (BarChart) itemView.findViewById(R.id.bar_graph);
        }
    }



    public void setResults(List<Stat>data) {
        this.results = data;

        this.playerStats = mapResultsToPlayer(results);
    }

    private HashMap<String, List<Stat>> mapResultsToPlayer(List<Stat> results) {
        HashMap<String, List<Stat>> playerStats = new HashMap<>();
        int i = 0;
        for(Stat s: results){
            String fullName = s.getFirstName() + " " + s.getLastName();
            if(playerStats.get(fullName) == null){
                List<Stat> list = new ArrayList<>();
                playerStats.put(fullName, list);
                intPlayerStats.put(i++,list);
            }else{
                List<Stat> list = playerStats.get(fullName);
                assert list != null;
                list.add(s);
                playerStats.put(fullName, list);
                 list = intPlayerStats.get(i);
                intPlayerStats.put(i,list);
            }
        }
        return playerStats;
    }

    public void setSingleFixture(Boolean single){
        this.singleFixture = single;
    }

    public void setSingleStat(Boolean single){
        this.singleStat = single;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        /**
        if(singleFixture && singleStat){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
            return new BasicViewHolder(view);
        }

        else if (singleStat){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
            return new LineChartViewHolder(view);
        }else {
         */
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_type, parent, false);
            return new BarChartViewHolder(view);
//        }
      /**
        return null;
       */
    }

//    @Override
//    public int getItemViewType(int position) {
//
//        switch (dataSet.get(position).type) {
//            case 0:
//                return Model.TEXT_TYPE;
//            case 1:
//                return Model.IMAGE_TYPE;
//            case 2:
//                return Model.AUDIO_TYPE;
//            default:
//                return -1;
//        }
//    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        List<Stat> statsForPlayer = intPlayerStats.get(listPosition);

        if (statsForPlayer != null) {

//            if(singleFixture && singleStat){
//                ((BarChartViewHolder) holder).statName.setText(stat.getStatName());
            String fullName = statsForPlayer.get(0).getFirstName() + " " + statsForPlayer.get(0).getLastName();
                ((BarChartViewHolder) holder).statName.setText(fullName);
                createBarChart(holder, statsForPlayer);


//            }
            /**else if (singleStat){
                ((LineChartViewHolder) holder).statName.setText(stat.getStatName());
//                ((LineChartViewHolder) holder).lineChart.setLine(stat.data);
            }else {
                ((BasicViewHolder) holder).statName.setText(stat.getStatName());
                ((BasicViewHolder) holder).responseVal.setText(stat.);

            }
*/
            }
        }

    private void createBarChart(RecyclerView.ViewHolder holder, List<Stat> statsForPlayer) {
        BarChart barChart = ((BarChartViewHolder) holder).barChart;
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<String> xValues = new ArrayList<>();

        int i = 0;
        for(Stat s: statsForPlayer){
            BarEntry barEntry = new BarEntry(i, Float.parseFloat(s.getCount()));
            entries.add(barEntry);
            xValues.add(s.getStatName());
            i++;
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Bar Chart");

        BarData data = new BarData(barDataSet);

        // TODO : Column Labels don't line up properly
        ValueFormatter xAxisFormatter = new IndexAxisValueFormatter(xValues);
        XAxis xAxis = barChart.getXAxis();

        xAxis.setLabelCount(xValues.size(), true);
        xAxis.setValueFormatter(xAxisFormatter);


        barChart.setData(data);
        barChart.invalidate();

    }


    @Override
    public int getItemCount() {
        return results.size();
    }
}

