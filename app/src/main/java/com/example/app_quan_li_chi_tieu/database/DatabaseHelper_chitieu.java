package com.example.app_quan_li_chi_tieu.database;

import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper_chitieu extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột trong bảng
    public static final String TABLE_NAME = "expense_types";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EXPENSE_TYPE = "expense_type";

    // Câu lệnh tạo bảng
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_EXPENSE_TYPE + " TEXT)";

    public DatabaseHelper_chitieu(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Phương thức để thêm loại chi tiêu vào cơ sở dữ liệu
    public long addExpenseType(String expenseType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EXPENSE_TYPE, expenseType);
        long newRowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return newRowId;
    }
}

