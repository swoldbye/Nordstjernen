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
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.skibslogapp.DbTranslator;
import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.TogtDAO;
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
public class TabLayout_frag extends Fragment implements TogtDAO.TogtObserver {

    private static final String TAG = "TabLayout_frag";
    List<List<Logpunkt>> etapper = new ArrayList<>();
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    static Togt togt;
    static ViewPager viewPager;
    int startPos;
    PageAdapter pageAdapter;


    public TabLayout_frag(Togt togt, int startPos) {
        this.togt = togt;
        this.startPos = startPos;
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


        TabLayout tabLayout;

        Log.d(TAG, "onCreateView: Started.");

        //get logs
        DbTranslator dbTranslator = new DbTranslator(getContext());
        etapper = dbTranslator.getList(togt);
        System.out.println("Size of first = " + etapper.size());

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = view.findViewById(R.id.viewPager);

//        for (int i = 0; i < days; i++){
//            tabLayout.addTab(tabLayout.newTab());
//        }

        TogtDAO.addTogtObserver(this);

        pageAdapter = new PageAdapter(getFragmentManager(), etapper.size(), etapper);
        viewPager.setAdapter(pageAdapter);


        //links the viewpager with the tablayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //tabLayout.setTabsFromPagerAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(startPos);

    }

    public void toggleMinimize(boolean toggle){
        PostOversigt currentPage = (PostOversigt) pageAdapter.getFragment(viewPager.getCurrentItem());
        currentPage.toggleMinimize(toggle);
        appBarLayout.setVisibility( toggle ? View.GONE : View.VISIBLE);
    }


    public void closeTabs() {
        appBarLayout.setVisibility(View.GONE);
    }

    public void openTabs() {
        appBarLayout.setVisibility(View.VISIBLE);
    }

    public static int getTabPos(){
        return viewPager.getCurrentItem();
    }

    public static Togt getCurrentTogt(){
        return togt;
    }





    @Override
    public void onUpdate(Togt togt2) {
        DbTranslator dbTranslator = new DbTranslator(getContext());
        this.togt = togt2;

        etapper = dbTranslator.getList(togt2);
//        System.out.println("Size of new array is: " + etapper.size());
//        //pageAdapter.notifyDataSetChanged();
        pageAdapter.updateList(etapper, viewPager.getCurrentItem(), getActivity());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        TogtDAO.removeTogtObserver(this);
    }
}
