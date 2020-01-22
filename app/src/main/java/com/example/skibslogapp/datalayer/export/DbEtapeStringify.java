package com.example.skibslogapp.datalayer.export;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * This class creates a list of Logpunkt converted to LogpunktString (string version)
 * from a given Etape
 */
class DbEtapeStringify {

    ArrayList<LogpunktString> convert(Etape etape) {

        ArrayList<LogpunktString> stringifiedPunkter = new ArrayList<>();

        // Loading Logpunkter from database
        List<Logpunkt> logpunkter = new LogpunktDAO(GlobalContext.get()).getLogpunkter(etape);

        //=============================================================
        /*
        Here we extract the data we want to use from the SQL DB to a temporary DTO,
        Which can be used for making the CSV file. However it is optional to fill in
        any points in a log so we have to check for null pointers.

        The below also converts all the DB data to Strings.
         */
        for (Logpunkt logpunkt : logpunkter) {
            LogpunktString logpunktString = new LogpunktString();

            logpunktString.setEtapeID(String.valueOf(etape.getId()));

            if (logpunkt.getDate() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(logpunkt.getDate());
                String dateString = String.format(Locale.UK, "%d/%d-%d %02d:%02d",
                        cal.get(Calendar.DAY_OF_MONTH),
                        cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE)
                );
                logpunktString.setDato(dateString);
            }

            if (logpunkt.getRoere() != -1) {
                String rore = String.valueOf(logpunkt.getRoere());
                logpunktString.setRoere(rore);
            }

            if (logpunkt.getVindretning() != null) {
                logpunktString.setVindretning(logpunkt.getVindretning());
            }

            if (logpunkt.getVindhastighed() != -1) {
                String vindHast = String.valueOf(logpunkt.getVindhastighed());
                logpunktString.setVindhastighed(vindHast);
            }

            if (logpunkt.getStroemRetning() != null) {
                String StromRet = logpunkt.getStroemRetning();
                logpunktString.setStroemRetning(StromRet);
            }

            if (logpunkt.getStroemhastighed() > -1) {
                logpunktString.setStroemHastighed(String.valueOf(logpunkt.getStroemhastighed()));
            }

            if (logpunkt.getKurs() > -1) {
                logpunktString.setKurs(String.valueOf(logpunkt.getKurs()));
            }

            if (logpunkt.getNote() != null) {
                String note = logpunkt.getNote();
                logpunktString.setNote(note);
            }

            String MOB = String.valueOf(logpunkt.getMandOverBord());
            logpunktString.setMandOverBord(MOB);

            if (logpunkt.getPosition() != null) {
                logpunktString.setBredeGrad(logpunkt.getPosition().getBreddegradString());
                logpunktString.setHoejdeGrad(logpunkt.getPosition().getLaengdegradString());
            }

            if (logpunkt.getSejlfoering() != null) {
                logpunktString.setSejlFoering(logpunkt.getSejlfoering());
            }
            if (logpunkt.getSejlstilling() != null) {
                logpunktString.setSejlstilling(logpunkt.getSejlstilling());
            }

            stringifiedPunkter.add(logpunktString);
        }

        // ===============================================================

        return stringifiedPunkter;
    }
}
