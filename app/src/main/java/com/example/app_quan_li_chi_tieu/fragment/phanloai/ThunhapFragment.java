package com.example.app_quan_li_chi_tieu.fragment.phanloai;

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
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.DanhMuc.Category;
import com.example.app_quan_li_chi_tieu.DanhMuc.CategoryAdapter;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_phanloai;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThunhapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThunhapFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseHelper_phanloai dbHelper;
    private CategoryAdapter categoryAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThunhapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThunhapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThunhapFragment newInstance(String param1, String param2) {
        ThunhapFragment fragment = new ThunhapFragment();
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
    public void onResume() {
        super.onResume();
        loadCategoryData();
    }
    public static ThunhapFragment newInstance() {
        return new ThunhapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_thunhap, container, false);

        dbHelper = new DatabaseHelper_phanloai(getActivity());

        ListView listView = rootView.findViewById(R.id.lv_chitieu);

        categoryAdapter = new CategoryAdapter(getActivity(), new ArrayList<>());
        listView.setAdapter(categoryAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi danh mục được chọn
                Category selectedCategory = categoryAdapter.getItem(position);
                if (selectedCategory != null) {
                    // Thực hiện các tác vụ liên quan đến danh mục, ví dụ: hiển thị dialog, sửa hoặc xóa
                    showCategoryOptionsDialog(selectedCategory);
                }
                return true; // Đánh dấu sự kiện đã được xử lý, ngăn sự kiện ngắn khi nhấn và giữ.
            }
        });

//        rootView.findViewById(R.id.button_thaotac).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                String s = "truyền dữ liệu thành công";
//                Intent intent = new Intent(view.getContext(), Them_chi_tieu.class);
////                intent.putExtra("data",s);
//                startActivity(intent);
//            }
//        });
        rootView.findViewById(R.id.button_thaotac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một BottomSheetDialog
                CustomBottomSheetDialog_thu_nhap bottomSheetDialog = new CustomBottomSheetDialog_thu_nhap(view.getContext());

                // Bây giờ, bạn cần gắn nội dung (layout) cho BottomSheetDialog.
                // Ví dụ, bạn có thể sử dụng LayoutInflater để nạp một tệp layout XML:
                View bottomSheetView = getLayoutInflater().inflate(R.layout.input_phanloai, null);

                // Gắn layout cho BottomSheetDialog
                bottomSheetDialog.setContentView(bottomSheetView);

                // Hiển thị BottomSheetDialog
                bottomSheetDialog.show();
                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        // Sự kiện khi Bottom Sheet bị ẩn
                        loadCategoryData(); // Cập nhật dữ liệu sau khi ẩn Bottom Sheet
                    }
                });

            }
        });


        return rootView;
    }
    private void showCategoryOptionsDialog(Category category) {
        // Hiển thị dialog với các tùy chọn sửa hoặc xóa danh mục
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Tùy chọn danh mục");
        builder.setItems(new CharSequence[]{"Sửa", "Xóa"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        int s = category.getImage();
                        Intent intent = new Intent(getContext(), Them_thu_nhap.class);
                        intent.putExtra("data",s+"");
                        startActivity(intent);
                        // Xử lý tùy chọn sửa danh mục
                        // Sử dụng category.getId() để biết danh mục nào đang được sửa
                        break;
                    case 1:
                        // Xử lý tùy chọn xóa danh mục
                        // Sử dụng category.getId() để biết danh mục nào đang được xóa
                        int id = category.getId();
                        dbHelper.deleteExpense(id);
                        loadCategoryData();
                        break;
                }
            }
        });
        builder.show();
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
            Cursor cursor = db.rawQuery("SELECT * FROM "+ DatabaseHelper_phanloai.TABLE_NAME+" WHERE type ='thu_nhap'", null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_phanloai.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper_phanloai.COLUMN_EXPENSE_NAME));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper_phanloai.COLUMN_TYPE));
                int image = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_phanloai.COLUMN_EXPENSE_IMG));

                Category category = new Category(id, name, image, type);
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