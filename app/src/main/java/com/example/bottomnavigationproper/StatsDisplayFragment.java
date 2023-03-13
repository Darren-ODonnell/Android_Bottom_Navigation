package com.example.bottomnavigationproper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bottomnavigationproper.Adapters.MultiViewTypeAdapter;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Models.StatName;

//import com.example.bottomnavigationproper.utils.PlayerResultsAdapter;
import com.example.bottomnavigationproper.ViewModels.StatViewModel;

import java.util.ArrayList;
import java.util.List;

public class StatsDisplayFragment extends Fragment {

    private StatViewModel viewModel;
    private MultiViewTypeAdapter adapter;
    private Player player;
    private Fixture fixture;
    private StatName statName;
    private List<String> locations;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

        assert this.getArguments() != null;
        player = (Player) this.getArguments().getSerializable("Player");
        fixture = (Fixture) this.getArguments().getSerializable("Fixture");
        statName = (StatName) this.getArguments().getSerializable("StatName");

        adapter = new MultiViewTypeAdapter();

        locations = new ArrayList<>();

        initViewModel();

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(StatViewModel.class);
        viewModel.init();
        viewModel.getStatResponseLiveData().observe(this, new Observer<List<StatsView>>() {
            @Override
            public void onChanged(List<StatsView> statsViewList) {
                if (statsViewList != null) {
                    adapter.setResults(statsViewList);
                    adapter.notifyDataSetChanged();
                }

            }
        });
        viewModel.getSingleFixtureLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                adapter.setSingleFixture(aBoolean);

            }
        });
        viewModel.getSingleStatLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                adapter.setSingleStat(aBoolean);

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

        return view;
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

    @Override
    public void onStop() {
        viewModel.getStatNameLiveData().removeObservers(this);
        viewModel.getStatResponseLiveData().removeObservers(this);
        viewModel.getSingleStatLiveData().removeObservers(this);
        viewModel.getSingleFixtureLiveData().removeObservers(this);
        super.onStop();

    }


}