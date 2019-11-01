package com.example.skibslogapp.viewControl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.skibslogapp.MainActivity;
import com.example.skibslogapp.Model.LogInstans;
import com.example.skibslogapp.Model.Togt;
import com.example.skibslogapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PostOversigt extends AppCompatActivity implements View.OnClickListener{

    ListView postListView;
    Button opretButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_oversigt);
        getSupportActionBar().hide();

        postListView = findViewById(R.id.postListView);
        opretButton = findViewById(R.id.opretButton);
        opretButton.setOnClickListener(this);


        ArrayList<LogInstans> tempLogs = new ArrayList<>();
        tempLogs.add(new LogInstans("11:34", "SSØ", "005", "F", "LÆ"));
        tempLogs.add(new LogInstans("11:35", "N", "006", "Ø", "AG"));
        tempLogs.add(new LogInstans("11:37", "NØ", "026", "F", "BI"));
        tempLogs.add(new LogInstans("12:00", "SØ", "010", "F", "FO"));
        tempLogs.add(new LogInstans("12:32", "NV", "500", "N1", "HA"));
        tempLogs.add(new LogInstans("12:35", "NVN", "234", "F", "HA"));
        tempLogs.add(new LogInstans("12:50", "SSØ", "543", "N2", "LÆ"));
        tempLogs.add(new LogInstans("13:30", "SSØ", "345", "F", "FO"));
        tempLogs.add(new LogInstans("13:34", "SSØ", "453", "N#", "AG"));
        tempLogs.add(new LogInstans("14:00", "SSØ", "023", "F", "BI"));

        tempLogs.addAll(Togt.getTogter());

        PostListAdapter adapter = new PostListAdapter(this, R.layout.postlist_view_layout, tempLogs);
        postListView.setAdapter(adapter);
    }

    public void onClick(View view){
        if(view == opretButton){
            Intent intent = new Intent(this, MainActivity.class); //kan ikke skifte denne ud med settings :/
            startActivity(intent);
        }
    }
}
