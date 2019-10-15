package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.skibslogapp.Togt.togt;

//Developer Branch
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String finalVindRetning;
    String finalSejlføring;
    //button colors:
    int basicColor = Color.argb(255, 0,56,255);
    int standOutColor = Color.argb(255, 0,183,255);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vind Retning
        final Button nordButton = (Button)findViewById(R.id.nordButton);
        final Button østButton = (Button) findViewById(R.id.østButton);
        final Button sydButton = (Button) findViewById(R.id.sydButton);
        final Button vestButton = (Button) findViewById(R.id.vestButton);

        //Kurs
        final EditText kursEditText = (EditText)findViewById(R.id.kursEditText);

        //Sejl Stilling
        final EditText sejlStillingEditText = (EditText)findViewById(R.id.sejlStillingEditText);

        //Antal Roere
        final EditText antalRoereEditText = (EditText)findViewById(R.id.antalRoereEditText);

        //Sejlføring
        final Button fButton = (Button)findViewById(R.id.fButton);
        final Button øButton = (Button)findViewById(R.id.øButton);
        final Button n1Button = (Button)findViewById(R.id.n1Button);
        final Button n2Button = (Button)findViewById(R.id.n2Button);
        final Button n3Button = (Button)findViewById(R.id.n3Button);

        //Opret Post
        final Button opretButton = (Button)findViewById(R.id.opretButton);



        //Vind Retning Listener
        nordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(finalVindRetning.equals("nord")){
                    finalVindRetning = "";
                    nordButton.setBackgroundColor(basicColor);
                }else {
                    finalVindRetning = "nord";
                    nordButton.setBackgroundColor(standOutColor);
                    østButton.setBackgroundColor(basicColor);
                    sydButton.setBackgroundColor(basicColor);
                    vestButton.setBackgroundColor(basicColor);
                }
            }
        });
        østButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(finalVindRetning.equals("øst")){
                    finalVindRetning = "";
                    østButton.setBackgroundColor(basicColor);
                }else {
                    finalVindRetning = "øst";
                    østButton.setBackgroundColor(standOutColor);
                    nordButton.setBackgroundColor(basicColor);
                    sydButton.setBackgroundColor(basicColor);
                    vestButton.setBackgroundColor(basicColor);
                }
            }
        });
        sydButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(finalVindRetning.equals("syd")){
                    finalVindRetning = "";
                    sydButton.setBackgroundColor(basicColor);
                }else {
                    finalVindRetning = "syd";
                    sydButton.setBackgroundColor(standOutColor);
                    vestButton.setBackgroundColor(basicColor);
                    nordButton.setBackgroundColor(basicColor);
                    østButton.setBackgroundColor(basicColor);
                }
            }
        });
        vestButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(finalVindRetning.equals("vest")){
                    finalVindRetning = "";
                    vestButton.setBackgroundColor(basicColor);
                }else {
                    finalVindRetning = "vest";
                    vestButton.setBackgroundColor(standOutColor);
                    nordButton.setBackgroundColor(basicColor);
                    østButton.setBackgroundColor(basicColor);
                    sydButton.setBackgroundColor(basicColor);
                }
            }
        });

        //Sejlføring
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalSejlføring = "f";
                fButton.setBackgroundColor(standOutColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(basicColor);
            }
        });
        øButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalSejlføring = "ø";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(standOutColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(basicColor);
            }
        });
        n1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalSejlføring = "n1";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(standOutColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(basicColor);
            }
        });
        n2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalSejlføring = "n2";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(standOutColor);
                n3Button.setBackgroundColor(basicColor);
            }
        });
        n3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalSejlføring = "n3";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(standOutColor);
            }
        });

        opretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogInstans nyeste = new LogInstans(finalVindRetning,
                                                    kursEditText.getText().toString(),
                                                    finalSejlføring,
                                                    sejlStillingEditText.getText().toString());
                togt.add(nyeste);

                Intent i = new Intent(this, LogOversigt.class);
                startActivity(i);
            }
        });
    }
}
