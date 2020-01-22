package com.example.skibslogapp.datalayer.global;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Claes, Malte
 * Generate a CSV 'string' from a given Togt. Each row is a Logpunkt and each collumn
 * one of the data fields (date, position, note etc.).
 */
public class GenerateCSV {

    /**
     * Generate a CSV String from a given Togt object
     */
    public String generateTogt(Togt togt) {
        StringBuilder data = new StringBuilder();

        // "Enable" UETF-8, so we can use æ, ø, å
        data.append('\ufeff');
        data.append("\n");

        // Add column names
        data.append("etape_id,dato,breddegrad,længdegrad,mob,note,kurs,vindretning,vindhastighed,strømretning,strømhastighed,sejlføring,sejlstilling,roere");
        data.append("\n");

        // Add Etaper
        List<Etape> etaper = new EtapeDAO(GlobalContext.get()).getEtaper(togt);
        for (Etape etape : etaper) {
            data.append(generateEtape(etape));
        }
        return data.toString();
    }

    /**
     * Generates rows of Logpunkt objects from the given Etape.
     * Each row are comma seperated values.
     */
    private String generateEtape(Etape etape) {

        String str = "";

        List<LogpunktString> logpunktStrings = new DbEtapeStringify().convert(etape);
        for (LogpunktString logpunkt : logpunktStrings) {
            str +=
                    logpunkt.getEtapeID() +
                            "," + logpunkt.getDato() +
                            "," + logpunkt.getBredeGrad() +
                            "," + logpunkt.getHoejdeGrad() +
                            "," + logpunkt.getMandOverBord() +
                            "," + logpunkt.getNote().replace("\n", " ") +
                            "," + logpunkt.getKurs() +
                            "," + logpunkt.getVindretning() +
                            "," + logpunkt.getVindhastighed() +
                            "," + logpunkt.getStroemRetning() +
                            "," + logpunkt.getStroemHastighed() +
                            "," + logpunkt.getSejlFoering() +
                            "," + logpunkt.getSejlstilling() +
                            "," + logpunkt.getRoere();
            str += "\n";
        }
        return str;
    }
}
