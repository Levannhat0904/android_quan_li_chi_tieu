package com.example.app_quan_li_chi_tieu.fragment.phanloai;

import com.example.app_quan_li_chi_tieu.category.Category;
import com.example.app_quan_li_chi_tieu.category.CategoryAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomBottomSheetDialog extends BottomSheetDialog {
    private DatabaseHelper_chitieu dbHelper;
    private CategoryAdapter categoryAdapter;
    int resID_img; // Lưu id ảnh đã chọn
    private EditText txtImage; // EditText để hiển thị tên ảnh đã chọn
    private ImageView imageSelect; // ImageView để hiển thị ảnh đã chọn
    private ImageView selectedIcon = null; // Để lưu trữ biểu tượng được chọn

    public CustomBottomSheetDialog(Context context) {
        super(context);
        setContentView(R.layout.input_phanloai);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txtImage = findViewById(R.id.editTextExpenseType); // Ánh xạ EditText
        imageSelect = findViewById(R.id.image_select); // Ánh xạ ImageView
        // Lấy danh sách tên tất cả các tài nguyên drawable trong thư mục res/drawable
        Field[] drawables = R.drawable.class.getFields();
        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khi bấm vào ImageButton, quay lại hoạt động trước đó
                EditText edt_loai = findViewById(R.id.editTextExpenseType);
//                edt_loai.setText(resID_img + "");
                addToDatabase(edt_loai.getText().toString(), resID_img);
                dismiss();
            }
        });

        // Tạo một GridLayout để chứa các biểu tượng
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Duyệt qua danh sách tên tài nguyên và hiển thị các biểu tượng
        for (Field drawableField : drawables) {
            try {
                String drawableName = drawableField.getName();
                // Kiểm tra nếu tên tài nguyên bắt đầu bằng "icons"
                if (drawableName.startsWith("icons")) {
                    int resID = getContext().getResources().getIdentifier(drawableName, "drawable", getContext().getPackageName());
                    Drawable iconDrawable = getContext().getResources().getDrawable(resID);
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageDrawable(iconDrawable);
//                    imageView.setPadding(50, 10, 50, 10);

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
                            imageView.setColorFilter(getContext().getResources().getColor(android.R.color.holo_blue_light));
                            selectedIcon = imageView;
//                            txtImage.setText(resID + "");
                            resID_img = resID;
                            imageSelect.setImageResource(resID);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addToDatabase(String name, int img) {
        dbHelper = new DatabaseHelper_chitieu(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper_chitieu.COLUMN_EXPENSE_TYPE, name);
        values.put(DatabaseHelper_chitieu.COLUMN_EXPENSE_IMG, img);
        long newRowId = db.insert(DatabaseHelper_chitieu.TABLE_NAME, null, values);
        db.close();

        Toast.makeText(getContext(), "Thêm dữ liệu thành công!", Toast.LENGTH_SHORT).show();
    }



}
