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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView2, StatsDisplayFragment.class, null)
                        .commit();
            }
        });
    }

    private List<String> getPlayerNames(){
        PlayerRepository service = new PlayerRepository();
        List<String> names = new ArrayList<>();
//        for(Player p: players) {
//            names.add(p.getFirstname() + " " + p.getLastname());
//        }
        return names;
    }


    private void initialiseSpinner(View view, int spinnerId, List<String> names) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView2,fragment);
        fragmentTransaction.commit();
    }



}