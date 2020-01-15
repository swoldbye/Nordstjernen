package com.example.skibslogapp.postOversigt;

import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.skibslogapp.DbTranslator;
import com.example.skibslogapp.R;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class TabLayout_frag extends Fragment {

    private static final String TAG = "TabLayout_frag";
    List<List<Logpunkt>> etapper = new ArrayList<>();
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    DbTranslator dbTranslator;
    Togt togt;

    public TabLayout_frag(Togt togt) {
        this.togt = togt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        appBarLayout = view.findViewById(R.id.AppBarLayout);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



//
//        ArrayList<Logpunkt> first = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Logpunkt logpunkt = new Logpunkt();
//            logpunkt.setDate(new Date(System.currentTimeMillis()));
//            logpunkt.setKurs(100);
//            logpunkt.setSejlfoering("ag");
//            logpunkt.setNote("Et eller andet.");
//            logpunkt.setSejlfoering("NNØ");
//            logpunkt.setNote("Dette er en note");
//            first.add(logpunkt);
//        }
//
//        ArrayList<Logpunkt> second = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            Logpunkt logpunkt = new Logpunkt();
//            logpunkt.setDate(new Date(System.currentTimeMillis()));
//            logpunkt.setKurs(100);
//            logpunkt.setSejlfoering("ag");
//            logpunkt.setNote("Et eller andet.");
//            logpunkt.setSejlfoering("NNØ");
//            logpunkt.setNote("Dette er en note");
//            second.add(logpunkt);
//        }
//        ArrayList<Logpunkt> third = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Logpunkt logpunkt = new Logpunkt();
//            logpunkt.setDate(new Date(System.currentTimeMillis()));
//            logpunkt.setKurs(100);
//            logpunkt.setSejlfoering("ag");
//            logpunkt.setNote("Et eller andet.");
//            logpunkt.setSejlfoering("NNØ");
//            logpunkt.setNote("Dette er en note");
//            third.add(logpunkt);
//        }
//
//        etapper.add(first);
//        etapper.add(second);
//        etapper.add(third);

        TabLayout tabLayout;
        final ViewPager viewPager;
        PageAdapter pageAdapter;

        Log.d(TAG, "onCreateView: Started.");

        //get logs
        dbTranslator = new DbTranslator(getContext());
        etapper = dbTranslator.getList(togt);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

//        for (int i = 0; i < days; i++){
//            tabLayout.addTab(tabLayout.newTab());
//        }

        pageAdapter = new PageAdapter(getFragmentManager(), etapper.size(), etapper);
        viewPager.setAdapter(pageAdapter);

//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 0){
//                    //Do something else if needed like change color etc.
//                }else if (tab.getPosition() == 1){
//                    //Do something else if needed
//                }else if (tab.getPosition() == 2){
//                    //Do something else if needed
//                }else if (tab.getPosition() == 3){
//                    //Do something else if needed
//                }else if (tab.getPosition() == 4){
//                    //Do something else if needed
//                } // add more if statements or delete them all if we dont need them.
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        //links the viewpager with the tablayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //tabLayout.setTabsFromPagerAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void closeTabs() {
        appBarLayout.setVisibility(View.GONE);
    }

    public void openTabs() {
        appBarLayout.setVisibility(View.VISIBLE);
    }
}
