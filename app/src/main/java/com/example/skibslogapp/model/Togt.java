package com.example.skibslogapp.model;

import java.util.ArrayList;

public class Togt {
    //Opdateret:
    int ID;
    String Name;
    public ArrayList etapper = new ArrayList<Etape>();


    //Ikke opdateret:
    static ArrayList togt = new ArrayList<LogInstans>();

    private Togt() {}

    public static ArrayList<LogInstans> getTogter(){
        return togt;
    }

    public static void addLogPost(LogInstans x){
        togt.add(x);
    }


}
