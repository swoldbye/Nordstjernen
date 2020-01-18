package com.example.skibslogapp.datalayer.global;

import android.content.Context;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;
import java.util.List;

public class GenerateCSV {
    Context Contex;
    public StringBuilder make(Context cont){
        //TODO: consider to implement this so that we do not need to hava a context
        Contex = cont;
        ArrayList<LogPunktStringDTO> logs = getTogtFromDB();
        StringBuilder data = new StringBuilder();
        data.append("TogtID,EtapeID,Dato, Roere, vindretning, vindhastighed, StroemRetning, Kurs, Note, MandOverBord, BredeGrad, HoejdeGrad, SejlFoering,Sejlstilling");
        for (int i = 0; i < logs.size(); i++) {
            data.append("\n" + String.valueOf(i) + "," +
                    String.valueOf(i * i)+ "," + String.valueOf(i + i)+
                    "," + String.valueOf(i * 5)+ "," + "NNV"+","+logs.get(i).getNote());
        }
        return data;
    }

    public ArrayList<LogPunktStringDTO> getTogtFromDB(){
        /**
         @param logData a list with one string pr. log point, ready to be put into a .csv file
         */
        ArrayList<LogPunktStringDTO> logData = new ArrayList<>();

        //=============================================================
        // Might have to change the below
        //_________________________________

        //Getting the togter in the DB
        TogtDAO togter = new TogtDAO(Contex);
        List<Togt> dbTogter = togter.getTogter();

        //Getting the Etaper in the DB
        EtapeDAO dao = new EtapeDAO(Contex);
        //Todo: this needs to take the togt the User wants to export.
        //not just togt nr. 1.
        List<Etape> etappen = dao.getEtaper(dbTogter.get(0));

        //Getting the Logs
        LogpunktDAO logs = new LogpunktDAO(Contex);
        int itlength = 3;
        for(int i=0; i<itlength; i++){
            LogPunktStringDTO punkt = new LogPunktStringDTO();
            //String note = logs.getLogpunkter(etappen.get(0)).get(i).getNote();
            if(logs.getLogpunkter(etappen.get(0)).get(i).getNote() != null){
                String note = logs.getLogpunkter(etappen.get(0)).get(i).getNote();
                punkt.setNote(note);
            }
            logData.add(punkt);
        }

        //=============================================================

        return logData;
    }


}
