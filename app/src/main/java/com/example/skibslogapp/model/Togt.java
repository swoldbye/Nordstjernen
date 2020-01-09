package com.example.skibslogapp.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;

public class Togt {

    static ArrayList togt = new ArrayList<LogInstans>();

    private long id = -1;
    private String name;

    public Togt(String name) {
        this.name = name;
    }

    ArrayList<Besaetning> besaetningArrayList = new ArrayList<>();
    String skipper;
    String startDest;
    String name;
    String date;
    String ship;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Togt(String skipper, String startDest, String name, String date, String ship) {
        this.skipper = skipper;
        this.startDest = startDest;
        this.name = name;
        this.date = date;
        this.ship = ship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShip() {
        return ship;
    }

    public void setShip(String ship) {
        this.ship = ship;
    }

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

    public static ArrayList<LogInstans> getTogter(){
        return togt;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Togt togt){
        return id == togt.id && name.equals(togt.name);
    }
}
