package com.example.skibslogapp.datalayer.global;

import android.content.Context;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GenerateCSV {
    Context Contex;
    public StringBuilder make(Context cont, int Togt, int Etape){
        //TODO: consider to implement this so that we do not need to hava a context
        Contex = cont;
        ArrayList<LogPunktStringDTO> logs = getTogtFromDB(Togt, Etape);
        StringBuilder data = new StringBuilder();
        data.append("TogtID,EtapeID,Dato, Roere, vindretning, vindhastighed, StroemRetning, Kurs, Note, MandOverBord, BredeGrad, LeangdeGrad, SejlFoering,Sejlstilling");
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

    public ArrayList<LogPunktStringDTO> getTogtFromDB(int Togt, int Etape){
        /**
         @param logData a list with one string pr. log point, ready to be put into a .csv file
         */
        ArrayList<LogPunktStringDTO> logData = new ArrayList<>();



        //Getting the togter in the DB
        TogtDAO togter = new TogtDAO(Contex);
        List<Togt> dbTogter = togter.getTogter();

        //Getting the Etaper in the DB
        EtapeDAO dao = new EtapeDAO(Contex);
        //Todo: this needs to take the togt the User wants to export.
        //not just togt nr. 1.
        List<Etape> etappen = dao.getEtaper(dbTogter.get(Togt));

        //Getting the Logs
        LogpunktDAO logs = new LogpunktDAO(Contex);
        List<Logpunkt> Logs = logs.getLogpunkter(etappen.get(Etape));

        //=============================================================
        /** @author Claes
        Here we extract the data we want to use from the SQL DB to a temporary DTO,
        Which can be used for making the CSV file. However it is optional to fill in
        any points in a log so we have to check for null pointers.

        The below also converts all the DB data to Strings.
         */
        int itlength = Logs.size();
        for(int i=0; i<itlength; i++){
            LogPunktStringDTO punkt = new LogPunktStringDTO();

            punkt.setTogtID(String.valueOf(Togt));
            punkt.setEtapeID(String.valueOf(Etape));

            if(Logs.get(i).getDate() != null){
                Calendar cal = Calendar.getInstance();
                cal.setTime(Logs.get(i).getDate());
                String dateString = String.format(Locale.UK,"%d/%d-%d %02d:%02d",
                        cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH)+1,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE)
                );
                punkt.setDato(dateString);
            }

            if(Logs.get(i).getRoere() != -1){
                String rore = String.valueOf(Logs.get(i).getRoere());
                punkt.setRoere(rore);
            }

            if(Logs.get(i).getVindhastighed() != -1){
                String vindHast = String.valueOf(Logs.get(i).getVindhastighed());
                punkt.setVindhastighed(vindHast);
            }

            if(Logs.get(i).getStroemRetning() != null){
                String StromRet = Logs.get(i).getStroemRetning();
                punkt.setStroemRetning(StromRet);
            }

            if(Logs.get(i).getKurs() != -1){
                String Kurs = String.valueOf(Logs.get(i).getKurs());
                punkt.setVindhastighed(Kurs);
            }

            if(Logs.get(i).getNote() != null){
                String note = Logs.get(i).getNote();
                punkt.setNote(note);
            }

            String MOB = String.valueOf(Logs.get(i).getMandOverBord());
            punkt.setMandOverBord(MOB);

            if(Logs.get(i).getPosition() != null){
                punkt.setBredeGrad(Logs.get(i).getPosition().getBreddegradString());
            }

            if(Logs.get(i).getPosition() != null){
                punkt.setBredeGrad(Logs.get(i).getPosition().getLaengdegradString());
            }
            if(Logs.get(i).getSejloeringString() != null){
                String sf = Logs.get(i).getSejloeringString();
                punkt.setSejlFoering(sf);
            }
            if(Logs.get(i).getSejlstillingString() != null){
                String sf = Logs.get(i).getSejlfoering();
                punkt.setSejlFoering(sf);
            }
            logData.add(punkt);
        }

        //=============================================================

        return logData;
    }


}
