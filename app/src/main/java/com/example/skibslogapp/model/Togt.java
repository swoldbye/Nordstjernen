package com.example.skibslogapp.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;

public class Togt {

    static ArrayList togt = new ArrayList<LogInstans>();

    ArrayList<Besaetning> besaetningArrayList = new ArrayList<>();
    String skipper;
    String startDest;
    String slutDest;
    Date dato;

    private Togt() {}

    public static ArrayList getTogt() {
        return togt;
    }

    public static void setTogt(ArrayList togt) {
        Togt.togt = togt;
    }

    public ArrayList<Besaetning> getBesaetningArrayList() {
        return besaetningArrayList;
    }

    public void setBesaetningArrayList(ArrayList<Besaetning> besaetningArrayList) {
        this.besaetningArrayList = besaetningArrayList;
    }

    public String getSkipper() {
        return skipper;
    }

    public void setSkipper(String skipper) {
        this.skipper = skipper;
    }

    public String getStartDest() {
        return startDest;
    }

    public void setStartDest(String startDest) {
        this.startDest = startDest;
    }

    public String getSlutDest() {
        return slutDest;
    }

    public void setSlutDest(String slutDest) {
        this.slutDest = slutDest;
    }

    public Date getDato() {
        return dato;
    }

    public void setDato(Date dato) {
        this.dato = dato;
    }

    public static ArrayList<LogInstans> getTogter(){
        return togt;
    }

    public static void addLogPost(LogInstans x){
        togt.add(x);
    }

}
