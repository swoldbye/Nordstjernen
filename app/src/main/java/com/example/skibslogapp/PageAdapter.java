package com.example.skibslogapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * TODO use FragmentStatePagerAdapter because we will be working with dynamic no. of tabs
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs; // The total number of tabs

    public PageAdapter(FragmentManager fragmentManager, int numOfTabs){
        super(fragmentManager);
        this.numOfTabs = numOfTabs;
    }

    /**
     * This decides which fragments to call from which tab
     *
     * @param position the position of the tab
     *
     * @return The fragment you want to change
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PostActivity();

            case 1:
                return new Day2();

            case 2:
                return new Day3();

            case 3:
                return new Day4();

            case 4:
                return new Day5();

             default:
                return null;
        }
    }

    /**
     * Returns the total number of tabs in the tab layout
     */
    @Override
    public int getCount() {
        return numOfTabs;
    }
}
