package com.example.skibslogapp;

import android.util.Log;

import com.example.skibslogapp.model.LogInstans;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DbTranslator {

    /*
    This class will temporarily emulate obtaining data about 'etapper' from the database, and turn them into
    a 2D array which, through the use of public methods, be obtained from the class.
     */

    List<List<LogInstans>> etappeLogs = new ArrayList<>();


    void makeList(){
        List<LogInstans> logOne = new ArrayList<>();
        logOne.add(new LogInstans("1", "1", "1", "1", "1", "1"));


        List<LogInstans> logTwo = new ArrayList<>();
        logOne.add(new LogInstans("2", "2", "2", "2", "2", "2"));

        List<LogInstans> logThree = new ArrayList<>();
        logOne.add(new LogInstans("3", "3", "3", "3", "3", "3"));

        etappeLogs.add(logOne);
        etappeLogs.add(logTwo);
        etappeLogs.add(logThree);
    }

    public List<List<LogInstans>> getEtappeLogs(){
        return etappeLogs;
    }

}
