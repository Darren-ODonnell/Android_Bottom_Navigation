package com.example.bottomnavigationproper;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Position;
import com.example.bottomnavigationproper.Models.Result;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Models.StatModel;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Models.Teamsheet;
import com.example.bottomnavigationproper.ViewModels.GameViewModel;
import com.example.bottomnavigationproper.Adapters.InGameStatsAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameFragment extends Fragment {

    private GameViewModel viewModel;

    //    private PlayerViewModel playerViewModel;
    private Spinner spinnerPlayer;
    private Teamsheet playerSelected;
    private Teamsheet playerSelected2;

    //    private FixtureViewModel fixtureViewModel;
    private Spinner spinnerSuccess;
    private Boolean successSelected;

    private Spinner spinnerStatName;
    private StatName statNameSelected;

    private Spinner fixtureSpinner;

    private Fixture currentFixture;

    private PausableChronometer chronometer;
    private boolean matchStarted = false;
    private boolean matchInPlay = false;
    private Integer half = 1;

    String homeScore = "", awayScore = "";
    TextView homeScoreTV, awayScoreTV;

    int homeGoals, homePoints, awayGoals, awayPoints;

    View view;

    View mView;
    List<StatsView> statsViews = new ArrayList<>();
    List<StatName> statNames = new ArrayList<>();
    List<Teamsheet> players = new ArrayList<>();
    List<String> successList = new ArrayList<>();
    List<Fixture> fixtures = new ArrayList<>();

    List<StatsView> pregameAnalysisWins = new ArrayList<>();
    boolean winsPopulated = false;
    List<StatsView> pregameAnalysisLosses = new ArrayList<>();
    boolean lossesPopulated = false;

    InGameStatsAdapter adapter = new InGameStatsAdapter();




    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);
        homeScoreTV = view.findViewById(R.id.homeScoreTV);
        awayScoreTV = view.findViewById(R.id.awayScoreTV);
        initStatsSelectionViewModel();
        initPreGameAnalysisButton();

        return view;
    }

    private void showFixtureSelection() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View fView = getLayoutInflater().inflate(R.layout.fixture_selection_fragment, null);
        fixtureSpinner = (Spinner) fView.findViewById(R.id.spinnerFixtureSelection);
        setFixtureList();

        mBuilder.setView(fView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();


        fView.findViewById(R.id.fixtureSelectionButton).setOnClickListener(v -> {
            currentFixture = (Fixture)fixtureSpinner.getSelectedItem();

            TextView home = requireView().findViewById(R.id.homeTeamTV);
            TextView away = requireView().findViewById(R.id.awayTeamTV);

            home.setText(currentFixture.getHomeTeam().getAbbrev());
            away.setText(currentFixture.getAwayTeam().getAbbrev());

            initPlayersAndStats();
            initChronometer();
            dialog.dismiss();

            initPregameAnalysis();
            createPreGameAnalysisSelectionDialog();



        });

    }

    private void initPregameAnalysis() {
        viewModel.getPregameAnalysisLosses().observe(this, new Observer<List<StatsView>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<StatsView> statsViews) {
                pregameAnalysisLosses = statsViews;
                winsPopulated = true;
            }
        });

        viewModel.getPregameAnalysisWins().observe(this, new Observer<List<StatsView>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<StatsView> statsViews) {
                pregameAnalysisWins = statsViews;
                lossesPopulated = true;
                //TODO method call functionally dependant on result of both observers, may need global booleans to handle checks
                displayPreGameAnalysis(pregameAnalysisWins, pregameAnalysisLosses);
            }
        });



    }

    private void createPreGameAnalysisSelectionDialog(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View fView = getLayoutInflater().inflate(R.layout.pregame_analysis_selection, null);


        mBuilder.setView(fView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        fView.findViewById(R.id.current_opponent_stats).setOnClickListener(v -> {
            dialog.dismiss();
            initOppositionGamesAnalysis();
        });
        fView.findViewById(R.id.recent_history_stats).setOnClickListener(v -> {
            dialog.dismiss();
            initRecentGamesAnalysis();
        });
    }
    private void initPreGameAnalysisButton(){
        view.findViewById(R.id.display_pregame_analysis).setOnClickListener(v -> {
            createPreGameAnalysisSelectionDialog();
        });
    }

    private void initOppositionGamesAnalysis(){
        viewModel.getStatsForLossesByOpponent(currentFixture);
        viewModel.getStatsForWinsByOpponent(currentFixture);
    }

    private void initRecentGamesAnalysis() {
        viewModel.getAvgStatsForLastFiveFixturesLost();
        viewModel.getAvgStatsForLastFiveFixturesWon();
    }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void displayPreGameAnalysis(List<StatsView> set1, List<StatsView> set2){
        if(winsPopulated || lossesPopulated){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
            View fView = getLayoutInflater().inflate(R.layout.pregame_analysis, null);

            populateRV(fView, R.id.analysis_rv_1, set1, R.color.green3);
            populateRV(fView, R.id.analysis_rv_2, set2, R.color.red);

            mBuilder.setView(fView);
            AlertDialog dialog = mBuilder.create();
            dialog.show();

            fView.findViewById(R.id.pregame_button).setOnClickListener(v -> {

                lossesPopulated = false;
                winsPopulated = false;
                dialog.dismiss();
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void populateRV(View fView, int rv, List<StatsView> set, int colour){
        RecyclerView recyclerView = (RecyclerView) fView.findViewById(rv);
        InGameStatsAdapter adapter = new InGameStatsAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        List<String> names = set.stream()
                .map(obj -> obj.getStatName())
                .collect(Collectors.toList());

        List<String> percents = set.stream()
                .map(obj -> obj.getCount())
                .collect(Collectors.toList());

        adapter.setResults(names);
        if(!percents.isEmpty())
            adapter.setAverages(percents, Integer.parseInt(percents.get(0)));
        adapter.setColour(getResources().getColor(colour));
        adapter.notifyDataSetChanged();
    }

    private void initChronometer() {
        chronometer = (PausableChronometer)requireView().findViewById(R.id.chronometer);

        ImageButton startStop = requireView().findViewById(R.id.startStop);

        startStop.setOnClickListener(view -> {
            if(UserSingleton.getInstance().isAdminOrCoach()) {

                if(!matchStarted){

                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    matchStarted = true;
                    matchInPlay = true;
                    startStop.setBackgroundResource(R.drawable.ic_baseline_stop);

                }else{
                    chronometer.stop();
                    showStats();
                }
            }else{
                Toast.makeText(getContext(), "This Functionality is limited to Admin and Coach Only", Toast.LENGTH_LONG).show();
            }

        });

            ImageButton playPause = requireView().findViewById(R.id.playPlause);
            playPause.setOnClickListener(view -> {
                if(UserSingleton.getInstance().isAdminOrCoach()) {

                    if (matchInPlay) {
                        chronometer.stop();
                        showStats();
                    } else
                        chronometer.start();
                    if (matchInPlay)
                        playPause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    else
                        playPause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    matchInPlay = !matchInPlay;
                    //Pause button changes half
                    half = (half == 1) ? 2 : 1;
                }else
                    Toast.makeText(getContext(), "This Functionality is limited to Admin and Coach Only", Toast.LENGTH_LONG).show();

            });

    }

    private void showStats() {
        viewModel.getStats(currentFixture);
    }

    private void createStatsDisplayDialog(){
        HashMap<String, List<StatsView>> groupedStats = getGroupedStats(statsViews);

        List<String> statNames = new ArrayList<>();
        List<String> percents = getPercents(groupedStats);

        for(String s: groupedStats.keySet()){

            statNames.add(s);
            List<StatsView> statsViews = groupedStats.get(s);
            Integer success = 0;
            Integer total = 0;
            for(int i = 0; i < Objects.requireNonNull(statsViews).size(); i++){
                total += Integer.parseInt(statsViews.get(i).getCount());
                if(statsViews.get(i).getSuccess()){
                    success += Integer.parseInt(statsViews.get(i).getCount());
                }
            }
            Double percent = (double)success /total * 100;

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            String percentStr = df.format(percent);
            percents.add(percentStr);
        }


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View fView = getLayoutInflater().inflate(R.layout.game_stat_display_dialog, null);

        RecyclerView rv = (RecyclerView) fView.findViewById(R.id.player_favStats_rv);


        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);



        adapter.setResults(statNames);
        adapter.setPercents(percents);
        adapter.setColour(getResources().getColor(R.color.pink1));
        adapter.notifyDataSetChanged();

        mBuilder.setView(fView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        fView.findViewById(R.id.game_stat_button).setOnClickListener(v -> {

            dialog.dismiss();
        });
    }

    public double getSecondsFromDurationString(String value){

        String [] parts = value.split(":");

        // Wrong format, no value for you.
        if(parts.length < 2 || parts.length > 3)
            return 0;

        double seconds = 0.0, minutes = 0.0, hours = 0.0;

        if(parts.length == 2){
            seconds = Double.parseDouble(parts[1]);
            minutes = Double.parseDouble(parts[0]);
        }
        else if(parts.length == 3){
            seconds = Double.parseDouble(parts[2]);
            minutes = Double.parseDouble(parts[1]);
            hours = Double.parseDouble(parts[0]);
        }

        double totalSeconds = seconds + (minutes*60) + (hours*3600);
        return totalSeconds / 60;
    }

    private List<String> getUniqueStats(List<StatsView> statsViews) {
        List<String> uniq = new ArrayList<>();

        for(StatsView statsView : statsViews){
            if(!uniq.contains(statsView.getStatName()))
                uniq.add(statsView.getStatName());

        }
        return uniq;
    }

    private List<String> getPercents(HashMap<String, List<StatsView>> groupedStats) {
        List<String> percents = new ArrayList<>();

        for(String s: groupedStats.keySet()){

            List<StatsView> statsViews = groupedStats.get(s);
            double success = 0;
            double total = 0;
            for(int i = 0; i < Objects.requireNonNull(statsViews).size(); i++){
                StatsView statsView = statsViews.get(i);
                total += Integer.parseInt(statsView.getCount());
                if(statsViews.get(i).getSuccess()){
                    success += Integer.parseInt(statsViews.get(i).getCount());
                }
            }
            double percent = success/total * 100;

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            String percentStr = df.format(percent);
            percents.add(percentStr);
        }
        return percents;
    }

    private HashMap<String, List<StatsView>> getGroupedStats(List<StatsView> statsViews) {
        HashMap<String, List<StatsView>> groupedStats = new HashMap<>();

        for(int i = 0; i < statsViews.size(); i++) {
            StatsView statsView = statsViews.get(i);
            String statName = statsView.getStatName();
            List<StatsView> statsViewList;
            if (groupedStats.containsKey(statName)) {
                statsViewList = groupedStats.get(statName);
            } else {
                statsViewList = new ArrayList<>();
            }
            statsViewList.add(statsView);
            groupedStats.put(statName, statsViewList);
        }

        return groupedStats;

    }


    private void initGridLayoutButtons() {
        GridLayout grid = (GridLayout) view.findViewById(R.id.pitchGridLocations);
//        grid.setBackgroundResource(R.drawable.ic_gaelic_football_pitch_diagram);

        int childCount = grid.getChildCount();
        int playerNo = 0;

        for (int i=0; i < childCount; i++){

            RelativeLayout gridSection;



            gridSection = (RelativeLayout) grid.getChildAt(childCount-1-i);


            if(i != 8){
                populateContents(gridSection, playerNo);

                playerNo++;
            }

            int finalI = i;
            gridSection.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if(UserSingleton.getInstance().isAdminOrCoach()) {

                                // your click code here
                            mView = getLayoutInflater().inflate(R.layout.fragment_game_input, null);
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                            initVoiceRecognition();

                            AlertDialog dialog = createInputDialog(mView);

                            mView.findViewById(R.id.inputStatButton).setOnClickListener(v -> {
                                createStat(finalI);
                                speechRecognizer.destroy();
                                dialog.dismiss();
                            });

                        }else
                            Toast.makeText(getContext(), "This Functionality is limited to Admin and Coach Only", Toast.LENGTH_LONG).show();


                    }
                });
           }
    }

//    private void checkStatForScoreOpposition(StatModel stat) {
//        switch (stat.getStatNameId().toLowerCase(Locale.ROOT)){
//            case "scg":
//                awayGoals++;
//                break;
//            case "scpo":
//                awayPoints++;
//            case "fs":
//                if(stat.getSuccess())
//                    awayGoals++;
//                else
//                    awayPoints++;
//        }
//        updateScore();
//
//    }

    private void updateScore(){
//        String homeScore = homeGoals+ ":" +homePoints;
//        String awayScore = awayGoals+ ":" +awayPoints;

        TextView homeScoreTV = requireView().findViewById(R.id.homeScoreTV);
        TextView awayScoreTV = requireView().findViewById(R.id.awayScoreTV);

        homeScoreTV.setText(homeScore);
        awayScoreTV.setText(awayScore);

    }

//    private void checkStatForScoreHome(StatModel stat) {
//        switch (stat.getStatNameId().toLowerCase(Locale.ROOT)){
//            case "scg":
//                homeGoals++;
//                break;
//            case "scpo":
//                homePoints++;
//            case "fs":
//                if(stat.getSuccess())
//                    homeGoals++;
//                else
//                    homePoints++;
//        }
//        updateScore();
//
//    }

    private void populateContents(RelativeLayout gridSection, int playerNo) {
        TextView numTV = (TextView) gridSection.getChildAt(1);
        TextView nameTV = (TextView) gridSection.getChildAt(2);

        Teamsheet teamsheet = players.get(playerNo);

        String name = teamsheet.getPlayer().getAbbrevName();
        nameTV.setText(name);
        //TODO playerno on pitch doesn't update
        String num = Long.toString(players.get(playerNo).getJerseyNumber());
        numTV.setText(num);
    }

    private void createStat(int gridIndex) {
        List<String> gridLocations = new ArrayList<>(Arrays.asList("","A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3", "D1", "D2", "D3", "E1", "E2", "E3"));

        if(statNameSelected.getName().equals("Substitute")){
            //TODO finish substitute stat
            Position temp = playerSelected.getPosition();
            playerSelected.setPosition(playerSelected2.getPosition());
            playerSelected2.setPosition(temp);
            List<Teamsheet> subsToChange = new ArrayList<>();
            subsToChange.add(playerSelected);
            subsToChange.add(playerSelected2);

            viewModel.updateTeamsheet(subsToChange);
        }else {
            StatModel stat = new StatModel();
            stat.setLocationId(gridLocations.get(gridIndex));
            stat.setPlayerId(playerSelected.getPlayer().getId());
            stat.setStatNameId(statNameSelected.getId());
            stat.setSuccess(successSelected);
            stat.setHalf(half);
            stat.setFixtureId(currentFixture.getId());
            String time = parseTime();
            stat.setTimeOccurred(time);

            viewModel.persistStat(TokenSingleton.getInstance().getBearerTokenString(), stat, currentFixture);
        }


    }

    private String parseTime() {
        String timeTxt = chronometer.getText().toString();
        Double time = getSecondsFromDurationString(timeTxt);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(time);
    }

    private AlertDialog createInputDialog( View mView) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        Spinner successSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerSuccess);
        setSuccessList(successSpinner);
        Spinner statSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerStat);
        setStatNameList(statSpinner);
        Spinner playerSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerPlayer);
        setPlayerList(playerSpinner);

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        return dialog;

    }

    private void initStatsSelectionViewModel(){
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);
        viewModel.init();
        viewModel.getFixturesResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<Fixture>>(){
            @Override
            public void onChanged(List<Fixture> fixtureList) {
                if (fixtureList != null) {
                    //TODO Get current fixture for populating Home Away team sections, return single fixture
                    fixtures = fixtureList;
                    showFixtureSelection();
                }
            }
        });


        viewModel.getStatNameLiveData().observe(getViewLifecycleOwner(), new Observer<List<StatName>>() {
            @Override
            public void onChanged(List<StatName> statNameList) {
                if(statNameList != null){
                    statNames = statNameList;
                }
            }
        });

        viewModel.getScoreLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                homeScore = result.getHomeScore();
                awayScore = result.getAwayScore();
                homeScoreTV.setText(homeScore);
                awayScoreTV.setText(awayScore);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.getStatNames();
        viewModel.getFixtures();




    }

    public void initPlayersAndStats(){
        viewModel.getTeamsheetResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<Teamsheet>>() {
            @Override
            public void onChanged(List<Teamsheet> playerList) {
                if(playerList != null){
                    players = playerList;
                    Teamsheet teamsheet = new Teamsheet();
                    Player player = new Player();
                    player.setId(80L);
                    player.setFirstname("");
                    player.setLastname("Opposition");

                    teamsheet.setPlayer(player);

                    players.add(teamsheet);

                    initGridLayoutButtons();
                }
            }
        });

        viewModel.getStatsLiveData().observe(getViewLifecycleOwner(), new Observer<List<StatsView>>() {
            @Override
            public void onChanged(List<StatsView> statsViewList) {
                if(statsViewList != null){
                    statsViews = statsViewList;
                    viewModel.getScore(currentFixture);
                    adapter.notifyDataSetChanged();
                    if(!matchInPlay){
                        createStatsDisplayDialog();

                    }
                }
            }
        });

        viewModel.getTeamsheets(currentFixture);

    }

    private void setFixtureList() {
        ArrayAdapter<Fixture> adapter =
                new ArrayAdapter<Fixture>(getContext(),  R.layout.fixture_spinner_item, fixtures);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        fixtureSpinner.setAdapter(adapter);

        fixtureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                currentFixture = (Fixture)parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void setPlayerList(Spinner spinner){

        ArrayAdapter<Teamsheet> adapter =
                new ArrayAdapter<Teamsheet>(getContext(),  R.layout.fixture_spinner_item, players);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                playerSelected = (Teamsheet) parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void set2ndPlayerList(Spinner spinner){

        ArrayAdapter<Teamsheet> adapter =
                new ArrayAdapter<Teamsheet>(getContext(),  R.layout.fixture_spinner_item, players);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                playerSelected2 = (Teamsheet) parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    public void setSuccessList(Spinner spinner){
//        List<Boolean> options = new ArrayList<>();
//        options.add(0, Boolean.TRUE);
//        options.add(0, Boolean.FALSE);
        successList = Dictionaries.getInstance().getSuccess();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(),  R.layout.fixture_spinner_item, successList);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = (String)parent.getItemAtPosition(pos);
                successSelected = Dictionaries.getInstance().getSuccessBoolean(selected);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setStatNameList(Spinner spinner){

        ArrayAdapter<StatName> adapter =
                new ArrayAdapter<StatName>(getContext(),  R.layout.fixture_spinner_item, statNames);
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

//    private EditText editText;
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private ImageView micButton;


    public void initVoiceRecognition(){
        if(ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }


        micButton = mView.findViewById(R.id.button);
//        editText = mView.findViewById(R.id.text);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
//                editText.setText("");
//                editText.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResults(Bundle bundle) {
                Dictionaries dict = Dictionaries.getInstance();
                micButton.setImageResource(R.drawable.ic_mic_black_off);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String dataStr = data.get(0);
                Log.d("speechResult", dataStr);
//                int num = validateInput(dataStr);
//                Log.d("speechResult", Integer.toString(num));

                String[] words = dataStr.split(" ");

               String player = getPlayerNLP(words[0]);


                Spinner playerSpinner = mView.findViewById(R.id.gameSpinnerPlayer);

                String finalPlayer = player;
                Teamsheet matchingTeamsheet = players.stream()
                        .filter(t -> t.getJerseyNumber() == Integer.parseInt(finalPlayer))
                        .findFirst()
                        .orElse(null);

                playerSpinner.setSelection(players.indexOf(matchingTeamsheet));

                String word = words[1];
                if (words.length > 3){
                    word += words[2];
                    Log.d("speechResult", "word " + word);

                }

                String stat = dict.getStatNames()
                        .get(
                                validateInput(word, dict.getStatNames())
                        );

                if(stat.equals("Substitute")){
                    String player2 = getPlayerNLP(words[words.length - 1]);


                    // Replace the success spinner with a second spinner
                    Spinner substituteSpinner = mView.findViewById(R.id.gameSpinnerSuccess);
                    set2ndPlayerList(substituteSpinner);

                    String finalPlayer2 = player2;
                    Teamsheet matchingTeamsheet2 = players.stream()
                            .filter(t -> t.getJerseyNumber() == Integer.parseInt(finalPlayer2))
                            .findFirst()
                            .orElse(null);

                    playerSpinner.setSelection(players.indexOf(matchingTeamsheet));
                    substituteSpinner.setSelection(players.indexOf(matchingTeamsheet2));


                }else {
                    Spinner successSpinner = mView.findViewById(R.id.gameSpinnerSuccess);
                    setSuccessList(successSpinner);
                    String success = dict.getSuccess()
                            .get(
                                    validateInput(words[words.length - 1], dict.getSuccess())
                            );
                    successSpinner.setSelection(successList.indexOf(success));
                }



                Spinner statSpinner = mView.findViewById(R.id.gameSpinnerStat);

                List<String> statNameStrings = new ArrayList<>();
                for(StatName statName: statNames){
                    statNameStrings.add(statName.toString());
                }

                statSpinner.setSelection(statNameStrings.indexOf(stat));





                // getSoundexMatch returns the highest difference value

                // getWord returns the most accurate match

//                if(soundex == 0){
//                    getSoundexMatch(words[0] + words[1]);
//                }else{
//                    getWord(statNames, words[0]);
//                }
//                if(num != -1){
//                    onSpeechInput(num);
//                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        micButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    micButton.setImageResource(R.drawable.ic_mic_black_off);
                    speechRecognizer.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    micButton.setImageResource(R.drawable.ic_mic_black_on);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getPlayerNLP(String word) {
        Dictionaries dict = Dictionaries.getInstance();
        HashMap<String, String> numMap = dict.getNumMap();
        String playerNum;
        try{
            playerNum = Integer.toString(Integer.parseInt(word));
        }catch (NumberFormatException e){


            playerNum = dict.getAllNums()
                    .get(
                            validateInput(word, dict.getAllNums())
                    );

        }

        return numMap.get(playerNum);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void getWords(){
//        Dictionaries dict = Dictionaries.getInstance();
//        int successIndex = validateInput(successSelected, dict.getSuccess());
//        int playerIndex = validateInput(playerSelected, dict.getPlayerNumbers());
//        int statIndex = validateInput(statSelected, dict.getStatNames());
//
//        String success = dict.getSuccess().get(successIndex);
//        String player = dict.getPlayerNumbers().get(playerIndex);
//
//        //TODO Get player as index from teamsheet or playerlist
//        String stat = dict.getStatNames().get(statIndex);
//        createStatFromInput(playerIndex, statIndex, successIndex);
//    }
//
//    private void createStatFromInput(int playerIndex, int statIndex, int successIndex) {
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int validateInput(String dataStr, List<String> dataset) {
        Soundexer soundexer = new Soundexer();

        HashMap<Integer, List<String>> response = soundexer.getWordRatings(dataStr, dataset);
        int max = Collections.max(response.keySet());
        List<String> words = response.get(max);
        String word = words.get(0);
        Log.d("speechResult", word);

        int position = dataset.indexOf(word);
        return position;
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }


    @Override
    public void onStop() {

        viewModel.getFixturesResponseLiveData().removeObservers(this);
        viewModel.getStatNameLiveData().removeObservers(this);
        viewModel.getScoreLiveData().removeObservers(this);
        viewModel.getTeamsheetResponseLiveData().removeObservers(this);
        viewModel.getStatsLiveData().removeObservers(this);
        super.onStop();
    }
}