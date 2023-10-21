package com.example.app_quan_li_chi_tieu.user;

public class user {
    private int id;
    private String name;
    private int tuoi;
    private String gioitinh;
    private String nghenghiep;

    public user() {
    }

    public user(int id, String name, int tuoi, String gioitinh, String nghenghiep) {
        this.id = id;
        this.name = name;
        this.tuoi = tuoi;
        this.gioitinh = gioitinh;
        this.nghenghiep = nghenghiep;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTuoi() {
        return tuoi;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public String getNghenghiep() {
        return nghenghiep;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public void setNghenghiep(String nghenghiep) {
        this.nghenghiep = nghenghiep;
    }
}
