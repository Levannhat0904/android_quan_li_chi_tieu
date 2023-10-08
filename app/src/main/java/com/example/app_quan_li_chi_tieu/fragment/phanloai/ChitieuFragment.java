package com.example.app_quan_li_chi_tieu.fragment.phanloai;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;
import java.util.ArrayList;

public class ChitieuFragment extends Fragment {

    private EditText test;
//    private ViewPager2 viewPager2;
    private DatabaseHelper_chitieu dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> expenseList = new ArrayList<>();

    public ChitieuFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        loadExpenseData();
    }


    public static ChitieuFragment newInstance() {
        return new ChitieuFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chitieu, container, false);
        test = rootView.findViewById(R.id.test);
        System.out.println("đã load");
        ImageButton button = rootView.findViewById(R.id.button_thaotac);

        dbHelper = new DatabaseHelper_chitieu(getActivity());

        ListView listView = rootView.findViewById(R.id.lv_chitieu);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, expenseList);
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đổi sang trang thứ 3 (index bắt đầu từ 0)
//                viewPager2.setCurrentItem(2);
                Intent intent;
                intent = new Intent(view.getContext(), input_view.class);

                startActivity(intent);
            }
        });

        loadExpenseData();

        return rootView;
    }




    private void loadExpenseData() {
        expenseList.clear(); // Xóa dữ liệu cũ trong danh sách

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DatabaseHelper_chitieu.COLUMN_EXPENSE_TYPE}; // Sử dụng hằng số đã định nghĩa

        Cursor cursor = db.query(
                DatabaseHelper_chitieu.TABLE_NAME, // Sử dụng hằng số đã định nghĩa
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String expenseType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper_chitieu.COLUMN_EXPENSE_TYPE)); // Sử dụng hằng số đã định nghĩa
            expenseList.add(expenseType); // Thêm loại chi tiêu vào danh sách
        }
        cursor.close();
        db.close();

        // Thông báo Adapter cập nhật dữ liệu
        adapter.notifyDataSetChanged();
    }

}
