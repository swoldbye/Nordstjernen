package com.example.skibslogapp.datalayer.global;

import android.content.Context;

import java.util.ArrayList;

/**
 * @author Claes
 * This file generates a String from which we can generate our CSV file
 */
public class GenerateCSV {
    Context Contex;
    public StringBuilder make(Context cont, int Togt, int Etape){
        //TODO: consider to implement this so that we do not need to hava a context

        Contex = cont;
        ArrayList<LogPunktStringDTO> logs = getEtapeFromDB(cont, Togt, Etape);

        //Making a string for the CSV file
        StringBuilder data = new StringBuilder();
        data.append("TogtID,EtapeID,Dato,Roere,vindretning,vindhastighed,StroemRetning,Kurs,Note,MandOverBord,BredeGrad,LeangdeGrad,SejlFoering,Sejlstilling");
        for (int i = 0; i < logs.size(); i++) {
            data.append("\n" + logs.get(i).getTogtID() + "," +
                    logs.get(i).getEtapeID()+ "," + logs.get(i).getDato()+
                    "," + logs.get(i).getRoere()+ "," + logs.get(i).getVindretning()+
                    "," + logs.get(i).getVindhastighed()+"," + logs.get(i).getStroemRetning()+
                    "," + logs.get(i).getKurs()+","+logs.get(i).getNote()+
                    "," + logs.get(i).getMandOverBord()+"," + logs.get(i).getBredeGrad()+
                    "," + logs.get(i).getHoejdeGrad()+"," + logs.get(i).getSejlFoering()+
                    "," + logs.get(i).getSejlstilling());
        }
        return data;
    }

    public ArrayList<LogPunktStringDTO> getEtapeFromDB(Context cont, int Togt, int Etape){
        DbEtapeStringify Converter = new DbEtapeStringify();
        ArrayList<LogPunktStringDTO> LogPunkterne = Converter.convert(cont, Togt, Etape);
        return LogPunkterne;
    }


}
