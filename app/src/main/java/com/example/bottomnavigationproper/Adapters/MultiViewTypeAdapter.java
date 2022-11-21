//package com.example.bottomnavigationproper.Adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.bottomnavigationproper.ChartType;
//import com.example.bottomnavigationproper.Models.Stat;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.charts.LineChart;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MultiViewTypeAdapter extends RecyclerView.Adapter{
//    private List<Stat> results = new ArrayList<>();
//    private Boolean singleStat;
//    private Boolean singleFixture;
//    private ChartType chartType;
//
//    public static class BasicViewHolder extends RecyclerView.ViewHolder {
//
//        TextView statName;
//        TextView responseVal;
//
//        public BasicViewHolder(View itemView) {
//            super(itemView);
//
//            this.statName = (TextView) itemView.findViewById(R.id.type);
//            this.responseVal = (TextView) itemView.findViewById(R.id.card_view);
//        }
//    }
//
//    public static class BarChartViewHolder extends RecyclerView.ViewHolder {
//
//        TextView statName;
//        BarChart barChart;
//
//        public BarChartViewHolder(View itemView) {
//            super(itemView);
//
//            this.statName = (TextView) itemView.findViewById(R.id.type);
//            this.barChart = (BarChart) itemView.findViewById(R.id.card_view);
//        }
//    }
//
//    public static class LineChartViewHolder extends RecyclerView.ViewHolder {
//
//        TextView statName;
//        LineChart lineChart;
//
//        public LineChartViewHolder(View itemView) {
//            super(itemView);
//
//            this.statName = (TextView) itemView.findViewById(R.id.type);
//            this.lineChart = (LineChart) itemView.findViewById(R.id.background);
//
//        }
//    }
//
//    public MultiViewTypeAdapter(ArrayList<Stat>data) {
//        this.results = data;
//    }
//
//    public void setSingleFixture(Boolean single){
//        this.singleFixture = single;
//    }
//
//    public void setSingleStat(Boolean single){
//        this.singleStat = single;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View view;
//        if(singleFixture && singleStat){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_type, parent, false);
//            return new BasicViewHolder(view);
//        }else if (singleStat){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_type, parent, false);
//            return new LineChartViewHolder(view);
//        }else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_type, parent, false);
//            return new BarChartViewHolder(view);
//        }
//        return null;
//    }
//
////    @Override
////    public int getItemViewType(int position) {
////
////        switch (dataSet.get(position).type) {
////            case 0:
////                return Model.TEXT_TYPE;
////            case 1:
////                return Model.IMAGE_TYPE;
////            case 2:
////                return Model.AUDIO_TYPE;
////            default:
////                return -1;
////        }
////    }
//
//    @Override
//    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
//
//        Stat stat = results.get(listPosition);
//        if (stat != null) {
//
//            if(singleFixture && singleStat){
//                ((BarChartViewHolder) holder).statName.setText(stat.getStatName());
//
//            }else if (singleStat){
//                ((LineChartViewHolder) holder).statName.setText(stat.getStatName());
////                ((LineChartViewHolder) holder).lineChart.setLine(stat.data);
//            }else {
//                ((BasicViewHolder) holder).statName.setText(stat.getStatName());
//                ((BasicViewHolder) holder).responseVal.setText(stat.);
//
//            }
//
//            }
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataSet.size();
//    }
//}
