package com.moyang.ntoolbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.tab_layout_view_pager);

        initView();
        return view;
    }

    private void initView(){

        titles.clear();
        fragments.clear();

        titles.add("物品");
        titles.add("日期");
        titles.add("交易");
        titles.add("事件");

        fragments.add(new ItemListFragment());
        fragments.add(new DateListFragment());
        fragments.add(new MoneyListFragment());
        fragments.add(new EventListFragment());

        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(title);
            tabLayout.addTab(tab);
        }

        BottomNavAdapter adapter = new BottomNavAdapter(getChildFragmentManager(), fragments,titles);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void setCurrentPage(int n){
        viewPager.setCurrentItem(n);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
