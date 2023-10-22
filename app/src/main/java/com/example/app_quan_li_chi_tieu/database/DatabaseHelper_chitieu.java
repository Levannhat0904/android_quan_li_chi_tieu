package com.example.app_quan_li_chi_tieu.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app_quan_li_chi_tieu.Chi_tieu.ChiTieu;

public class DatabaseHelper_chitieu extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense.db";
    private static final int DATABASE_VERSION =13;

    // Tên bảng và các cột trong bảng
    public static final String TABLE_NAME = "chi_tieu";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_CAT_ID = "cat_id"; // Cột khoá ngoại
    // Constructor
    public DatabaseHelper_chitieu(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRICE + " INTEGER NOT NULL, " +
                COLUMN_NOTE + " TEXT NOT NULL, " +
                COLUMN_DATE + " DATE, " +
                COLUMN_CAT_ID + " INTEGER, " +
                COLUMN_TYPE + " TEXT, " + // Add the 'type' column
                "FOREIGN KEY (" + COLUMN_CAT_ID + ") REFERENCES " + DatabaseHelper_phanloai.TABLE_NAME +
                "(" + DatabaseHelper_phanloai.COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE );";
        db.execSQL(createTableQuery);
    }


    public boolean deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + " = " + id, null);
        db.close();
        return rowsAffected > 0;
    }
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
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
        db.close();
    }
//    viết hàm thêm dữ liệu
    public void insertData(int price, String note, String date, int cat_id, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO "+ TABLE_NAME + " VALUES (NULL, '"+price+"', '"+note+"', '"+date+"', '"+cat_id+"', '"+type+"');";
        db.execSQL(query);
        db.close();
    }
//    viết hàm cập nhật dữ liệu
    public void updateData(int id, int price, String note, String date, int cat_id, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+ TABLE_NAME + " SET price = '"+price+"', note = '"+note+"', date = '"+date+"', cat_id = '"+cat_id+"',type = '"+type+"' WHERE _id = '"+id+"';";
        db.execSQL(query);
        db.close();
    }
//    viết hàm xóa dữ liệu
    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ TABLE_NAME + " WHERE _id = '"+id+"';";
        db.execSQL(query);
        db.close();
    }
//    viết hàm lấy dữ liệu
    public ChiTieu getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE _id = '"+id+"';";
        ChiTieu chiTieu = null;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            int id1 = cursor.getInt(0);
            int price = cursor.getInt(1);
            String note = cursor.getString(2);
            String date = cursor.getString(3);
            int cat_id = cursor.getInt(4);
            String type = cursor.getString(5);
            chiTieu = new ChiTieu(id1, price, date, cat_id,note, type);
        }
        cursor.close();
        db.close();
        return chiTieu;
    }
//    hàm tính tổng
    public int getTotalPriceByType(String type) {
        int totalPrice = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + COLUMN_PRICE + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = '" + type + "';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            totalPrice = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return totalPrice;
    }
    public void delete_theo_phan_loai(int cat_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+ TABLE_NAME + " WHERE "+COLUMN_CAT_ID+" = "+cat_id+";";
        System.out.println(query);
        db.execSQL(query);
        db.close();
    }
    public Cursor getAllDataByType(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = '" + type + "';";
        return db.rawQuery(query, null);
    }
        public Cursor getDataByMonthAndType(String month, String type) {
            SQLiteDatabase db = this.getReadableDatabase();
            // Lấy giá trị tháng từ chuỗi ngày tháng
            String extractedMonth = "substr(" + COLUMN_DATE + ", instr(" + COLUMN_DATE + ", '/') + 1, instr(substr(" + COLUMN_DATE + ", instr(" + COLUMN_DATE + ", '/') + 1), '/') - 1)";
            // Xây dựng câu truy vấn SELECT
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + extractedMonth + " = ? AND " + COLUMN_TYPE + " = ?";
            // Thực hiện truy vấn
            return db.rawQuery(query, new String[]{month, type});
        }
//        hàm tính tổng sau khi lọc
    public int getTotalPriceByMonthAndType(String month, String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;
        String query = "SELECT SUM(" + COLUMN_PRICE + ") FROM " + TABLE_NAME +
                " WHERE " + COLUMN_DATE + " LIKE '%" + month + "%' AND " +
                COLUMN_TYPE + " = '" + type + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }
        cursor.close();
        return total;
    }
}
