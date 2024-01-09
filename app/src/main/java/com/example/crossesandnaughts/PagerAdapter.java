package com.example.crossesandnaughts;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int num;

    public PagerAdapter(FragmentManager fm, int num )
    {
        super( fm, num);
        this.num = num;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        ScoresFragment tab = new ScoresFragment();;
        switch ( position )
        {
            case 0:
                tab.setTabId(0);
                break;
            case 1:
                tab.setTabId(1);
                break;
            case 2:
                tab.setTabId(2);
                break;
            default:

        }

        return tab;
    }

    @Override
    public int getCount() {
        return num;
    }

}
