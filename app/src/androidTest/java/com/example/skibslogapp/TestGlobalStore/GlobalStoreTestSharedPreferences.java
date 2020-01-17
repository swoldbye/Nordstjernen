package com.example.skibslogapp.TestGlobalStore;

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
    private TESTTogtDAO TestListTogtDAO;
    private TESTEtapeDAO TestListEtapeDAO;

    private Context context;


    public void init(){
        this.context = InstrumentationRegistry.getInstrumentation().getTargetContext();

            pref = context.getSharedPreferences("SharedPrefC3", context.MODE_PRIVATE);//Mode private so that no other application can access these data.
        /*
        Using these instead of SQL
         */
        TestListTogtDAO = new TESTTogtDAO();
        TestListEtapeDAO = new TESTEtapeDAO();



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

        List<Togt> allTogter = TestListTogtDAO.getTogter();

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

  public Etape getEtape(Togt togt){
        long etapeId = pref.getLong(TOGT_TAG,-1);

        List<Etape> allEtaper = TestListEtapeDAO.getEtape(togt);

        for (Etape a : allEtaper) {
            if (a.getId() == etapeId) {
                return a;
            }
        }

        //If the togt do not exist
        Etape defaultReturnEtape = new Etape();

        return defaultReturnEtape;

    }



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




    @Test
    public void storeEtapeTest() {
        init();
        Togt testTogt = TestListTogtDAO.getTogter().get(0);

        Etape unittestEtape = TestListEtapeDAO.getEtape(testTogt).get(0);
        storeEtappe(unittestEtape.getId());


        Etape returnEtape = getEtape(testTogt);

        assertEquals(unittestEtape.getId(),returnEtape.getId());
        assertEquals(unittestEtape.getTogtId(),returnEtape.getTogtId());

    }




}
