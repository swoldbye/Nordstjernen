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

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Togt;
import com.example.skibslogapp.view.OpretLog_frag;
import com.google.android.material.tabs.TabLayout;

public class PostActivity extends Fragment implements View.OnClickListener {


    Button openButton;
    Togt togt;

    TabLayout_frag tabLayout_frag;
    OpretLog_frag opretLog_frag;
    FrameLayout opretPostContainerFrame;

    public PostActivity(Togt togt) {
        this.togt = togt;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_container, container, false);

        opretPostContainerFrame = view.findViewById(R.id.opretPostContainerFrame);
        openButton = view.findViewById(R.id.OpenButton);
        openButton.setOnClickListener(this);


        tabLayout_frag = new TabLayout_frag(togt);
        opretLog_frag = new OpretLog_frag();


        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.tabOversigtContainerFrame, tabLayout_frag)
                .commit();

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.opretPostContainerFrame, opretLog_frag)
                .commit();

        opretPostContainerFrame.setVisibility(View.GONE);


        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == openButton) {
//            if (openButton.getText().toString() == "open") {
//                expandPost();
//            } else {
//                closePost();
//            }

        }
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
}
