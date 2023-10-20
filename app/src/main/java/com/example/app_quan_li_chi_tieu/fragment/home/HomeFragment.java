package com.example.app_quan_li_chi_tieu.fragment.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.app_quan_li_chi_tieu.Chi_tieu.ChiTieu;
import com.example.app_quan_li_chi_tieu.Chi_tieu.ChiTieuAdapter;
import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.DanhMuc.Category;
import com.example.app_quan_li_chi_tieu.DanhMuc.CategoryAdapter;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_phanloai;
import com.example.app_quan_li_chi_tieu.fragment.phanloai.Them_chi_tieu;
import com.example.app_quan_li_chi_tieu.fragment.phanloai.ThunhapFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private DatabaseHelper_chitieu dbHelper_chitieu;
    private ChiTieuAdapter chiTieuAdapter;
//    DatabaseHelper_chitieu db;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public void onResume() {
        super.onResume();
        loadCategoryData();
    }

    public static HomeFragment newInstance() {

        return new HomeFragment();

    }
    private void initializeDatabaseHelper() {
        dbHelper_chitieu = new DatabaseHelper_chitieu(getActivity());
        chiTieuAdapter = new ChiTieuAdapter(getActivity(), new ArrayList<>());

        dbHelper_chitieu.getWritableDatabase(); // Mở cơ sở dữ liệu ở đây
        loadCategoryData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        loadCategoryData();
        // Inflate the layout for this fragment
        initializeDatabaseHelper();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        hiển thị listview
        ListView listView = rootView.findViewById(R.id.lv_home);
        listView.setAdapter(chiTieuAdapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            ChiTieu chitieu = chiTieuAdapter.getItem(position);

                // Hiển thị dialog với các tùy chọn sửa hoặc xóa danh mục
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Tùy chọn danh mục");
                builder.setItems(new CharSequence[]{"Sửa", "Xóa"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
//                                goi bottom sheet
                                int id_chitieu = chitieu.getId();
                                CustomBottomSheetDialog_home_chinhsua bottomSheetDialog = new CustomBottomSheetDialog_home_chinhsua(view.getContext(), id_chitieu);
                                // Bây giờ, bạn cần gắn nội dung (layout) cho BottomSheetDialog.
                                // Ví dụ, bạn có thể sử dụng LayoutInflater để nạp một tệp layout XML:
                                View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_view_home_chinh_sua, null);
                                // Gắn layout cho BottomSheetDialog
                                bottomSheetDialog.setContentView(bottomSheetView);

                                // Hiển thị BottomSheetDialog
                                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        loadCategoryData();
                                    }
                                });
                                bottomSheetDialog.show();

                                break;
                            case 1:
                                // Sử dụng category.getId() để biết danh mục nào đang được xóa
                                int id = chitieu.getId();
                                dbHelper_chitieu.deleteExpense(id);
                                loadCategoryData();
                                break;
                        }
                    }
                });
                builder.show();
                return true;
        });
        rootView.findViewById(R.id.button_thaotac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một BottomSheetDialog
                CustomBottomSheetDialog_home bottomSheetDialog = new CustomBottomSheetDialog_home(view.getContext());

                // Bây giờ, bạn cần gắn nội dung (layout) cho BottomSheetDialog.
                // Ví dụ, bạn có thể sử dụng LayoutInflater để nạp một tệp layout XML:
                View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_view_home, null);
                // Gắn layout cho BottomSheetDialog
                bottomSheetDialog.setContentView(bottomSheetView);

                // Hiển thị BottomSheetDialog
                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        loadCategoryData();
                    }
                });
                bottomSheetDialog.show();
            }
        });

        return rootView;
    }
    private void loadCategoryData() {
        List<ChiTieu> ChiTieuList = getChiTieuDataFromSQLite();
        chiTieuAdapter.clear();
        chiTieuAdapter.addAll(ChiTieuList);
        chiTieuAdapter.notifyDataSetChanged();
    }

    private List<ChiTieu> getChiTieuDataFromSQLite() {
        List<ChiTieu> ChiTieuList = new ArrayList<>();
        SQLiteDatabase db_chitieu = dbHelper_chitieu.getReadableDatabase();

        try {
            Cursor cursor = db_chitieu.rawQuery("SELECT * FROM " + DatabaseHelper_chitieu.TABLE_NAME, null);
//
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_ID));
                int price = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_PRICE));
                int cat_id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_CAT_ID)); // Thay đổi tên cột
                String dateString = cursor.getString(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_DATE));
//                Date date = new Date(dateString); // Chuyển đổi ngày tháng từ chuỗi sang đối tượng Date
                String note = cursor.getString(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_NOTE));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_TYPE));
                ChiTieu chiTieu = new ChiTieu(id, price, dateString, cat_id, note, type);
                ChiTieuList.add(chiTieu);
            }

            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db_chitieu.close();
        }

        return ChiTieuList;
    }

}