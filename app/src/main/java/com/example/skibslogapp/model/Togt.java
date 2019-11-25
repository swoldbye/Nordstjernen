package com.example.skibslogapp.model;

import java.util.ArrayList;

public class Togt {
    int ID;

    static ArrayList togt = new ArrayList<LogInstans>();


    private Togt() {}

    public static ArrayList<LogInstans> getTogter(){
        return togt;
    }

    public static void addLogPost(LogInstans x){
        togt.add(x);
    }


}
