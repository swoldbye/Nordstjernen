package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.skibslogapp.Model.Togt;
import com.example.skibslogapp.Model.LogInstans;
import com.example.skibslogapp.viewControl.LogOversigt;


//Developer Branch
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String finalVindRetning = "";
    String finalSejlføring = "";
    //button colors:
    int basicColor = Color.argb(255, 255, 255, 255);
    int standOutColor = Color.argb(255, 0, 183, 255);
    Button nordButton, østButton, sydButton, vestButton;
    EditText kursEditText, sejlStillingEditText, antalRoereEditText;
    Button fButton, øButton, n1Button, n2Button, n3Button;
    Button opretButton;
    TextView vindretning_input;
    Button vindretning_delete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Vind Retning
        nordButton = (Button) findViewById(R.id.nordButton);
        østButton = (Button) findViewById(R.id.østButton);
        sydButton = (Button) findViewById(R.id.sydButton);
        vestButton = (Button) findViewById(R.id.vestButton);

        //Kurs
        kursEditText = (EditText) findViewById(R.id.kursEditText);

        //Sejl Stilling
        sejlStillingEditText = (EditText) findViewById(R.id.sejlStillingEditText);

        //Antal Roere
        antalRoereEditText = (EditText) findViewById(R.id.antalRoereEditText);

        //Sejlføring

        fButton = (Button) findViewById(R.id.fButton);
        øButton = (Button) findViewById(R.id.øButton);
        n1Button = (Button) findViewById(R.id.n1Button);
        n2Button = (Button) findViewById(R.id.n2Button);
        n3Button = (Button) findViewById(R.id.n3Button);

        //Opret Post
        opretButton = (Button) findViewById(R.id.opretButton);

        //On click Listeners:
        nordButton.setOnClickListener(this);
        østButton.setOnClickListener(this);
        sydButton.setOnClickListener(this);
        vestButton.setOnClickListener(this);
        fButton.setOnClickListener(this);
        øButton.setOnClickListener(this);
        n1Button.setOnClickListener(this);
        n2Button.setOnClickListener(this);
        n3Button.setOnClickListener(this);
        opretButton.setOnClickListener(this);

        vindretning_delete = findViewById(R.id.vindretning_delete);
        vindretning_delete.setOnClickListener(this);
        vindretning_delete.setVisibility(View.INVISIBLE);

        vindretning_input = findViewById(R.id.vindretning_input);
        vindretning_input.setText("");
    }


    public void onClick(View v) {



        if( v == nordButton || v == østButton || v == sydButton || v == vestButton) {
            String currentInput = vindretning_input.getText().toString();

            if (currentInput.length() < 3) {
                currentInput += ((Button) v).getText().toString();
                vindretning_input.setText(currentInput);
                vindretning_delete.setVisibility(View.VISIBLE);
            }

        }else if(v == vindretning_delete){
            vindretning_input.setText("");
            vindretning_delete.setVisibility(View.INVISIBLE);
        } else if (v == fButton) {                                      //sejlFøring
            if(finalSejlføring.equals("f")){
                finalSejlføring = "";
                fButton.setBackgroundColor(basicColor);
            }else {
                finalSejlføring = "f";
                fButton.setBackgroundColor(standOutColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(basicColor);
            }
        } else if (v == øButton) {
            if(finalSejlføring.equals("ø")){
                finalSejlføring = "";
                øButton.setBackgroundColor(basicColor);
            }else {
                finalSejlføring = "ø";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(standOutColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(basicColor);
            }
        } else if (v == n1Button) {
            if(finalSejlføring.equals("n1")){
                finalSejlføring = "";
                n1Button.setBackgroundColor(basicColor);
            }else {
                finalSejlføring = "n1";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(standOutColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(basicColor);
            }
        } else if (v == n2Button) {
            if(finalSejlføring.equals("n2")){
                finalSejlføring = "";
                n2Button.setBackgroundColor(basicColor);
            }else {
                finalSejlføring = "n2";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(standOutColor);
                n3Button.setBackgroundColor(basicColor);
            }
        } else if (v == n3Button) {
            if(finalSejlføring.equals("n3")){
                finalSejlføring = "";
                n3Button.setBackgroundColor(basicColor);
            }else {
                finalSejlføring = "n3";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(standOutColor);
            }
        } else if (v == opretButton) {
            LogInstans nyeste = new LogInstans(finalVindRetning,
                    kursEditText.getText().toString(),
                    finalSejlføring,
                    sejlStillingEditText.getText().toString());
            Togt.addLogPost(nyeste);
            Intent i = new Intent(this, LogOversigt.class);
            startActivity(i);
        }
    }







}
