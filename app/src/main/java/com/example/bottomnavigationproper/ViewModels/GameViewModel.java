package com.example.bottomnavigationproper.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.Models.Player;
import com.example.bottomnavigationproper.Models.Result;
import com.example.bottomnavigationproper.Models.StatModel;
import com.example.bottomnavigationproper.Models.StatsView;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.Models.Teamsheet;
import com.example.bottomnavigationproper.Services.FixtureRepository;
import com.example.bottomnavigationproper.Services.PlayerRepository;
import com.example.bottomnavigationproper.Services.StatRepository;
import com.example.bottomnavigationproper.Services.TeamsheetRepository;

import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private FixtureRepository fixtureRepository;
    private LiveData<List<Fixture>> fixtureResponseLiveData;

    private TeamsheetRepository teamsheetRepository;
    private LiveData<List<Teamsheet>> teamsheetResponseLiveData;

    private StatRepository statRepository;
    private LiveData<List<StatName>> statNameLiveData;
    private LiveData<Result> scoreLiveData;

    private LiveData<List<StatsView>> statsLiveData;
    private LiveData<List<StatsView>> pregameAnalysisWinsLiveData;
    private LiveData<List<StatsView>> pregameAnalysisLossesLiveData;

    public GameViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        fixtureRepository = new FixtureRepository();
        fixtureResponseLiveData = fixtureRepository.getFixturesResponseLiveData();

        teamsheetRepository = new TeamsheetRepository();
        teamsheetResponseLiveData = teamsheetRepository.getTeamsheetsResponseLiveData();

        statRepository = new StatRepository();
        statNameLiveData = statRepository.getStatNameLiveData();
        statsLiveData = statRepository.getStatsResponseLiveData();

        scoreLiveData = statRepository.getScoreLiveData();

        pregameAnalysisWinsLiveData = statRepository.getPregameWinAnalysisLiveData();
        pregameAnalysisLossesLiveData = statRepository.getPregameLossAnalysisLiveData();
    }

    public void getFixtures() {
        fixtureRepository.getFixtures(TokenSingleton.getInstance().getBearerTokenString());
    }

    public void getTeamsheets(Fixture fixture) {
        teamsheetRepository.getTeamsheetsForFixture(fixture, TokenSingleton.getInstance().getBearerTokenString());
    }

    public void getStatNames(){
        statRepository.getStatNames(TokenSingleton.getInstance().getBearerTokenString());
    }

    public void getStats(Fixture fixture){
        statRepository.countAllPlayerStatNameByFixtureDateGroupSuccess(TokenSingleton.getInstance().getBearerTokenString(), fixture.getFixtureDate());
    }

    public void getScore(Fixture fixture){
        statRepository.getScoreForFixture(TokenSingleton.getInstance().getBearerTokenString(), fixture.getFixtureDate());
    }

    public void getStatsForLossesByOpponent(Fixture fixture){
        String opposition = (fixture.getHomeTeam().getName().equals("St Judes"))
                ? fixture.getAwayTeam().getName()
                : fixture.getHomeTeam().getName();
        statRepository.getAvgStatsForLossesByOpponent(TokenSingleton.getInstance().getBearerTokenString(), opposition);
    }

    public void getStatsForWinsByOpponent(Fixture fixture){
        String opposition = (fixture.getHomeTeam().getName().equals("St Judes"))
                ? fixture.getAwayTeam().getName()
                : fixture.getHomeTeam().getName();
        statRepository.getAvgStatsForWinsByOpponent(TokenSingleton.getInstance().getBearerTokenString(), opposition);
    }
    public void getAvgStatsForLastFiveFixturesLost(){
        statRepository.getAvgStatsForLastFiveFixturesLost(TokenSingleton.getInstance().getBearerTokenString());
    }

    public void getAvgStatsForLastFiveFixturesWon(){
        statRepository.getAvgStatsForLastFiveFixturesWon(TokenSingleton.getInstance().getBearerTokenString());
    }


    public LiveData<Result> getScoreLiveData() {
        return scoreLiveData;
    }

    public LiveData<List<Fixture>> getFixturesResponseLiveData() {
        return fixtureResponseLiveData;
    }
    public LiveData<List<Teamsheet>> getTeamsheetResponseLiveData() {
        return teamsheetResponseLiveData;
    }
    public LiveData<List<StatName>> getStatNameLiveData(){ return statNameLiveData;}
    public LiveData<List<StatsView>> getStatsLiveData(){ return statsLiveData;}
    public LiveData<List<StatsView>> getPregameAnalysisWins(){ return pregameAnalysisWinsLiveData;}
    public LiveData<List<StatsView>> getPregameAnalysisLosses(){ return pregameAnalysisLossesLiveData;}




    public void persistStat(String bearerTokenString, StatModel stat, Fixture fixture) {
        statRepository.persistStat(bearerTokenString, stat, fixture.getFixtureDate());
        getStats(fixture);
    }

    public void updateTeamsheet(List<Teamsheet> subsToChange){
        teamsheetRepository.update(TokenSingleton.getInstance().getBearerTokenString(), subsToChange);
    }
}
