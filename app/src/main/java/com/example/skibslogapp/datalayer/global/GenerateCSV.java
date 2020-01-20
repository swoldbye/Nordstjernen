package com.example.skibslogapp.datalayer.global;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Claes
 * This file generates a String from which we can generate our CSV file
 */
public class GenerateCSV {




    public String generateTogt(Togt togt){

        StringBuilder data = new StringBuilder();

        data.append("etape_id,dato,roere,vindretning,vindhastighed,stroemretning,kurs,note,mob,breddegrad,laengdegrad,sejlfoering,sejlstilling");


        List<Etape> etaper = new EtapeDAO(GlobalContext.get()).getEtaper(togt);
        for( Etape etape : etaper ){
            data
                .append( generateEtape(etape) )
                .append( "\n");
        }

        return data.toString();

/*
        Context context = GlobalContext.get();
        String fileName = togt.getName().replace(' ', '_');

        try( FileOutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE) ){




            // Get the full file path
            File file= new File(context.getFilesDir(), fileName);
            Uri path = FileProvider.getUriForFile(context, "com.example.exportcsv.fileprovider", file);

            out.close();
            return path;

        }catch(IOException exception){
            exception.printStackTrace();
        }
        return null;*/

    }


    public StringBuilder generateEtape(Etape etape){

        StringBuilder data = new StringBuilder();
        for( LogPunktStringDTO logpunkt : getStringifiedLogpunkter(etape) ){
            data.append("\n" +
                logpunkt.getEtapeID()+ "," + logpunkt.getDato()+
                "," + logpunkt.getRoere()+ "," + logpunkt.getVindretning()+
                "," + logpunkt.getVindhastighed()+"," + logpunkt.getStroemRetning()+
                "," + logpunkt.getKurs()+","+logpunkt.getNote()+
                "," + logpunkt.getMandOverBord()+"," + logpunkt.getBredeGrad()+
                "," + logpunkt.getHoejdeGrad()+"," + logpunkt.getSejlFoering()+
                "," + logpunkt.getSejlstilling());
        }
        return data;
    }

    private ArrayList<LogPunktStringDTO> getStringifiedLogpunkter(Etape etape){
        DbEtapeStringify Converter = new DbEtapeStringify();
        return Converter.convert(etape);
    }


}
