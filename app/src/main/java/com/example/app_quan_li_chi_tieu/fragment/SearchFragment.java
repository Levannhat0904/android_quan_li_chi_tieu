package com.example.app_quan_li_chi_tieu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.app_quan_li_chi_tieu.Adapter_cat;
import com.example.app_quan_li_chi_tieu.Cat;
import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.adapter_phanloai.viewpager_adapter_phanloai;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // Khởi tạo TabLayout và ViewPager2 từ layout
        TabLayout mTabLayout = rootView.findViewById(R.id.tab_layout_phanloai);
        ViewPager2 mViewPager2 = rootView.findViewById(R.id.viewPager_phanloai);

        // Khởi tạo adapter và thiết lập cho ViewPager2
        viewpager_adapter_phanloai adapter = new viewpager_adapter_phanloai(this);
        mViewPager2.setAdapter(adapter);

        // Kết nối TabLayout và ViewPager2 với nhau
        new TabLayoutMediator(mTabLayout, mViewPager2, (tab, position) -> {
            // Đặt tiêu đề cho từng tab tại vị trí tương ứng
            if (position == 0) {
                tab.setText("Chi tiêu");
            } else if (position == 1) {
                tab.setText("Thu nhập");
            }
        }).attach();

        return rootView;
    }
}