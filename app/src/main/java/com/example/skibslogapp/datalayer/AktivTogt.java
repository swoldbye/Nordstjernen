package com.example.skibslogapp.datalayer;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.skibslogapp.datalayer.local.EtapeDAO;

import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;

import java.util.List;


/**
 * Class to statically store the 'current' Togt and Etape.
 *
 * The data will be stored in shared preferences, so they're kept
 * if you close the app.
 */
public class AktivTogt extends ViewModel {

    //Using mutable livedata so that we can have public methods for updating values.
    private static Etape etape;
    private static Togt togt;
    private static MutableLiveData<Togt> currentTogt;     //From Togt
    private static MutableLiveData<Etape> currentEtape; //From Etape

    private static SaveLocal DBpref;

    /** Set the current Etape */
    public static void setEtape(Etape etape) {
        AktivTogt.etape = etape;
        DBpref.storeEtappe(etape.getId());
        if(currentEtape == null){
            currentEtape = new MutableLiveData<Etape>();
        }
        currentEtape.setValue(etape);
    }

    /** Get the current Togt */
    public static void setTogt(Togt togt) {
        AktivTogt.togt = togt;
        DBpref.storeTogt(togt.getId());
        if(currentTogt == null){
            currentTogt = new MutableLiveData<Togt>();
        }
        currentTogt.setValue(togt);
    }

    /** Returns the current Togt */
    public static Togt getTogt(){
        if(togt == null){
            togt = DBpref.getTogt();
        }
        return togt;
    }

    /** Returns the current Etape */
    public static Etape getEtape(){
        if(etape == null){
            togt = DBpref.getTogt();
            etape = DBpref.getEtape(togt);  }
        return etape;
    }


    @Override
    protected void onCleared() {
        DBpref.storeTogt(togt.getId());
        DBpref.storeEtappe(etape.getId());
        super.onCleared();
    }


    public static void setContext(Context context) {
        DBpref = new SaveLocal(context);
    }

    /*
    Inner class for accessing shared preferences. It savas the ids of the current Togt and Etape, fetch them from the database
    and returns to the AktivTogt.
     */
    static class SaveLocal {

        private long etapeId;
        private long togtid;
        private SharedPreferences pref;
        private final String TOGT_TAG = "Test_Current Togt id";
        private final String ETAPPE_TAG = "Test_Current Etappe ID";
        private TogtDAO togtDatabase;
        private EtapeDAO etapeDatabase;

        //Used for testing
        //private TESTTogtDAO TESTTogtDatabase;
       // private TESTEtapeDAO TESTEtapeDatabase;

        SaveLocal(Context context){
            if(pref == null){
                pref = context.getSharedPreferences("SharedPrefC3", context.MODE_PRIVATE);//Mode private so that no other application can access these data.
            }

           togtDatabase = new TogtDAO(context);
           etapeDatabase = new EtapeDAO(context);

            //Databaser used for testing
            //TESTEtapeDatabase = new TESTEtapeDAO();
            //TESTTogtDatabase = new TESTTogtDAO();
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


        /*
        Get the id of the togt from shared praferences and fetch the togt from the database.
        If no togt is found, a default one will be created.
         */
        public Togt getTogt(){
           togtid = pref.getLong(TOGT_TAG,1);

           //Searching for the togt
            //List<Togt> allTogter =  TESTTogtDatabase.getTogter();
            List<Togt> allTogter = togtDatabase.getTogter();

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


        /*
        Get the id of the etape from shared praferences and fetch the Etape from the database.
        @Param: Togt, the etapeDatabase use togt to get the etape.
         */
        public Etape getEtape(Togt togt){
            etapeId = pref.getLong(ETAPPE_TAG,1);
            //List<Etape> allEtaper = TESTEtapeDatabase.getEtape(togt);
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

    }
}
