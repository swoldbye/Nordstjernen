package com.example.skibslogapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

/**
 *
 */
public class TabLayout_frag extends Fragment {

    private static final String TAG = "TabLayout_frag";

    public TabLayout_frag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        TabLayout tabLayout;
        ViewPager viewPager;
        PageAdapter pageAdapter;
        //int days = 8;

        Log.d(TAG, "onCreateView: Started.");

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

//        for (int i = 0; i < days; i++){
//            tabLayout.addTab(tabLayout.newTab());
//        }

        pageAdapter = new PageAdapter(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0){
                    //Do something else if needed like change color etc.
                }else if (tab.getPosition() == 1){
                    //Do something else if needed
                }else if (tab.getPosition() == 2){
                    //Do something else if needed
                }else if (tab.getPosition() == 3){
                    //Do something else if needed
                }else if (tab.getPosition() == 4){
                    //Do something else if needed
                } // add more if statements or delete them all if we dont need them.
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //links the viewpager with the tablayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setTabsFromPagerAdapter(pageAdapter);
        return view;
    }

}
