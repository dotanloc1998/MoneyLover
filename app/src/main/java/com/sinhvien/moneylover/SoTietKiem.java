package com.sinhvien.moneylover;

public class SoTietKiem {
    private String maSo;
    private String maNganHang;
    private String ngayGui;
    private int soTienGui;
    private String kyHan;
    private double laiSuat;
    private double laiSuatKhonKyHan;
    private String traLai;
    private String khiDenHan;
    private int tienLai;
    private String email;
    private String trangThai;
    private String ngayTinhLaiKhongKyHan;

    public SoTietKiem(String maSo, String maNganHang, String ngayGui, int soTienGui, String kyHan, double laiSuat, double laiSuatKhonKyHan, String traLai, String khiDenHan, int tienLai, String email, String trangThai, String ngayTinhLaiKhongKyHan) {
        this.maSo = maSo;
        this.maNganHang = maNganHang;
        this.ngayGui = ngayGui;
        this.soTienGui = soTienGui;
        this.kyHan = kyHan;
        this.laiSuat = laiSuat;
        this.laiSuatKhonKyHan = laiSuatKhonKyHan;
        this.traLai = traLai;
        this.khiDenHan = khiDenHan;
        this.tienLai = tienLai;
        this.email = email;
        this.trangThai = trangThai;
        this.ngayTinhLaiKhongKyHan = ngayTinhLaiKhongKyHan;
    }

    public SoTietKiem SaoChepSo(SoTietKiem soCanChep) {
        SoTietKiem saoChep = new SoTietKiem();
        saoChep.setEmail(soCanChep.getEmail());
        saoChep.setKhiDenHan(soCanChep.getKhiDenHan());
        saoChep.setKyHan(soCanChep.getKyHan());
        saoChep.setLaiSuat(soCanChep.getLaiSuat());
        saoChep.setLaiSuatKhonKyHan(soCanChep.getLaiSuatKhonKyHan());
        saoChep.setMaNganHang(soCanChep.getMaNganHang());
        saoChep.setMaSo(soCanChep.getMaSo());
        saoChep.setNgayGui(soCanChep.getNgayGui());
        saoChep.setNgayTinhLaiKhongKyHan(soCanChep.getNgayTinhLaiKhongKyHan());
        saoChep.setSoTienGui(soCanChep.getSoTienGui());
        saoChep.setTienLai(soCanChep.getTienLai());
        saoChep.setTraLai(soCanChep.getTraLai());
        saoChep.setTrangThai(soCanChep.getTrangThai());
        return saoChep;
    }

    public SoTietKiem(String maSo, String maNganHang, String ngayGui, int soTienGui, String kyHan, double laiSuat, double laiSuatKhonKyHan, String traLai, String khiDenHan, int tienLai, String email, String ngayTinhLaiKhongKyHan) {
        this.maSo = maSo;
        this.maNganHang = maNganHang;
        this.ngayGui = ngayGui;
        this.soTienGui = soTienGui;
        this.kyHan = kyHan;
        this.laiSuat = laiSuat;
        this.laiSuatKhonKyHan = laiSuatKhonKyHan;
        this.traLai = traLai;
        this.khiDenHan = khiDenHan;
        this.tienLai = tienLai;
        this.email = email;
        this.ngayTinhLaiKhongKyHan = ngayTinhLaiKhongKyHan;
        this.trangThai = "CTT";
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public SoTietKiem() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaSo() {
        return maSo;
    }

    public void setMaSo(String maSo) {
        this.maSo = maSo;
    }

    public String getMaNganHang() {
        return maNganHang;
    }

    public void setMaNganHang(String maNganHang) {
        this.maNganHang = maNganHang;
    }

    public String getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(String ngayGui) {
        this.ngayGui = ngayGui;
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

    public double getLaiSuatKhonKyHan() {
        return laiSuatKhonKyHan;
    }

    public void setLaiSuatKhonKyHan(double laiSuatKhonKyHan) {
        this.laiSuatKhonKyHan = laiSuatKhonKyHan;
    }

    public String getTraLai() {
        return traLai;
    }

    public void setTraLai(String traLai) {
        this.traLai = traLai;
    }

    public String getKhiDenHan() {
        return khiDenHan;
    }

    public void setKhiDenHan(String khiDenHan) {
        this.khiDenHan = khiDenHan;
    }

    public int getTienLai() {
        return tienLai;
    }

    public void setTienLai(int tienLai) {
        this.tienLai = tienLai;
    }

    public String getNgayTinhLaiKhongKyHan() {
        return ngayTinhLaiKhongKyHan;
    }

    public void setNgayTinhLaiKhongKyHan(String ngayTinhLaiKhongKyHan) {
        this.ngayTinhLaiKhongKyHan = ngayTinhLaiKhongKyHan;
    }
}
