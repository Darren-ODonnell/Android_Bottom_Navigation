package com.example.bottomnavigationproper;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.ViewModels.GameViewModel;
import com.example.bottomnavigationproper.ViewModels.StatsSelectionViewModel;

import java.io.Serializable;
import java.util.List;

public class StatsFragment extends Fragment {

//    private PlayerViewModel playerViewModel;
    private Spinner spinnerPlayer;
    private Player playerSelected;

    private StatsSelectionViewModel viewModel;
//    private FixtureViewModel fixtureViewModel;
    private Spinner spinnerFixture;
    private Fixture fixtureSelected;

    private Spinner spinnerStatName;
    private StatName statNameSelected;


    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        initFixtureViewModel();
//        initPlayerViewModel();
        initStatsSelectionViewModel();

    }

    private void initStatsSelectionViewModel(){
        viewModel = new ViewModelProvider(this).get(StatsSelectionViewModel.class);
        viewModel.init();
        viewModel.getFixturesResponseLiveData().observe(this, new Observer<List<Fixture>>(){
            @Override
            public void onChanged(List<Fixture> fixtureList) {
                if (fixtureList != null) {
                    setFixtureList(fixtureList);
                }
            }
        });
        viewModel.getPlayerResponseLiveData().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                if(players != null){
                    setPlayerList(players);
                }
            }
        });

        viewModel.getStatNameLiveData().observe(this, new Observer<List<StatName>>() {
            @Override
            public void onChanged(List<StatName> statNames) {
                if(statNames != null){
                    setStatNameList(statNames);
                }
            }
        });
        viewModel.getStatNames();
        viewModel.getFixtures();
        viewModel.getPlayers();

        viewModel.getStatResponseLiveData().observe(this, new Observer<List<StatsView>>() {
            @Override
            public void onChanged(List<StatsView> statsViewList) {
                if (statsViewList != null) {
                    navigateToHeatMap(statsViewList);
                }

            }
        });
    }

    public void setPlayerList(List<Player> players){
        if(!UserSingleton.getInstance().isAdminOrCoach()){
            spinnerPlayer.setVisibility(View.INVISIBLE);
            playerSelected = UserSingleton.getInstance().getPlayer();
        }else {
            Player all = new Player();
            all.setFirstname("All");
            all.setLastname("Players");
            players.add(0, all);
            ArrayAdapter<Player> adapter =
                    new ArrayAdapter<Player>(getContext(), android.R.layout.simple_spinner_dropdown_item, players);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPlayer.setAdapter(adapter);

            spinnerPlayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    playerSelected = (Player) parent.getItemAtPosition(pos);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public void setFixtureList(List<Fixture> fixtures){
        Fixture all = new Fixture();
        all.setFixtureDate("All Fixtures");
        fixtures.add(0, all);
        ArrayAdapter<Fixture> adapter =
                new ArrayAdapter<Fixture>(getContext(),  android.R.layout.simple_spinner_dropdown_item, fixtures);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinnerFixture.setAdapter(adapter);

        spinnerFixture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                fixtureSelected = (Fixture)parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setStatNameList(List<StatName> statNames){
        StatName all = new StatName();
        all.setName("All Stats");
        statNames.add(0, all);
        ArrayAdapter<StatName> adapter =
                new ArrayAdapter<StatName>(getContext(),  android.R.layout.simple_spinner_dropdown_item, statNames);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinnerStatName.setAdapter(adapter);

        spinnerStatName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                statNameSelected = (StatName)parent.getItemAtPosition(pos);
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
        spinnerFixture = view.findViewById(R.id.spinnerFixture);
        spinnerPlayer = view.findViewById(R.id.spinnerPlayer);
        spinnerStatName = view.findViewById(R.id.spinnerStat);
        initButton(view);

        return view;
    }

    private void initButton(View view) {
        ImageButton heatButton = view.findViewById(R.id.heatmapDisplayButton);
        heatButton.setOnClickListener(v -> {
            retrieveStats(playerSelected, fixtureSelected, statNameSelected);
        });
        ImageButton button = view.findViewById(R.id.statsDisplayButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("Player", playerSelected);
                args.putSerializable("Fixture", fixtureSelected);
                args.putSerializable("StatName", statNameSelected);
                Fragment toFragment = new StatsDisplayFragment();
                toFragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView2, toFragment, null)
                        .commit();
            }
        });
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
        if (playerAll && fixtureAll && statAll) viewModel.getAllPlayerStatFixtureHeatMap();
        else if (playerAll && fixtureAll) viewModel.getAllPlayerFixtureHeatMap(statName);
        else if (playerAll && statAll) viewModel.getAllPlayerStatHeatMap(fixture);
        else if (statAll && fixtureAll) viewModel.getAllStatFixtureHeatMap(player);
        else if (playerAll) viewModel.getAllPlayerHeatMap(fixture, statName);
        else if (fixtureAll) viewModel.getAllFixtureHeatMap(player, statName);
        else if (statAll) viewModel.getAllStatsHeatMap(player, fixture);
        else viewModel.getStatHeatMap(player, fixture, statName);
    }

    private void navigateToHeatMap(List<StatsView> statsViewList){
            Bundle args = new Bundle();
            args.putSerializable("statList", (Serializable) statsViewList);
            args.putSerializable("Player", playerSelected);
            args.putSerializable("Fixture", fixtureSelected);
            args.putSerializable("StatName", statNameSelected);
            Fragment toFragment = new GridFragment();
            toFragment.setArguments(args);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView2, toFragment, null)
                    .commit();
    }

    @Override
    public void onStop() {
        viewModel.getStatResponseLiveData().removeObservers(this);
        viewModel.getStatNameLiveData().removeObservers(this);
        viewModel.getSingleStatLiveData().removeObservers(this);
        viewModel.getPlayerResponseLiveData().removeObservers(this);
        viewModel.getSingleFixtureLiveData().removeObservers(this);
        viewModel.getFixturesResponseLiveData().removeObservers(this);
        super.onStop();

    }

}