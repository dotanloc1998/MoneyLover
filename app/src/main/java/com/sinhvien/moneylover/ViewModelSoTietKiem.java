package com.sinhvien.moneylover;

public class ViewModelSoTietKiem {
    private int id;
    private String maSo;
    private int soTienGui;
    private String kyHan;
    private double laiSuat;
    private String ngayGui;

    public ViewModelSoTietKiem() {
    }

    public ViewModelSoTietKiem(String maSo, int soTienGui, String kyHan, double laiSuat, String ngayGui) {
        this.maSo = maSo;
        this.soTienGui = soTienGui;
        this.kyHan = kyHan;
        this.laiSuat = laiSuat;
        this.ngayGui = ngayGui;
    }

    public ViewModelSoTietKiem(int id, String maSo, int soTienGui, String kyHan, double laiSuat, String ngayGui) {
        this.id = id;
        this.maSo = maSo;
        this.soTienGui = soTienGui;
        this.kyHan = kyHan;
        this.laiSuat = laiSuat;
        this.ngayGui = ngayGui;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSo() {
        return maSo;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }

    public int getSoTienGui() {
        return soTienGui;
    }

    public void setSoTienGui(int soTienGui) {
        this.soTienGui = soTienGui;
    }

    public String getKyHan() {
        return kyHan;
    }

    public void setKyHan(String kyHan) {
        this.kyHan = kyHan;
    }

    public double getLaiSuat() {
        return laiSuat;
    }

    public void setLaiSuat(double laiSuat) {
        this.laiSuat = laiSuat;
    }

    public String getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(String ngayGui) {
        this.ngayGui = ngayGui;
    }
}
