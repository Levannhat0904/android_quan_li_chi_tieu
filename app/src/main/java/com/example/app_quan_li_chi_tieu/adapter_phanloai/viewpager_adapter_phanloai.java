package com.example.app_quan_li_chi_tieu.adapter_phanloai;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_quan_li_chi_tieu.fragment.phanloai.ChitieuFragment;
import com.example.app_quan_li_chi_tieu.fragment.ThunhapFragment;

public class viewpager_adapter_phanloai extends FragmentStateAdapter {
    public viewpager_adapter_phanloai(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ChitieuFragment();
            case 1:
                return  new ThunhapFragment();
            default:
                return  new ChitieuFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
