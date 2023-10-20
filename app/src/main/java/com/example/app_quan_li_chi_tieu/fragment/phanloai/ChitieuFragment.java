package com.example.app_quan_li_chi_tieu.fragment.phanloai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.DanhMuc.Category;
import com.example.app_quan_li_chi_tieu.DanhMuc.CategoryAdapter;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_phanloai;

import java.util.ArrayList;
import java.util.List;

public class ChitieuFragment extends Fragment {

    private DatabaseHelper_phanloai dbHelper;
    private CategoryAdapter categoryAdapter;
    public ChitieuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCategoryData();
    }

    public static ChitieuFragment newInstance() {
        return new ChitieuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chitieu, container, false);

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
                CustomBottomSheetDialog_chi_tieu bottomSheetDialog = new CustomBottomSheetDialog_chi_tieu(view.getContext());

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
                        int id_danhmuc = category.getId();
                        CustomBottomSheetDialog_chi_tieu_chinhsua bottomSheetDialog = new CustomBottomSheetDialog_chi_tieu_chinhsua(getActivity(), id_danhmuc);
                        View bottomSheetView = getLayoutInflater().inflate(R.layout.input_phanloai, null);
                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetDialog.show();
                        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                loadCategoryData();
                            }
                        });
                        break;
                    case 1:
                        // Xử lý tùy chọn xóa danh mục
//                         Sử dụng category.getId() để biết danh mục nào đang được xóa
                        int id = category.getId();
                        dbHelper.deleteExpense(id);
                        DatabaseHelper_chitieu dbHelper1 = new DatabaseHelper_chitieu(getActivity());
                        dbHelper1.delete_theo_phan_loai(id);
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
            Cursor cursor = db.rawQuery("SELECT * FROM "+ DatabaseHelper_phanloai.TABLE_NAME+" WHERE type ='ChiTieu'", null);

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