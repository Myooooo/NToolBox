package com.moyang.ntoolbox;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BottomNavAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles;

    public BottomNavAdapter(FragmentManager fm) {
        super(fm);
    }

    public BottomNavAdapter(FragmentManager fm,List<Fragment> list,List<String> titles) {
        super(fm);
        this.fragments = list;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position%fragments.size());
    }

}