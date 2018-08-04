package com.osm00apps.lrmah.synonymclusterforgre;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.osm00apps.lrmah.fragments.ClustersFragment;
import com.osm00apps.lrmah.fragments.MeaningsFragment;
import com.osm00apps.lrmah.fragments.NotesFragment;
import com.osm00apps.lrmah.fragments.SynonymsFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new MeaningsFragment();
            case 1:
                return new SynonymsFragment();

            case 2:
                return new ClustersFragment();
            case 3:
                return new NotesFragment();
                default:
              return new MeaningsFragment();
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}