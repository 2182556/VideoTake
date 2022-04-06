package com.videotake.UI.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.videotake.R;
import com.videotake.UI.Adapters.HomeFragmentsAdapter;

public class HomeFragment extends Fragment {
    private final String TAG_NAME = HomeFragment.class.getSimpleName();
    HomeFragmentsAdapter collectionAdapter;
    ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        collectionAdapter = new HomeFragmentsAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(collectionAdapter);
        collectionAdapter.addFragment(new DiscoverFragment());
        collectionAdapter.addFragment(new SearchFragment());
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if(position == 0)
                tab.setText(R.string.discover);
            else
                tab.setText(R.string.search);
        }).attach();
    }
}

