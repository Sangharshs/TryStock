package com.apps.myturn.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.apps.myturn.Fragments.CompletedFragment;
import com.apps.myturn.Fragments.LeaderboardFragment;
import com.apps.myturn.Fragments.LiveFragment;
import com.apps.myturn.Fragments.TeamFragment;
import com.apps.myturn.Fragments.TeamdetailsFragment;
import com.apps.myturn.Fragments.UpcomingFragment;

public class ResultviewpagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public ResultviewpagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                TeamFragment tab1 = new TeamFragment();
                return tab1;
            case 1:
                LeaderboardFragment tab2 = new LeaderboardFragment();
                return tab2;
            case 2:
                TeamdetailsFragment tab3 = new TeamdetailsFragment();
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Team";
        else if (position == 1)
            title = "Leaderboard";
        else if (position == 2)
            title = "Details";
        return title;
    }
}