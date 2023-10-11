package com.example.app_quan_li_chi_tieu.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_quan_li_chi_tieu.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Context context;

    public CategoryAdapter(Context context, List<Category> categories) {
        super(context, 0, categories);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        }

        Category category = getItem(position);

        if (category != null) {
            TextView categoryName = convertView.findViewById(R.id.category_name);
            ImageView categoryImage = convertView.findViewById(R.id.category_image);

            categoryName.setText(category.getName());
            categoryImage.setImageResource(category.getImage());
        }
        return convertView;
    }
}

