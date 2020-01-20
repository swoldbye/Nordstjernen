package com.example.skibslogapp.datalayer.global;

import android.content.Context;

import com.example.skibslogapp.GlobalContext;
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

/**
 * @author Claes
 * This class creates a lit of Log points as strings coorispoinding to one etape.
 * Based on what is in the SQL DB
 */
public class DbEtapeStringify {

    public ArrayList<LogPunktStringDTO> convert(Etape etape){
         // a list with one string pr. log point, ready to be put into a .csv file
        ArrayList<LogPunktStringDTO> stringifiedPunkter = new ArrayList<>();

     /*   //TODO: generateEtape sure an error is trown to the user, in case the databse is empthy saying nothing to export
        //Getting the togter in the DB
        TogtDAO togter = new TogtDAO(Contex);
        List<Togt> dbTogter = togter.getTogter();

        //Getting the Etaper in the DB
        EtapeDAO dao = new EtapeDAO(Contex);
        List<Etape> etappen = dao.getEtaper(dbTogter.get(Togt));
*/
        //Getting the Logs
        List<Logpunkt> logpunkter = new LogpunktDAO(GlobalContext.get()).getLogpunkter(etape);

        //=============================================================
        /*
        Here we extract the data we want to use from the SQL DB to a temporary DTO,
        Which can be used for making the CSV file. However it is optional to fill in
        any points in a log so we have to check for null pointers.

        The below also converts all the DB data to Strings.
         */
        for( Logpunkt logpunkt : logpunkter ){
            LogPunktStringDTO logpunktString = new LogPunktStringDTO();

            logpunktString.setEtapeID(String.valueOf(etape.getId()));

            if(logpunkt.getDate() != null){
                Calendar cal = Calendar.getInstance();
                cal.setTime(logpunkt.getDate());
                String dateString = String.format(Locale.UK,"%d/%d-%d %02d:%02d",
                        cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH)+1,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE)
                );
                logpunktString.setDato(dateString);
            }

            if(logpunkt.getRoere() != -1){
                String rore = String.valueOf(logpunkt.getRoere());
                logpunktString.setRoere(rore);
            }

            if(logpunkt.getVindhastighed() != -1){
                String vindHast = String.valueOf(logpunkt.getVindhastighed());
                logpunktString.setVindhastighed(vindHast);
            }

            if(logpunkt.getStroemRetning() != null){
                String StromRet = logpunkt.getStroemRetning();
                logpunktString.setStroemRetning(StromRet);
            }

            if(logpunkt.getKurs() != -1){
                String Kurs = String.valueOf(logpunkt.getKurs());
                logpunktString.setVindhastighed(Kurs);
            }

            if(logpunkt.getNote() != null){
                String note = logpunkt.getNote();
                logpunktString.setNote(note);
            }

            String MOB = String.valueOf(logpunkt.getMandOverBord());
            logpunktString.setMandOverBord(MOB);

            if(logpunkt.getPosition() != null){
                logpunktString.setBredeGrad(logpunkt.getPosition().getBreddegradString());
            }

            if(logpunkt.getPosition() != null){
                logpunktString.setBredeGrad(logpunkt.getPosition().getLaengdegradString());
            }
            if(logpunkt.getSejloeringString() != null){
                String sf = logpunkt.getSejloeringString();
                logpunktString.setSejlFoering(sf);
            }
            if(logpunkt.getSejlstillingString() != null){
                String sf = logpunkt.getSejlfoering();
                logpunktString.setSejlFoering(sf);
            }
            stringifiedPunkter.add(logpunktString);
        }

        // ===============================================================

        return stringifiedPunkter;
    }
}
