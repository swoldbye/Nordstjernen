package com.example.skibslogapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * TODO use FragmentStatePagerAdapter because we will be working with dynamic no. of tabs
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs; // The total number of tabs
    Fragment fragment = null;

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

//        for (int i = 0; i < numOfTabs; i++) {
//            if (i == position) {
//                fragment = PostActivity.newInstance();
//                break;
//            }
//        }
//        return fragment;
        PostActivity postActivity = new PostActivity();
        return postActivity;

//        return PostActivity.newInstance(position);
/*
        switch (position) {
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

            case 5:
                return new Day6();

            case 6:
                return new Day7();

            case 7:
                return new Day8();

            case 8:
                return new Day9();

            case 9:
                return new Day10();

            default:
                return null;

        }

 */
    }

    /**
     * Returns the total number of tabs in the tab layout
     */
    @Override
    public int getCount() {
        return 5;
    }


    /**
     * Set a title for each tab with a position
     *
     * @param position The position of the tab
     * @return  the title
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "Dag " + (position + 1);
    }
}