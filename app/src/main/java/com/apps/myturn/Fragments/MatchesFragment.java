package com.apps.myturn.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.myturn.Adapters.ViewpagerAdapter;
import com.apps.myturn.R;
import com.google.android.material.tabs.TabLayout;

public class MatchesFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    View view;
    ViewPager viewPager2;
    ViewpagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_matches, container, false);
        viewPager2 = view.findViewById(R.id.viewpager22);
        tabLayout  = view.findViewById(R.id.tabs);

        ViewpagerAdapter adapter = new ViewpagerAdapter(getParentFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager2.setAdapter(adapter);

        // It is used to join TabLayout with ViewPager.
        tabLayout.setupWithViewPager(viewPager2);

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager2.setCurrentItem(1);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}