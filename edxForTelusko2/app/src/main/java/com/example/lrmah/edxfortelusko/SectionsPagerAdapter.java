package com.example.lrmah.edxfortelusko;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new CoursesFragment();
            case 1:
                return new DiscoveryFragment();

                default:
              return new CoursesFragment();
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}