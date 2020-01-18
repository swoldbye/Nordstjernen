package com.example.skibslogapp.model;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import java.util.Locale;


public class Togt {

    private long id = -1;
    private String name;
    private String startDestination;
    private String skib;
    private String skipper;

    public Togt(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
    }

    public String getSkib() {
        return skib;
    }

    public void setSkib(String skib) {
        this.skib = skib;
    }

    public String getSkipper() {
        return skipper;
    }

    public void setSkipper(String skipper) {
        this.skipper = skipper;
    }

    public boolean equals(Togt togt){
        return  id == togt.id &&
                TextUtils.equals(name, togt.name) &&
                TextUtils.equals(skib, togt.skib) &&
                TextUtils.equals(skipper, togt.skipper) &&
                TextUtils.equals(startDestination, togt.startDestination);
    }


    @Override
    @NonNull
    public String toString(){
        return String.format(Locale.US,
                "Togt{ ID: %d, Navn: %s, Skib: %s, Skipper: %s, Startd.: %s }",
                id, name, skib != null ? skib : "-", skipper != null ? skipper : "-", startDestination != null ? startDestination : "-"
        );
    }
}
