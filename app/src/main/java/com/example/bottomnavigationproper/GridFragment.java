package com.example.bottomnavigationproper;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Models.StatsView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GridFragment extends Fragment {

    private List<StatsView> statsViewList;

    private HashMap<String, Integer> locations;
    View view;
    Player player;
    StatName statName;
    Fixture fixture;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert this.getArguments() != null;

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

        statsViewList = getArguments().getParcelableArrayList("statList");
        player = (Player) getArguments().getSerializable("Player");
        fixture = (Fixture) getArguments().getSerializable("Fixture");
        statName = (StatName) getArguments().getSerializable("StatName");






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
        view = inflater.inflate(R.layout.grid_layout, container, false);

        heatMap(view);
        initInfoButton();
        initLabels();

        return view;
    }

    private void initLabels() {
        TextView fixtureTV =  view.findViewById(R.id.heatmap_fixture);
        TextView playerTV =  view.findViewById(R.id.heatmap_player);
        TextView statNameTV =  view.findViewById(R.id.heatmap_stat);

        fixtureTV.setText(fixture.getFixtureDate());
        String fullname = player.getFirstname() + " " + player.getLastname();
        playerTV.setText(fullname);
        statNameTV.setText(statName.getName());
    }

    private void initInfoButton() {
        view.findViewById(R.id.info_button).setOnClickListener(v ->{
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View fView = getLayoutInflater().inflate(R.layout.heatmap_info, null);

            mBuilder.setView(fView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();


            fView.findViewById(R.id.close_heatmap_info_button).setOnClickListener(r -> {
                dialog.dismiss();
            });
        });
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
        Map<String, List<StatsView>> grid = getLocationCountGrid(statsViewList);
        Map<String, Integer> successGrid = getSuccessPercentGrid(grid);


        Map<String, Integer> colourGrid = initGrid();

        int highest = getLargestCount(grid);



        // Setting colourGrid
        for(String key: colourGrid.keySet()){
            if(grid.containsKey(key)){
                List<StatsView> location = grid.get(key);
                assert location != null;
                int count = Integer.parseInt(location.get(0).getCount());
                if(location.size()>1)
                    count += Integer.parseInt(location.get(1).getCount());

                if(count > highest){
                    highest = count;
                }

                colourGrid.put(key, getColour(count, highest));
            }else{
                colourGrid.put(key, getColour(0,1));
            }

        }

        // Example key,value (A1, yellow) , (A2, red)
        updateGridXmlWithColours(view, colourGrid, successGrid);
    }

    // Gets highest count per grid section ( colours are set based off highest count )
    private int getLargestCount(Map<String, List<StatsView>> grid) {
        //Used to determine what value to set the darkest colour to
        int highest = -1;

        for(String key: grid.keySet()){
            List<StatsView> location = grid.get(key);
            int count = Integer.parseInt(location.get(0).getCount());
            if(location.size()>1)
                count += Integer.parseInt(location.get(1).getCount());

            if(count > highest){
                highest = count;
            }
        }
        return highest;
    }

    private Map<String, Integer> getSuccessPercentGrid(Map<String, List<StatsView>> statMap) {
        Map<String, Integer> grid = initGrid();

        //cycle through grid
        for(String key: grid.keySet()){

            int successCount = 0;

             if (statMap.containsKey(key)){
                 List<StatsView> list = statMap.get(key);
                 assert list != null;
                 int total = 0;
                 int count1 = Integer.parseInt(list.get(0).getCount());
                 int count2 = 0;//divide by 1 returns the same number
                 if(list.size()>1)
                     count2 = Integer.parseInt(list.get(1).getCount());
                    total = count2;
                 successCount = (list.get(0).getSuccess()) ? count1 : count2;
                 total += count1;

                 if(!list.isEmpty()){
                     grid.put(key, (successCount * 100) / total);
                 }else
                     grid.put(key, 0);
             }
        }

        return grid;
    }

    private Map<String, List<StatsView>> getLocationCountGrid(List<StatsView> statsViewList) {
        Map<String, List<StatsView>> grid = new HashMap<>();

        // loop on data
        for(StatsView s: statsViewList){
            String loc = s.getLocation();
            // if location has a list
            if(grid.get(loc) == null){
                grid.put(loc, new ArrayList<StatsView>());
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
        grid.put("E1", 0);grid.put("E2", 0);grid.put("E3", 0);
        grid.put("D1", 0);grid.put("D2", 0);grid.put("D3", 0);
        grid.put("C1", 0);grid.put("C2", 0);grid.put("C3", 0);
        grid.put("B1", 0);grid.put("B2", 0);grid.put("B3", 0);
        grid.put("A1", 0);grid.put("A2", 0);grid.put("A3", 0);
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
        else if(count > 0)
            return getResources().getColor(R.color.lightYellow);
        else
            return getResources().getColor(R.color.lightGray);
    }

}