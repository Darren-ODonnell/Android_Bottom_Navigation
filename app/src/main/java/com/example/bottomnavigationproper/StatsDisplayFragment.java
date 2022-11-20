package com.example.bottomnavigationproper;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Button;

import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.utils.StatResultAdapter;

//import com.example.bottomnavigationproper.utils.PlayerResultsAdapter;
import com.example.bottomnavigationproper.ViewModels.StatViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsDisplayFragment extends Fragment {

    private StatViewModel viewModel;
    private StatResultAdapter adapter;
    private Player player;
    private Fixture fixture;
    private StatName statName;
    private List<String> locations;

    private Button searchButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert this.getArguments() != null;
        player = (Player) this.getArguments().getSerializable("Player");
        fixture = (Fixture) this.getArguments().getSerializable("Fixture");
        statName = (StatName) this.getArguments().getSerializable("StatName");

        adapter = new StatResultAdapter();

        locations = new ArrayList<>();

        viewModel = new ViewModelProvider(this).get(StatViewModel.class);
        viewModel.init();
        viewModel.getStatResponseLiveData().observe(this, new Observer<List<Stat>>() {
            @Override
            public void onChanged(List<Stat> statList) {
                if (statList != null) {
                    adapter.setResults(statList);
                    for(Stat s: statList){
                        locations.add(s.getLocation());
                    }
                    heatMap(locations);
                }
            }
        });

        retrieveStats(player, fixture, statName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats_display, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_playersearch_searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        searchButton = view.findViewById(R.id.fragment_playersearch_search);

        searchButton.setOnClickListener(v -> {
            searchButton.setText(player.toString());
            performSearch();
        });

        return view;
    }

    public void performSearch() {
        viewModel.getStats();
    }

    public void retrieveStats(Player player, Fixture fixture, StatName statName) {
        boolean playerAll, fixtureAll, statAll;

        playerAll = player.toString().equals("All Players");
        fixtureAll = fixture.toString().equals("All Fixtures");
        statAll = statName.toString().equals("All Stats");

        getStats(player, fixture, statName, playerAll, fixtureAll, statAll);
    }

    public void getStats(Player player, Fixture fixture, StatName statName,
                         boolean playerAll, boolean fixtureAll, boolean statAll) {

        if (playerAll && fixtureAll && statAll) viewModel.getAllPlayerStatFixture();
        else if (playerAll && fixtureAll) viewModel.getAllPlayerFixture(statName);
        else if (playerAll && statAll) viewModel.getAllPlayerStat(fixture);
        else if (statAll && fixtureAll) viewModel.getAllStatFixture(player);
        else if (playerAll) viewModel.getAllPlayer(fixture, statName);
        else if (fixtureAll) viewModel.getAllFixture(player, statName);
        else if (statAll) viewModel.getAllStats(player, fixture);
        else viewModel.getStat(player, fixture, statName);


    }


    public void heatMap(List<String> locationList){
        Map<String, Integer> grid = new HashMap<>();
        grid.put("A1", 0);grid.put("A2", 0);grid.put("A3", 0);
        grid.put("B1", 0);grid.put("B2", 0);grid.put("B3", 0);
        grid.put("C1", 0);grid.put("C2", 0);grid.put("C3", 0);
        grid.put("D1", 0);grid.put("D2", 0);grid.put("D3", 0);
        grid.put("E1", 0);grid.put("E2", 0);grid.put("E3", 0);

        // loop on data
        for(String s: locationList){
            grid.put(s, grid.get(s) + 1);
        }


        Map<String, Integer> colourGrid = new HashMap<>();

        //Used to determine what value to set the darkest colour to
        int highest = -1;

        for(String key: grid.keySet()){
            int count = grid.get(key);
            if(count > highest){
                highest = count;
            }
        }
        for(String key: grid.keySet()){
            int count = grid.get(key);
            colourGrid.put(key, getColour(count, highest));
        }

        // Example key,value (A1, yellow) , (A2, red)
        navigateToHeatMap(colourGrid);
    }



    private int getColour(int count, int highestVal) {
        if(count > .8*(highestVal)) return R.color.red;
        else if(count > .6*(highestVal)) return R.color.redOrange;
        else if(count > .4*(highestVal)) return R.color.orange;
        else if(count > .2*(highestVal)) return R.color.yellow;
        else return R.color.lightBlue;


    }

    private void navigateToHeatMap(Map<String, Integer> colourGrid){
        getView().findViewById(R.id.navHeatMap).setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putSerializable("colourMap", (Serializable) colourGrid);
                Fragment toFragment = new GridLayout();
                toFragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView2, toFragment, null)
                        .commit();
        });
    }
}