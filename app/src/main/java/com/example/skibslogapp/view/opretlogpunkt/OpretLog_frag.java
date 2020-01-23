package com.example.skibslogapp.view.opretlogpunkt;

import android.Manifest;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skibslogapp.datalayer.local.EtapeDAO;
import com.example.skibslogapp.datalayer.local.LogpunktDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.positionregistration.PositionController;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.R;
import com.example.skibslogapp.view.logpunktinput.LogNote_frag;
import com.example.skibslogapp.view.logpunktinput.LogViewModel;
import com.example.skibslogapp.view.logpunktoversigt.LogpunktTabLayout;

import java.util.Calendar;
import java.util.Date;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Fragment to collect information of held fragments (from .../logpunktinput/) and save it to the database
 */
public class OpretLog_frag extends Fragment implements View.OnClickListener {
    private boolean mobIsDown = true; //Starts with the "Mand Over Bord" btn in the button of the page.
    private View mob;
    private PositionController coordinates;

    private Button opretButton;
    private LogViewModel logVM;
    private TextView closeButton;
    private Etape currentEtape;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        logVM = ViewModelProviders.of(getActivity()).get(LogViewModel.class);
        logVM.resetValues();
        super.onCreate(savedInstanceState);

        //Activate logging of coordinates. This is placed in onCreate to ensure that the logging will start at
        //The first time the logging is activated.
        coordinates = new PositionController(getActivity().getApplicationContext(), this);

        // Load etape of the current TabLayout
        EtapeDAO etapeDAO = new EtapeDAO(getContext());
        currentEtape = etapeDAO.getEtaper(LogpunktTabLayout.getCurrentTogt()).get(LogpunktTabLayout.getTabPos());
    }

    /**
     * Starts measuring the position of the user, if permission is granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED ){
            coordinates.startMeassureKoordinat();
            Toast.makeText(getActivity(), "Lokation er aktiveret", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Lokation er ikke aktiveret", Toast.LENGTH_SHORT).show();
            // Will return false if the user tabs "Bont ask me again/Permission denied".
            // Returns true if the user previusly rejected the message and now try to access it again. -> Indication of user confussion
            if (this.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(getActivity(), "Lokation skal være aktiveret for at GPS lokation kan logges. Klik \"CLOSE\" og \"OPEN\" for at acceptere lokation", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opretlog_frag, container, false);

        //Close Button
        closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(this);

        //"Man over board"-Floating Button
        mob = view.findViewById(R.id.mob_button);
        mob.setOnClickListener(this);

        //Create Post
        opretButton = view.findViewById(R.id.opretButton);
        opretButton.setOnClickListener(this);

        //Reference to implement listener
        LogNote_frag noteFrag = (LogNote_frag) getChildFragmentManager().findFragmentById(R.id.fragment_opretLog_note);
        if(noteFrag != null) noteFrag.setListener(this::toggleMOBPosition);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == opretButton || v == mob) {
            createLogpunkt(v == mob);
        } else if(v == closeButton){
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    /**
     * Sets the information from LogVM and gathered information on logpunkt and saves it to the database
     * @param mandOverBord  If called by pressing the "MOB"-button
     */
    private void createLogpunkt(boolean mandOverBord) {
        // Getting calender instance
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // Setting minutes and hour
        calendar.set(Calendar.HOUR_OF_DAY, logVM.getHours());
        calendar.set(Calendar.MINUTE, logVM.getMinutes());

        // Create Logpunkt from time in calendar
        Logpunkt logpunkt = new Logpunkt( new Date(calendar.getTimeInMillis()) );
        logpunkt.setInformation(logVM);
        logpunkt.setPosition(coordinates.getKoordinates());
        logpunkt.setMandOverBord(mandOverBord);

        //Save to database
        LogpunktDAO logpunktDAO = new LogpunktDAO(getContext());
        logpunktDAO.addLogpunkt(currentEtape, logpunkt);

        getActivity().getSupportFragmentManager().popBackStack();
    }

    /**
     * Toggles if the MOB-Floating Button is in the top or the bottom of the page
     * Needed to see when writing a note and possible other cases
     */
    private void toggleMOBPosition() {
        FrameLayout mob_container = getView().findViewById(R.id.mob_container);
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(mob_container.getLayoutParams());
        if (mobIsDown) {
            params.gravity = Gravity.TOP;
        } else {
            params.gravity = Gravity.BOTTOM;
        }
        mobIsDown = !mobIsDown;
        mob_container.setLayoutParams(params);
    }

    @Override
    public void onStart() {
        super.onStart();
        coordinates.startMeassureKoordinat(); //Start measuring location
    }

    @Override
    public void onStop() {
        super.onStop();
        coordinates.removeLocationUpdates(); //Stop measuring location
    }

    // Opret Log Callback --------------------------------------------------------------------------
    // Used to register when the OpretLog is closed
    public interface OpretLogCallback{
        void run();
    }
    private OpretLogCallback onClosedCallback = null;

    /**
     * Sets a callback to be run, the OpretLog fragment is closed */
    public void onClosed(OpretLogCallback onClosedCallback){
        this.onClosedCallback = onClosedCallback;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Run callback
        if( onClosedCallback != null ){
            onClosedCallback.run();
        }
    }
}