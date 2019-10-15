package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.skibslogapp.Model.Togt;
import com.example.skibslogapp.viewControl.LogInstans;
import com.example.skibslogapp.viewControl.LogOversigt;
import com.example.skibslogapp.viewControl.Settings;


//Developer Branch
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String finalVindRetning = "";
    String finalSejlføring = "";
    //button colors:
    int basicColor = Color.argb(255, 0, 56, 255);
    int standOutColor = Color.argb(255, 0, 183, 255);
    Button nordButton, østButton, sydButton, vestButton;
    EditText kursEditText, sejlStillingEditText, antalRoereEditText;
    Button fButton, øButton, n1Button, n2Button, n3Button;
    Button opretButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    public void onClick(View v) {
        if (v == nordButton) {                                    //Vind Retning
            if (finalVindRetning.equals("nord")) {
                finalVindRetning = "";
                nordButton.setBackgroundColor(basicColor);
            } else {
                finalVindRetning = "nord";
                nordButton.setBackgroundColor(standOutColor);
                østButton.setBackgroundColor(basicColor);
                sydButton.setBackgroundColor(basicColor);
                vestButton.setBackgroundColor(basicColor);
            }
        } else if (v == østButton) {
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

        } else if (v == sydButton) {
            if (finalVindRetning.equals("syd")) {
                finalVindRetning = "";
                sydButton.setBackgroundColor(basicColor);
            } else {
                finalVindRetning = "syd";
                sydButton.setBackgroundColor(standOutColor);
                vestButton.setBackgroundColor(basicColor);
                nordButton.setBackgroundColor(basicColor);
                østButton.setBackgroundColor(basicColor);
            }
        } else if (v == vestButton) {
            if (finalVindRetning.equals("vest")) {
                finalVindRetning = "";
                vestButton.setBackgroundColor(basicColor);
            } else {
                finalVindRetning = "vest";
                vestButton.setBackgroundColor(standOutColor);
                nordButton.setBackgroundColor(basicColor);
                østButton.setBackgroundColor(basicColor);
                sydButton.setBackgroundColor(basicColor);
            }
        } else if (v == fButton) {                                  //sejlFøring
            finalSejlføring = "f";
            fButton.setBackgroundColor(standOutColor);
            øButton.setBackgroundColor(basicColor);
            n1Button.setBackgroundColor(basicColor);
            n2Button.setBackgroundColor(basicColor);
            n3Button.setBackgroundColor(basicColor);
        } else if (v == øButton) {
            finalSejlføring = "ø";
            fButton.setBackgroundColor(basicColor);
            øButton.setBackgroundColor(standOutColor);
            n1Button.setBackgroundColor(basicColor);
            n2Button.setBackgroundColor(basicColor);
            n3Button.setBackgroundColor(basicColor);
        } else if (v == n1Button) {
            finalSejlføring = "n1";
            fButton.setBackgroundColor(basicColor);
            øButton.setBackgroundColor(basicColor);
            n1Button.setBackgroundColor(standOutColor);
            n2Button.setBackgroundColor(basicColor);
            n3Button.setBackgroundColor(basicColor);
        } else if (v == n2Button) {
            finalSejlføring = "n2";
            fButton.setBackgroundColor(basicColor);
            øButton.setBackgroundColor(basicColor);
            n1Button.setBackgroundColor(basicColor);
            n2Button.setBackgroundColor(standOutColor);
            n3Button.setBackgroundColor(basicColor);
        } else if (v == n3Button) {
            finalSejlføring = "n3";
            fButton.setBackgroundColor(basicColor);
            øButton.setBackgroundColor(basicColor);
            n1Button.setBackgroundColor(basicColor);
            n2Button.setBackgroundColor(basicColor);
            n3Button.setBackgroundColor(standOutColor);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 101, Menu.NONE, "Togt Oversigt");
        menu.add(Menu.NONE, 102, Menu.NONE, "Settings").setIcon(android.R.drawable.ic_menu_preferences).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu.add(Menu.NONE, 103, Menu.NONE, "Luk appen").setIcon(android.R.drawable.ic_menu_close_clear_cancel).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        //'Inflates' the xml menu I made.
        //getMenuInflater().inflate(R.menu.usemenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //for debugging: textView.append("\nonOptionsItemSelected called with" + item.getTitle());
        if (item.getItemId() == 101) {
            Toast.makeText(this, "Du viderstilles nu", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://javabog.dk"));
            startActivity(intent);

        } else if (item.getItemId() == 102) {

            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        } else if (item.getItemId() == 103) {
            Toast.makeText(this, "Du har valgt at lukke appen.", Toast.LENGTH_LONG).show();
            finish();

        }
        return true;
    }


}
