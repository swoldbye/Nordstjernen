package com.example.skibslogapp.postOversigt;

import android.content.Context;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;
import java.util.List;

public class DbTranslator {

    private EtapeDAO etapeDAO;
    private LogpunktDAO logpunktDAO;

    public DbTranslator(Context context) {
        etapeDAO = new EtapeDAO(context);
        logpunktDAO = new LogpunktDAO(context);
    }

    private List<Logpunkt> getLogpunkter(Etape etape) {
        List<Logpunkt> logs1D;
        logs1D = logpunktDAO.getLogpunkter(etape);
        return logs1D;
    }

    public List<List<Logpunkt>> getList(Togt togt) {
        ArrayList<List<Logpunkt>> logs2D = new ArrayList<>();
        List<Etape> etapper = etapeDAO.getEtaper(togt);
        for (Etape e : etapper) {
            List<Logpunkt> logs = getLogpunkter(e);
            logs2D.add(logs);
        }
        return logs2D;
    }
}










