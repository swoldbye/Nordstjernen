/*
package com.example.skibslogapp.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.global.GenerateCSV;

import java.io.File;
import java.io.FileOutputStream;

public class UdtagData_frag extends Fragment implements View.OnClickListener {
    Button sendD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_udtagdata, container, false);
        sendD = view.findViewById(R.id.sendData);
        sendD.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == sendD) {
            //it seems it does not sense the the click on this button
            System.out.println("the send data button was clicked.");
            Toast.makeText(getActivity(), "Dataen bliver pakken til export",
                    Toast.LENGTH_LONG).show();
            export();

        }
    }
*/
/*
    public void export() {
        //generate data
        String data = new GenerateCSV().generateTogt(togt);
        StringBuilder data = csvdata.generateEtape(0,0);

        *//*
*/
/**
         * @author Claes
         * below we gernerate a CSV file form a String and then export it
         *//*
*/
/*
        try {
            Context context = getActivity();
            //saving the file into device
            FileOutputStream out = context.openFileOutput("EtapeData.csv", Context.MODE_PRIVATE);
            //out.write('\ufeff'); //this was intended for allowing utf-8 in the csv file.
            out.write((data.toString()).getBytes());
            out.close();

            //exporting
            File filelocation = new File(context.getFilesDir(), "EtapeData.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.exportcsv.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "EtapeData");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }*//*

}*/
