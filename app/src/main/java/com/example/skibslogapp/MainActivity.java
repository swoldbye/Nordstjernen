package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Switch;

import com.example.skibslogapp.Model.Togt;
import com.example.skibslogapp.Model.LogInstans;
import com.example.skibslogapp.viewControl.LogOversigt;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    EditText kursEditText, antalRoereEditText, editTime, vindHastighedEditTxt;
    Button fButton, øButton, n1Button, n2Button, n3Button;
    Button læ, ag, ha, fo, bi;
    Button opretButton;
    TextView vindretning_input;
    Button vindretning_delete;
    Switch sbBb;
    View mob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //Time
        editTime = (EditText) findViewById(R.id.editTime);
        resetTimeButton = (Button) findViewById(R.id.resetTimeButton);

        //Vind Retning
        nordButton = (Button) findViewById(R.id.nordButton);
        østButton = (Button) findViewById(R.id.østButton);
        sydButton = (Button) findViewById(R.id.sydButton);
        vestButton = (Button) findViewById(R.id.vestButton);

        //Vindhastighed
        vindHastighedEditTxt = (EditText) findViewById(R.id.vindhastighed_edittext);

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

        //Mand over bord
        mob = findViewById(R.id.mob_button);

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

        mob.setOnClickListener(this);


        editTime.setOnClickListener(this);
        resetTimeButton.setOnClickListener(this);

        vindretning_delete = findViewById(R.id.vindretning_delete);
        vindretning_delete.setOnClickListener(this);
        vindretning_delete.setVisibility(View.INVISIBLE);

        vindretning_input = findViewById(R.id.vindretning_input);
        vindretning_input.setText("");


        basicColor = getResources().getColor(R.color.grey);
        standOutColor = getResources().getColor(R.color.colorPrimary);

        //On Editor Listeners
        antalRoereEditText.setOnEditorActionListener(clearFocusOnDone);
        kursEditText.setOnEditorActionListener(clearFocusOnDone);
        editTime.setOnEditorActionListener(clearFocusOnDone);
        vindHastighedEditTxt.setOnEditorActionListener(clearFocusOnDone);

        final Handler handler =new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                if(!editTime.hasFocus()) {
                    handler.postDelayed(this, 1000);
                    String simpleDate3 = new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime());
                    editTime.setHint(simpleDate3);
                }
            }
        };
        handler.postDelayed(r, 0000);

        editTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) editTime.setHint("");
                else editTime.setHint(new SimpleDateFormat("kk:mm").format(Calendar.getInstance().getTime()));

                String time = editTime.getText().toString();
                //Accept 3 digit input
                if (time.length() == 1) {
                    if(Integer.parseInt(time.substring(0,1)) < 10) {
                        String newTime = "0" + time.substring(0,1) + ":";
                        editTime.setText(time = newTime.concat("00"));
                    }
                }
                if(time.length() == 3) {
                    editTime.setText(time.concat("00"));
                }
                if(time.length() == 4) {
                    if(Integer.parseInt(time.substring(0,1)) < 10) {
                        String newTime = "0" + time.substring(0,1) + ":" + time.substring(1,2) + time.substring(3,4);
                        editTime.setText(time = newTime);
                    }
                }
                //Control for correct input
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
                int timeStringLengthAfter = editTime.getText().toString().length();
                if(timeStringLengthAfter > timeStringLengthBefore && timeStringLengthAfter == 2) { //Insert colon
                    editTime.setText(getString(R.string.time_colon, editTime.getText()));
                    editTime.setSelection(3);
                }
                timeStringLengthBefore = timeStringLengthAfter;
            }
        });

        kursEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String s = kursEditText.getText().toString();
                try {
                    if(Integer.parseInt(s) <= 360) {
                        //Correct lesser inputs
                        if(s.length() == 1) s = "00".concat(s);
                        else if(s.length() == 2) s = "0".concat(s);
                        kursEditText.setText(s);
                    } else kursEditText.setText("");
                } catch (NumberFormatException e) {} //Do nothing

            }
        });


    }

    /**
     * Clears the focus when clicking the "Done" or "Next" button on the keyboard
     */
    TextView.OnEditorActionListener clearFocusOnDone = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                v.clearFocus(); //Clears focus, which cascade into it resetting through OnFocusChange()
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        }
    };

    /**
     * Code snippet taken from https://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside
     * Hides the keyboard when going out of focus. Should also work for fragments
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
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
        } else if (v == fButton) {                                      //sejlFøring
            if (finalSejlføring.equals("f")) {
                finalSejlføring = "";
                fButton.setBackgroundColor(basicColor);
                fButton.setTextColor(Color.parseColor("#000000"));
            } else {
                finalSejlføring = "f";
                øButton.setBackgroundColor(basicColor); øButton.setTextColor(Color.parseColor("#000000"));
                n1Button.setBackgroundColor(basicColor); n1Button.setTextColor(Color.parseColor("#000000"));
                n2Button.setBackgroundColor(basicColor); n2Button.setTextColor(Color.parseColor("#000000"));
                n3Button.setBackgroundColor(basicColor); n3Button.setTextColor(Color.parseColor("#000000"));
                fButton.setBackgroundColor(standOutColor); fButton.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (v == øButton) {
            if (finalSejlføring.equals("ø")) {
                finalSejlføring = "";
                øButton.setBackgroundColor(basicColor);
                øButton.setTextColor(Color.parseColor("#000000"));
            } else {
                finalSejlføring = "ø";
                fButton.setBackgroundColor(basicColor); fButton.setTextColor(Color.parseColor("#000000"));
                n1Button.setBackgroundColor(basicColor); n1Button.setTextColor(Color.parseColor("#000000"));
                n2Button.setBackgroundColor(basicColor); n2Button.setTextColor(Color.parseColor("#000000"));
                n3Button.setBackgroundColor(basicColor); n3Button.setTextColor(Color.parseColor("#000000"));
                øButton.setBackgroundColor(standOutColor); øButton.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (v == n1Button) {
            if (finalSejlføring.equals("n1")) {
                finalSejlføring = "";
                n1Button.setBackgroundColor(basicColor);
                n1Button.setTextColor(Color.parseColor("#000000"));
            } else {
                finalSejlføring = "n1";
                fButton.setBackgroundColor(basicColor); fButton.setTextColor(Color.parseColor("#000000"));
                øButton.setBackgroundColor(basicColor); øButton.setTextColor(Color.parseColor("#000000"));
                n2Button.setBackgroundColor(basicColor); n2Button.setTextColor(Color.parseColor("#000000"));
                n3Button.setBackgroundColor(basicColor); n3Button.setTextColor(Color.parseColor("#000000"));
                n1Button.setBackgroundColor(standOutColor); n1Button.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (v == n2Button) {
            if (finalSejlføring.equals("n2")) {
                finalSejlføring = "";
                n2Button.setBackgroundColor(basicColor);
                n2Button.setTextColor(Color.parseColor("#000000"));
            } else {
                finalSejlføring = "n2";
                fButton.setBackgroundColor(basicColor); fButton.setTextColor(Color.parseColor("#000000"));
                øButton.setBackgroundColor(basicColor); øButton.setTextColor(Color.parseColor("#000000"));
                n1Button.setBackgroundColor(basicColor); n1Button.setTextColor(Color.parseColor("#000000"));
                n3Button.setBackgroundColor(basicColor); n3Button.setTextColor(Color.parseColor("#000000"));
                n2Button.setBackgroundColor(standOutColor); n2Button.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (v == n3Button) {
            if (finalSejlføring.equals("n3")) {
                finalSejlføring = "";
                n3Button.setBackgroundColor(basicColor);
                n3Button.setTextColor(Color.parseColor("#000000"));
            } else {
                finalSejlføring = "n3";
                fButton.setBackgroundColor(basicColor); fButton.setTextColor(Color.parseColor("#000000"));
                øButton.setBackgroundColor(basicColor); øButton.setTextColor(Color.parseColor("#000000"));
                n1Button.setBackgroundColor(basicColor); n1Button.setTextColor(Color.parseColor("#000000"));
                n2Button.setBackgroundColor(basicColor); n2Button.setTextColor(Color.parseColor("#000000"));
                n3Button.setBackgroundColor(standOutColor); n3Button.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (v == læ) {
            if (sejlStilling.equals("læ")) {
                sejlStilling = "";
                læ.setBackgroundColor(basicColor);
                læ.setTextColor(Color.parseColor("#000000"));
            } else {
                sejlStilling = "læ";
                ag.setBackgroundColor(basicColor); ag.setTextColor(Color.parseColor("#000000"));
                ha.setBackgroundColor(basicColor); ha.setTextColor(Color.parseColor("#000000"));
                fo.setBackgroundColor(basicColor); fo.setTextColor(Color.parseColor("#000000"));
                bi.setBackgroundColor(basicColor); bi.setTextColor(Color.parseColor("#000000"));
                læ.setBackgroundColor(standOutColor); læ.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (v == ha) {
            if (sejlStilling.equals("ha")) {
                sejlStilling = "";
                ha.setBackgroundColor(basicColor);
                ha.setTextColor(Color.parseColor("#000000"));
            } else {
                sejlStilling = "ha";
                læ.setBackgroundColor(basicColor); læ.setTextColor(Color.parseColor("#000000"));
                ag.setBackgroundColor(basicColor); ag.setTextColor(Color.parseColor("#000000"));
                fo.setBackgroundColor(basicColor); fo.setTextColor(Color.parseColor("#000000"));
                bi.setBackgroundColor(basicColor); bi.setTextColor(Color.parseColor("#000000"));
                ha.setBackgroundColor(standOutColor); ha.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (v == fo) {
            if (sejlStilling.equals("fo")) {
                sejlStilling = "";
                fo.setBackgroundColor(basicColor);
                fo.setTextColor(Color.parseColor("#000000"));
            } else {
                sejlStilling = "fo";
                læ.setBackgroundColor(basicColor); læ.setTextColor(Color.parseColor("#000000"));
                ha.setBackgroundColor(basicColor); ha.setTextColor(Color.parseColor("#000000"));
                ag.setBackgroundColor(basicColor); ag.setTextColor(Color.parseColor("#000000"));
                bi.setBackgroundColor(basicColor); bi.setTextColor(Color.parseColor("#000000"));
                fo.setBackgroundColor(standOutColor); fo.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (v == bi) {
            if (sejlStilling.equals("bi")) {
                sejlStilling = "";
                bi.setBackgroundColor(basicColor);
                bi.setTextColor(Color.parseColor("#000000"));
            } else {
                sejlStilling = "bi";
                læ.setBackgroundColor(basicColor); læ.setTextColor(Color.parseColor("#000000"));
                ha.setBackgroundColor(basicColor); ha.setTextColor(Color.parseColor("#000000"));
                fo.setBackgroundColor(basicColor); fo.setTextColor(Color.parseColor("#000000"));
                ag.setBackgroundColor(basicColor); ag.setTextColor(Color.parseColor("#000000"));
                bi.setBackgroundColor(standOutColor); bi.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else if (v == ag) {
            if (sejlStilling.equals("ag")) {
                sejlStilling = "";
                ag.setBackgroundColor(basicColor);
                ag.setTextColor(Color.parseColor("#000000"));
            } else {
                sejlStilling = "ag";
                læ.setBackgroundColor(basicColor); læ.setTextColor(Color.parseColor("#000000"));
                ha.setBackgroundColor(basicColor); ha.setTextColor(Color.parseColor("#000000"));
                fo.setBackgroundColor(basicColor); fo.setTextColor(Color.parseColor("#000000"));
                bi.setBackgroundColor(basicColor); bi.setTextColor(Color.parseColor("#000000"));
                ag.setBackgroundColor(standOutColor); ag.setTextColor(Color.parseColor("#FFFFFF"));
            }

        } else if (v == opretButton || v == findViewById(R.id.mob_button)) {
            LogInstans nyeste = new LogInstans(finalVindRetning,
                    kursEditText.getText().toString(),
                    finalSejlføring.concat(" -" + styrbordEllerBagbord), sejlStilling);
            Togt.addLogPost(nyeste);
            Intent i = new Intent(this, LogOversigt.class);
            startActivity(i);


        }else if(v == resetTimeButton){
            editTime.setText("");
        }

    }
}
