package com.example.app_quan_li_chi_tieu.Chi_tieu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_quan_li_chi_tieu.DanhMuc.CategoryAdapter;
import com.example.app_quan_li_chi_tieu.R;
import com.example.app_quan_li_chi_tieu.DanhMuc.Category;
import com.example.app_quan_li_chi_tieu.database.DatabaseHelper_phanloai;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTieuAdapter extends ArrayAdapter<ChiTieu> {
    private Context context;

    public ChiTieuAdapter(Context context, List<ChiTieu> chiTieu) {
        super(context, 0, chiTieu);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout_home, parent, false);
        }
        DecimalFormat df = new DecimalFormat("#,##0 VND");
//        String formattedNumber = df.format(number);
        ChiTieu chiTieu = getItem(position);
        DatabaseHelper_phanloai databaseHelper_phanloai = new DatabaseHelper_phanloai(context);
        if (chiTieu != null) {
            TextView categoryName = convertView.findViewById(R.id.category_name);
            ImageView categoryImage = convertView.findViewById(R.id.category_image);
            TextView tV_date = convertView.findViewById(R.id.tv_date);
            TextView price = convertView.findViewById(R.id.tv_price);
            TextView note = convertView.findViewById(R.id.tv_note);
            TextView tv_phanloai = convertView.findViewById(R.id.tv_phanloai);
            tv_phanloai.setText(chiTieu.getType());
            Category category =databaseHelper_phanloai.getCategory(1);
            categoryName.setText(databaseHelper_phanloai.getCategory(chiTieu.getCat_id()).getName());
            categoryImage.setImageResource(databaseHelper_phanloai.getCategory(chiTieu.getCat_id()).getImage());
            tV_date.setText(chiTieu.getDate().toString());
            price.setText(df.format(chiTieu.getPrice()));
            note.setText(chiTieu.getNote());


        }
        return convertView;
    }

}

