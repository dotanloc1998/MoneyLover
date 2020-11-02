package com.sinhvien.moneylover;

public class NganHang {
    private String maNganHang;
    private String tenNganHang;

    public NganHang(String maNganHang, String tenNganHang) {
        this.maNganHang = maNganHang;
        this.tenNganHang = tenNganHang;
    }

    public NganHang() {
    }

    public String getMaNganHang() {
        return maNganHang;
    }

    public void setMaNganHang(String maNganHang) {
        this.maNganHang = maNganHang;
    }

    public String getTenNganHang() {
        return tenNganHang;
    }

    public void setTenNganHang(String tenNganHang) {
        this.tenNganHang = tenNganHang;
    }
}
