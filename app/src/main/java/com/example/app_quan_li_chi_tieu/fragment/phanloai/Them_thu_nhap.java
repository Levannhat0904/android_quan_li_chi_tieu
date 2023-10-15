package com.example.app_quan_li_chi_tieu.fragment.phanloai;

import android.content.ContentValues;
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
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_phanloai;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Them_thu_nhap extends AppCompatActivity
{
    private DatabaseHelper_phanloai dbHelper;
     int resID_img;//lưu id ảnh đã chọn
    private ArrayAdapter<String> adapter;
    private ArrayList<String> expenseList = new ArrayList<>();
    private EditText txtImage; // EditText để hiển thị tên ảnh đã chọn
    private ImageView imageSelect; // ImageView để hiển thị ảnh đã chọn
    private ImageView selectedIcon = null; // Để lưu trữ biểu tượng được chọn
    private void addToDatabase(String name, int img, String type) {
        dbHelper = new DatabaseHelper_phanloai(this); // Khởi tạo đối tượng dbHelper
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper_phanloai.COLUMN_EXPENSE_NAME, name);
        values.put(DatabaseHelper_phanloai.COLUMN_TYPE, type);
        values.put(DatabaseHelper_phanloai.COLUMN_EXPENSE_IMG, img);
        long newRowId = db.insert(DatabaseHelper_phanloai.TABLE_NAME, null, values);
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
        String receivedData = getIntent().getStringExtra("data");

        // Xử lý dữ liệu, ví dụ: hiển thị dữ liệu trong TextView
        txtImage.setText(receivedData);
        imageSelect = findViewById(R.id.image_select); // Ánh xạ ImageView
        // Lấy danh sách tên tất cả các tài nguyên drawable trong thư mục res/drawable
        Field[] drawables = R.drawable.class.getFields();
        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
//        imageButtonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Khi bấm vào ImageButton, quay lại hoạt động trước đó
//                EditText edt_loai=findViewById(R.id.editTextExpenseType);
//                edt_loai.setText(resID_img+"");
//                String type ="thu_nhap";
//                addToDatabase(edt_loai.getText().toString(), resID_img, type);
////                onBackPressed();
//                finish();
//            }
//        });

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
                            txtImage.setText(resID+"");
                            String s= "R.drawable."+drawableName;
                            resID_img =resID;
                            imageSelect.setImageResource(resID);
                            // Lấy tên tài nguyên từ drawableName
                            String resourceName = drawableName;
                            // Hoặc lấy ID của tài nguyên từ resID
                            int resourceId = resID;
                            // Hiển thị tên hoặc ID trong một Toast hoặc làm gì đó khác
//                            Toast.makeText(Them_chi_tieu.this, "Resource Name: " + resourceId, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
