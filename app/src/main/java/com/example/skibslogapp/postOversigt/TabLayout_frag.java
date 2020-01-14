package com.example.skibslogapp.postOversigt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.skibslogapp.R;
import com.example.skibslogapp.model.LogInstans;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TabLayout_frag extends Fragment {

    private static final String TAG = "TabLayout_frag";
    ArrayList<List<LogInstans>> etapper = new ArrayList<>();
    TabLayout tabLayout;
    AppBarLayout appBarLayout;

    public TabLayout_frag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        appBarLayout = view.findViewById(R.id.AppBarLayout);

        ArrayList<LogInstans> first = new ArrayList<>();
        first.add(new LogInstans("10:39", "asdf", "asdf", "asf", "asdf", "asdf"));
        first.add(new LogInstans("12:39", "1", "2", "3", "4", "5"));
        first.add(new LogInstans("10:39", "asdf", "asdf", "asf", "asdf", "asdf"));
        first.add(new LogInstans("12:39", "1", "2", "3", "4", "5"));
        first.add(new LogInstans("10:39", "asdf", "asdf", "asf", "asdf", "asdf"));
        first.add(new LogInstans("12:39", "1", "2", "3", "4", "5"));
        first.add(new LogInstans("10:39", "asdf", "asdf", "asf", "asdf", "asdf"));
        first.add(new LogInstans("12:39", "1", "2", "3", "4", "5"));
        first.add(new LogInstans("10:39", "asdf", "asdf", "asf", "asdf", "asdf"));
        first.add(new LogInstans("12:39", "1", "2", "3", "4", "5"));


        ArrayList<LogInstans> second = new ArrayList<>();
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        second.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));

        ArrayList<LogInstans> third = new ArrayList<>();
        third.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        third.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));
        third.add(new LogInstans("22:10", "qwer", "qwer", "qwer", "qwer", "qwer"));


        etapper.add(first);
        etapper.add(second);
        etapper.add(third);

        TabLayout tabLayout;
        final ViewPager viewPager;
        PageAdapter pageAdapter;

        Log.d(TAG, "onCreateView: Started.");

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

        return view;
    }

    public void closeTabs() {
        appBarLayout.setVisibility(View.GONE);
    }

    public void openTabs() {
        appBarLayout.setVisibility(View.VISIBLE);
    }
}
