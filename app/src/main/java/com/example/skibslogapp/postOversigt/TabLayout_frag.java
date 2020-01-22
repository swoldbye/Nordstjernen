package com.example.skibslogapp.postOversigt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.skibslogapp.R;
import com.example.skibslogapp.datalayer.local.TogtDAO;
import com.example.skibslogapp.model.Logpunkt;
import com.example.skibslogapp.model.Togt;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TabLayout_frag extends Fragment implements TogtDAO.TogtObserver {

    private List<List<Logpunkt>> etapper = new ArrayList<>();
    private AppBarLayout appBarLayout;
    private static Togt togt;
    private static ViewPager viewPager;
    private int startPos;
    private PageAdapter pageAdapter;


    TabLayout_frag(Togt togt, int startPos) {
        TabLayout_frag.togt = togt;
        this.startPos = startPos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_layout, container, false);
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

        // Get Logs
        DbTranslator dbTranslator = new DbTranslator(getContext());
        etapper = dbTranslator.getList(togt);

        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = view.findViewById(R.id.viewPager);

        TogtDAO.addTogtObserver(this);

        pageAdapter = new PageAdapter(getFragmentManager(), etapper);
        viewPager.setAdapter(pageAdapter);

        //links the viewpager with the tablayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(startPos);

    }

    public void toggleMinimize(boolean toggle){
        PostOversigt currentPage = (PostOversigt) pageAdapter.getFragment(viewPager.getCurrentItem());
        currentPage.toggleMinimize(toggle);
        appBarLayout.setVisibility( toggle ? View.GONE : View.VISIBLE);
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
        TabLayout_frag.togt = togt2;
        etapper = dbTranslator.getList(togt2);
        pageAdapter.updateList(etapper, viewPager.getCurrentItem(), getActivity());
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        TogtDAO.removeTogtObserver(this);
    }
}
