package com.example.skibslogapp.datalayer.local;

import android.content.Context;

import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;

import java.util.List;

public class SQLiteTest {


    public static void test(Context context){
        System.out.println("\nStarting test");

        LogbogDAO dao = new LogbogDAO(context);
        List<Togt> togter = dao.getTogter();

        if( togter.size() == 0){
            dao.addTogt(new Togt("Tokes Sommertogt"));
            dao.addTogt(new Togt("En tur rundt om fyn"));
            dao.addTogt(new Togt("Ud i det blå"));
        }

        togter = dao.getTogter();

        System.out.println("Loading togter:");
        for( Togt togt : togter ){
            System.out.printf("\t%s (%d)\n", togt.getName(), togt.getId());

            System.out.println("Creating new etape for"+togt.getName());
            dao.addEtape(togt, new Etape() );

            System.out.println("Etaper for "+togt.getName()+" before update: ");
            List<Etape> etaper = dao.getEtaper(togt);
            for( Etape etape : etaper ){
                System.out.println(etape);
                etape.setEndDate(null);
                dao.updateEtape(etape);
            }

            System.out.println("Etaper for "+togt.getName()+" after update: ");
            etaper = dao.getEtaper(togt);
            for( Etape etape : etaper ){
                System.out.println(etape);
            }
        }
    }

}
