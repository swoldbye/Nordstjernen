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

public class OpretLog_frag extends Fragment implements View.OnClickListener {
    private boolean mobIsDown = true; //Starts with the "Mand Over Bord" btn in the button of the page.
    private View mob;
    private PositionController testCoordinates;

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
        testCoordinates = new PositionController(getActivity().getApplicationContext(), this);

        // Load etape of the current TabLayout
        EtapeDAO etapeDAO = new EtapeDAO(getContext());
        currentEtape = etapeDAO.getEtaper(LogpunktTabLayout.getCurrentTogt()).get(LogpunktTabLayout.getTabPos());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED ){
            testCoordinates.startMeassureKoordinat();
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
        closeButton = view.findViewById(R.id.closeButton);

        //Opret Post
        opretButton = view.findViewById(R.id.opretButton);

        //Reference to implement listener
        LogNote_frag noteFrag = (LogNote_frag) getChildFragmentManager().findFragmentById(R.id.fragment_opretLog_note);
        noteFrag.setListener(this::toggleMOBPosition);

        //Mand over bord
        mob = view.findViewById(R.id.mob_button);

        mob.setOnClickListener(this);
        opretButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == opretButton || v == mob) {
            createLogpunkt(v == mob);
        }else if(v == closeButton){
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void createLogpunkt(boolean mandOverBord) {
        // Fetching time ---------------------------------------------------------------
        String timeStr = logVM.getTime();

        // Getting calender instance
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // Setting minutes and hour
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeStr.substring(0, 2)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeStr.substring(3, 5)));

        // Create Logpunkt from time in calendar
        Logpunkt logpunkt = new Logpunkt( new Date(calendar.getTimeInMillis()) );
        logpunkt.setInformation(logVM);
        logpunkt.setPosition(testCoordinates.getKoordinates());
        logpunkt.setMandOverBord(mandOverBord);

        LogpunktDAO logpunktDAO = new LogpunktDAO(getContext());
        logpunktDAO.addLogpunkt(currentEtape, logpunkt);

        System.out.printf("Created logpunkt: %s", logpunkt.toString());

        getActivity().getSupportFragmentManager().popBackStack();
    }

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
        testCoordinates.startMeassureKoordinat();
    }

    @Override
    public void onStop() {
        super.onStop();
        testCoordinates.removeLocationUpdates();
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