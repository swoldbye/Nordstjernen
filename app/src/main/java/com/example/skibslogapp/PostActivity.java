package com.example.skibslogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skibslogapp.model.LogInstans;
import com.example.skibslogapp.view.OpretLog_frag;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class PostActivity extends Fragment implements PostOversigt.OnPostOversigtListener, OpretLog_frag.OnMainActivityListener {

    private static final String TAG = "PostActivity";

    OpretLog_frag opretLog_frag;
    PostOversigt postOversigt = new PostOversigt(this);

    TabLayout tabLayout;
    TabItem item1, item2, item3, item4, item5;
    ViewPager viewPager;
    PageAdapter pageAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_post, container, false);

        Log.d(TAG, "onCreateView: Started.");

//        tabLayout = view.findViewById(R.id.tabLayout);
//        item1 = view.findViewById(R.id.tab1);
//        item2 = view.findViewById(R.id.tab2);
//        item3 = view.findViewById(R.id.tab3);
//        item4 = view.findViewById(R.id.tab4);
//        item5 = view.findViewById(R.id.tab5);
//        viewPager = view.findViewById(R.id.viewPager);
//
//        pageAdapter = new PageAdapter(getFragmentManager(),tabLayout.getTabCount());
//        viewPager.setAdapter(pageAdapter);

        addOversigtFrag();
        return view;
    }


    private void addOversigtFrag(){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(R.id.postOversigtContainerFrame, postOversigt).commit();
    }

    @Override
    public void hideOpretPost() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(opretLog_frag)
                .commit();
    }

    @Override
    public void showOpretPost() {
        opretLog_frag = new OpretLog_frag(this);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.opretPostContainerFrame, opretLog_frag)
                .commit();
    }

    @Override
    public void updateList(LogInstans nyeste) {
        postOversigt.setList(nyeste);
    }
}
