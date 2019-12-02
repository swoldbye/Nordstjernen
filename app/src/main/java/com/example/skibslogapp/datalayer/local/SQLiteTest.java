package com.example.skibslogapp.datalayer.local;

import android.content.Context;

import com.example.skibslogapp.model.Togt;

import java.util.List;

public class SQLiteTest {


    public static void test(Context context){
        System.out.println("\nStarting test");


        LogbogDAO dao = new LogbogDAO(context);

        System.out.println("Creating togter");
        dao.addTogt(new Togt("Tokes Eftårstur"));
        dao.addTogt(new Togt("Troels' Sommertur"));
        dao.addTogt(new Togt("Sjælland Rundt"));

        List<Togt> togter = dao.getTogter();

        System.out.println("Loading togter:");
        for( Togt togt : togter ){
            System.out.printf("\t%s (%d)\n", togt.getName(), togt.getId());
        }


        System.out.println("Finished test");
    }

}
