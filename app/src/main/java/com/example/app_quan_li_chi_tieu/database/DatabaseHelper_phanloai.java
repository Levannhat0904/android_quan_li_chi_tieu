package com.example.app_quan_li_chi_tieu.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app_quan_li_chi_tieu.DanhMuc.Category;

public class DatabaseHelper_phanloai extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense.db";
    private static final int DATABASE_VERSION = 5;

    // Tên bảng và các cột trong bảng
    public static final String TABLE_NAME = "phanloai";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_EXPENSE_NAME = "name";
    public static final String COLUMN_EXPENSE_IMG = "img";

    // Constructor
    public DatabaseHelper_phanloai(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng
        System.out.println("onCreate");
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXPENSE_NAME + " TEXT NOT NULL, " +
                COLUMN_TYPE + " TEXT NOT NULL, " +
                COLUMN_EXPENSE_IMG + " INTEGER);";
        db.execSQL(createTableQuery);
    }
    public boolean deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + " = " + id, null);
        db.close();
        return rowsAffected > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    //    viết hàm xóa dữ liệu
    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }
//    viết hàm xóa dữ liệu
    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME+" where "+COLUMN_ID+" = "+id);
    }
//    viết hàm thêm dữ liệu
    public void insertData(String name, String type, int img){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into "+ TABLE_NAME+" ("+COLUMN_EXPENSE_NAME+","+COLUMN_TYPE+","+COLUMN_EXPENSE_IMG+") values ('"+name+"','"+type+"',"+img+")");
    }
//    viết hàm sửa dữ liệu
    public void updateData(int id, String name, String type, int img){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update "+ TABLE_NAME+" set "+COLUMN_EXPENSE_NAME+" = '"+name+"', "+COLUMN_TYPE+" = '"+type+"', "+COLUMN_EXPENSE_IMG+" = "+img+" where "+COLUMN_ID+" = "+id);
    }
    public Category getCategory(int catId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME+" WHERE "+COLUMN_ID+" ='"+catId+"'", null);

        Category category = null;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                int img = cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENSE_IMG));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_EXPENSE_NAME));
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));

                category = new Category(id, name, img,type);
            }
            cursor.close();
        }

        db.close();

        return category;
    }

}
