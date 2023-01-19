package com.example.bottomnavigationproper.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.Models.StatView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.example.bottomnavigationproper.R;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiViewTypeAdapter extends RecyclerView.Adapter{
    private List<StatView> results = new ArrayList<>();
    private HashMap<String, List<StatView>> playerStats = new HashMap<>();
    private HashMap<Integer, List<StatView>> intPlayerStats = new HashMap<>();
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

        TextView playerName;
        TextView fixture;
        BarChart barChart;

        public BarChartViewHolder(View itemView) {
            super(itemView);

            this.playerName = (TextView) itemView.findViewById(R.id.bar_player);
            this.fixture = (TextView) itemView.findViewById(R.id.bar_fixture);
            this.barChart = (BarChart) itemView.findViewById(R.id.bar_graph);
        }
    }



    public void setResults(List<StatView>data) {
        this.results = data;

        this.playerStats = mapResultsToPlayer(results);
    }

    private HashMap<String, List<StatView>> mapResultsToPlayer(List<StatView> results) {
        HashMap<String, List<StatView>> playerStats = new HashMap<>();
        int i = 0;
        for(StatView s: results){
            String fullName = s.getFirstName() + " " + s.getLastName();
            if(playerStats.get(fullName) == null){
                List<StatView> list = new ArrayList<>();
                playerStats.put(fullName, list);
                intPlayerStats.put(i++,list);
            }else{
                List<StatView> list = playerStats.get(fullName);
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

        List<StatView> statsForPlayer = intPlayerStats.get(listPosition);

        if (statsForPlayer != null) {

//            if(singleFixture && singleStat){
//                ((BarChartViewHolder) holder).statName.setText(stat.getStatName());
            String fullName = statsForPlayer.get(0).getFirstName() + " " + statsForPlayer.get(0).getLastName();
            String fixtureDate = statsForPlayer.get(0).getFixtureDate();
                ((BarChartViewHolder) holder).playerName.setText(fullName);
                ((BarChartViewHolder) holder).fixture.setText(fixtureDate);
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

    private void createBarChart(RecyclerView.ViewHolder holder, List<StatView> statsForPlayer) {
        BarChart barChart = ((BarChartViewHolder) holder).barChart;
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<String> xValues = new ArrayList<>();

        int i = 0;
        for(StatView s: statsForPlayer){
            BarEntry barEntry = new BarEntry(i, Float.parseFloat(s.getCount()));
            entries.add(barEntry);
            xValues.add(s.getStatName());
            i++;
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Bar Chart");
        barDataSet.setColor(R.color.purple_200);
        barDataSet.setDrawValues(false);

        BarData data = new BarData(barDataSet);


        XAxis xAxis = barChart.getXAxis();
        Description desc = new Description();
        desc.setText("");
        barChart.setDescription(desc);

        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelRotationAngle(-60);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(xValues.size());

        barChart.setData(data);
        barChart.invalidate();

        Legend legend = barChart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);

    }


    @Override
    public int getItemCount() {
        return results.size();
    }
}

