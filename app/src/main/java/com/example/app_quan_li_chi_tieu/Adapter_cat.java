package com.example.app_quan_li_chi_tieu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_cat extends ArrayAdapter<Cat> {
    Activity context;
    int resource;
    ArrayList<Cat> objects;
    public Adapter_cat(Activity context, int resource, ArrayList<Cat> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    //gọi getView
    public View getView(int position, View convertView, android.view.ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        convertView = inflater.inflate(this.resource,null);
        Cat cat=this.objects.get(position);
        //khai báo tham chiếu và hiển thị ảnh
        ImageView img=convertView.findViewById(R.id.img);
        img.setImageResource(cat.getImg());
        //khaii báoo, tham chiếu và hiển thị tên
        TextView txtName=convertView.findViewById(R.id.name);
        txtName.setText(cat.getName());
        return convertView;
    }
}
