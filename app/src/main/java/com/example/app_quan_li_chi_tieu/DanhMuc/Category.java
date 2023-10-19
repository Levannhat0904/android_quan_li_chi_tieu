package com.example.app_quan_li_chi_tieu.DanhMuc;

public class Category {
    private int id;
    private String name;
    private String type;
    private int image;

    public Category() {
    }

    public Category(int id, String name, int image, String type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
