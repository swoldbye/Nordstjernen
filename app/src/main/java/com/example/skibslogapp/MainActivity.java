package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Switch;

import com.example.skibslogapp.Model.Togt;
import com.example.skibslogapp.Model.LogInstans;
import com.example.skibslogapp.viewControl.LogOversigt;
import com.example.skibslogapp.viewControl.utility.ToggleViewList;

import java.text.SimpleDateFormat;
import java.util.Calendar;


//Developer Branch
public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    int timeStringLengthBefore = 0;
    String finalVindRetning = "";
    String finalSejlføring = "";
    String styrbordEllerBagbord = "";
    String sejlStilling = "";

    //button colors:
    int basicColor;
    int standOutColor;
    Button resetTimeButton;
    Button nordButton, østButton, sydButton, vestButton;
    EditText kursEditText, antalRoereEditText, editTime;
    Button opretButton;
    TextView vindretning_input;
    Button vindretning_delete;
    Switch sbBb;

    ToggleButtonList hals_Buttons;
    ToggleButtonList sejlStilling_Buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Tidsslet
        editTime = (EditText) findViewById(R.id.editTime);
        final Handler handler =new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                handler.postDelayed(this, 1000);
                String simpleDate3 = new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime());
                editTime.setHint(simpleDate3);
            }
        };
        handler.postDelayed(r, 0000);

        editTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String time = editTime.getText().toString();
                if(time.length() != 5 || time.lastIndexOf(":") != time.indexOf(":") //Control of string
                    || Integer.parseInt(time.substring(0,2)) > 23 || Integer.parseInt(time.substring(3, 5)) > 59) { //Control of numbers
                    editTime.setText("");
                }
            }
        });

        editTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTime.getText().toString().length() > 0){
                    resetTimeButton.setVisibility(View.VISIBLE);
                } else resetTimeButton.setVisibility(View.INVISIBLE);
                int timeStringLengthAfter = editTime.getText().toString().length();
                if(timeStringLengthAfter > timeStringLengthBefore && timeStringLengthAfter == 2) { //Insert colon
                    editTime.setText(getString(R.string.time_colon, editTime.getText()));
                    editTime.setSelection(3);
                }
                timeStringLengthBefore = timeStringLengthAfter;
            }
        });



        //Reset Tidsslet
        resetTimeButton = (Button) findViewById(R.id.resetTimeButton);

        //Vind Retning
        nordButton = (Button) findViewById(R.id.nordButton);
        østButton = (Button) findViewById(R.id.østButton);
        sydButton = (Button) findViewById(R.id.sydButton);
        vestButton = (Button) findViewById(R.id.vestButton);

        //Kurs
        kursEditText = (EditText) findViewById(R.id.kursEditText);


        //Antal Roere
        antalRoereEditText = (EditText) findViewById(R.id.antalRoereEditText);


        //Opret Post
        opretButton = (Button) findViewById(R.id.opretButton);

        hals_Buttons = new ToggleButtonList(
            findViewById(R.id.hals_bagbord_btn),
            findViewById(R.id.hals_styrbord_btn)
        );

        sejlStilling_Buttons = new ToggleButtonList(
            findViewById(R.id.læ),
            findViewById(R.id.ag),
            findViewById(R.id.bi),
            findViewById(R.id.fo),
            findViewById(R.id.ha)
        );

        hals_Buttons = new ToggleButtonList(
            findViewById(R.id.fButton),
            findViewById(R.id.øButton),
            findViewById(R.id.n1Button),
            findViewById(R.id.n2Button),
            findViewById(R.id.n3Button)
        );


        //On click Listeners:
        nordButton.setOnClickListener(this);
        østButton.setOnClickListener(this);
        sydButton.setOnClickListener(this);
        vestButton.setOnClickListener(this);

        editTime.setOnClickListener(this);
        resetTimeButton.setOnClickListener(this);
        resetTimeButton.setVisibility(View.INVISIBLE);

        vindretning_delete = findViewById(R.id.vindretning_delete);
        vindretning_delete.setOnClickListener(this);
        vindretning_delete.setVisibility(View.INVISIBLE);

        vindretning_input = findViewById(R.id.vindretning_input);
        vindretning_input.setText("");


        basicColor = getResources().getColor(R.color.grey);
        standOutColor = getResources().getColor(R.color.colorPrimary);

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (sbBb.isChecked()) {
            styrbordEllerBagbord = "bb";
        } else {
            styrbordEllerBagbord = "sb";
        }

    }


    public void onClick(View v) {


        // Vindretning
        if (v == nordButton || v == østButton || v == sydButton || v == vestButton) {
            String currentInput = vindretning_input.getText().toString();

            if (currentInput.length() < 3) {
                currentInput += ((Button) v).getText().toString();
                vindretning_input.setText(currentInput);
                vindretning_delete.setVisibility(View.VISIBLE);
            }

        } else if (v == vindretning_delete) {
            vindretning_input.setText("");
            vindretning_delete.setVisibility(View.INVISIBLE);

        } else if (v == opretButton) {
            LogInstans nyeste = new LogInstans(finalVindRetning,
                    kursEditText.getText().toString(),
                    finalSejlføring.concat(" -" + styrbordEllerBagbord), sejlStilling);
            Togt.addLogPost(nyeste);
            Intent i = new Intent(this, LogOversigt.class);
            startActivity(i);


        }else if(v == resetTimeButton){
            editTime.setText("");
            resetTimeButton.setVisibility(View.INVISIBLE);
        }

    }


    /**
     * Implementation of the ToggleViewList specific for the buttons
     * on the log entry creation page. */
    private class ToggleButtonList extends ToggleViewList{

        /* This constructor is necessary, as the default
            parent class constructor takes 0 views. (ask Malte)*/
        ToggleButtonList(View ... views){
            super(views);
        }

        /**
         * What to do when a view (in this case button) is disabled
         * @param view The view (button) being disabled */
        @Override
        public void onViewUntoggled(View view) {
            Button btn = (Button) view;
            Resources res = btn.getContext().getResources();
            btn.setBackgroundTintList( res.getColorStateList(R.color.grey) );
            btn.setTextColor( res.getColorStateList(R.color.colorPrimary) );
        }

        /**
         * What to do when a view (in this case button) is enabled
         * @param view The view (button) being disabled */
        @Override
        public void onViewToggled(View view) {
            Button btn = (Button) view;
            Resources res = btn.getContext().getResources();
            btn.setBackgroundTintList( res.getColorStateList(R.color.colorAccent) );
            btn.setTextColor( Color.WHITE );
        }
    }
}
