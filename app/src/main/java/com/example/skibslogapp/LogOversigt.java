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
    Togt togt = new Togt();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogInstans a = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans b = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans c = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans d = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans e = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans f = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans g = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans h = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans i = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans j = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans k = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans m = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans o = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans p = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);
        LogInstans q = new LogInstans("asd","dsgfdsgds","fff","dsad",togt);

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
