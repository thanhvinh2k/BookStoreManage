package com.poly.duan1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.poly.duan1.DAO.HoaDonChiTietDAO;
import com.poly.duan1.DAO.HoaDonDAO;
import com.poly.duan1.DAO.LoaiThietBiDAO;
import com.poly.duan1.DAO.ThietBiDAO;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbShopTBDT";
    private static final int VERSON = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ThietBiDAO.SQL_THIET_BI);
        db.execSQL(LoaiThietBiDAO.SQL_LOAI_TB);
        db.execSQL(HoaDonDAO.SQL_HOA_DON);
        db.execSQL(HoaDonChiTietDAO.SQL_HDCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ThietBiDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LoaiThietBiDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HoaDonDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HoaDonChiTietDAO.TABLE_NAME);

        onCreate(db);
    }
}
