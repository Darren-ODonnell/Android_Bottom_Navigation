package com.example.bottomnavigationproper;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Stat;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Services.StatRepository;
import com.example.bottomnavigationproper.ViewModels.GameViewModel;
import com.example.bottomnavigationproper.ViewModels.StatsSelectionViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameFragment extends Fragment {

    private GameViewModel viewModel;

    //    private PlayerViewModel playerViewModel;
    private Spinner spinnerPlayer;
    private Player playerSelected;

    //    private FixtureViewModel fixtureViewModel;
    private Spinner spinnerSuccess;
    private boolean successSelected;

    private Spinner spinnerStatName;
    private StatName statNameSelected;

    private Fixture currentFixture;

    View view;

    List<StatName> statNames;
    List<Player> players;




    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);



//        spinnerSuccess = view.findViewById(R.id.gameSpinnerSuccess);
//        spinnerPlayer = view.findViewById(R.id.gameSpinnerPlayer);
//        spinnerStatName = view.findViewById(R.id.gameSpinnerStat);
//        initSpinners();
        showFixtureSelection();
        initGridLayoutButtons(view);
        initStatsSelectionViewModel();

        return view;
    }

    private void showFixtureSelection() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.fixture_selection_fragment, null);
        mBuilder.setTitle("Select Fixture");
        Spinner fixtureSpinner = (Spinner) mView.findViewById(R.id.spinnerFixtureSelection);
        setFixtureList(fixtureSpinner);
        Spinner statSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerStat);
        setStatNameList(statSpinner);
        Spinner playerSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerPlayer);
        setPlayerList(playerSpinner);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    private void initGridLayoutButtons(View view) {
        GridLayout grid = (GridLayout) view.findViewById(R.id.pitchGridLocations);
        int childCount = grid.getChildCount();

        for (int i= 0; i < childCount; i++){
            TextView gridSection = (TextView) grid.getChildAt(i);
            int finalI = i;
            gridSection.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    // your click code here
                    Toast.makeText(getContext(), Integer.toString(finalI), Toast.LENGTH_SHORT).show();
                    List<String> gridLocations = new ArrayList<>(Arrays.asList("A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3", "D1", "D2", "D3", "E1", "E2", "E3"));

                    Stat stat = new Stat();
                    stat.setLocation(gridLocations.get(finalI));

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.fragment_game_input, null);
                    mBuilder.setTitle("Input Stat");
                    Spinner successSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerSuccess);
                    setSuccessList(successSpinner);
                    Spinner statSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerStat);
                    setStatNameList(statSpinner);
                    Spinner playerSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerPlayer);
                    setPlayerList(playerSpinner);

                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    mView.findViewById(R.id.inputStatButton).setOnClickListener(v -> {
                        stat.setFirstName(playerSelected.getFirstname());
                        stat.setLastName(playerSelected.getLastname());
                        stat.setStatName(statNameSelected.getName());
                        stat.setSuccess(successSelected);
                        stat.setFixtureDate(currentFixture.getFixtureDate());
                    });

                }
            });
        }
    }

    private void initStatsSelectionViewModel(){
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        viewModel.init();
        viewModel.getFixturesResponseLiveData().observe(this, new Observer<List<Fixture>>(){
            @Override
            public void onChanged(List<Fixture> fixtureList) {
                if (fixtureList != null) {
                    //TODO Get current fixture for populating Home Away team sections, return single fixture
                    setFixtureList(fixtureList);
                }
            }
        });
        viewModel.getPlayerResponseLiveData().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> playerList) {
                if(playerList != null){
                    //TODO setPlayerList contents to list of starting 15 (dependent on current fixture
                    players = playerList;
                }
            }
        });

        viewModel.getStatNameLiveData().observe(this, new Observer<List<StatName>>() {
            @Override
            public void onChanged(List<StatName> statNameList) {
                if(statNameList != null){
                    statNames = statNameList;
                }
            }
        });
        viewModel.getStatNames();
        viewModel.getFixtures();
        viewModel.getPlayers();


    }

    private void setFixtureList(List<Fixture> fixtureList) {
        //TODO Get current fixture
    }

    public void setPlayerList(Spinner spinner){
        ArrayAdapter<Player> adapter =
                new ArrayAdapter<Player>(getContext(),  android.R.layout.simple_spinner_dropdown_item, players);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                playerSelected = (Player)parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setSuccessList(Spinner spinner){
        List<Boolean> options = new ArrayList<>();
        options.add(0, Boolean.TRUE);
        options.add(0, Boolean.FALSE);
        ArrayAdapter<Boolean> adapter =
                new ArrayAdapter<Boolean>(getContext(),  android.R.layout.simple_spinner_dropdown_item, options);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                successSelected = (Boolean)parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setStatNameList(Spinner spinner){

        ArrayAdapter<StatName> adapter =
                new ArrayAdapter<StatName>(getContext(),  android.R.layout.simple_spinner_dropdown_item, statNames);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                statNameSelected = (StatName)parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_game, container, false);
//        spinnerSuccess = view.findViewById(R.id.gameSpinnerSuccess);
//        spinnerPlayer = view.findViewById(R.id.gameSpinnerPlayer);
//        spinnerStatName = view.findViewById(R.id.gameSpinnerStat);
////      TODO move to game_input fragment  initButton();
//
//        return view;
//    }
    /** TODO Here down should be in game input fragment

    private void initButton() {
        Button statButton = view.findViewById(R.id.inputStatButton);
        statButton.setOnClickListener(v -> {
            createStat(playerSelected, successSelected, statNameSelected);
        });
//        Button button = view.findViewById(R.id.statsDisplayButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle args = new Bundle();
//                args.putSerializable("Player", playerSelected);
//                args.putSerializable("Success", successSelected);
//                args.putSerializable("StatName", statNameSelected);
//                Fragment toFragment = new StatsDisplayFragment();
//                toFragment.setArguments(args);
//
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragmentContainerView2, toFragment, null)
//                        .commit();
//            }
//        });
    }

    public void createStat(Player player, Boolean success, StatName statName) {
        // Fixture , Location, Time, Half
        Stat stat = new Stat();
        stat.setFirstName(player.getFirstname());
        stat.setLastName(player.getLastname());
        stat.setStatName(statName.getName());
        stat.setSuccess(success);

        //TODO handle currentFixture retrieval from repo
        stat.setFixtureDate(currentFixture.getFixtureDate());
        stat.setTimeOccurred(currentTime());
//        stat.setLocation(TODO Fill with location live data);
        stat.setHalf(half(currentTime()));

        persistStat(stat);

    }

    private void persistStat(Stat stat) {
        StatRepository repository = new StatRepository();
        repository.persistStat(stat);
    }

    public int half(String time){
        //TODO check if time is greater than halfway through the match
     //TODO need livedata for half, reset to 0 for every game, when start button clicked -> set half 1,
     //TODO if puased clicked set half 2
        return 1;
    }

    //TODO Get Time since start of game
    public String currentTime(){
        return null;
    }

    // TODO Get Grid Location for button click and set location live data
    public void gridLocation(){
        GridLayout grid = (GridLayout) view.findViewById(R.id.pitchGridLocations);
        int childCount = grid.getChildCount();

        for (int i= 0; i < childCount; i++){
            TextView container = (TextView) grid.getChildAt(i);
            container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    // your click code here
                    //TODO Add an activity or fragment to take in player, stat and success
                    String gridLocationText = container.getText().toString();
                }
            });
        }
    }
     **/

}