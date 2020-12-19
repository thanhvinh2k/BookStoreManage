package com.poly.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.poly.duan1.DatabaseHelper;
import com.poly.duan1.model.HoaDon;
import com.poly.duan1.model.HoaDonChiTiet;
import com.poly.duan1.model.ThietBi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAO {
    private SQLiteDatabase db;

    public static final String TABLE_NAME = "HoaDonChiTiet";
    public static final String SQL_HDCT = "CREATE TABLE HoaDonChiTiet (maHDCT INTEGER PRIMARY KEY AUTOINCREMENT, mahoadon text NOT NULL, maTB text NOT NULL, soLuongMua INTEGER);";
    public static final String TAG = "HoaDonChiTietDAO";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public HoaDonChiTietDAO (Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    public long insertHDCT(HoaDonChiTiet hoaDonChiTiet) {
        ContentValues values = new ContentValues();
        values.put("mahoadon", hoaDonChiTiet.getHoaDon().getMaHoaDon());
        values.put("maTB", hoaDonChiTiet.getThietBi().getMaThietBi());
        values.put("soLuongMua", hoaDonChiTiet.getSoLuongMua());

        return db.insert(TABLE_NAME, null, values);
    }

    public List<HoaDonChiTiet> getAllHDCTByID(String maHoaDon) throws ParseException {
        List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
        String sql = "SELECT maHDCT, HoaDon.maHD, HoaDon.ngayMua, ThietBi.maThietBi, ThietBi.tenThietBi, ThietBi.loaiThietBi, " +
                "ThietBi.hangSX, ThietBi.soLuong, ThietBi.giaBan, HoaDonChiTiet.soLuongMua " +
                "FROM HoaDonChiTiet INNER JOIN HoaDon ON HoaDonChiTiet.mahoadon = HoaDon.maHD " +
                "INNER JOIN ThietBi ON HoaDonChiTiet.maTB = ThietBi.maThietBi WHERE HoaDonChiTiet.mahoadon = '" + maHoaDon + "'";
        Cursor c = db.rawQuery(sql, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setMaHDCT(c.getInt(0));
            hoaDonChiTiet.setHoaDon(new HoaDon(c.getString(1), sdf.parse(c.getString(2))));
            hoaDonChiTiet.setThietBi(new ThietBi(c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getInt(7), c.getDouble(8)));
            hoaDonChiTiet.setSoLuongMua(c.getInt(9));

            dsHDCT.add(hoaDonChiTiet);
            c.moveToNext();
        }
        c.close();
        return dsHDCT;
    }

    public int updateHDCT(HoaDonChiTiet hdct) {
        ContentValues values = new ContentValues();
        values.put("maHDCT", hdct.getMaHDCT());
        values.put("mahoadon", hdct.getHoaDon().getMaHoaDon());
        values.put("maTB", hdct.getThietBi().getMaThietBi());
        values.put("soLuongMua", hdct.getSoLuongMua());
        return db.update(TABLE_NAME, values, "maHDCT=?", new String[]{String.valueOf(hdct.getMaHDCT())});
    }

    public int deleteHDCT(String maHDCT) {
        return db.delete(TABLE_NAME, "maHDCT=?", new String[]{maHDCT});
    }

    public boolean checkHoaDon(String maHoaDon) {
        String[] columns = {"mahoadon"};
        String selection = "mahoadon=?";
        String[] selectionArgs = {maHoaDon};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();
            int i = c.getCount();
            c.close();
            if (i <= 0){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public double getDoanhThuTheoNgay() {
        double doanhThu = 0;
        String sql = "SELECT SUM(tongtien) FROM (SELECT SUM(ThietBi.giaBan * HoaDonChiTiet.soLuongMua) as 'tongtien'" +
                "FROM HoaDon INNER JOIN HoaDonChiTiet ON HoaDon.maHD = HoaDonChiTiet.mahoadon " +
                "INNER JOIN ThietBi ON HoaDonChiTiet.maTB = ThietBi.maThietBi WHERE HoaDon.ngayMua = date('now') GROUP BY HoaDonChiTiet.maTB)tmp";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            doanhThu = c.getDouble(0);
            Log.d("doanhthungay==//", String.valueOf(c.getDouble(0)));
        }
        return doanhThu;
    }

    public double getDoanhThuTheoThang() {
        double doanhThu = 0;
        String sql = "SELECT SUM(tongtien) FROM (SELECT SUM(ThietBi.giaBan * HoaDonChiTiet.soLuongMua) as 'tongtien' " +
                "FROM HoaDon INNER JOIN HoaDonChiTiet ON HoaDon.maHD = HoaDonChiTiet.mahoadon INNER JOIN ThietBi " +
                "ON HoaDonChiTiet.maTB = ThietBi.maThietBi WHERE strftime('%m', HoaDon.ngayMua) = strftime('%m', 'now') GROUP BY HoaDonChiTiet.maTB)tmp";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            doanhThu = c.getDouble(0);
            Log.d("doanhthuthang==//", String.valueOf(c.getDouble(0)));
        }

        return doanhThu;
    }

    public double getDoanhThuTheoNam() {
        double doanhThu = 0;
        String sql = "SELECT SUM(tongtien) FROM (SELECT SUM(ThietBi.giaBan * HoaDonChiTiet.soLuongMua) as 'tongtien' " +
                "FROM HoaDon INNER JOIN HoaDonChiTiet ON HoaDon.maHD = HoaDonChiTiet.mahoadon INNER JOIN ThietBi " +
                "ON HoaDonChiTiet.maTB = ThietBi.maThietBi WHERE strftime('%Y', HoaDon.ngayMua) = strftime('%Y', 'now') GROUP BY HoaDonChiTiet.maTB)tmp";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            doanhThu = c.getDouble(0);
            Log.d("doanhthunam==//", String.valueOf(c.getDouble(0)));
        }
        return doanhThu;
    }

}
