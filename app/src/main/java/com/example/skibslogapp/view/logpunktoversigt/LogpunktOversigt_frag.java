package com.example.skibslogapp.view.logpunktoversigt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.view.opretlogpunkt.OpretLog_frag;

/**
 * This class is a the fragment that contains the tab layout and the create Logpunkt button
 */
public class LogpunktOversigt_frag extends Fragment {

    private Togt togt;
    private int startPos;

    private LogpunktTabLayout logpunktTabLayout;

    public LogpunktOversigt_frag(Etape etape, int startPos){
        TogtDAO togtDAO = new TogtDAO(GlobalContext.get());
        this.togt = togtDAO.getTogt(etape.getTogtId());
        this.startPos = startPos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logpunktoversigt_frag, container, false);
        logpunktTabLayout = new LogpunktTabLayout(togt, startPos);

        Button opretButton = view.findViewById(R.id.logpunktoversigt_opret);
        opretButton.setOnClickListener(v -> openOpretLogpunkt() );

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.tabOversigtContainerFrame, logpunktTabLayout)
                .commit();
        return view;
    }

    /**
     * Method to open the opretLogpunkt fragment on the press of the "OPRET LOGPUNKT" button.
     */
    private void openOpretLogpunkt(){
        OpretLog_frag fragment = new OpretLog_frag();

        getActivity().getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(R.anim.slide_upslow2, R.anim.slide_upslow, R.anim.slide_downslow2, R.anim.slide_downslow)
            .add(R.id.opretPostContainerFrame, fragment)
            .addToBackStack(null)
            .commit();

        logpunktTabLayout.toggleMinimize(true);
        fragment.onClosed(() -> logpunktTabLayout.toggleMinimize(false));
    }

    /*
    We have to destroy the two fragments in order to coordinate measuring
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(logpunktTabLayout)
                .commit();
    }
}
