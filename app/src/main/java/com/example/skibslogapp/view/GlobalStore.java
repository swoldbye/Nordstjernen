package com.example.skibslogapp.view;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.TESTTogtDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;

import java.util.List;

/*
Extendt ViewModel so that we get access to the onCleaned method.
 */

public class GlobalStore extends ViewModel {


    //Using mutable livedata so that we can hvave public methods for updating values.

    //Using the id to get the Togt and Etape from the DB.

    private static Etape etape;
    private static Togt togt;
    private static MutableLiveData<Togt> currentTogt;     //From Togt
    private static MutableLiveData<String> currentSkipper;  //From Togt
    private static MutableLiveData<Etape> currentEtape; //From Etape
    //static SharedPreferences DBpref;
    static Context context;
    static SaveLocal DBpref;


    //For testing
    private static MutableLiveData<String> currentName;

    @Override
    protected void onCleared() {

        DBpref.storeTogt(togt.getId());
        DBpref.storeEtappe(etape.getId());
        super.onCleared();
    }


    /*
    Getters and Setters
     */

    public static void setEtape(Etape etape) {
        GlobalStore.etape = etape;
        DBpref.storeEtappe(etape.getId());

        if(currentEtape == null){
            currentEtape = new MutableLiveData<Etape>();
        }
        currentEtape.setValue(etape);
    }

    public static void setTogt(Togt togt) {
        GlobalStore.togt = togt;
        DBpref.storeTogt(togt.getId());

        if(currentTogt == null){
            currentTogt = new MutableLiveData<Togt>();
        }

        currentTogt.setValue(togt);
    }

    public static void setContext(Context context) {
        GlobalStore.context = context;
        DBpref = new SaveLocal(context);
    }


    /*
    getter for mutable data. Data object that is beeing observed
     */

    public static MutableLiveData<Etape> getCurrentEtape() {
        if(currentEtape==null){
            etape = DBpref.getEtape(togt);
            currentEtape = new MutableLiveData<>();
            currentEtape.setValue(etape);
        }

        return currentEtape;
    }


    public static MutableLiveData<Togt> getCurrentTogt() {

        if(currentTogt==null){
            togt = DBpref.getTogt();
            currentTogt = new MutableLiveData<>();
            currentTogt.setValue(togt);
        }

        return currentTogt;
    }



    /*
    Inner class for accessing shared preferences. It savas the ids of the current Togt and Etape, fetch them from the database
    and returns to the GlobalStore.
     */
    static class SaveLocal extends AppCompatActivity {

        private long etapeId;
        private long togtid;
        private SharedPreferences pref;
        private final String TOGT_TAG = "Test_Current Togt id";
        private final String ETAPPE_TAG = "Test_Current Etappe ID";
        private TogtDAO togtDatabase;
        private EtapeDAO etapeDatabase;

        //Used for testing
        private TESTTogtDAO TESTTogtDatabase;

        private Context context;


        SaveLocal(Context context){
            this.context = context.getApplicationContext();
            if(pref == null){
                pref = context.getSharedPreferences("SharedPrefC3", context.MODE_PRIVATE);//Mode private so that no other application can access these data.
            }

            TESTTogtDatabase = new TESTTogtDAO();
            //togtDatabase = new TogtDAO(context);
            etapeDatabase = new EtapeDAO(context);
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
           togtid = pref.getLong(TOGT_TAG,1);


           //Searching for the togt
            List<Togt> allTogter =  TESTTogtDatabase.getTogter();
            //List<Togt> allTogter = togtDatabase.getTogter();

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
            etapeId = pref.getLong(TOGT_TAG,1);

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
