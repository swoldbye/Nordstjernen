package com.example.skibslogapp;

import java.util.ArrayList;

public class Togt {
    public static ArrayList togt = new ArrayList<>();


    Togt(){


    }

    public ArrayList<LogInstans> getTogter(){
        return togt;

    }

    public void addLogPost(LogInstans x){
        togt.add(x);


    }

}
