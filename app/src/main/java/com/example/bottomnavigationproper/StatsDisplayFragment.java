package com.example.bottomnavigationproper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.List;

public class StatsDisplayFragment extends Fragment {

    private StatViewModel viewModel;
    private StatResultAdapter adapter;
    private Player player;
    private Fixture fixture;
    private StatName statName;

    private Button searchButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert this.getArguments() != null;
        player = (Player) this.getArguments().getSerializable("Player");
        fixture = (Fixture) this.getArguments().getSerializable("Fixture");
        statName = (StatName) this.getArguments().getSerializable("StatName");

        adapter = new StatResultAdapter();

        viewModel = new ViewModelProvider(this).get(StatViewModel.class);
        viewModel.init();
        viewModel.getStatResponseLiveData().observe(this, new Observer<List<Stat>>(){
            @Override
            public void onChanged(List<Stat> statList) {
                if (statList != null) {
                    adapter.setResults(statList);
                }
            }
        });
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

    public void retrieveStats(Player player, Fixture fixture, StatName statName){
        boolean playerAll, fixtureAll, statAll;

        playerAll = player.toString().equals("All Players");
        fixtureAll = fixture.toString().equals("All Fixtures");
        statAll = statName.toString().equals("All Stats");

        getStats(player, fixture, statName, playerAll, fixtureAll, statAll);
    }
    public void getStats(Player player, Fixture fixture, StatName statName,
                         boolean playerAll, boolean fixtureAll, boolean statAll){

        if (playerAll && fixtureAll && statAll) viewModel.getAllPlayerStatFixture();
        else if (playerAll && fixtureAll) viewModel.getAllPlayerFixture(statName);
        else if (playerAll && statAll) viewModel.getAllPlayerStat(fixture);
        else if (statAll && fixtureAll) viewModel.getAllStatFixture(player);
        else if (playerAll) viewModel.getAllPlayer(fixture, statName);
        else if (fixtureAll) viewModel.getAllFixture(player,statName);
        else if (statAll) viewModel.getAllStats(player, fixture);
        else viewModel.getStat(player, fixture, statName);


    }
}