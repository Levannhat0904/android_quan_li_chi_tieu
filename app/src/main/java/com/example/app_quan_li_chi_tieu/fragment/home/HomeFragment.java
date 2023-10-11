package com.example.app_quan_li_chi_tieu.fragment.home;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.category.Category;
import com.example.app_quan_li_chi_tieu.category.CategoryAdapter;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;
import com.example.app_quan_li_chi_tieu.fragment.phanloai.CustomBottomSheetDialog;

import java.util.ArrayList;
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
    private DatabaseHelper_chitieu dbHelper;
    private CategoryAdapter categoryAdapter;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chitieu, container, false);

        rootView.findViewById(R.id.button_thaotac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một BottomSheetDialog
                CustomBottomSheetDialog bottomSheetDialog = new CustomBottomSheetDialog(view.getContext());

                // Bây giờ, bạn cần gắn nội dung (layout) cho BottomSheetDialog.
                // Ví dụ, bạn có thể sử dụng LayoutInflater để nạp một tệp layout XML:
                View bottomSheetView = getLayoutInflater().inflate(R.layout.input_phanloai, null);

                // Gắn layout cho BottomSheetDialog
                bottomSheetDialog.setContentView(bottomSheetView);

                // Hiển thị BottomSheetDialog
                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
//                        loadCategoryData();
                    }
                });
                bottomSheetDialog.show();
            }
        });

        return rootView;
    }
    private void loadCategoryData() {
        List<Category> categoryList = getCategoryDataFromSQLite();
        categoryAdapter.clear();
        categoryAdapter.addAll(categoryList);
        categoryAdapter.notifyDataSetChanged();
    }

    private List<Category> getCategoryDataFromSQLite() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM chi_tieu", null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_EXPENSE_TYPE));
                int image = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_chitieu.COLUMN_EXPENSE_IMG));

                Category category = new Category(id, name, image);
                categoryList.add(category);
            }

            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return categoryList;
    }
}