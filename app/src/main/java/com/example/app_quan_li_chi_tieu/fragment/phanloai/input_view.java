package com.example.app_quan_li_chi_tieu.fragment.phanloai;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class input_view extends AppCompatActivity
{
    private DatabaseHelper_chitieu dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> expenseList = new ArrayList<>();
    private EditText txtImage; // EditText để hiển thị tên ảnh đã chọn
    private ImageView imageSelect; // ImageView để hiển thị ảnh đã chọn
    private ImageView selectedIcon = null; // Để lưu trữ biểu tượng được chọn
    private void addToDatabase(String expenseType) {
        dbHelper = new DatabaseHelper_chitieu(this); // Khởi tạo đối tượng dbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper_chitieu.COLUMN_EXPENSE_TYPE, expenseType);
        long newRowId = db.insert(DatabaseHelper_chitieu.TABLE_NAME, null, values);

        db.close();
        Toast.makeText(this, "Thêm dữ liệu thành công!", Toast.LENGTH_SHORT).show();
        // Sau khi thêm vào cơ sở dữ liệu, cập nhật danh sách loại chi tiêu và thông báo Adapter
//        loadExpenseData();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_phanloai);
        // Tạo một GridLayout để chứa các biểu tượng
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        txtImage = findViewById(R.id.editTextExpenseType); // Ánh xạ EditText
        imageSelect = findViewById(R.id.image_select); // Ánh xạ ImageView
        // Lấy danh sách tên tất cả các tài nguyên drawable trong thư mục res/drawable
        Field[] drawables = R.drawable.class.getFields();
        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi bấm vào ImageButton, quay lại hoạt động trước đó
                EditText edt_loai=findViewById(R.id.editTextExpenseType);
                addToDatabase(edt_loai.getText().toString());
//                onBackPressed();
                finish();
            }
        });

        // Duyệt qua danh sách tên tài nguyên và hiển thị các biểu tượng
        for (Field drawableField : drawables) {
            try {
                String drawableName = drawableField.getName();
                // Kiểm tra nếu tên tài nguyên bắt đầu bằng "icon_"
                if (drawableName.startsWith("icons")) {
                    int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
                    Drawable iconDrawable = getResources().getDrawable(resID);

                    ImageView imageView = new ImageView(this);
                    imageView.setImageDrawable(iconDrawable);
                    imageView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(GridLayout.UNDEFINED, 1f), GridLayout.spec(GridLayout.UNDEFINED, 1f)));
//                    imageView.setLayoutParams(new GridLayout.LayoutParams());
                    gridLayout.addView(imageView);
                    // Đặt sự kiện click cho ImageView
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Nếu đã chọn một biểu tượng trước đó, thì đặt lại màu của biểu tượng đó
                            if (selectedIcon != null) {
                                selectedIcon.clearColorFilter();
                            }
                            // Thay đổi màu của biểu tượng khi click
                            imageView.setColorFilter(getResources().getColor(android.R.color.holo_blue_light));
                            selectedIcon = imageView;
                            txtImage.setText(drawableName);
                            String s= "R.drawable."+drawableName;
                            imageSelect.setImageResource(resID);
                            // Lấy tên tài nguyên từ drawableName
                            String resourceName = drawableName;
                            // Hoặc lấy ID của tài nguyên từ resID
                            int resourceId = resID;
                            // Hiển thị tên hoặc ID trong một Toast hoặc làm gì đó khác
//                            Toast.makeText(input_view.this, "Resource Name: " + resourceName, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
