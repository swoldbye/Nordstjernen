package com.example.skibslogapp.view.logpunktoversigt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.skibslogapp.model.Logpunkt;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LogpunktPageAdapter extends FragmentStatePagerAdapter {

    /**
     * A pagerAdapter to allow swiping in the tabLayout
     */

    private List<List<Logpunkt>> etapper;
    private Map<Integer, Fragment> fragmentMap;

    LogpunktPageAdapter(FragmentManager fragmentManager, List<List<Logpunkt>> etapper) {
        super(fragmentManager);
        this.etapper = etapper;
        fragmentMap = new HashMap<>();
        System.out.println("Count is : " + getCount());
    }

    /**
     * An overided function with the purpose of creating a fragment for each page in the viewPager.
     *
     * @param position the position of the tab
     * @return The fragment you want to change
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        LogpunktList postOversigt = new LogpunktList(etapper.get(position));
        fragmentMap.put(position, postOversigt);
        return postOversigt;
    }

    /**
     * A function that defines how many page there should be in the viewPager, and by extention the
     * tabLayout because the two are tied together.
     * @return
     */
    @Override
    public int getCount() {
        return etapper.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "E" + (position + 1);
    }

    /**
     * Function that is repsonible for updating the list. Gets called from LogpunkTabLayout on it's
     * TogtObserver.
     */
    void updateList(List<List<Logpunkt>> newEtapper, int position, FragmentActivity FA) {
        this.etapper = newEtapper;

        LogpunktList existing = (LogpunktList) getFragment(position);
        LogpunktAdapter logpunktAdapter = (LogpunktAdapter) existing.getAdapter();
        logpunktAdapter.updateData(newEtapper.get(position));

        int etapperSize = etapper.get(position).size();
        if( etapperSize > 0 ){
            existing.postRecyclerView.smoothScrollToPosition(  etapperSize - 1);
        }
    }

    Fragment getFragment(int position) {
        return fragmentMap.get(position);
    }

}