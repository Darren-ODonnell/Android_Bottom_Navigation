package com.example.bottomnavigationproper;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class GameFragment extends Fragment {

    private GameViewModel viewModel;

    //    private PlayerViewModel playerViewModel;
    private Spinner spinnerPlayer;
    private Player playerSelected;

    //    private FixtureViewModel fixtureViewModel;
    private Spinner spinnerSuccess;
    private Boolean successSelected;

    private Spinner spinnerStatName;
    private StatName statNameSelected;

    private Fixture currentFixture;

    View view;

    View mView;
    List<StatName> statNames = new ArrayList<>();
    List<Player> players = new ArrayList<>();
    List<String> successList = new ArrayList<>();




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
//        showFixtureSelection();
        initStatsSelectionViewModel();
        //TODO showFixtureSelection()
        initGridLayoutButtons(view);

        return view;
    }

//    private void showFixtureSelection() { TODO finish this method
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//        View mView = getLayoutInflater().inflate(R.layout.fixture_selection_fragment, null);
//        mBuilder.setTitle("Select Fixture");
//        Spinner fixtureSpinner = (Spinner) mView.findViewById(R.id.spinnerFixtureSelection);
//        setFixtureList(fixtureSpinner);
//        Spinner statSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerStat);
//        setStatNameList(statSpinner);
//        Spinner playerSpinner = (Spinner) mView.findViewById(R.id.gameSpinnerPlayer);
//        setPlayerList(playerSpinner);
//
//        mBuilder.setView(mView);
//        AlertDialog dialog = mBuilder.create();
//        dialog.show();
//
//    }

    private void initGridLayoutButtons(View view) {
        GridLayout grid = (GridLayout) view.findViewById(R.id.pitchGridLocations);
        int childCount = grid.getChildCount();

        for (int i= 0; i < childCount; i++){
            TextView gridSection = (TextView) grid.getChildAt(i);
            int finalI = i;
            gridSection.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    // your click code here
                    mView = getLayoutInflater().inflate(R.layout.fragment_game_input, null);
                    initVoiceRecognition();

                    createInputDialog(mView);

                    mView.findViewById(R.id.inputStatButton).setOnClickListener(v -> {
                        createStat(finalI);
                        speechRecognizer.destroy();
                        //TODO Persist stat
                    });

                }
            });
        }
    }

    private Stat createStat(int gridIndex) {
        List<String> gridLocations = new ArrayList<>(Arrays.asList("A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3", "D1", "D2", "D3", "E1", "E2", "E3"));

        Stat stat = new Stat();
        stat.setLocation(gridLocations.get(gridIndex));
        stat.setFirstName(playerSelected.getFirstname());
        stat.setLastName(playerSelected.getLastname());
        stat.setStatName(statNameSelected.getName());
        stat.setSuccess(successSelected);
        stat.setFixtureDate(currentFixture.getFixtureDate());
        return stat;

    }

    private void createInputDialog( View mView) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
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
//        List<Boolean> options = new ArrayList<>();
//        options.add(0, Boolean.TRUE);
//        options.add(0, Boolean.FALSE);
        successList = Dictionaries.getInstance().getSuccess();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, successList);
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

    private EditText editText;
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private ImageView micButton;


    public void initVoiceRecognition(){
        if(ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }


        micButton = mView.findViewById(R.id.button);
        editText = mView.findViewById(R.id.text);
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
                editText.setText("");
                editText.setHint("Listening...");
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

                HashMap<String, String> numMap = dict.getNumMap();

                String playerNum = dict.getAllNums()
                        .get(
                            validateInput(words[0], dict.getAllNums())
                );

                String player = numMap.get(playerNum);

//                String player = dict.getNumMap()
//                        .get(
//                                Integer.toString(
//                                        validateInput()
//                                )
//                        );
                String stat = dict.getStatNames()
                        .get(
                                validateInput(words[1], dict.getStatNames())
                        );
                String success = dict.getSuccess()
                        .get(
                                validateInput(words[2], dict.getSuccess())
                        );

                Spinner playerSpinner = mView.findViewById(R.id.gameSpinnerPlayer);
                playerSpinner.setSelection(Integer.parseInt(player));

                Spinner statSpinner = mView.findViewById(R.id.gameSpinnerStat);

                List<String> statNameStrings = new ArrayList<>();
                for(StatName statName: statNames){
                    statNameStrings.add(statName.toString());
                }

                statSpinner.setSelection(statNameStrings.indexOf(stat));

                Spinner successSpinner = mView.findViewById(R.id.gameSpinnerSuccess);
                successSpinner.setSelection(successList.indexOf(success));



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

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
//            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
//        }
//    }
//    private ActivityResultLauncher<String[]> activityResultLauncher;
//    public GameFragment() {
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
//            @Override
//            public void onActivityResult(Map<String, Boolean> result) {
//                Log.e("activityResultLauncher", ""+result.toString());
//                Boolean areAllGranted = true;
//                for(Boolean b : result.values()) {
//                    areAllGranted = areAllGranted && b;
//                }
//
//                if(areAllGranted) {
//                    capturePhoto();
//                }
//            }
//        });
//    }

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