package com.example.skibslogapp.viewControl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.skibslogapp.MainActivity;
import com.example.skibslogapp.Model.Togt;
import com.example.skibslogapp.R;

import java.util.ArrayList;

public class LogOversigt extends AppCompatActivity implements OnItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String[] lande = {"Danmark", "Norge", "Sverige", "Finland", "Holland", "Italien", "Tyskland",
                "Frankrig", "Spanien", "Portugal", "Nepal", "Indien", "Kina", "Japan", "Thailand"};

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.logoversigt, R.id.overskrift,(ArrayList<LogInstans>) Togt.getTogter());

        ListView logOversigt = new ListView(this);
        logOversigt.setOnItemClickListener(this);
        logOversigt.setAdapter(adapter);

        setContentView(logOversigt);
    }
    public void onItemClick(AdapterView<?> liste, View v, int position, long id) {
        Toast.makeText(this, "Klik på " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 101, Menu.NONE, "Settings").setIcon(android.R.drawable.ic_menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //for debugging: textView.append("\nonOptionsItemSelected called with" + item.getTitle());
        if (item.getItemId() == 101) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        return true;
    }
}
