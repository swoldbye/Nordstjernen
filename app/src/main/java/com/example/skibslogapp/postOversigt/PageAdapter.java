package com.example.skibslogapp.postOversigt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.skibslogapp.model.Logpunkt;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PageAdapter extends FragmentStatePagerAdapter {

    private List<List<Logpunkt>> etapper;
    private Map<Integer, Fragment> fragmentMap;

    PageAdapter(FragmentManager fragmentManager, List<List<Logpunkt>> etapper) {
        super(fragmentManager);
        this.etapper = etapper;
        fragmentMap = new HashMap<>();
        System.out.println("Count is : " + getCount());
    }

    /**
     * This decides which fragments to call from which tab
     *
     * @param position the position of the tab
     * @return The fragment you want to change
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        PostOversigt postOversigt = new PostOversigt(etapper.get(position));
        fragmentMap.put(position, postOversigt);
        return postOversigt;
    }

    @Override
    public int getCount() {
        return etapper.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "E" + (position + 1);
    }

    void updateList(List<List<Logpunkt>> newEtapper, int position, FragmentActivity FA) {
        this.etapper = newEtapper;

        PostOversigt existing = (PostOversigt) getFragment(position);
        RecyclerAdapter recyclerAdapter = (RecyclerAdapter) existing.getAdapter();
        recyclerAdapter.updateData(newEtapper.get(position));
        existing.postRecyclerView.smoothScrollToPosition(etapper.get(position).size() - 1);
    }

    Fragment getFragment(int position) {
        return fragmentMap.get(position);
    }
}