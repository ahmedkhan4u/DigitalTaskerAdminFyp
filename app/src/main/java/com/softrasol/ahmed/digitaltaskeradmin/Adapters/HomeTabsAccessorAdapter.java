package com.softrasol.ahmed.digitaltaskeradmin.Adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeTabsAccessorAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> fragmentTitle = new ArrayList<>();

    public HomeTabsAccessorAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmentTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }
}
