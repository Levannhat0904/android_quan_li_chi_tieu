package com.example.app_quan_li_chi_tieu.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app_quan_li_chi_tieu.user.user;

public class DatabaseHelper_user extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "expense.db";
    private static final int DATABASE_VERSION = 8;

    public DatabaseHelper_user(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS UserInfo " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "tuoi INTEGER, " +
                "gioitinh TEXT, " +
                "nghenghiep TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserInfo");
        onCreate(db);
    }
//    viết hàm xóa dữ liệu
    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from UserInfo");
    }
//    viết hàm xóa dữ liệu
    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from UserInfo where id = "+id);
    }
//    viét hàm update dữ liệu
    public void updateData(int id, String name, int tuoi, String gioitinh, String nghenghiep){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update UserInfo set name = '"+name+"', tuoi = "+tuoi+", gioitinh = '"+gioitinh+"', nghenghiep = '"+nghenghiep+"' where id = "+id);
    }
//    thêm dữ liệu
    public void insertData(String name, int tuoi, String gioitinh, String nghenghiep){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into UserInfo (name, tuoi, gioitinh, nghenghiep) values ('"+name+"', "+tuoi+", '"+gioitinh+"', '"+nghenghiep+"')");
    }
//    viết hàm lấy dữ liệu
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UserInfo", null);
//        Lấy so dòng dữ liệu
        return cursor;
    }
//    lấy só dòng dữ liệu
    public int getCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UserInfo", null);
//        Lấy so dòng dữ liệu
        return cursor.getCount();
    }

//    viết hàm lấy dữ liệu theo id
    public Cursor getDataById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UserInfo where id = "+id, null);
        return cursor;
    }
//    get user
    public user getUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from UserInfo", null);
        user user = null;
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int tuoi = cursor.getInt(2);
            String gioitinh = cursor.getString(3);
            String nghenghiep = cursor.getString(4);
            user = new user(id, name, tuoi, gioitinh, nghenghiep);
        }
        return user;
    }
}
