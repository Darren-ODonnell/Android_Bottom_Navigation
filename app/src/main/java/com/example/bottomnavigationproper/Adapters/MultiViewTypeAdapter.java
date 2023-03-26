package com.example.bottomnavigationproper.Adapters;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.CustomXAxisRenderer;
import com.example.bottomnavigationproper.Dictionaries;
import com.example.bottomnavigationproper.Models.StatsView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.example.bottomnavigationproper.R;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiViewTypeAdapter extends RecyclerView.Adapter{
    private List<StatsView> results = new ArrayList<>();
    private HashMap<String, List<StatsView>> playerStats = new HashMap<>();
    private HashMap<Integer, List<StatsView>> intPlayerStats = new HashMap<>();
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
        TextView statName;
        BarChart barChart;

        public BarChartViewHolder(View itemView) {
            super(itemView);

            this.playerName = (TextView) itemView.findViewById(R.id.bar_player);
            this.fixture = (TextView) itemView.findViewById(R.id.bar_fixture);
            this.statName = (TextView) itemView.findViewById(R.id.bar_stat);
            this.barChart = (BarChart) itemView.findViewById(R.id.bar_graph);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setResults(List<StatsView>data) {
        this.results = data;

        this.playerStats = mapResultsToPlayer(results);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private HashMap<String, List<StatsView>> mapResultsToPlayer(List<StatsView> results) {
        HashMap<String, List<StatsView>> playerStats = new HashMap<>();
        getItemCount();
        int i = 0;
        for(StatsView s: results) {
            String fullName = s.getFirstName() + " " + s.getLastName();

                    if (!playerStats.containsKey(fullName)) {
                        List<StatsView> list = new ArrayList<>();
                        list.add(s);
                        playerStats.put(fullName, list);
                        intPlayerStats.put(i++, list);
                    } else {
                        List<StatsView> list = playerStats.get(fullName);
                        assert list != null;
                        list.add(s);
                        playerStats.replace(fullName, list);
    //                    list = intPlayerStats.get(i);
                        intPlayerStats.replace(i, list);
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

        List<StatsView> statsForPlayer = intPlayerStats.get(listPosition);

        if (statsForPlayer != null) {

//            if(singleFixture && singleStat){
//                ((BarChartViewHolder) holder).statName.setText(stat.getStatName());
            String fullName = statsForPlayer.get(0).getFirstName() + " " + statsForPlayer.get(0).getLastName();
            String fixtureDate = statsForPlayer.get(0).getFixtureDate();
            String statName = statsForPlayer.get(0).getStatName();
                ((BarChartViewHolder) holder).playerName.setText(fullName);
                if(singleFixture)
                    ((BarChartViewHolder) holder).fixture.setText(fixtureDate);
                else
                    ((BarChartViewHolder) holder).fixture.setText("All Fixtures");

                createBarChart(holder, statsForPlayer);

                if(singleStat)
                    ((BarChartViewHolder) holder).statName.setText(statName);
                else
                    ((BarChartViewHolder) holder).statName.setText("All Stats");




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

    private void createBarChart(RecyclerView.ViewHolder holder, List<StatsView> statsForPlayer) {
        BarChart barChart = ((BarChartViewHolder) holder).barChart;
        barChart.setBackgroundColor(barChart.getContext().getResources().getColor(R.color.green2));
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<String> xValues = new ArrayList<>();

        int i = 0;
        for (StatsView s : statsForPlayer) {
            BarEntry barEntry = new BarEntry(i, Float.parseFloat(s.getCount()));
            entries.add(barEntry);
            if (singleStat) {
                String temp;
                String opp = s.getAwayTeam();
                if (opp.equals("St Judes")) {
                    if (s.getHomeTeam().contains("/")) {
                        opp = s.getHomeTeam().split("/")[0];
                    } else {
                        opp = s.getHomeTeam();
                    }
                } else {
                    if (s.getAwayTeam().contains("/")) {
                        opp = s.getAwayTeam().split("/")[0];
                    } else {
                        opp = s.getAwayTeam();
                    }

                }
                temp = opp + "\n" + s.getFixtureDate();
                xValues.add(temp);
            }else
                xValues.add(s.getStatName());

            i++;
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Stat Occurrence Frequency");

        List<Integer> colors = new ArrayList<>();
        if (singleStat){
            for (int j = 0; j < xValues.size(); j++) {
                colors.add(getColorForName(statsForPlayer.get(0).getStatName()));
            }
        }else{
            for (int j = 0; j < xValues.size(); j++) {
                colors.add(getColorForName(xValues.get(j)));
            }
        }

        barDataSet.setColors(colors);
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

        if(singleStat)
            barChart.setXAxisRenderer(new CustomXAxisRenderer(barChart.getViewPortHandler(), barChart.getXAxis(), barChart.getTransformer(YAxis.AxisDependency.LEFT)));

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

    private int getColorForName(String s) {
        List<String> statnames = Dictionaries.getInstance().getStatNames();
        List<Integer> colours = Dictionaries.getInstance().getColours();

        int i = statnames.indexOf(s);
        return (i == -1) ? Color.BLACK : colours.get(i);
    }


    @Override
    public int getItemCount() {
        return intPlayerStats.size();
    }
}

