package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duan1.DatabaseHelper;
import com.poly.duan1.model.HoaDon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private SQLiteDatabase db;
    public static final String TABLE_NAME = "HoaDon";
    public static final String SQL_HOA_DON = "CREATE TABLE HoaDon (maHD text PRIMARY KEY, ngayMua date);";
    public static final String TAG = "HoaDonDAO";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public HoaDonDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    public List<HoaDon> getALLHoaDon() throws ParseException {
        List<HoaDon> dsHoaDon = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHoaDon(c.getString(c.getColumnIndex("maHD")));
            hoaDon.setNgayMua(sdf.parse(c.getString(c.getColumnIndex("ngayMua"))));
            dsHoaDon.add(hoaDon);
            c.moveToNext();
        }
        c.close();
        return dsHoaDon;
    }

    public long insertHoaDon(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("maHD", hoaDon.getMaHoaDon());
        values.put("ngayMua", sdf.format(hoaDon.getNgayMua()));

        try {
            if (db.insert(TABLE_NAME,null,values) == -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public int updateHoaDon(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("maHD", hoaDon.getMaHoaDon());
        values.put("ngayMua", sdf.format(hoaDon.getNgayMua()));

        return db.update(TABLE_NAME, values, "maHD=?", new String[]{hoaDon.getMaHoaDon()});
    }

    public int deleteHoaDon(String maHD) {
        return db.delete(TABLE_NAME, "maHD=?", new String[]{maHD});
    }

}
