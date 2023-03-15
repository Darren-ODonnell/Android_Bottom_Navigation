package com.example.bottomnavigationproper;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bottomnavigationproper.APIs.FavouriteStatsSingleton;
import com.example.bottomnavigationproper.Adapters.InGameStatsAdapter;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.ViewModels.StatViewModel;
import com.example.bottomnavigationproper.ViewModels.StatsSelectionViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;

    List<StatsView> stats = new ArrayList<>();

    RecyclerView favStatsRV;

    View view;

    InGameStatsAdapter adapter = new InGameStatsAdapter();




    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initStatViewModel();

    }

    private void initStatViewModel(){
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.init();
        viewModel.getStatResponseLiveData().observe(this, new Observer<List<StatsView>>(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<StatsView> statList) {
                if (statList != null) {
                    stats = statList;
                    populateStats();
                    viewModel.getStatResponseLiveData().removeObserver(this);
                }
            }
        });

        if(UserSingleton.getInstance().isAdminOrCoach())
            viewModel.countStatAllPlayer();
        else
            viewModel.countStatByPlayer(UserSingleton.getInstance().getPlayer());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateStats() {
        populatePlayerName();
        populateFavStats();
        populateBestStat();
        populateWorstStat();


    }

    private void populatePlayerName() {
        TextView playerNameTV = view.findViewById(R.id.home_player);
        String name;
        if(UserSingleton.getInstance().isAdminOrCoach()) {
            name = "All Players";
        }else
            name = UserSingleton.getInstance().getPlayer().getFirstname() + " "
                    + UserSingleton.getInstance().getPlayer().getLastname();

        playerNameTV.setText(name);
    }

    private void populateWorstStat() {

        StatsView worstStatView = FavouriteStatsSingleton.getInstance().findSmallestPercent(stats);

        populateStatLayout(R.id.worst_stat, worstStatView);

    }

    private void populateBestStat() {

        StatsView bestStatView = FavouriteStatsSingleton.getInstance().findGreatestPercent(stats);

        populateStatLayout(R.id.best_stat, bestStatView);

    }

    private void populateStatLayout(int id, StatsView statsView){
        LinearLayout topLayout = view.findViewById(id);

        LinearLayout layout = (LinearLayout) topLayout.getChildAt(1);
        TextView name = (TextView) layout.getChildAt(0);
        String key = statsView.getStatName();
        name.setText(key);

        int countStat = getCountOfStat(stats, key);


        TextView count = (TextView) layout.getChildAt(1);
        count.setText(String.valueOf(countStat));


        TextView percent = (TextView) topLayout.getChildAt(2);
        String value = statsView.getCount() + "%";
        percent.setText(value);
    }
    private int getCountOfStat(List<StatsView> stats, String name){
        int count = 0;
        for(StatsView stat: stats){
            if(name.equals(stat.getStatName())){
                count += Integer.parseInt(stat.getCount());
            }
        }
        return count;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateFavStats() {
        List<StatsView> favStats = FavouriteStatsSingleton.getInstance().getFavouritesOnly(stats);
        List<StatsView> recyclerStats = FavouriteStatsSingleton.getInstance().findListOfPercents(favStats);
        favStatsRV = view.findViewById(R.id.player_favStats_rv);



        List<String> statNames = new ArrayList<>();
        recyclerStats.forEach(s -> statNames.add(s.getStatName()));

        List<String> percents = new ArrayList<>();
        recyclerStats.forEach(s -> percents.add(s.getCount()));



        adapter.setResults(statNames);
        adapter.setPercents(percents);
        adapter.setColour(getResources().getColor(R.color.pink1));
        adapter.notifyDataSetChanged();

        favStatsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        favStatsRV.setAdapter(adapter);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }


    @Override
    public void onStop() {
        viewModel.getStatResponseLiveData().removeObservers(this);
        super.onStop();

    }


}