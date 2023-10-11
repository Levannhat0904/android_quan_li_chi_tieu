package com.example.app_quan_li_chi_tieu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper_chitieu extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột trong bảng
    public static final String TABLE_NAME = "chi_tieu";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EXPENSE_TYPE = "name";
    public static final String COLUMN_EXPENSE_IMG = "img";

    // Constructor
    public DatabaseHelper_chitieu(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EXPENSE_TYPE + " TEXT NOT NULL, " +
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
}
