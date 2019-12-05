package com.example.skibslogapp.datalayer.local;

import android.content.Context;

import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.util.List;

public class SQLiteTest {


    public static void test(Context context){
        System.out.println("\nStarting test");

        LogbogDAO dao = new LogbogDAO(context);
        List<Togt> togter = dao.getTogter();

        if( togter.size() == 0){

            Togt togt = new Togt("Tokes Sommertogt");
            dao.addTogt(togt);

            Etape etape = new Etape();
            dao.addEtape(togt, etape);

            Logpunkt logpunkt = new Logpunkt(null);

            dao.addLogpunkt(etape, logpunkt);
        }

        Etape etape = dao.getEtaper(dao.getTogter().get(0)).get(0);
        List<Logpunkt> punkter = dao.getLogpunkter(etape);

        for( Logpunkt punkt : punkter ){
            System.out.println(punkt);
        }






        /*
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

            }*/
    }
}
