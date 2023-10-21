package com.example.app_quan_li_chi_tieu.fragment.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_phanloai;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_user;
import com.example.app_quan_li_chi_tieu.user.user;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link infoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class infoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText edtName;
    private EditText edtTuoi;
    private EditText edtGioiTinh;
    private EditText edtNgheNghiep;
    private TextView tvInfo;
    private int idUser;
    DatabaseHelper_user dbUserHelper;
    DatabaseHelper_phanloai dbPhanLoaiHelper;
    DatabaseHelper_chitieu dbChitieuHelper;
    private boolean isInfoVisible = false;
    public infoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment infoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static infoFragment newInstance(String param1, String param2) {
        infoFragment fragment = new infoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbUserHelper = new DatabaseHelper_user(getContext());
//        tạo database
        dbUserHelper.onCreate(dbUserHelper.getWritableDatabase());
        dbUserHelper.getWritableDatabase();
        user user = dbUserHelper.getUser();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_info, container, false);
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        LinearLayout linearLayout = view.findViewById(R.id.layoutItem);
        edtName = view.findViewById(R.id.edt_name);
        edtTuoi = view.findViewById(R.id.edt_tuoi);
        edtGioiTinh = view.findViewById(R.id.edt_gioitinh);
        edtNgheNghiep = view.findViewById(R.id.edt_nghenghiep);
        tvInfo = view.findViewById(R.id.tv_info);

        System.out.println(dbUserHelper.getCount());
        user user = dbUserHelper.getUser();
        if (user != null) {
            edtName.setText(user.getName());
            edtTuoi.setText(user.getTuoi() + "");
            edtGioiTinh.setText(user.getGioitinh());
            edtNgheNghiep.setText(user.getNghenghiep());
            idUser = user.getId();

        }
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isInfoVisible) {
                    // Nếu thông tin đã hiển thị, ẩn nó
                    linearLayout.setVisibility(View.GONE);
                    isInfoVisible = false;
                } else {
                    // Nếu thông tin chưa hiển thị, hiển thị nó
                    linearLayout.setVisibility(View.VISIBLE);
                    isInfoVisible = true;
                }
            }
        });
        Button btnThem = view.findViewById(R.id.btn_add);
        Button btnupdate = view.findViewById(R.id.btn_update);
        Button btnhuy = view.findViewById(R.id.btn_cancel);

        btnThem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int tuoi = 0;
                String name = edtName.getText().toString();
                try {
                     tuoi = Integer.parseInt(edtTuoi.getText().toString());
                }catch (Exception e){
                     tuoi = 0;
                }
                String gioitinh = edtGioiTinh.getText().toString();
                String nghenghiep = edtNgheNghiep.getText().toString();
                dbUserHelper.insertData(name,tuoi,gioitinh,nghenghiep);
                btnThem.setVisibility(View.GONE);
                btnupdate.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                isInfoVisible = false;
//                hiển thị thông báo
                Toast.makeText(getContext(),"Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tuoi = 0;
                String name = edtName.getText().toString();
                try {
                    tuoi = Integer.parseInt(edtTuoi.getText().toString());
                }catch (Exception e){
                    tuoi = 0;
                }
                String gioitinh = edtGioiTinh.getText().toString();
                String nghenghiep = edtNgheNghiep.getText().toString();
                dbUserHelper.updateData(idUser,name,tuoi,gioitinh,nghenghiep);
                linearLayout.setVisibility(View.GONE);
                isInfoVisible = false;
//                hiển thị thông báo
                Toast.makeText(getContext(),"Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearLayout.setVisibility(View.GONE);
                isInfoVisible = false;
            }
        });
        if(dbUserHelper.getCount()<=0){
            btnThem.setVisibility(View.VISIBLE);
            btnupdate.setVisibility(View.GONE);
            btnhuy.setVisibility(View.VISIBLE);
        }else{
            btnThem.setVisibility(View.GONE);
            btnupdate.setVisibility(View.VISIBLE);
            btnhuy.setVisibility(View.VISIBLE);
        }

//        lấy thông tin nhập vào

TextView tv_delete = view.findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbUserHelper.deleteAllData();
                    dbPhanLoaiHelper = new DatabaseHelper_phanloai(getContext());
                    dbPhanLoaiHelper.deleteAllData();
                    dbChitieuHelper = new DatabaseHelper_chitieu(getContext());
                    dbChitieuHelper.deleteAllData();
                    edtName.setText("");
                    edtTuoi.setText("");
                    edtGioiTinh.setText("");
                    edtNgheNghiep.setText("");
                    linearLayout.setVisibility(View.GONE);
                    isInfoVisible = false;
                    btnupdate.setVisibility(View.GONE);
                    btnThem.setVisibility(View.VISIBLE);
//                    hiển thị thông báo
                    Toast.makeText(getContext(),"Xóa thành công", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
        return view;
    }
}