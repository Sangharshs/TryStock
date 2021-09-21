package com.apps.myturn.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.apps.myturn.Fragments.CompletedFragment;
import com.apps.myturn.Fragments.LiveFragment;
import com.apps.myturn.Fragments.UpcomingFragment;

public class ViewpagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public ViewpagerAdapter(FragmentManager fm, int tabCount) {
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
                UpcomingFragment tab1 = new UpcomingFragment();
                return tab1;
            case 1:
                LiveFragment tab2 = new LiveFragment();
                return tab2;
            case 2:
                CompletedFragment tab3 = new CompletedFragment();
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
            title = "Upcoming";
        else if (position == 1)
            title = "Live";
        else if (position == 2)
            title = "Completed";
        return title;
    }
}