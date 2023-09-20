package com.example.app_quan_li_chi_tieu.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewpager2Adapter extends FragmentStateAdapter
{
    public MyViewpager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

            switch (position)
            {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new SearchFragment();
                case 2:
                    return new ThongkeFragment();
                case 3:
                    return new infoFragment();
                default:
                    return new HomeFragment();
            }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
