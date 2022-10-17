package com.example.bottomnavigationproper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.bottomnavigationproper.Services.PlayerService;
import com.example.bottomnavigationproper.databinding.FragmentStatsBinding;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class StatsFragment extends Fragment {

    FragmentStatsBinding binding;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        Button button = view.findViewById(R.id.statsDisplayButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new StatsDisplayFragment());
            }
        });
        List<String> names = new ArrayList<>();
        names.add("Darren");
        names.add("Liam");
        initialiseSpinner(view, R.id.spinner, names);

        return view;
    }

    private void getPlayers(){
        PlayerService service = new PlayerService();
        SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.jwt_token), Context.MODE_PRIVATE);
        String retrievedToken  = preferences.getString("TOKEN",null);
        service.getPlayers(retrievedToken);
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