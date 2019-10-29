//package com.example.skibslogapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Switch;
//
//import com.example.skibslogapp.Model.Togt;
//import com.example.skibslogapp.Model.LogInstans;
//import com.example.skibslogapp.viewControl.LogOversigt;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//
////Developer Branch
//public class MainActivityOld extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
//    int timeStringLengthBefore = 0;
//    String finalVindRetning = "";
//    String finalSejlføring = "";
//    String styrbordEllerBagbord = "";
//    String sejlStilling = "";
//
//    //button colors:
//    int basicColor;
//    int standOutColor;
//    Button resetTimeButton;
//    Button nordButton, østButton, sydButton, vestButton;
//    EditText kursEditText, antalRoereEditText, editTime;
//    Button fButton, øButton, n1Button, n2Button, n3Button;
//    Button læ, ag, ha, fo, bi;
//    Button opretButton;
//    TextView vindretning_input;
//    Button vindretning_delete;
//    Switch sbBb;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_old);
//
//        getSupportActionBar().hide();
//
//        //Tidsslet
//        editTime = (EditText) findViewById(R.id.editTime);
//        final Handler handler =new Handler();
//        final Runnable r = new Runnable() {
//            public void run() {
//                handler.postDelayed(this, 1000);
//                String simpleDate3 = new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime());
//                editTime.setHint(simpleDate3);
//            }
//        };
//        handler.postDelayed(r, 0000);
//
//        editTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                String time = editTime.getText().toString();
//                if(time.length() != 5 || time.lastIndexOf(":") != time.indexOf(":") //Control of string
//                    || Integer.parseInt(time.substring(0,2)) > 23 || Integer.parseInt(time.substring(3, 5)) > 59) { //Control of numbers
//                    editTime.setText("");
//                }
//            }
//        });
//
//        editTime.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(editTime.getText().toString().length() > 0){
//                    resetTimeButton.setVisibility(View.VISIBLE);
//                } else resetTimeButton.setVisibility(View.INVISIBLE);
//                int timeStringLengthAfter = editTime.getText().toString().length();
//                if(timeStringLengthAfter > timeStringLengthBefore && timeStringLengthAfter == 2) { //Insert colon
//                    editTime.setText(getString(R.string.time_colon, editTime.getText()));
//                    editTime.setSelection(3);
//                }
//                timeStringLengthBefore = timeStringLengthAfter;
//            }
//        });
//
//        //Reset Tidsslet
//        resetTimeButton = (Button) findViewById(R.id.resetTimeButton);
//
//        //Vind Retning
//        nordButton = (Button) findViewById(R.id.nordButton);
//        østButton = (Button) findViewById(R.id.østButton);
//        sydButton = (Button) findViewById(R.id.sydButton);
//        vestButton = (Button) findViewById(R.id.vestButton);
//
//        //Kurs
//        kursEditText = (EditText) findViewById(R.id.kursEditText);
//
//        //Sejl Stilling
//        læ = (Button) findViewById(R.id.læ);
//        ag = (Button) findViewById(R.id.ag);
//        bi = (Button) findViewById(R.id.bi);
//        fo = (Button) findViewById(R.id.fo);
//        ha = (Button) findViewById(R.id.ha);
//
//        //Antal Roere
//        antalRoereEditText = (EditText) findViewById(R.id.antalRoereEditText);
//
//        //Sejlføring
//
//        fButton = (Button) findViewById(R.id.fButton);
//        øButton = (Button) findViewById(R.id.øButton);
//        n1Button = (Button) findViewById(R.id.n1Button);
//        n2Button = (Button) findViewById(R.id.n2Button);
//        n3Button = (Button) findViewById(R.id.n3Button);
//
//        sbBb = (Switch) findViewById(R.id.switch1);
//        sbBb.setOnCheckedChangeListener(this);
//
//        //Opret Post
//        opretButton = (Button) findViewById(R.id.opretButton);
//
//        //On click Listeners:
//        nordButton.setOnClickListener(this);
//        østButton.setOnClickListener(this);
//        sydButton.setOnClickListener(this);
//        vestButton.setOnClickListener(this);
//        fButton.setOnClickListener(this);
//        øButton.setOnClickListener(this);
//        n1Button.setOnClickListener(this);
//        n2Button.setOnClickListener(this);
//        n3Button.setOnClickListener(this);
//        opretButton.setOnClickListener(this);
//        læ.setOnClickListener(this);
//        ha.setOnClickListener(this);
//        fo.setOnClickListener(this);
//        ag.setOnClickListener(this);
//        bi.setOnClickListener(this);
//
//        editTime.setOnClickListener(this);
//        resetTimeButton.setOnClickListener(this);
//        resetTimeButton.setVisibility(View.INVISIBLE);
//
//        vindretning_delete = findViewById(R.id.vindretning_delete);
//        vindretning_delete.setOnClickListener(this);
//        vindretning_delete.setVisibility(View.INVISIBLE);
//
//        vindretning_input = findViewById(R.id.vindretning_input);
//        vindretning_input.setText("");
//
//
//        basicColor = getResources().getColor(R.color.grey);
//        standOutColor = getResources().getColor(R.color.colorPrimary);
//
//    }
//
//
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (sbBb.isChecked()) {
//            styrbordEllerBagbord = "bb";
//        } else {
//            styrbordEllerBagbord = "sb";
//        }
//
//    }
//
//
//    public void onClick(View v) {
//
//
//        // Vindretning
//        if (v == nordButton || v == østButton || v == sydButton || v == vestButton) {
//            String currentInput = vindretning_input.getText().toString();
//
//            if (currentInput.length() < 3) {
//                currentInput += ((Button) v).getText().toString();
//                vindretning_input.setText(currentInput);
//                vindretning_delete.setVisibility(View.VISIBLE);
//            }
//
//
//        } else if (v == vindretning_delete) {
//            vindretning_input.setText("");
//            vindretning_delete.setVisibility(View.INVISIBLE);
//        } else if (v == fButton) {                                      //sejlFøring
//            if (finalSejlføring.equals("f")) {
//                finalSejlføring = "";
//                fButton.setBackgroundColor(basicColor);
//                fButton.setTextColor(Color.parseColor("#000000"));
//            } else {
//                finalSejlføring = "f";
//                øButton.setBackgroundColor(basicColor); øButton.setTextColor(Color.parseColor("#000000"));
//                n1Button.setBackgroundColor(basicColor); n1Button.setTextColor(Color.parseColor("#000000"));
//                n2Button.setBackgroundColor(basicColor); n2Button.setTextColor(Color.parseColor("#000000"));
//                n3Button.setBackgroundColor(basicColor); n3Button.setTextColor(Color.parseColor("#000000"));
//                fButton.setBackgroundColor(standOutColor); fButton.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//        } else if (v == øButton) {
//            if (finalSejlføring.equals("ø")) {
//                finalSejlføring = "";
//                øButton.setBackgroundColor(basicColor);
//                øButton.setTextColor(Color.parseColor("#000000"));
//            } else {
//                finalSejlføring = "ø";
//                fButton.setBackgroundColor(basicColor); fButton.setTextColor(Color.parseColor("#000000"));
//                n1Button.setBackgroundColor(basicColor); n1Button.setTextColor(Color.parseColor("#000000"));
//                n2Button.setBackgroundColor(basicColor); n2Button.setTextColor(Color.parseColor("#000000"));
//                n3Button.setBackgroundColor(basicColor); n3Button.setTextColor(Color.parseColor("#000000"));
//                øButton.setBackgroundColor(standOutColor); øButton.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//        } else if (v == n1Button) {
//            if (finalSejlføring.equals("n1")) {
//                finalSejlføring = "";
//                n1Button.setBackgroundColor(basicColor);
//                n1Button.setTextColor(Color.parseColor("#000000"));
//            } else {
//                finalSejlføring = "n1";
//                fButton.setBackgroundColor(basicColor); fButton.setTextColor(Color.parseColor("#000000"));
//                øButton.setBackgroundColor(basicColor); øButton.setTextColor(Color.parseColor("#000000"));
//                n2Button.setBackgroundColor(basicColor); n2Button.setTextColor(Color.parseColor("#000000"));
//                n3Button.setBackgroundColor(basicColor); n3Button.setTextColor(Color.parseColor("#000000"));
//                n1Button.setBackgroundColor(standOutColor); n1Button.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//        } else if (v == n2Button) {
//            if (finalSejlføring.equals("n2")) {
//                finalSejlføring = "";
//                n2Button.setBackgroundColor(basicColor);
//                n2Button.setTextColor(Color.parseColor("#000000"));
//            } else {
//                finalSejlføring = "n2";
//                fButton.setBackgroundColor(basicColor); fButton.setTextColor(Color.parseColor("#000000"));
//                øButton.setBackgroundColor(basicColor); øButton.setTextColor(Color.parseColor("#000000"));
//                n1Button.setBackgroundColor(basicColor); n1Button.setTextColor(Color.parseColor("#000000"));
//                n3Button.setBackgroundColor(basicColor); n3Button.setTextColor(Color.parseColor("#000000"));
//                n2Button.setBackgroundColor(standOutColor); n2Button.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//        } else if (v == n3Button) {
//            if (finalSejlføring.equals("n3")) {
//                finalSejlføring = "";
//                n3Button.setBackgroundColor(basicColor);
//                n3Button.setTextColor(Color.parseColor("#000000"));
//            } else {
//                finalSejlføring = "n3";
//                fButton.setBackgroundColor(basicColor); fButton.setTextColor(Color.parseColor("#000000"));
//                øButton.setBackgroundColor(basicColor); øButton.setTextColor(Color.parseColor("#000000"));
//                n1Button.setBackgroundColor(basicColor); n1Button.setTextColor(Color.parseColor("#000000"));
//                n2Button.setBackgroundColor(basicColor); n2Button.setTextColor(Color.parseColor("#000000"));
//                n3Button.setBackgroundColor(standOutColor); n3Button.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//        } else if (v == læ) {
//            if (sejlStilling.equals("læ")) {
//                sejlStilling = "";
//                læ.setBackgroundColor(basicColor);
//                læ.setTextColor(Color.parseColor("#000000"));
//            } else {
//                sejlStilling = "læ";
//                ag.setBackgroundColor(basicColor); ag.setTextColor(Color.parseColor("#000000"));
//                ha.setBackgroundColor(basicColor); ha.setTextColor(Color.parseColor("#000000"));
//                fo.setBackgroundColor(basicColor); fo.setTextColor(Color.parseColor("#000000"));
//                bi.setBackgroundColor(basicColor); bi.setTextColor(Color.parseColor("#000000"));
//                læ.setBackgroundColor(standOutColor); læ.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//        } else if (v == ha) {
//            if (sejlStilling.equals("ha")) {
//                sejlStilling = "";
//                ha.setBackgroundColor(basicColor);
//                ha.setTextColor(Color.parseColor("#000000"));
//            } else {
//                sejlStilling = "ha";
//                læ.setBackgroundColor(basicColor); læ.setTextColor(Color.parseColor("#000000"));
//                ag.setBackgroundColor(basicColor); ag.setTextColor(Color.parseColor("#000000"));
//                fo.setBackgroundColor(basicColor); fo.setTextColor(Color.parseColor("#000000"));
//                bi.setBackgroundColor(basicColor); bi.setTextColor(Color.parseColor("#000000"));
//                ha.setBackgroundColor(standOutColor); ha.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//        } else if (v == fo) {
//            if (sejlStilling.equals("fo")) {
//                sejlStilling = "";
//                fo.setBackgroundColor(basicColor);
//                fo.setTextColor(Color.parseColor("#000000"));
//            } else {
//                sejlStilling = "fo";
//                læ.setBackgroundColor(basicColor); læ.setTextColor(Color.parseColor("#000000"));
//                ha.setBackgroundColor(basicColor); ha.setTextColor(Color.parseColor("#000000"));
//                ag.setBackgroundColor(basicColor); ag.setTextColor(Color.parseColor("#000000"));
//                bi.setBackgroundColor(basicColor); bi.setTextColor(Color.parseColor("#000000"));
//                fo.setBackgroundColor(standOutColor); fo.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//        } else if (v == bi) {
//            if (sejlStilling.equals("bi")) {
//                sejlStilling = "";
//                bi.setBackgroundColor(basicColor);
//                bi.setTextColor(Color.parseColor("#000000"));
//            } else {
//                sejlStilling = "bi";
//                læ.setBackgroundColor(basicColor); læ.setTextColor(Color.parseColor("#000000"));
//                ha.setBackgroundColor(basicColor); ha.setTextColor(Color.parseColor("#000000"));
//                fo.setBackgroundColor(basicColor); fo.setTextColor(Color.parseColor("#000000"));
//                ag.setBackgroundColor(basicColor); ag.setTextColor(Color.parseColor("#000000"));
//                bi.setBackgroundColor(standOutColor); bi.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//        } else if (v == ag) {
//            if (sejlStilling.equals("ag")) {
//                sejlStilling = "";
//                ag.setBackgroundColor(basicColor);
//                ag.setTextColor(Color.parseColor("#000000"));
//            } else {
//                sejlStilling = "ag";
//                læ.setBackgroundColor(basicColor); læ.setTextColor(Color.parseColor("#000000"));
//                ha.setBackgroundColor(basicColor); ha.setTextColor(Color.parseColor("#000000"));
//                fo.setBackgroundColor(basicColor); fo.setTextColor(Color.parseColor("#000000"));
//                bi.setBackgroundColor(basicColor); bi.setTextColor(Color.parseColor("#000000"));
//                ag.setBackgroundColor(standOutColor); ag.setTextColor(Color.parseColor("#FFFFFF"));
//            }
//
//        } else if (v == opretButton) {
//            LogInstans nyeste = new LogInstans(finalVindRetning,
//                    kursEditText.getText().toString(),
//                    finalSejlføring.concat(" -" + styrbordEllerBagbord), sejlStilling);
//            Togt.addLogPost(nyeste);
//            Intent i = new Intent(this, LogOversigt.class);
//            startActivity(i);
//
//
//        }else if(v == resetTimeButton){
//            editTime.setText("");
//            resetTimeButton.setVisibility(View.INVISIBLE);
//        }
//
//    }
//}