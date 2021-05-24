package com.variable.home_kode.Users.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.variable.home_kode.Users.FRAGMENTS.Detail_frg;
import com.variable.home_kode.Users.FRAGMENTS.Photos_FRG;
import com.variable.home_kode.Users.FRAGMENTS.Video_frg;

public class PageAdapter extends FragmentPagerAdapter {
    int tabcount;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new Detail_frg();
            case 1: return new Photos_FRG();
          //  case 2: return new Video_frg();
            default:  return null;
        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
