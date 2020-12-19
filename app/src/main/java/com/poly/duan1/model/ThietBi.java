package com.poly.duan1.model;

public class ThietBi {
    private String maThietBi;
    private String tenThietBi;
    private String loaiThietBi;
    private String hangSX;
    private int soLuong;
    private double giaBan;

    public ThietBi() {
    }

    public ThietBi(String maThietBi, String tenThietBi, String loaiThietBi, String hangSX, int soLuong, double giaBan) {
        this.maThietBi = maThietBi;
        this.tenThietBi = tenThietBi;
        this.loaiThietBi = loaiThietBi;
        this.hangSX = hangSX;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
    }

    public String getMaThietBi() {
        return maThietBi;
    }

    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }

    public String getTenThietBi() {
        return tenThietBi;
    }

    public void setTenThietBi(String tenThietBi) {
        this.tenThietBi = tenThietBi;
    }

    public String getLoaiThietBi() {
        return loaiThietBi;
    }

    public void setLoaiThietBi(String loaiThietBi) {
        this.loaiThietBi = loaiThietBi;
    }

    public String getHangSX() {
        return hangSX;
    }

    public void setHangSX(String hangSX) {
        this.hangSX = hangSX;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }
}
