package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duan1.DatabaseHelper;
import com.poly.duan1.model.LoaiThietBi;

import java.util.ArrayList;
import java.util.List;

public class LoaiThietBiDAO {
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "LoaiThietBi";
    public static final String SQL_LOAI_TB = "CREATE TABLE LoaiThietBi (maLoaiTB text PRIMARY KEY, loaiTB text, vitri text);";
    public static final String TAG = "LoaiThietBiDAO";

    public LoaiThietBiDAO(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    public List<LoaiThietBi> getAllLoaiTB() {
        List<LoaiThietBi> dsLoaiTB = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            LoaiThietBi loaiThietBi = new LoaiThietBi();
            loaiThietBi.setMaLoaiThietBi(c.getString(c.getColumnIndex("maLoaiTB")));
            loaiThietBi.setLoaiThietBi(c.getString(c.getColumnIndex("loaiTB")));
            loaiThietBi.setViTri(c.getString(c.getColumnIndex("vitri")));
            dsLoaiTB.add(loaiThietBi);
            c.moveToNext();
        }
        c.close();
        return dsLoaiTB;
    }

    public long insertLoaiTB(LoaiThietBi loaiThietBi) {
        ContentValues values = new ContentValues();
        values.put("maLoaiTB", loaiThietBi.getMaLoaiThietBi());
        values.put("loaiTB", loaiThietBi.getLoaiThietBi());
        values.put("vitri", loaiThietBi.getViTri());

        try {
            if (db.insert(TABLE_NAME,null,values) == -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public int updateLoaiTB(LoaiThietBi loaiThietBi) {
        ContentValues values = new ContentValues();
        values.put("maLoaiTB", loaiThietBi.getMaLoaiThietBi());
        values.put("loaiTB", loaiThietBi.getLoaiThietBi());
        values.put("vitri", loaiThietBi.getViTri());

        return db.update(TABLE_NAME, values, "maLoaiTB=?", new String[]{loaiThietBi.getMaLoaiThietBi()});
    }

    public int deleteLoaiTB(String maLoaiTB) {
        return db.delete(TABLE_NAME, "maLoaiTB=?", new String[]{maLoaiTB});
    }
}
