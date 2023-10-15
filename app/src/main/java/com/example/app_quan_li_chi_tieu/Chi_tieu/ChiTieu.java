package com.example.app_quan_li_chi_tieu.Chi_tieu;

import com.example.app_quan_li_chi_tieu.DanhMuc.Category;

import java.util.Date;

public class ChiTieu {
    private int id;
    private int price;
    private String date;
    private int cat_id;
    private String note;

    public ChiTieu() {
    }

    public ChiTieu(int id, int price, String date, int cat_id, String note) {
        this.id = id;
        this.price = price;
        this.date = date;
        this.cat_id = cat_id;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
