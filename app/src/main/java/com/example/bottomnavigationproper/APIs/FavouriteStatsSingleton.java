package com.example.bottomnavigationproper.APIs;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.bottomnavigationproper.Models.StatsView;

import java.util.List;
import java.util.stream.Collectors;

public class FavouriteStatsSingleton {

    private static FavouriteStatsSingleton instance;

    private List<String> favouriteStats;

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

        if(favouriteStats.size() > 0)
            stats = stats.stream()
                    .filter(s -> !favouriteStats.contains(s.getStatName()))
                    .collect(Collectors.toList());
        return stats;
    }

}
