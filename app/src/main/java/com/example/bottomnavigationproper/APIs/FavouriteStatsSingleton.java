package com.example.bottomnavigationproper.APIs;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.bottomnavigationproper.Models.StatsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FavouriteStatsSingleton {

    private static FavouriteStatsSingleton instance;

    private List<String> favouriteStats = new ArrayList<>();

    private FavouriteStatsSingleton(){

    }

    public static FavouriteStatsSingleton getInstance(){
        instance = (instance == null)? new FavouriteStatsSingleton(): instance;
        return instance;
    }

    public void setFavouriteStats(List<String> favouriteStats){
        this.favouriteStats = favouriteStats;
    }

    public List<String> getFavouriteStats(){
        return favouriteStats;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<StatsView> sortByFavouriteStats(List<StatsView> stats){
        List<StatsView> allStats = stats;

        if(favouriteStats.size() > 0)
            stats = stats.stream()
                    .filter(s -> !favouriteStats.contains(s.getStatName()))
                    .collect(Collectors.toList());

        for(StatsView s: allStats){
            if(!stats.contains(s))
                stats.add(s);
        }
        return stats;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<StatsView> getFavouritesOnly(List<StatsView> stats){

        if(favouriteStats.size() > 0)
            stats = stats.stream()
                    .filter(s -> favouriteStats.contains(s.getStatName()))
                    .collect(Collectors.toList());
        return stats;
    }

    public StatsView findGreatestPercent(List<StatsView> stats){
        Map<String, List<StatsView>> grid = getStatCountMap(stats);
        Map<String, Integer> successGrid = getSuccessPercentGrid(grid);
        return findGreatest(successGrid);
    }

    public StatsView findSmallestPercent(List<StatsView> stats){
        Map<String, List<StatsView>> grid = getStatCountMap(stats);
        Map<String, Integer> successGrid = getSuccessPercentGrid(grid);
        return findSmallest(successGrid);
    }

    public List<StatsView> findListOfPercents(List<StatsView> stats) {
        Map<String, List<StatsView>> grid = getStatCountMap(stats);
        Map<String, Integer> successMap = getSuccessPercentGrid(grid);
        return convertMapToStatsView(successMap);
    }

    private List<StatsView> convertMapToStatsView(Map<String, Integer> successMap) {
        List<StatsView> stats = new ArrayList<>();
        for(String key: successMap.keySet()){
            StatsView s = new StatsView();
            s.setStatName(key);
            s.setCount(String.valueOf(successMap.get(key)));
            stats.add(s);
        }
        return stats;
    }

    private StatsView findGreatest(Map<String, Integer> successGrid) {
        StatsView statsView = new StatsView();

        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : successGrid.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        assert maxEntry != null;

        statsView.setStatName(maxEntry.getKey());
        statsView.setCount(Integer.toString(maxEntry.getValue()));
        return statsView;

    }

    private StatsView findSmallest(Map<String, Integer> successGrid) {
        StatsView statsView = new StatsView();
//TODO findlargest and findsmallest both return the incorrect percentages, they should be much closer to 100%

        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : successGrid.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) < 0) {
                maxEntry = entry;
            }
        }
        assert maxEntry != null;
        statsView.setStatName(maxEntry.getKey());
        statsView.setCount(Integer.toString(maxEntry.getValue()));
        return statsView;

    }

    private Map<String, Integer> getSuccessPercentGrid(Map<String, List<StatsView>> statMap) {
        Map<String, Integer> grid = new HashMap<>();


        //cycle through grid
        for(String key: statMap.keySet()){

            int successCount = 0;
             List<StatsView> list = statMap.get(key);
            assert list != null;
            int total = 0;
            int count1 = Integer.parseInt(list.get(0).getCount());//get true because query is ordered by true, false
            int count2 = 0;//divide by 1 returns the same number
            if(list.size()>1)
                count2 = Integer.parseInt(list.get(1).getCount());
            total = count2;
            successCount = (list.get(0).getSuccess()) ? count1 : count2;
            total += count1;

            grid.put(key, (successCount * 100) / total);

        }

        return grid;
    }

    private Map<String, List<StatsView>> getStatCountMap(List<StatsView> statsViewList) {
        Map<String, List<StatsView>> grid = new HashMap<>();

        // loop on data
        for(StatsView s: statsViewList){
            String statName = s.getStatName();
            // if location has a list
            if(grid.get(statName) == null){
                grid.put(statName, new ArrayList<StatsView>());
            }
            //get current state of list
            List list = grid.get(statName);
            //add item to list
            list.add(s);
            //put list back in map
            grid.put(statName, list);
            // if location is empty


        }
        return grid;
    }


}
