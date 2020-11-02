package com.sinhvien.moneylover;

public class NguoiDung {
    private String email;
    private String matKhau;
    private int soTien;

    public NguoiDung(String email, String matKhau, int soTien) {
        this.email = email;
        this.matKhau = matKhau;
        this.soTien = soTien;
    }

    public NguoiDung(String email, String matKhau) {
        this.email = email;
        this.matKhau = matKhau;
        this.soTien = 0;
    }

    public NguoiDung() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }
}
