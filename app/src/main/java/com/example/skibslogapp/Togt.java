package com.example.skibslogapp;

import java.util.ArrayList;

public class Togt {
    static ArrayList togt = new ArrayList<LogInstans>();


    private Togt() {}

    static ArrayList<LogInstans> getTogter(){
        return togt;
    }

    static void addLogPost(LogInstans x){
        togt.add(x);
    }

}
