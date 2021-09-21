package com.apps.myturn.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.apps.myturn.Fragments.BankdetailsFragment;
import com.apps.myturn.Fragments.CompletedFragment;
import com.apps.myturn.Fragments.LiveFragment;
import com.apps.myturn.Fragments.UpcomingFragment;
import com.apps.myturn.PancardFragment;

public class ViewpagerverificationAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public ViewpagerverificationAdapter(FragmentManager fm, int tabCount) {
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
                PancardFragment tab1 = new PancardFragment();
                return tab1;
            case 1:
                BankdetailsFragment tab2 = new BankdetailsFragment();
                return tab2;

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
            title = "Pancard";
        else if (position == 1)
            title = "Bank Details";
        return title;
    }
}