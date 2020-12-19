package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duan1.DatabaseHelper;
import com.poly.duan1.model.ThietBi;

import java.util.ArrayList;
import java.util.List;


public class ThietBiDAO {
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "ThietBi";
    public static final String SQL_THIET_BI = "CREATE TABLE ThietBi (maThietBi text PRIMARY KEY, tenThietBi text, loaiThietBi text, hangSX text, soLuong INTEGER, giaBan double);";
    public static final String TAG = "ThietBiDAO";

    public ThietBiDAO (Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    public List<ThietBi> getThietBi(String sql, String... selectionArgs) {
        List<ThietBi> dsThietBi = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            ThietBi thietBi = new ThietBi();
            thietBi.setMaThietBi(c.getString(c.getColumnIndex("maThietBi")));
            thietBi.setTenThietBi(c.getString(c.getColumnIndex("tenThietBi")));
            thietBi.setLoaiThietBi(c.getString(c.getColumnIndex("loaiThietBi")));
            thietBi.setHangSX(c.getString(c.getColumnIndex("hangSX")));
            thietBi.setSoLuong(c.getInt(c.getColumnIndex("soLuong")));
            thietBi.setGiaBan(c.getDouble(c.getColumnIndex("giaBan")));
            dsThietBi.add(thietBi);
            c.moveToNext();
        }
        c.close();
        return dsThietBi;
    }

    public List<ThietBi> getAllTB() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getThietBi(sql);
    }

    public ThietBi getThietBiByID(String id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE maThietBi=?";
        List<ThietBi> thietBiList =  getThietBi(sql, id);
        return thietBiList.get(0);
    }

    public long insertThietBi(ThietBi thietBi) {
        ContentValues values = new ContentValues();
        values.put("maThietBi", thietBi.getMaThietBi());
        values.put("tenThietBi", thietBi.getTenThietBi());
        values.put("loaiThietBi", thietBi.getLoaiThietBi());
        values.put("hangSX", thietBi.getHangSX());
        values.put("soLuong", thietBi.getSoLuong());
        values.put("giaBan", thietBi.getGiaBan());

        try {
            if (db.insert(TABLE_NAME,null,values) == -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public int updateThietBi(ThietBi thietBi) {
        ContentValues values = new ContentValues();
        values.put("maThietBi", thietBi.getMaThietBi());
        values.put("tenThietBi", thietBi.getTenThietBi());
        values.put("loaiThietBi", thietBi.getLoaiThietBi());
        values.put("hangSX", thietBi.getHangSX());
        values.put("soLuong", thietBi.getSoLuong());
        values.put("giaBan", thietBi.getGiaBan());

        return db.update(TABLE_NAME, values, "maThietBi=?", new String[]{thietBi.getMaThietBi()});
    }

    public int deleteThietBi(String maTB) {
        return db.delete(TABLE_NAME, "maThietBi=?", new String[]{maTB});
    }

    public List<ThietBi> getTop10ThietBi(String month) {
        List<ThietBi> top10TB = new ArrayList<>();
        String sql = "SELECT maTB, SUM(soLuongMua) as soluong FROM HoaDonChiTiet INNER JOIN HoaDon " +
                "ON HoaDon.maHD = HoaDonChiTiet.mahoadon WHERE strftime('%m',HoaDon.ngayMua) = '"+month+"' " +
                "GROUP BY maTB ORDER BY soLuongMua DESC LIMIT 10";

        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            Log.d("cursor===////", c.getString(0));
            ThietBi thietBi = new ThietBi();
            thietBi.setMaThietBi(c.getString(0));
            thietBi.setTenThietBi("");
            thietBi.setLoaiThietBi("");
            thietBi.setHangSX("");
            thietBi.setSoLuong(c.getInt(1));
            thietBi.setGiaBan(0);
            top10TB.add(thietBi);
            c.moveToNext();
        }
        c.close();
        return top10TB;
    }

}
