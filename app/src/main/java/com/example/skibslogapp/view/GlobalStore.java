package com.example.skibslogapp.view;

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

/*
Extendt ViewModel so that we get access to the onCleaned method.
 */


public class GlobalStore extends ViewModel {


    //Using mutable livedata so that we can hvave public methods for updating values.

    //Using the id to get the Togt and Etape from the DB.

    private static Etape etape;
    private static Togt togt;
    private static MutableLiveData<String> currentTogt;     //From Togt
    private static MutableLiveData<String> currentSkipper;  //From Togt
    private static MutableLiveData<String> currentBesaetning; //From Etape
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

    public GlobalStore(){
        System.out.println("Using GlobalStore singel constructor");
    }




    /*
    Methods used though out the code to set the current Togt and Etape
    */
    public static void setCurrentTogt(Togt togt){
    setTogt(togt);
    DBpref.storeTogt(togt.getId());

    if(currentTogt==null){
        currentTogt = new MutableLiveData<String>();
    }
    currentTogt.setValue(togt.getName());

    if(currentSkipper==null){
        currentSkipper = new MutableLiveData<String>();
    }
    currentSkipper.setValue(togt.getSkipper());

    }

    public static void setCurretEtape(Etape etape){
        setEtape(etape);
        DBpref.storeEtappe(etape.getId());
        if(currentBesaetning==null){
            currentTogt = new MutableLiveData<String>();
        }

        /*
        Here we need the number of crew membrs of the current etape.
         */
        //currentTogt.setValue(etape.);

    }






    /*
    Getters and Setters
     */

    public static void setEtape(Etape etape) {
        GlobalStore.etape = etape;
        DBpref.storeEtappe(etape.getId());
    }

    public static void setTogt(Togt togt) {
        GlobalStore.togt = togt;
        DBpref.storeTogt(togt.getId());
        currentSkipper.setValue(togt.getSkipper());
    }

    public static void setContext(Context context) {
        GlobalStore.context = context;
        DBpref = new SaveLocal(context);
    }



    public static Etape getCurrentEtape(){ return etape;}


    /*
    getter for mutable data
     */

    public static MutableLiveData<String> getCurrentBesaetning() {
        if(currentBesaetning ==null){
            currentBesaetning = new MutableLiveData<String>();
        }



        return currentBesaetning;

    }

    public static MutableLiveData<String> getCurrentSkipper() {
        if(currentSkipper==null){
            togt = DBpref.getTogt();
            currentSkipper = new MutableLiveData<>();
            currentSkipper.setValue(togt.getSkipper());
        }

        return currentSkipper;
    }



    public MutableLiveData<String> getCurrentTogt() {

        if(currentTogt==null){
            togt = DBpref.getTogt();
            currentTogt = new MutableLiveData<>();
            currentTogt.setValue(togt.getName());
        }

        return currentTogt;
    }



/*
    private static void saveTogtDBPref(long togtId){
        DBpref = context.getSharedPreferences("SharedPrefC3", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = DBpref.edit();
        editor.putLong(TOGT_TAG, togtId);
        editor.apply();

    }

    private static void saveEtapeDBPref(long etapeId){
        DBpref = context.getSharedPreferences("SharedPrefC3", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = DBpref.edit();
        editor.putLong(ETAPPE_TAG, etapeId);
        editor.apply();
    }


    private static void getTogtDBPref(){
        stringList[0] = pref.getString(TOGT_TAG,"-");

    }

*/





    //For testing







    static class SaveLocal extends AppCompatActivity {


        private long togtid;
        private SharedPreferences pref;
        private final String TOGT_TAG = "Test_Current Togt id";
        private final String ETAPPE_TAG = "Test_Current Etappe ID";
        private TogtDAO togtDatabase;
        private EtapeDAO etapeDatabase;

        private Context context;


        SaveLocal(Context context){
            this.context = context.getApplicationContext();
            if(pref == null){
                pref = context.getSharedPreferences("SharedPrefC3", context.MODE_PRIVATE);//Mode private so that no other application can access these data.
            }
            togtDatabase = new TogtDAO(context);
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
           togtid = pref.getLong(TOGT_TAG,11);


           //Searching for the togt

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

        public Etape getEtape(Togt togt){
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



       /* public String[] getCurrentState(){
            String[] stringList = new String[2];
            stringList[0] = pref.getString(TOGT_TAG,"-");
            stringList[1] = pref.getString(ETAPPE_TAG,"-");
            return stringList;
        }

*/




    }
}
