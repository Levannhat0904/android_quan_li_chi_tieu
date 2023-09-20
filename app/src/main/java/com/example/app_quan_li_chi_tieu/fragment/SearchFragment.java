package com.example.app_quan_li_chi_tieu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.app_quan_li_chi_tieu.Adapter_cat;
import com.example.app_quan_li_chi_tieu.Cat;
import com.example.app_quan_li_chi_tieu.R;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_search,container,false);

        int img[]={R.drawable.menu,R.drawable.menu,R.drawable.menu,R.drawable.menu};
        String name[]={"Hôm nay","Tuần này","Tháng này","Năm nay"};
        //khai bao listview
        ArrayList<Cat> catArrayList;
        ListView listView;
        Adapter_cat adapter_cat;
        listView = rootview.findViewById(R.id.listview);
        catArrayList = new ArrayList<>();
        for (int i=0;i<img.length;i++){
            Cat cat = new Cat(img[i],name[i]);
            catArrayList.add(cat);
        }
        adapter_cat = new Adapter_cat(getActivity(),R.layout.custom_lv,catArrayList);
        listView.setAdapter(adapter_cat);
        // Inflate the layout for this fragment
        return rootview;
        //return inflater.inflate(R.layout.fragment_search, container, false);
    }
}