package com.poly.duan1.model;

public class LoaiThietBi {
    private String maLoaiThietBi;
    private String loaiThietBi;
    private String viTri;

    public LoaiThietBi() {
    }

    public LoaiThietBi(String maLoaiThietBi, String loaiThietBi, String viTri) {
        this.maLoaiThietBi = maLoaiThietBi;
        this.loaiThietBi = loaiThietBi;
        this.viTri = viTri;
    }

    public String getMaLoaiThietBi() {
        return maLoaiThietBi;
    }

    public void setMaLoaiThietBi(String maLoaiThietBi) {
        this.maLoaiThietBi = maLoaiThietBi;
    }

    public String getLoaiThietBi() {
        return loaiThietBi;
    }

    public void setLoaiThietBi(String loaiThietBi) {
        this.loaiThietBi = loaiThietBi;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }
}
