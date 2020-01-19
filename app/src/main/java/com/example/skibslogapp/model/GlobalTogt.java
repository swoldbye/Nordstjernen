package com.example.skibslogapp.model;

import android.content.Context;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;

import java.util.List;

/**
 * THIS IS A TEMPORARY CLASS!
 */
public class GlobalTogt {

    private static Togt togt = null;
    private static Etape etape = null;

    public static Togt getTogt(Context context) {

        if (togt == null) {
            TogtDAO togtDAO = new TogtDAO(context);
            List<Togt> togter = togtDAO.getTogter();
            if (togter.size() > 0) {
                togt = togter.get(0);
            } else {
                togt = new Togt("Global Togt");
                togtDAO.addTogt(togt);
            }
            return togt;
        }
        return togt;
    }

    public static Etape getEtape(Context context) {

        if (etape == null) {
            EtapeDAO etapeDAO = new EtapeDAO(context);
            List<Etape> etaper = etapeDAO.getEtaper(getTogt(context));
            if (etaper.size() > 0) {
                etape = etaper.get(0);
            } else {
                etape = new Etape();
                etapeDAO.addEtape(getTogt(context), etape);
            }
        }
        return etape;

    }

}
