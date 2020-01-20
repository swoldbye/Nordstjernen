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

public class PostActivity extends Fragment implements View.OnClickListener {


    Button openButton;
    Togt togt;
    Etape etape;
    int startPos;

    TabLayout_frag tabLayout_frag;
    OpretLog_frag opretLog_frag;
    FrameLayout opretPostContainerFrame;

    public PostActivity(Etape etape, int startPos){
        TogtDAO togtDAO = new TogtDAO(GlobalContext.get());
        Togt tempTogt = togtDAO.getTogt(etape.getTogtId());
        this.togt = tempTogt;
        this.etape = etape;
        this.startPos = startPos;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_container, container, false);

        opretPostContainerFrame = view.findViewById(R.id.opretPostContainerFrame);


        tabLayout_frag = new TabLayout_frag(togt, startPos);
        opretLog_frag = new OpretLog_frag();


        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.tabOversigtContainerFrame, tabLayout_frag)
                .commit();

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.opretPostContainerFrame, opretLog_frag)
                .commit();

        //opretPostContainerFrame.setVisibility(View.GONE);


        return view;
    }

    @Override
    public void onClick(View v) {

    }

    private void expandPost() {
        opretPostContainerFrame.setVisibility(View.VISIBLE);

        tabLayout_frag.closeTabs();
        openButton.setText("close");
    }

    private void closePost() {
        opretPostContainerFrame.setVisibility(View.GONE);
        tabLayout_frag.openTabs();
        openButton.setText("open");
    }


    /*
    We have to destroy the two fragments in order to coordinate maesuring
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(opretLog_frag)
                .remove(tabLayout_frag)
                .commit();
    }
}
