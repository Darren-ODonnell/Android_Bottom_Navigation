//package com.example.bottomnavigationproper;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.speech.RecognitionListener;
//import android.speech.RecognizerIntent;
//import android.speech.SpeechRecognizer;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.example.bottomnavigationproper.Models.Fixture;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//
//public class AudioRecogniser {
//
//    public static final Integer RecordAudioRequestCode = 1;
//    private SpeechRecognizer speechRecognizer;
//    private ImageView micButton;
//
//
//    public void initVoiceRecognition(){
//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
//            checkPermission();
//        }
//
//
//        micButton = findViewById(R.id.button);
//        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
//
//        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//
//        speechRecognizer.setRecognitionListener(new RecognitionListener() {
//            @Override
//            public void onReadyForSpeech(Bundle bundle) {
//
//            }
//
//            @Override
//            public void onBeginningOfSpeech() {
//                editText.setText("");
//                editText.setHint("Listening...");
//            }
//
//            @Override
//            public void onRmsChanged(float v) {
//
//            }
//
//            @Override
//            public void onBufferReceived(byte[] bytes) {
//
//            }
//
//            @Override
//            public void onEndOfSpeech() {
//
//            }
//
//            @Override
//            public void onError(int i) {
//
//            }
//
//            @Override
//            public void onResults(Bundle bundle) {
//                micButton.setImageResource(R.drawable.ic_mic_black_off);
//                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//                String dataStr = data.get(0);
//                Log.d("speechResult", dataStr);
//                int num = validateInput(dataStr);
//                Log.d("speechResult", Integer.toString(num));
//
//                String[] words = dataStr.split(" ");
//
//                int soundex = getSoundexMatch(words[0]);
//
//                // getSoundexMatch returns the highest difference value
//
//                // getWord returns the most accurate match
//
//                if(soundex == 0){
//                    getSoundexMatch(words[0] + words[1]);
//                }else{
//                    getWord(statNames, words[0]);
//                }
//                if(num != -1){
//                    onSpeechInput(num);
//                }
//            }
//
//            @Override
//            public void onPartialResults(Bundle bundle) {
//
//            }
//
//            @Override
//            public void onEvent(int i, Bundle bundle) {
//
//            }
//        });
//
//        micButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                    speechRecognizer.stopListening();
//                }
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
//                    micButton.setImageResource(R.drawable.ic_mic_black_on);
//                    speechRecognizer.startListening(speechRecognizerIntent);
//                }
//                return false;
//            }
//        });
//
//
//    }
//
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
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private int validateInput(String dataStr, List<String> dataset) {
//        Soundexer soundexer = new Soundexer();
//
//        HashMap<Integer, List<String>> response = soundexer.getWordRatings(dataStr, dataset);
//        int max = Collections.max(response.keySet());
//        List<String> words = response.get(max);
//        String word = words.get(0);
//        Log.d("speechResult", word);
//
//        int position = dataset.indexOf(word);
//        return position;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        speechRecognizer.destroy();
//    }
//
//    private void checkPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
//            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//}
