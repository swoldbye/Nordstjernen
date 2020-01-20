package com.example.skibslogapp;

import android.content.Context;
import android.util.Log;


import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DbTranslator {

    //ArrayList<List<Logpunkt>> logs2D = new ArrayList<>();
    EtapeDAO etapeDAO;
    LogpunktDAO logpunktDAO;

    public DbTranslator(Context context) {
        etapeDAO = new EtapeDAO(context);
        logpunktDAO = new LogpunktDAO(context);
    }

    private List<Etape> getEtapper(Togt togt) {
        List<Etape> etapper;
        etapper = etapeDAO.getEtaper(togt);
        return etapper;
    }

    private List<Logpunkt> getLogpunkter(Etape etape) {
        List<Logpunkt> logs1D;
        logs1D = logpunktDAO.getLogpunkter(etape);
        return logs1D;
    }

    public List<List<Logpunkt>> getList(Togt togt) {
        ArrayList<List<Logpunkt>> logs2D = new ArrayList<>();
        List<Etape> etapper = etapeDAO.getEtaper(togt);
        System.out.println("Size of translateList = " + etapper.size());
        for (Etape e : etapper) {
            List<Logpunkt> logs = getLogpunkter(e);
            logs2D.add(logs);
        }
        return logs2D;
    }
}










