package com.example.skibslogapp;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.platform.app.InstrumentationRegistry;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class GlobalStoreTestSharedPreferences {


    private long togtid;
    private SharedPreferences pref;
    private final String TOGT_TAG = "UnitTest_Current Togt id";
    private final String ETAPPE_TAG = "UnitTest_Current Etappe ID";
    private List<Togt> TestListTogtDAO;
    private List<Etape> TestListEtapeDAO;

    private Context context;


    public void init(){
        this.context = InstrumentationRegistry.getInstrumentation().getTargetContext();

            pref = context.getSharedPreferences("SharedPrefC3", context.MODE_PRIVATE);//Mode private so that no other application can access these data.
        /*
        Using these instead of SQL
         */
        TestListTogtDAO = new ArrayList<>();
        TestListEtapeDAO = new ArrayList<>();

        Togt test1 = new Togt("Test 1");
        test1.setSkipper("skipper1");
        test1.setId(1);

        Togt test2 = new Togt("Test 2");
        test2.setSkipper("skipper2");
        test2.setId(2);

        Togt test3 = new Togt("Test 3");
        test3.setSkipper("skipper3");
        test3.setId(3);

        TestListTogtDAO.add(test1);
        TestListTogtDAO.add(test2);
        TestListTogtDAO.add(test3);

    }

    public void storeTogt(long togtId) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(TOGT_TAG, togtId);
        editor.apply();
    }

    public void storeEtappe(long etapeId){
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(ETAPPE_TAG, etapeId);
        editor.apply();
    }

    public Togt getTogt(){
        togtid = pref.getLong(TOGT_TAG,-1);
        System.out.println("Togt id: "+togtid);


        //Searching for the togt

        List<Togt> allTogter = TestListTogtDAO;

        for (Togt a : allTogter) {
            if (a.getId() == togtid) {
                return a;
            }
        }

        //If the togt do not exist
        Togt defaultReturnTogt = new Togt("Default");
        defaultReturnTogt.setSkipper("default skipper");

        return defaultReturnTogt;
    }

  /*  public Etape getEtape(Togt togt){
        long etapeId = pref.getLong(TOGT_TAG,-1);

        List<Etape> allEtaper = etapeDatabase.getEtaper(togt);

        for (Etape a : allEtaper) {
            if (a.getId() == etapeId) {
                return a;
            }
        }

        //If the togt do not exist
        Etape defaultReturnEtape = new Etape();

        return defaultReturnEtape;

    }
    */


  /*
  Testing if the method is returning the right togt when finding the currentTogt in the sharedPreferences.
   */

    @Test
    public void storeTogtTest(){

        init();
        Togt unitTest1 = new Togt("Test 1");
        unitTest1.setSkipper("skipper1");
        unitTest1.setId(1);
        storeTogt(unitTest1.getId());


        /*
        Retunrn the current Togt
         */
        Togt RTunitTest1 = getTogt();

        assertEquals(unitTest1.getId(),RTunitTest1.getId());
        assertEquals(unitTest1.getName(),RTunitTest1.getName());
        assertEquals(unitTest1.getSkipper(),RTunitTest1.getSkipper());


    }


}
