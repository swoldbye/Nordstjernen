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
 * A simple {@link Fragment} subclass.
 */
public class TabLayout_frag extends Fragment {

    private static final String TAG = "TabLayout_frag";

    TabLayout tabLayout;
    TabItem item1, item2, item3, item4, item5;
    ViewPager viewPager;
    PageAdapter pageAdapter;


    public TabLayout_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);

        Log.d(TAG, "onCreateView: Started.");

        tabLayout = view.findViewById(R.id.tabLayout);
        item1 = view.findViewById(R.id.tab1);
        item2 = view.findViewById(R.id.tab2);
        item3 = view.findViewById(R.id.tab3);
        item4 = view.findViewById(R.id.tab4);
        item5 = view.findViewById(R.id.tab5);
        viewPager = view.findViewById(R.id.viewPager);

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
