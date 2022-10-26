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
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Services.PlayerRepository;
import com.example.bottomnavigationproper.ViewModels.PlayerViewModel;
import com.example.bottomnavigationproper.utils.PlayerResultsAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class StatsDisplayFragment extends Fragment {

    private PlayerViewModel viewModel;
    private PlayerResultsAdapter adapter;
    private Player player;

    private Button searchButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert this.getArguments() != null;
        player = (Player) this.getArguments().getSerializable("Player");


        adapter = new PlayerResultsAdapter();

        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        viewModel.init();
        viewModel.getPlayerResponseLiveData().observe(this, new Observer<List<Player>>(){
            @Override
            public void onChanged(List<Player> playerList) {
                if (playerList != null) {
                    adapter.setResults(playerList);
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

            performSearch();
        });

        return view;
    }

    public void performSearch() {
        viewModel.getPlayers();
    }
}