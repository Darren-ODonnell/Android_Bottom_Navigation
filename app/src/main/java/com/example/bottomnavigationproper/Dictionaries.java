package com.example.bottomnavigationproper;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Dictionaries {

    private static Dictionaries instance;

    private List<String> playerNumbers;
    private List<String> playerNumbersStrings;
    private List<String> allNums = new ArrayList<>();
    private HashMap<String, String> numMap = new HashMap<>();
    private List<String> statNames;
    private List<String> success;
    private List<String> successTrue;
    private List<String> successFalse;
    private List<String> onePlusTwo = new ArrayList<>();
    private List<String> twoPlusThree = new ArrayList<>();
    private List<String> allThree = new ArrayList<>();

    public static Dictionaries getInstance(){
        instance = (instance == null)? new Dictionaries(): instance;
        instance.loadDictionaries();
        return instance;
    }

    public Boolean getSuccessBoolean(String selected) {
        if(successTrue.contains(selected))
            return true;
        else
            return false;
    }

    public void loadDictionaries() {
        // player numbers
        playerNumbers = new ArrayList<>();
        for(int i = 1; i <= 30; i++){
            playerNumbers.add(Integer.toString(i));
        }
        playerNumbersStrings = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine","ten",
                "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen","twenty",
                "twentyone", "twentytwo", "twentythree","twentyfour", "twentyfive", "twentysix", "twentyseven", "twentyeight", "twentynine", "thirty", "Opposition");

        allNums.addAll(playerNumbers);
        allNums.addAll(playerNumbersStrings);

        for(int i = 1; i <= playerNumbers.size(); i++){
            numMap.put(playerNumbers.get(i-1), Integer.toString(i));
            numMap.put(playerNumbersStrings.get(i-1), Integer.toString(i));
        }
        numMap.put(playerNumbersStrings.get(playerNumbers.size()), "30");
        // stat names
        statNames = Arrays.asList("Block","Catch","FreePass","FreeScore","Hook","Handpass",
                "PuckOut","PuckOutOpp","Pass","Ruck","Save","Goal","Point",
                "Solo","Substitute");

        // success
        success = Arrays.asList("true","false","win","loss","On", "Off", "Won", "Lost", "Goal", "Point");
        successTrue = Arrays.asList("true","win","On", "Won", "Goal");
        successFalse = Arrays.asList("false", "loss", "Off", "Lost", "Point");

        // combining first and second words
        for (String number : playerNumbers)
            for (String statName : statNames)
                onePlusTwo.add(number + statName);


        // combining 2nd two
        for (String statName : statNames)
            for (String s : success)
                twoPlusThree.add(statName + s);


        // combining all three
        for ( String number :  playerNumbers )
            for ( String statName : statNames )
                for ( String success : success )
                    allThree.add(number + statName + success );


    }

    public List<String> getAllNums() {
        return allNums;
    }

    public void setAllNums(List<String> allNums) {
        this.allNums = allNums;
    }

    public HashMap<String, String> getNumMap() {
        return numMap;
    }

    public void setNumMap(HashMap<String, String> numMap) {
        this.numMap = numMap;
    }

    public List<String> getPlayerNumbers() {
        return playerNumbers;
    }

    public List<String> getPlayerNumbersStrings() {
        return playerNumbersStrings;
    }

    public List<String> getStatNames() {
        return statNames;
    }

    public List<String> getSuccess() {
        return success;
    }

    public List<String> getOnePlusTwo() {
        return onePlusTwo;
    }

    public List<String> getTwoPlusThree() {
        return twoPlusThree;
    }

    public List<String> getAllThree() {
        return allThree;
    }

    public List<Integer> getColours() {
        return Arrays.asList(
                0xFFCDEFAB, 0xFFABCDEF, 0xFFf23456, 0xFFf12345, 0xFFf5bc42, 0xFFc5f542, 0xFF42f557, 0xFF42bff5, 0xFF4287f5, 0xFF4245f5, 0xFF8a42f5, 0xFFd742f5, 0xFFf542cb, 0xFFf5428d, 0xFFf54248
        );
    }
}
