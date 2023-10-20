package com.example.app_quan_li_chi_tieu.fragment.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.DanhMuc.Category;
import com.example.app_quan_li_chi_tieu.DanhMuc.CategoryAdapter;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_phanloai;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class CustomBottomSheetDialog_home extends BottomSheetDialog {
    private DatabaseHelper_phanloai dbPhanLoaiHelper;
    private DatabaseHelper_chitieu dbChitieuHelper;
    private CategoryAdapter categoryAdapter;
    private DatePicker datePicker;
    private EditText editPrice;
    private EditText editTextNote;
    private EditText editDate;
    public CustomBottomSheetDialog_home(@NonNull Context context) {
        super(context);
//        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_view_home, null);
//        setContentView(contentView);
        setContentView(R.layout.bottom_sheet_view_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tìm TabLayout trong layout của dialog
        Button btn_chitieu = findViewById(R.id.chi_tieu);
        dbPhanLoaiHelper = new DatabaseHelper_phanloai(getContext());
        DatabaseHelper_phanloai dbHelper = new DatabaseHelper_phanloai(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Hoặc sử dụng getReadableDatabase() nếu chỉ đọc dữ liệu

// Sử dụng cơ sở dữ liệu ở đây
        SQLiteDatabase dbHelperReadableDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sqlite_version();", null);

        if (cursor.moveToFirst()) {
            String sqliteVersion = cursor.getString(0);
            // Làm cái gì đó với phiên bản SQLite ở đây
            System.out.println(sqliteVersion);
        } else {
            // Không tìm thấy phiên bản SQLite
        }

        cursor.close();

        ListView listView = findViewById(R.id.lv_bottom_sheet_home);
        // Bắt sự kiện khi một mục trong ListView được nhấn
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Sử dụng LayoutInflater để tạo một View cho InputDialogFragment
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.dialog_input_home, null);
                TextView tv_date = dialogView.findViewById(R.id.tv_Date);
//                tv_date.setText("Ngày: ");
                tv_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Lấy ngày hiện tại
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                                (sds, year, month, dayOfMonth) -> {
                                    // Xử lý lấy ngày tháng năm
                                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                    tv_date.setText(date);
                                }, 2021, 1, 1);
                        datePickerDialog.show();
                    }
                });
                // Tạo một AlertDialog để chứa dialogView
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấp vào nút Xác nhận
                        // Lấy dữ liệu từ listview
                        int a =categoryAdapter.getItem(position).getId();
                        String type =categoryAdapter.getItem(position).getType();
                        System.out.println(a);
                         editPrice = dialogView.findViewById(R.id.edit_price);
                         editTextNote = dialogView.findViewById(R.id.editTextNote);

                        // Lấy dữ liệu đã nhập từ các trường EditText
                        int priceText =Integer.parseInt(editPrice.getText().toString().trim()) ;
                        String noteText = editTextNote.getText().toString();
                        String dateText = tv_date.getText().toString().trim();

                        // Bây giờ bạn có thể làm gì đó với các giá trị đã lấy được, ví dụ:
                        // In ra các giá trị đã nhập
                        System.out.println("Giá tiền: " + priceText);
                        System.out.println("Ghi chú: " + noteText);
                        System.out.println("Ngày: " + dateText);
//                        datePicker = findViewById(R.id.datePicker);
                        DatabaseHelper_chitieu databaseHelper = new DatabaseHelper_chitieu(getContext()); // Trong đó, context là đối tượng Context của ứng dụng của bạn.
//                        String type = "ChiTieu";
                        // Lắng nghe sự kiện khi người dùng chọn ngày trên DatePicker
                        databaseHelper.insertData(priceText,noteText,dateText,a,type);

                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấp vào nút Hủy
                    }
                });
                builder.setView(dialogView);
                // Hiển thị AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>());
        listView.setAdapter(categoryAdapter);
        loadCategoryData(DatabaseHelper_phanloai.TABLE_NAME,"ChiTieu");
        btn_chitieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                hiển thị lv_bottom_sheet_home
                System.out.println("chitieu");
                dbPhanLoaiHelper = new DatabaseHelper_phanloai(getContext());

                ListView listView = findViewById(R.id.lv_bottom_sheet_home);

                categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>());
                listView.setAdapter(categoryAdapter);
                loadCategoryData(DatabaseHelper_phanloai.TABLE_NAME,"ChiTieu");
            }
        });

        Button btn_thunhap = findViewById(R.id.thu_nhap);
        btn_thunhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                hiển thị lv_bottom_sheet_home
                System.out.println("ThuNhap");
//                dbPhanLoaiHelper = new DatabaseHelper_phanloai(getContext());
                ListView listView = findViewById(R.id.lv_bottom_sheet_home);
                categoryAdapter = new CategoryAdapter(getContext(), new ArrayList<>());

                listView.setAdapter(categoryAdapter);
                loadCategoryData(DatabaseHelper_phanloai.TABLE_NAME,"ThuNhap");
            }
        });
    }
    private void loadCategoryData(String tabName, String type) {
        List<Category> categoryList = getCategoryDataFromSQLite(tabName,type);
        categoryAdapter.clear();
        categoryAdapter.addAll(categoryList);
        categoryAdapter.notifyDataSetChanged();
    }
    private List<Category> getCategoryDataFromSQLite(String tabName, String type_1) {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = dbPhanLoaiHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + tabName+" WHERE type ='"+type_1+"'", null);
            System.out.println("SELECT * FROM " + tabName+" WHERE type ='"+type_1+"'");
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_phanloai.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper_phanloai.COLUMN_EXPENSE_NAME));
                String type = cursor.getString(cursor.getColumnIndex(DatabaseHelper_phanloai.COLUMN_TYPE));
                int image = cursor.getInt(cursor.getColumnIndex(DatabaseHelper_phanloai.COLUMN_EXPENSE_IMG));

                Category category = new Category(id, name, image,type);
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
