package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Switch;

import com.example.skibslogapp.Model.Togt;
import com.example.skibslogapp.Model.LogInstans;
import com.example.skibslogapp.viewControl.LogOversigt;


//Developer Branch
public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    String finalVindRetning = "";
    String finalSejlføring = "";
    String styrbordEllerBagbord = "";
    String sejlStilling = "";
    //button colors:
    int basicColor = Color.argb(255, 255, 255, 255);
    int standOutColor = Color.argb(255, 0, 183, 255);
    Button nordButton, østButton, sydButton, vestButton;
    EditText kursEditText, antalRoereEditText;
    Button fButton, øButton, n1Button, n2Button, n3Button;
    Button læ, ag, ha, fo, bi;
    Button opretButton;
    TextView vindretning_input;
    Button vindretning_delete;
    Switch sbBb;


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
        læ = (Button) findViewById(R.id.læ);
        ag = (Button) findViewById(R.id.ag);
        bi = (Button) findViewById(R.id.bi);
        fo = (Button) findViewById(R.id.fo);
        ha = (Button) findViewById(R.id.ha);

        //Antal Roere
        antalRoereEditText = (EditText) findViewById(R.id.antalRoereEditText);

        //Sejlføring

        fButton = (Button) findViewById(R.id.fButton);
        øButton = (Button) findViewById(R.id.øButton);
        n1Button = (Button) findViewById(R.id.n1Button);
        n2Button = (Button) findViewById(R.id.n2Button);
        n3Button = (Button) findViewById(R.id.n3Button);

        sbBb = (Switch) findViewById(R.id.switch1);
        sbBb.setOnCheckedChangeListener(this);

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
        læ.setOnClickListener(this);
        ha.setOnClickListener(this);
        fo.setOnClickListener(this);
        ag.setOnClickListener(this);
        bi.setOnClickListener(this);

        vindretning_delete = findViewById(R.id.vindretning_delete);
        vindretning_delete.setOnClickListener(this);
        vindretning_delete.setVisibility(View.INVISIBLE);

        vindretning_input = findViewById(R.id.vindretning_input);
        vindretning_input.setText("");

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (sbBb.isChecked()) {
            styrbordEllerBagbord = "bb";
        }else{
            styrbordEllerBagbord = "sb";
        }

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
            if (finalVindRetning.equals("øst")) {
                finalVindRetning = "";
                østButton.setBackgroundColor(basicColor);
            } else {
                finalVindRetning = "øst";
                østButton.setBackgroundColor(standOutColor);
                nordButton.setBackgroundColor(basicColor);
                sydButton.setBackgroundColor(basicColor);
                vestButton.setBackgroundColor(basicColor);



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
            if (finalSejlføring.equals("f")) {
                finalSejlføring = "";
                fButton.setBackgroundColor(basicColor);
            } else {
                finalSejlføring = "f";
                fButton.setBackgroundColor(standOutColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(basicColor);
            }
        } else if (v == øButton) {
            if (finalSejlføring.equals("ø")) {
                finalSejlføring = "";
                øButton.setBackgroundColor(basicColor);
            } else {
                finalSejlføring = "ø";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(standOutColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(basicColor);
            }
        } else if (v == n1Button) {
            if (finalSejlføring.equals("n1")) {
                finalSejlføring = "";
                n1Button.setBackgroundColor(basicColor);
            } else {
                finalSejlføring = "n1";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(standOutColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(basicColor);
            }
        } else if (v == n2Button) {
            if (finalSejlføring.equals("n2")) {
                finalSejlføring = "";
                n2Button.setBackgroundColor(basicColor);
            } else {
                finalSejlføring = "n2";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(standOutColor);
                n3Button.setBackgroundColor(basicColor);
            }
        } else if (v == n3Button) {
            if (finalSejlføring.equals("n3")) {
                finalSejlføring = "";
                n3Button.setBackgroundColor(basicColor);
            } else {
                finalSejlføring = "n3";
                fButton.setBackgroundColor(basicColor);
                øButton.setBackgroundColor(basicColor);
                n1Button.setBackgroundColor(basicColor);
                n2Button.setBackgroundColor(basicColor);
                n3Button.setBackgroundColor(standOutColor);
            }
        } else if (v == læ) {
            if (sejlStilling.equals("læ")) {
                sejlStilling = "";
                læ.setBackgroundColor(basicColor);
            } else {
                sejlStilling = "læ";
                ag.setBackgroundColor(basicColor);
                ha.setBackgroundColor(basicColor);
                fo.setBackgroundColor(basicColor);
                bi.setBackgroundColor(basicColor);
                læ.setBackgroundColor(standOutColor);
            }
        } else if (v == ha) {
            if (sejlStilling.equals("ha")) {
                sejlStilling = "";
                ha.setBackgroundColor(basicColor);
            } else {
                sejlStilling = "ha";
                læ.setBackgroundColor(basicColor);
                ag.setBackgroundColor(basicColor);
                fo.setBackgroundColor(basicColor);
                bi.setBackgroundColor(basicColor);
                ha.setBackgroundColor(standOutColor);
            }
        }

        else if (v == fo) {
            if (sejlStilling.equals("fo")) {
                sejlStilling = "";
                fo.setBackgroundColor(basicColor);
            } else {
                sejlStilling = "fo";
                læ.setBackgroundColor(basicColor);
                ha.setBackgroundColor(basicColor);
                ag.setBackgroundColor(basicColor);
                bi.setBackgroundColor(basicColor);
                fo.setBackgroundColor(standOutColor);
            }
        }else if (v == bi) {
            if (sejlStilling.equals("bi")) {
                sejlStilling = "";
                bi.setBackgroundColor(basicColor);
            } else {
                sejlStilling = "bi";
                læ.setBackgroundColor(basicColor);
                ha.setBackgroundColor(basicColor);
                fo.setBackgroundColor(basicColor);
                ag.setBackgroundColor(basicColor);
                bi.setBackgroundColor(standOutColor);
            }
        } else if (v == ag) {
            if (sejlStilling.equals("ag")) {
                sejlStilling = "";
                ag.setBackgroundColor(basicColor);
            } else {
                sejlStilling = "ag";
                læ.setBackgroundColor(basicColor);
                ha.setBackgroundColor(basicColor);
                fo.setBackgroundColor(basicColor);
                bi.setBackgroundColor(basicColor);
                ag.setBackgroundColor(standOutColor);
            }
        }



        else if (v == opretButton) {
            LogInstans nyeste = new LogInstans(finalVindRetning,
                    kursEditText.getText().toString(),
                    finalSejlføring.concat(" -" + styrbordEllerBagbord), sejlStilling);
            Togt.addLogPost(nyeste);
            Intent i = new Intent(this, LogOversigt.class);
            startActivity(i);
        }

    }


}
