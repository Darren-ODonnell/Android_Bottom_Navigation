package com.example.bottomnavigationproper;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.utils.StatResultAdapter;
import com.example.bottomnavigationproper.*;
import com.example.bottomnavigationproper.Services.PlayerRepository;

//import com.example.bottomnavigationproper.utils.PlayerResultsAdapter;
import com.example.bottomnavigationproper.ViewModels.StatViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

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
}