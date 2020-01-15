package com.example.skibslogapp;

import android.util.Log;


import com.example.skibslogapp.model.Logpunkt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DbTranslator {

    /*
    This class will temporarily emulate obtaining data about 'etapper' from the database, and turn them into
    a 2D array which, through the use of public methods, be obtained from the class.
     */

    List<List<Logpunkt>> etappeLogs = new ArrayList<>();


    void makeList(){
        List<Logpunkt> logOne = new ArrayList<>();
        //logOne.add(new Logpunkt("1", "1", "1", "1", "1", "1"));


        List<Logpunkt> logTwo = new ArrayList<>();
        //logOne.add(new Logpunkt("2", "2", "2", "2", "2", "2"));

        List<Logpunkt> logThree = new ArrayList<>();
        //logOne.add(new Logpunkt("3", "3", "3", "3", "3", "3"));

        etappeLogs.add(logOne);
        etappeLogs.add(logTwo);
        etappeLogs.add(logThree);
    }

    public List<List<Logpunkt>> getEtappeLogs(){
        return etappeLogs;
    }

}
