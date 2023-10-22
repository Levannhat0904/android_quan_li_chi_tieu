package com.example.app_quan_li_chi_tieu.fragment.thongke;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongkeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongkeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongkeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongkeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongkeFragment newInstance(String param1, String param2) {
        ThongkeFragment fragment = new ThongkeFragment();
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
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);
        // Tạo một đối tượng DatabaseHelper_chitieu
        DatabaseHelper_chitieu databaseHelper = new DatabaseHelper_chitieu(getContext());
        Button btnChiTieu = view.findViewById(R.id.btn_ct);
        Button btnThuNhap= view.findViewById(R.id.btn_tn);
        Button btnFilter = view.findViewById(R.id.btn_locct);
        Button btn_loctn=view.findViewById(R.id.btn_loctn);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFilter();
            }
        });
        btn_loctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFilter1();
            }
        });
        btnChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayChiTieuData();
                TextView textViewDS = getView().findViewById(R.id.ds);
                textViewDS.setText("Danh sách chi tiêu");
                onResume();
            }
        });
        btnThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayThuNhapData();
                TextView textViewDS = getView().findViewById(R.id.ds);
                textViewDS.setText("Danh sách thu nhập");
                onResume();
            }
        });
        return view;
    }
    private void showDialogFilter1(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_filter1);
        Button btnFilter = dialog.findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextMonth = dialog.findViewById(R.id.edittext_month);
                String month = editTextMonth.getText().toString().trim();
                displayFilteredData(month, "ThuNhap");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void showDialogFilter() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_filter);
        Button btnFilter = dialog.findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextMonth = dialog.findViewById(R.id.edittext_month);
                String month = editTextMonth.getText().toString().trim();
                displayFilteredData(month, "ChiTieu");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void displayFilteredData(String month, String type) {
        // Tạo đối tượng DatabaseHelper_chitieu
        DatabaseHelper_chitieu databaseHelper = new DatabaseHelper_chitieu(getContext());
        // Lấy dữ liệu từ cơ sở dữ liệu theo tháng và loại
        Cursor cursor = databaseHelper.getDataByMonthAndType(month, type);
        // Tạo một SimpleCursorAdapter để ánh xạ dữ liệu từ Cursor vào ListView
        String[] fromColumns = {DatabaseHelper_chitieu.COLUMN_PRICE, DatabaseHelper_chitieu.COLUMN_NOTE, DatabaseHelper_chitieu.COLUMN_DATE};
        int[] toViews = {R.id.txt_price, R.id.txt_note, R.id.txt_date};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.list_item, cursor, fromColumns, toViews, 0);
        // Ánh xạ ListView trong fragment_thongke.xml
        ListView listView = getView().findViewById(R.id.lv_tk);
        // Đặt adapter cho ListView để hiển thị dữ liệu
        listView.setAdapter(adapter);
        // Tính tổng giá trị sau khi hiển thị dữ liệu lọc
        int total = databaseHelper.getTotalPriceByMonthAndType(month, type);
        // Tìm và ánh xạ TextView "chitieu" và TextView "thunhap"
        TextView textViewChiTieu = getView().findViewById(R.id.chitieu);
        TextView textViewThuNhap = getView().findViewById(R.id.thunhap);
        // Định dạng lại tổng giá trị thành dạng 30.000
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedTotal = decimalFormat.format(total);
        // Hiển thị tổng giá trị lên TextView tương ứng
        if (type.equals("ChiTieu")) {
            textViewChiTieu.setText(formattedTotal);
        } else {
            textViewThuNhap.setText(formattedTotal);
        }
    }
    private void displayThuNhapData() {
        // Tạo đối tượng DatabaseHelper_chitieu
        DatabaseHelper_chitieu databaseHelper = new DatabaseHelper_chitieu(getContext());
        // Truy vấn dữ liệu từ cơ sở dữ liệu có loại "ThuNhap"
        Cursor cursor = databaseHelper.getAllDataByType("ThuNhap");
        // Tạo một SimpleCursorAdapter để ánh xạ dữ liệu từ Cursor vào ListView
        String[] fromColumns = {DatabaseHelper_chitieu.COLUMN_PRICE, DatabaseHelper_chitieu.COLUMN_NOTE,DatabaseHelper_chitieu.COLUMN_DATE};
        int[] toViews = {R.id.txt_price, R.id.txt_note,R.id.txt_date};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.list_item, cursor, fromColumns, toViews, 0);
        // Gán adapter vào ListView
        ListView listView = getView().findViewById(R.id.lv_tk);
        listView.setAdapter(adapter);
    }
    private void displayChiTieuData() {
        // Tạo đối tượng DatabaseHelper_chitieu
        DatabaseHelper_chitieu databaseHelper = new DatabaseHelper_chitieu(getContext());
        // Lấy dữ liệu từ cơ sở dữ liệu theo loại "ChiTieu"
        Cursor cursor = databaseHelper.getAllDataByType("ChiTieu");
        // Tạo một SimpleCursorAdapter để ánh xạ dữ liệu từ Cursor vào ListView
        String[] fromColumns = {DatabaseHelper_chitieu.COLUMN_PRICE, DatabaseHelper_chitieu.COLUMN_NOTE,DatabaseHelper_chitieu.COLUMN_DATE};
        int[] toViews = {R.id.txt_price, R.id.txt_note,R.id.txt_date};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(), R.layout.list_item, cursor, fromColumns, toViews, 0);
        // Ánh xạ ListView trong fragment_thongke.xml
        ListView listView = getView().findViewById(R.id.lv_tk);
        // Đặt adapter cho ListView để hiển thị dữ liệu
        listView.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        // Tạo một đối tượng DatabaseHelper_chitieu
        DatabaseHelper_chitieu databaseHelper = new DatabaseHelper_chitieu(getContext());
        // Tìm và ánh xạ TextView "chitieu" và TextView "thunhap"
        TextView textViewChiTieu = getView().findViewById(R.id.chitieu);
        TextView textViewThuNhap = getView().findViewById(R.id.thunhap);
        // Tính tổng giá trị cột "price" cho type là "chi tiêu"
        int totalChiTieu = databaseHelper.getTotalPriceByType("ChiTieu");
        // Tính tổng giá trị cột "price" cho type là "thu nhập"
        int totalThuNhap = databaseHelper.getTotalPriceByType("ThuNhap");
        // Định dạng lại tổng giá trị thành dạng 30.000
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedChiTieu = decimalFormat.format(totalChiTieu);
        String formattedThuNhap = decimalFormat.format(totalThuNhap);
        // Hiển thị tổng giá trị lên TextView tương ứng
        textViewChiTieu.setText(formattedChiTieu);
        textViewThuNhap.setText(formattedThuNhap);
    }
}