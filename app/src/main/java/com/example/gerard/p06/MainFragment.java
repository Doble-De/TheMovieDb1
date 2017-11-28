package com.example.gerard.p06;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gerard on 28/11/2017.
 */

public class MainFragment extends Fragment {
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[] {
                    new PopularMoviesFragment(),
                    new TopRatedMoviesFragment(),
                    new UpcomingMoviesFragment(),
            };
            private final String[] mFragmentNames = new String[] {
                    getString(R.string.heading_popular),
                    getString(R.string.heading_top_rated),
                    getString(R.string.heading_upcoming)
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };

        ViewPager mViewPager = mView.findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = mView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return mView;
    }
}
