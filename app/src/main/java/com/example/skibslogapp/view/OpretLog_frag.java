package com.example.skibslogapp.view;


import android.Manifest;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.model.GlobalTogt;
import com.example.skibslogapp.model.Position.PositionController;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.R;
import com.example.skibslogapp.view.utility.KingButton;
import com.example.skibslogapp.view.utility.ToggleViewList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class OpretLog_frag extends Fragment implements View.OnClickListener {

    private int timeStringLengthBefore = 0;
    private String finalVindRetning = "";
    private String finalSejlføring = "";
    private String styrbordEllerBagbord = "";
    private String sejlStilling = "";

    //button colors:
    int basicColor;
    int standOutColor;

    Button resetTimeButton;
    Button vindNordBtn, vindØstBtn, vindSydBtn, vindVestBtn;
    KingButton fBtn, øBtn, n1Btn, n2Btn, n3Btn;
    Button nordButton_Strøm, østButton_Strøm, sydButton_Strøm, vestButton_Strøm;
    EditText kursEditText, antalRoereEditText, editTime,vindHastighedEditTxt, strømNingsretningEditText;
    TextView vindretning_input, strømretning_input;
    Button vindretning_delete, strømningsretning_delete;
    View mob;
    ToggleButtonList hals_Buttons;
    ToggleButtonList sejlStilling_Buttons;
    private ToggleButtonList sejlføring_Buttons;
    EditText noteEditText;
    PositionController testKoordinates;

    String simpleDate3;

    Button opretButton;

    GlobalStore model = new GlobalStore();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activat logging of coordinates. This is placed in onCreate to ensure that the logging will start at
        //The first time the logging is activated.
        testKoordinates = new PositionController(getActivity().getApplicationContext(), this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PERMISSION_GRANTED){
            testKoordinates.startMeassureKoordinat();
            Toast.makeText(getActivity(), "Lokation er aktiveret", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "Lokation er ikke aktiveret", Toast.LENGTH_SHORT).show();
            // Will retur false if the user tabs "Bont ask me again/Permission denied".
            // Returns true if the user previusly rejected the message and now try to access it again. -> Indication of user confussion
            if (this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(getActivity(), "Lokation skal være aktiveret for at GPS lokation kan logges. Klik \"CLOSE\" og \"OPEN\" for at acceptere lokation", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opret_log, container, false);

        //Tidsslet
        editTime = (EditText) view.findViewById(R.id.editTime);
        resetTimeButton = (Button) view.findViewById(R.id.resetTimeButton);

        //Vind Retning
        vindNordBtn = (Button) view.findViewById(R.id.nordButton);
        vindØstBtn = (Button) view.findViewById(R.id.østButton);
        vindSydBtn = (Button) view.findViewById(R.id.sydButton);
        vindVestBtn = (Button) view.findViewById(R.id.vestButton);

        //Strøm Retning
        nordButton_Strøm = (Button) view.findViewById(R.id.nordButton_strøm);
        østButton_Strøm = (Button) view.findViewById(R.id.østButton_strøm);
        sydButton_Strøm = (Button) view.findViewById(R.id.sydButton_strøm);
        vestButton_Strøm = (Button) view.findViewById(R.id.vestButton_strøm);

        //Kurs
        kursEditText = (EditText) view.findViewById(R.id.kursEditText);


        //Antal Roere
        antalRoereEditText = (EditText) view.findViewById(R.id.antalRoereEditText);

        //Vindhastighed
        vindHastighedEditTxt = view.findViewById(R.id.vindhastighed_edittext);

        //Strømningshastighed
        strømNingsretningEditText = view.findViewById(R.id.strømhastighed_edittext);
        //Opret Post
        opretButton = (Button) view.findViewById(R.id.opretButton);

        //Note
        noteEditText = (EditText) view.findViewById(R.id.noteEditText);
        noteEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                toggleMOBPosition(hasFocus);
            }
        });

        //Mand over bord
        mob = view.findViewById(R.id.mob_button);

        hals_Buttons = new ToggleButtonList(
            view.findViewById(R.id.hals_bagbord_btn),
            view.findViewById(R.id.hals_styrbord_btn)
        );

        sejlStilling_Buttons = new ToggleButtonList(
            view.findViewById(R.id.læ),
            view.findViewById(R.id.ag),
            view.findViewById(R.id.ha),
            view.findViewById(R.id.fo),
            view.findViewById(R.id.bi)
        );

        fBtn = view.findViewById(R.id.fButton);
        øBtn = view.findViewById(R.id.øButton);
        n1Btn = view.findViewById(R.id.n1Button);
        n2Btn = view.findViewById(R.id.n2Button);
        n3Btn = view.findViewById(R.id.n3Button);
        fBtn.createRelation(øBtn);
        n1Btn.createRelation(n2Btn);
        n1Btn.createRelation(n3Btn);
        n2Btn.createRelation(n3Btn);

        //On click Listeners:
        vindNordBtn.setOnClickListener(this);
        vindØstBtn.setOnClickListener(this);
        vindSydBtn.setOnClickListener(this);
        vindVestBtn.setOnClickListener(this);

        nordButton_Strøm.setOnClickListener(this);
        østButton_Strøm.setOnClickListener(this);
        sydButton_Strøm.setOnClickListener(this);
        vestButton_Strøm.setOnClickListener(this);

        mob.setOnClickListener(this);
        opretButton.setOnClickListener(this);

        editTime.setOnClickListener(this);
        resetTimeButton.setOnClickListener(this);

        vindretning_delete = view.findViewById(R.id.vindretning_delete);
        vindretning_delete.setOnClickListener(this);
        vindretning_delete.setVisibility(View.INVISIBLE);

        vindretning_input = view.findViewById(R.id.vindretning_input);
        vindretning_input.setText("");

        fBtn.setOnClickListener(this);
        øBtn.setOnClickListener(this);
        n1Btn.setOnClickListener(this);
        n2Btn.setOnClickListener(this);
        n3Btn.setOnClickListener(this);

        strømretning_input = view.findViewById(R.id.strøm_input);
        strømretning_input.setText("");

        strømningsretning_delete = view.findViewById(R.id.strøm_delete);
        strømningsretning_delete.setOnClickListener(this);
        strømningsretning_delete.setVisibility(View.INVISIBLE);

        basicColor = getResources().getColor(R.color.grey);
        standOutColor = getResources().getColor(R.color.colorPrimary);

        //On Editor Listeners
        antalRoereEditText.setOnEditorActionListener(clearFocusOnDone);
        kursEditText.setOnEditorActionListener(clearFocusOnDone);
        editTime.setOnEditorActionListener(clearFocusOnDone);
        vindHastighedEditTxt.setOnEditorActionListener(clearFocusOnDone);
        strømNingsretningEditText.setOnEditorActionListener(clearFocusOnDone);
        noteEditText.setOnEditorActionListener(clearFocusOnDone);

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
                        editTime.setText(time = "0".concat(time.substring(0,1).concat(":00")));
                    }
                }
                if(time.length() == 3) {
                    if (Integer.parseInt(time.substring(0,2)) < 24) {
                        editTime.setText(time = time.concat("00"));
                    }
                }
                if(time.length() == 4) {
                    if(Integer.parseInt(time.substring(0,1)) < 10) {
                        editTime.setText(time = "0".concat(time.substring(0,1).concat(":".concat(time.substring(1,2).concat(time.substring(3,4))))));
                    }
                    else if(Integer.parseInt(time.substring(0,2)) < 24) {
                        editTime.setText(time = time.substring(0,2).concat(":").concat(time.substring(3,4).concat("0")));
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

        return view;
    }




    /**
     * Clears the focus when clicking the "Done" or "Next" button on the keyboard
     */
    TextView.OnEditorActionListener clearFocusOnDone = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
            if(keyCode == EditorInfo.IME_ACTION_DONE || keyCode == EditorInfo.IME_ACTION_NEXT) {
                clearFocusOnDone(v);
            }
            return true;
        }
    };

    private void clearFocusOnDone(TextView v) {
        v.clearFocus(); //Clears focus, which cascade into it resetting through OnFocusChange()
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        toggleMOBPosition(getView().findViewById(R.id.noteEditText).hasFocus());
    }


    private void vindDirectionLogic(String currDirection, String btnDirection, String counterDirection) {
        if(!currDirection.contains(counterDirection)) {
            switch(currDirection.length()) {
                case 0:
                    vindretning_input.setText(btnDirection);
                    break;

                case 1:
                    if(btnDirection.equals("N") || btnDirection.equals("S")) vindretning_input.setText(btnDirection.concat(currDirection)); //Put in the front
                    else vindretning_input.setText(currDirection.concat(btnDirection)); //Put in the back
                    break;

                case 2:
                    if(currDirection.indexOf(btnDirection) == currDirection.lastIndexOf(btnDirection)) {
                        if(currDirection.contains(btnDirection)) vindretning_input.setText(btnDirection.concat(currDirection)); //Put in front
                        else if(btnDirection.equals("N") || btnDirection.equals("S"))
                            vindretning_input.setText(currDirection.substring(0,1).concat(btnDirection).concat(currDirection.substring(1,2))); //Put in the middle
                        else vindretning_input.setText(currDirection.concat(btnDirection)); //Put in the back
                    }
                    break;
            }
            vindretning_delete.setVisibility(View.VISIBLE);
        }
    }



    private void strømDirectionLogic(String currDirection, String btnDirection, String counterDirection) {
        if(!currDirection.contains(counterDirection)) {
            switch(currDirection.length()) {
                case 0:
                    strømretning_input.setText(btnDirection);
                    break;

                case 1:
                    if(btnDirection.equals("N") || btnDirection.equals("S")) strømretning_input.setText(btnDirection.concat(currDirection)); //Put in the front
                    else strømretning_input.setText(currDirection.concat(btnDirection)); //Put in the back
                    break;

                case 2:
                    if(currDirection.indexOf(btnDirection) == currDirection.lastIndexOf(btnDirection)) {
                        if(currDirection.contains(btnDirection)) strømretning_input.setText(btnDirection.concat(currDirection)); //Put in front
                        else if(btnDirection.equals("N") || btnDirection.equals("S"))
                            strømretning_input.setText(currDirection.substring(0,1).concat(btnDirection).concat(currDirection.substring(1,2))); //Put in the middle
                        else strømretning_input.setText(currDirection.concat(btnDirection)); //Put in the back
                    }
                    break;
            }
            strømningsretning_delete.setVisibility(View.VISIBLE);
        }
    }




    @Override
    public void onClick(View v) {

        LogOversigt_frag logOversigt_frag;
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        // Vindretning
        if(v == vindNordBtn) vindDirectionLogic(vindretning_input.getText().toString(), "N", "S");
        else if(v == vindØstBtn) vindDirectionLogic(vindretning_input.getText().toString(), "Ø", "V");
        else if(v == vindSydBtn) vindDirectionLogic(vindretning_input.getText().toString(), "S", "N");
        else if(v == vindVestBtn) vindDirectionLogic(vindretning_input.getText().toString(), "V", "Ø");

        else if (v == vindretning_delete) {
            vindretning_input.setText("");
            vindretning_delete.setVisibility(View.INVISIBLE);

        } else if(v == nordButton_Strøm) strømDirectionLogic(strømretning_input.getText().toString(), "N", "S");
        else if(v == østButton_Strøm) strømDirectionLogic(strømretning_input.getText().toString(), "Ø", "V");
        else if(v == sydButton_Strøm) strømDirectionLogic(strømretning_input.getText().toString(), "S", "N");
        else if(v == vestButton_Strøm) strømDirectionLogic(strømretning_input.getText().toString(), "V", "Ø");

        else if (v == strømningsretning_delete) {
            strømretning_input.setText("");
            strømningsretning_delete.setVisibility(View.INVISIBLE);
        }
        else if(v == fBtn || v == øBtn || v == n1Btn || v == n2Btn || v == n3Btn) {
            KingButton btn = (KingButton) v;
            btn.kingSelected();
        }
        else if (v == opretButton || v == mob) {

           //Test Observer with LiveData
            String newName = "This is a new name";
            model.getCurrentSkipper().setValue(newName);


            // Henter hals
            Button btn_styrbord = getView().findViewById(R.id.hals_styrbord_btn);
            Button pressedHals = hals_Buttons.getToggledView();
            String hals = "";
            if( pressedHals != null ){
                hals = "-";
                hals += pressedHals == btn_styrbord ? "sb" : "bb";
            }



            // Henter sejlføring
            String sejlføring = "";
                //Øverste sejldel
            if(fBtn.isSelected()) sejlføring += fBtn.getText().toString();
            else if(øBtn.isSelected()) sejlføring += øBtn.getText().toString();
                //Nederste sejl del
            if(n1Btn.isSelected()) sejlføring += sejlføring.length() > 0 ? "+" + n1Btn.getText().toString() : n1Btn.getText().toString();
            else if(n2Btn.isSelected()) sejlføring += sejlføring.length() > 0 ? "+" + n2Btn.getText().toString() : n2Btn.getText().toString();
            else if(n3Btn.isSelected()) sejlføring += sejlføring.length() > 0 ? "+" + n3Btn.getText().toString() : n3Btn.getText().toString();
                //Sætsammen med hals
            sejlføring += hals;

            // Henter sejlstilling
            String sejlstilling = "";
            Button pressedSejlstilling = sejlStilling_Buttons.getToggledView();
            if(pressedSejlstilling != null){
                sejlstilling = pressedSejlstilling.getText().toString();
            }

            String kursStr = kursEditText.getText().toString();


            // Fetching time ---------------------------------------------------------------

            String timeStr = editTime.getText().toString();
            if(timeStr.length() == 0){
                timeStr = editTime.getHint().toString();
            }

            // Getting calender instance
            Calendar calendar= Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            // Setting minutes and hour
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStr.substring(0, 2)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeStr.substring(3, 5)));

            // Create Logpunkt from time in calendar
            Logpunkt logpunkt = new Logpunkt( new Date(calendar.getTimeInMillis()) );
            logpunkt.setVindretning( vindretning_input.getText().toString() );
            logpunkt.setStroemRetning( strømretning_input.getText().toString() );
            logpunkt.setKurs( kursStr.equals("") ? -1 : Integer.parseInt(kursStr) );
            logpunkt.setSejlfoering( sejlføring );
            logpunkt.setSejlfoering( sejlstilling );
            logpunkt.setNote( noteEditText.getText().toString() );
            logpunkt.setPosition(testKoordinates.getKoordinates());

            LogpunktDAO logpunktDAO = new LogpunktDAO(getContext());
            logpunktDAO.addLogpunkt(GlobalTogt.getEtape(getContext()), logpunkt);

            System.out.printf("Created logpunkt: %s", logpunkt.toString());

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .remove(this)
                    .commit();

        }else if(v == resetTimeButton){
            editTime.setText("");
        }
    }

    public void toggleMOBPosition(boolean entering) {
        FrameLayout mob_container = getView().findViewById(R.id.mob_container);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(mob_container.getLayoutParams());
        if(entering) {
            params.gravity = Gravity.TOP;
        } else {
            params.gravity = Gravity.BOTTOM;
        }
        mob_container.setLayoutParams(params);
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
            btn.setBackgroundTintList( res.getColorStateList(R.color.colorPrimary) );
            btn.setTextColor( Color.WHITE );
        }

        /**
         * We know we're using buttons, so to make life easier, we convert
         * the views to buttons, before returning them         */
        @Override
        public Button getToggledView() {
            return (Button) super.getToggledView();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Measure koordinate");
       testKoordinates.startMeassureKoordinat();
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("Stopping measure koordinates");
      testKoordinates.removeLocationUpdates();
    }
}
