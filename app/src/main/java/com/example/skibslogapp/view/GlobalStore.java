package com.example.skibslogapp.view;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.GlobalTogt;
import com.example.skibslogapp.model.Togt;

public class GlobalStore extends ViewModel {

    private TogtDAO togtDatabase;
    private EtapeDAO etapeDatabase;
    //Using mutable livedata so that we can hvave public methods for updating values.

    private static MutableLiveData<Togt> currentTogt;
    private static MutableLiveData<Etape> currentEtape;
    private static MutableLiveData<String> currentSkipper;
    private static MutableLiveData<String> currentBesætning;

    //For testing
    private static MutableLiveData<String> currentName;

    public GlobalStore(){}



    //For testing
    public MutableLiveData<String> getCurrentSkipper() {
        if (currentSkipper == null) {
            currentSkipper = new MutableLiveData<String>();
        }
        currentSkipper.setValue("-");
        return currentSkipper;
    }


    public LiveData<Togt> getCurrentTogt(){
        if(currentTogt==null){
            currentTogt = new MutableLiveData<Togt>();
        }
        return currentTogt;
    }

    public LiveData<Etape> getCurrentEtape(){

        return currentEtape;
    }



    public MutableLiveData getCurrentBesætning(){
        return currentBesætning;
    }

    public void setTogtDatabase(TogtDAO togtDatabase) {
        this.togtDatabase = togtDatabase;
    }

    public void setEtapeDatabase(EtapeDAO etapeDatabase) {
        this.etapeDatabase = etapeDatabase;
    }

    public void setCurrentTogt(MutableLiveData<Togt> currentTogt) {
        this.currentTogt = currentTogt;
    }

    public void setCurrentEtape(MutableLiveData<Etape> currentEtape) {
        this.currentEtape = currentEtape;
    }

    public void setCurrentSkipper(MutableLiveData<String> currentSkipper) {
        this.currentSkipper = currentSkipper;
    }

    public void setCurrentBesætning(MutableLiveData<String> currentBesætning) {
        this.currentBesætning = currentBesætning;
    }

    public static void setCurrentName(MutableLiveData<String> currentName) {
        GlobalStore.currentName = currentName;
    }
}
