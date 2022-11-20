package com.example.bottomnavigationproper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.ViewModels.StatViewModel;
import com.example.bottomnavigationproper.utils.StatResultAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GridLayout extends Fragment {

    private List<Stat> statList;
    private List<String> locations;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert this.getArguments() != null;

        statList = (List<Stat>) this.getArguments().getSerializable("statList");

        locations = new ArrayList<>();
        for(Stat s: statList){
            locations.add(s.getLocation());
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_layout, container, false);

        heatMap(view, locations);

        return view;
    }

    private void setBackgroundColour(View view, int id, Integer colour){
        (view.findViewById(id))
                .setBackgroundColor(
                        getResources().getColor(colour)
                );
    }


    private void updateGridXmlWithColours(View view, Map<String, Integer> colourGrid) {
        setBackgroundColour(view, R.id.pitchGridA1, colourGrid.get("A1"));
        setBackgroundColour(view, R.id.pitchGridA2, colourGrid.get("A2"));
        setBackgroundColour(view, R.id.pitchGridA3, colourGrid.get("A3"));

        setBackgroundColour(view, R.id.pitchGridB1, colourGrid.get("B1"));
        setBackgroundColour(view, R.id.pitchGridB2, colourGrid.get("B2"));
        setBackgroundColour(view, R.id.pitchGridB3, colourGrid.get("B3"));

        setBackgroundColour(view, R.id.pitchGridC1, colourGrid.get("C1"));
        setBackgroundColour(view, R.id.pitchGridC2, colourGrid.get("C2"));
        setBackgroundColour(view, R.id.pitchGridC3, colourGrid.get("C3"));

        setBackgroundColour(view, R.id.pitchGridD1, colourGrid.get("D1"));
        setBackgroundColour(view, R.id.pitchGridD2, colourGrid.get("D2"));
        setBackgroundColour(view, R.id.pitchGridD3, colourGrid.get("D3"));

        setBackgroundColour(view, R.id.pitchGridE1, colourGrid.get("E1"));
        setBackgroundColour(view, R.id.pitchGridE2, colourGrid.get("E2"));
        setBackgroundColour(view, R.id.pitchGridE3, colourGrid.get("E3"));

    }

    public void heatMap(View view, List<String> locationList){
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
        updateGridXmlWithColours(view, colourGrid);
    }

    private int getColour(int count, int highestVal) {
        if(count > .8*(highestVal)) return R.color.red;
        else if(count > .6*(highestVal)) return R.color.redOrange;
        else if(count > .4*(highestVal)) return R.color.orange;
        else if(count > .2*(highestVal)) return R.color.yellow;
        else return R.color.lightBlue;


    }

}