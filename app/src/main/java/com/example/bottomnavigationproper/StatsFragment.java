package com.example.bottomnavigationproper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Services.PlayerRepository;
import com.example.bottomnavigationproper.ViewModels.PlayerViewModel;
import com.example.bottomnavigationproper.databinding.FragmentStatsBinding;
import com.example.bottomnavigationproper.utils.PlayerResultsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatsFragment extends Fragment {

    private PlayerViewModel viewModel;
    private Spinner spinner;

    private Player playerSelected;


    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
        viewModel.init();
        viewModel.getPlayerResponseLiveData().observe(this, new Observer<List<Player>>(){
            @Override
            public void onChanged(List<Player> playerList) {
                if (playerList != null) {
                    setList(playerList);
//                    adapter.setResults(playerList);
                }
            }
        });
        viewModel.getPlayers();
    }

    public void setList(List<Player> players){
        ArrayAdapter<Player> adapter =
                new ArrayAdapter<Player>(getContext(),  android.R.layout.simple_spinner_dropdown_item, players);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                playerSelected = (Player)parent.getItemAtPosition(pos);
                Log.d("player", playerSelected.toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        spinner = view.findViewById(R.id.spinner);
        initButton(view);



        return view;
    }

    private void initButton(View view) {
        Button button = view.findViewById(R.id.statsDisplayButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("Player", playerSelected);
                Fragment toFragment = new StatsDisplayFragment();
                toFragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView2, toFragment, null)
                        .commit();
            }
        });
    }



}