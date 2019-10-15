package com.example.skibslogapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LogOversigt extends AppCompatActivity implements OnItemClickListener {
    ArrayList<LogInstans> togt = Togt.getTogter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String[] lande = {"Danmark", "Norge", "Sverige", "Finland", "Holland", "Italien", "Tyskland",
                "Frankrig", "Spanien", "Portugal", "Nepal", "Indien", "Kina", "Japan", "Thailand"};

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.logoversigt, R.id.overskrift,(ArrayList<LogInstans>) togt.getTogter());

        ListView logOversigt = new ListView(this);
        logOversigt.setOnItemClickListener(this);
        logOversigt.setAdapter(adapter);

        setContentView(logOversigt);
    }
    public void onItemClick(AdapterView<?> liste, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }

}
