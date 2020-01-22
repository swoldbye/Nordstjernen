package com.example.skibslogapp.postOversigt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.skibslogapp.GlobalContext;
import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Etape;
import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.view.OpretLog_frag;
import com.google.android.material.tabs.TabLayout;

/**
 * This class is a the fragment that contains the tab layout and the create Logpunkt button
 */
public class PostActivity extends Fragment {

    private Togt togt;
    Etape etape;
    private int startPos;

    private TabLayout_frag tabLayout_frag;

    public PostActivity(Etape etape, int startPos){
        TogtDAO togtDAO = new TogtDAO(GlobalContext.get());
        this.togt = togtDAO.getTogt(etape.getTogtId());
        this.etape = etape;
        this.startPos = startPos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_container, container, false);

        tabLayout_frag = new TabLayout_frag(togt, startPos);

        Button opretButton = view.findViewById(R.id.logpunktoversigt_opret);
        opretButton.setOnClickListener(v -> openOpretLogpunkt() );

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.tabOversigtContainerFrame, tabLayout_frag)
                .commit();

        return view;
    }

    /**
     *
     */
    private void openOpretLogpunkt(){
        OpretLog_frag fragment = new OpretLog_frag();

        getActivity().getSupportFragmentManager().beginTransaction()
            .setCustomAnimations(R.anim.slide_upslow2, R.anim.slide_upslow, R.anim.slide_downslow2, R.anim.slide_downslow)
            .add(R.id.opretPostContainerFrame, fragment)
            .addToBackStack(null)
            .commit();

        tabLayout_frag.toggleMinimize(true);
        fragment.onClosed(() -> tabLayout_frag.toggleMinimize(false));
    }

    /*
    We have to destroy the two fragments in order to coordinate measuring
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(tabLayout_frag)
                .commit();
    }
}
