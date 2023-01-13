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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GridFragment extends Fragment {

    private List<Stat> statList;
    private HashMap<String, Integer> locations;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert this.getArguments() != null;

        statList = (List<Stat>) this.getArguments().getSerializable("statList");

//        locations = new ArrayList<>();

//        for(Stat s: statList){
//            locations.put(s.getLocation(),
//                            locations.get(s.getLocation()) + 1
//            );
//
//
//        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_layout, container, false);

        heatMap(view);

        return view;
    }

    private void setBackgroundColour(View tv, Integer colour, Integer percent){
        tv.setBackgroundColor(colour);

        String percentStr = (percent == null) ? "0" : percent.toString();
        percentStr += "%";
        ((TextView)tv).setText(percentStr);
    }


    private void updateGridXmlWithColours(View view, Map<String, Integer> colourGrid, Map<String, Integer> successGrid) {
        setBackgroundColour(view.findViewById(R.id.pitchGridA1), colourGrid.get("A1"), successGrid.get("A1"));
        setBackgroundColour(view.findViewById(R.id.pitchGridA2), colourGrid.get("A2"), successGrid.get("A2"));
        setBackgroundColour(view.findViewById(R.id.pitchGridA3), colourGrid.get("A3"), successGrid.get("A3"));

        setBackgroundColour(view.findViewById(R.id.pitchGridB1), colourGrid.get("B1"), successGrid.get("B1"));
        setBackgroundColour(view.findViewById(R.id.pitchGridB2), colourGrid.get("B2"), successGrid.get("B2"));
        setBackgroundColour(view.findViewById(R.id.pitchGridB3), colourGrid.get("B3"), successGrid.get("B3"));

        setBackgroundColour(view.findViewById(R.id.pitchGridC1), colourGrid.get("C1"), successGrid.get("C1"));
        setBackgroundColour(view.findViewById(R.id.pitchGridC2), colourGrid.get("C2"), successGrid.get("C2"));
        setBackgroundColour(view.findViewById(R.id.pitchGridC3), colourGrid.get("C3"), successGrid.get("C3"));

        setBackgroundColour(view.findViewById(R.id.pitchGridD1), colourGrid.get("D1"), successGrid.get("D1"));
        setBackgroundColour(view.findViewById(R.id.pitchGridD2), colourGrid.get("D2"), successGrid.get("D2"));
        setBackgroundColour(view.findViewById(R.id.pitchGridD3), colourGrid.get("D3"), successGrid.get("D3"));

        setBackgroundColour(view.findViewById(R.id.pitchGridE1), colourGrid.get("E1"), successGrid.get("E1"));
        setBackgroundColour(view.findViewById(R.id.pitchGridE2), colourGrid.get("E2"), successGrid.get("E2"));
        setBackgroundColour(view.findViewById(R.id.pitchGridE3), colourGrid.get("E3"), successGrid.get("E3"));

    }

    public void heatMap(View view){
        Map<String, List<Stat>> grid = getLocationCountGrid(statList);
        Map<String, Integer> successGrid = getSuccessPercentGrid(grid);


        Map<String, Integer> colourGrid = initGrid();

        int highest = getLargestCount(grid);

        // Setting colourGrid
        for(String key: grid.keySet()){
            int count = grid.get(key).size();
            colourGrid.put(key, getColour(count, highest));
        }

        // Example key,value (A1, yellow) , (A2, red)
        updateGridXmlWithColours(view, colourGrid, successGrid);
    }

    // Gets highest count per grid section ( colours are set based off highest count )
    private int getLargestCount(Map<String, List<Stat>> grid) {
        //Used to determine what value to set the darkest colour to
        int highest = -1;

        for(String key: grid.keySet()){
            int count = grid.get(key).size();
            if(count > highest){
                highest = count;
            }
        }
        return highest;
    }

    private Map<String, Integer> getSuccessPercentGrid(Map<String, List<Stat>> statMap) {
        Map<String, Integer> grid = initGrid();

        //cycle through grid
        for(String key: grid.keySet()){

            int successCount = 0;

             if (statMap.containsKey(key)){
                 List<Stat> list = statMap.get(key);
                 for(Stat s : list) {
                     if(s.getSuccess()){
                         successCount++;
                     }
                 }
                 if(!list.isEmpty()){
                     grid.put(key, (successCount * 100) / list.size());
                 }
             }
        }

        return grid;
    }

    private Map<String, List<Stat>> getLocationCountGrid(List<Stat> statList) {
        Map<String, List<Stat>> grid = new HashMap<>();

//        grid.put("A1", 0);grid.put("A2", 0);grid.put("A3", 0);
//        grid.put("B1", 0);grid.put("B2", 0);grid.put("B3", 0);
//        grid.put("C1", 0);grid.put("C2", 0);grid.put("C3", 0);
//        grid.put("D1", 0);grid.put("D2", 0);grid.put("D3", 0);
//        grid.put("E1", 0);grid.put("E2", 0);grid.put("E3", 0);









        // loop on data
        for(Stat s: statList){
            String loc = s.getLocation();
            // if location has a list
            if(grid.get(loc) == null){
                grid.put(loc, new ArrayList<Stat>());
            }
                //get current state of list
                List list = grid.get(loc);
                //add item to list
                list.add(s);
                //put list back in map
                grid.put(loc, list);
                // if location is empty


        }
        return grid;
    }

    private Map<String, Integer> initGrid() {
        Map<String, Integer> grid= new HashMap<>();
        grid.put("A1", 0);grid.put("A2", 0);grid.put("A3", 0);
        grid.put("B1", 0);grid.put("B2", 0);grid.put("B3", 0);
        grid.put("C1", 0);grid.put("C2", 0);grid.put("C3", 0);
        grid.put("D1", 0);grid.put("D2", 0);grid.put("D3", 0);
        grid.put("E1", 0);grid.put("E2", 0);grid.put("E3", 0);
        return grid;
    }

    private int getColour(int count, int highestVal) {
        if(count > .8*(highestVal))
            return getResources().getColor(R.color.red);
        else if(count > .6*(highestVal))
            return getResources().getColor(R.color.redOrange);
        else if(count > .4*(highestVal))
            return getResources().getColor(R.color.orange);
        else if(count > .2*(highestVal))
            return getResources().getColor(R.color.yellow);
        else
            return getResources().getColor(R.color.lightBlue);

    }

}