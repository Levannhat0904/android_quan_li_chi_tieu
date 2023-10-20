package com.example.app_quan_li_chi_tieu.fragment.home;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.app_quan_li_chi_tieu.Chi_tieu.ChiTieu;
import com.example.app_quan_li_chi_tieu.Chi_tieu.ChiTieuAdapter;
import com.example.app_quan_li_chi_tieu.DanhMuc.Category;
import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_chitieu;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_phanloai;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CustomBottomSheetDialog_home_chinhsua extends BottomSheetDialog {
    private int id;
    private ChiTieuAdapter chiTieuAdapter;
    public CustomBottomSheetDialog_home_chinhsua(@NonNull Context context,int id) {
        super(context);
        this.id = id;
        setContentView(R.layout.bottom_sheet_view_home_chinh_sua);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bottom_sheet_view_home_chinh_sua);
        DatabaseHelper_chitieu dbHelper_chitieu = new DatabaseHelper_chitieu(getContext());
        DatabaseHelper_phanloai dbHelper_phanloai = new DatabaseHelper_phanloai(getContext());
        ChiTieu chiTieu = dbHelper_chitieu.getData(id);
        EditText edt_price = findViewById(R.id.so_tien);
        EditText edt_note = findViewById(R.id.note);
        TextView edt_date = findViewById(R.id.date);
        ImageView icon = findViewById(R.id.icon);
        TextView txt_category = findViewById(R.id.category);
        edt_note.setText(chiTieu.getNote()+"");
        edt_price.setText(chiTieu.getPrice()+"");
        edt_date.setText(chiTieu.getDate()+"");

        int cat_id= chiTieu.getCat_id();
        String type = chiTieu.getType();
        Category category = dbHelper_phanloai.getCategory(cat_id);
        icon.setImageResource(category.getImage());
        txt_category.setText(category.getName());

        Button btn_save = findViewById(R.id.save);
        btn_save.setOnClickListener(v -> {
            String note = edt_note.getText().toString();
            int price = Integer.parseInt(edt_price.getText().toString());
            String date = edt_date.getText().toString();
            ChiTieu chiTieu1 = new ChiTieu(id, price, date, cat_id,note, type);
            dbHelper_chitieu.updateData(id,price,note,date,cat_id,type);
//            chiTieuAdapter.notifyDataSetChanged();
            dismiss();
        });
        edt_date.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog( getContext(), (view, year, month, dayOfMonth) -> {
                edt_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }, 2020, 11, 20);
//            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
//                edt_date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
//            });
            datePickerDialog.show();

        });

    }
}
