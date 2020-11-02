package com.sinhvien.moneylover;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TinhToan {
    public static int TinhLaiThang(int soTienGoc, double laiSuat, String soThang) {
        String[] thang = soThang.split(" ");
        int thangInt = Integer.parseInt(thang[0]);
        return (int) Math.round(soTienGoc * (laiSuat / 100 * thangInt / 12));
    }

    public static int TinhLaiDinhKyHangThang(int soTienGoc, double laiSuat, String soThang) {
        String[] thang = soThang.split(" ");
        int thangInt = Integer.parseInt(thang[0]);
        return (int) Math.round(soTienGoc * (laiSuat / 100 * thangInt / 12) * 1 / thangInt);
    }

    public static int TinhLaiNgay(int soTienGoc, double laiSuat, int soNgay) {
        if (soNgay == 0) {
            return soTienGoc;
        }
        return (int) Math.round(soTienGoc * (laiSuat / 100 * soNgay / 365));
    }

    public static int TinhLaiThang2(int soTienGoc, double laiSuat, int soThang) {
        if (soThang == 0) {
            return soTienGoc;
        }
        return (int) Math.round(soTienGoc * (laiSuat / 100 * soThang / 12));
    }

    public static boolean ToiNgayGuiThemChua(String ngayGuiString, String loaiKyHan) {
        try {
            String[] soThang = loaiKyHan.split(" ");
            int thang = Integer.parseInt(soThang[0]);
            Date ngayGuiDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayGuiString);
            Calendar ngayGui = Calendar.getInstance();
            ngayGui.setTime(ngayGuiDate);
            Calendar homNay = Calendar.getInstance();
            ngayGui.add(Calendar.MONTH, -thang);
            if (ngayGui.get(Calendar.YEAR) == homNay.get(Calendar.YEAR) && ngayGui.get(Calendar.MONTH) == homNay.get(Calendar.MONTH) && ngayGui.get(Calendar.DATE) == homNay.get(Calendar.DATE)) {
                return true;
            }
        } catch (ParseException e) {

        }
        return false;
    }

    public static boolean DungNgayChua(String ngayGuiString, String loaiKyHan) {
        try {
            String[] soThang = loaiKyHan.split(" ");
            int thang = Integer.parseInt(soThang[0]);
            Date ngayGuiDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayGuiString);
            Calendar ngayGui = Calendar.getInstance();
            ngayGui.setTime(ngayGuiDate);
            Calendar homNay = Calendar.getInstance();
            ngayGui.add(Calendar.MONTH, thang);
            if (ngayGui.equals(homNay) || ngayGui.compareTo(homNay) < 0)
                return true;

        } catch (ParseException e) {
        }
        return false;
    }

    public static boolean DungNgayChuaThang(String ngayGuiString, int thang) {
        try {
            Date ngayGuiDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayGuiString);
            Calendar ngayGui = Calendar.getInstance();
            ngayGui.setTime(ngayGuiDate);
            Calendar homNay = Calendar.getInstance();
            ngayGui.add(Calendar.MONTH, thang);
            if (ngayGui.equals(homNay) || ngayGui.compareTo(homNay) < 0)
                return true;
        } catch (ParseException e) {

        }
        return false;
    }

    public static boolean DungNgayChuaNgay(String ngayGuiString, int ngay) {
        try {
            Date ngayGuiDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayGuiString);
            Calendar ngayGui = Calendar.getInstance();
            ngayGui.setTime(ngayGuiDate);
            Calendar homNay = Calendar.getInstance();
            ngayGui.add(Calendar.DAY_OF_MONTH, ngay);
            if (ngayGui.equals(homNay) || ngayGui.compareTo(homNay) < 0)
                return true;
        } catch (ParseException e) {
        }
        return false;
    }

    public static String CongKyHan(String ngayVao, String loaiKyHan) {
        try {
            String[] soThang = loaiKyHan.split(" ");
            int thang = Integer.parseInt(soThang[0]);
            Date ngayGuiDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayVao);
            Calendar ngayGui = Calendar.getInstance();
            ngayGui.setTime(ngayGuiDate);
            ngayGui.add(Calendar.MONTH, thang);
            return new SimpleDateFormat("dd/MM/yyyy").format(ngayGui.getTime());
        } catch (ParseException e) {

        }
        return "";
    }

    public static String CongThang(String ngayVao, int thang) {
        try {
            Date ngayGuiDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayVao);
            Calendar ngayGui = Calendar.getInstance();
            ngayGui.setTime(ngayGuiDate);
            ngayGui.add(Calendar.MONTH, thang);
            return new SimpleDateFormat("dd/MM/yyyy").format(ngayGui.getTime());
        } catch (ParseException e) {

        }
        return "";
    }

    public static String CongNgay(String ngayVao, int ngay) {
        try {
            Date ngayGuiDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayVao);
            Calendar ngayGui = Calendar.getInstance();
            ngayGui.setTime(ngayGuiDate);
            ngayGui.add(Calendar.DAY_OF_MONTH, ngay);
            return new SimpleDateFormat("dd/MM/yyyy").format(ngayGui.getTime());
        } catch (ParseException e) {

        }
        return "";
    }

    public static int GuiDuocBaoNhieuNgay(String ngayGuiString) {
        try {
            Date ngayGuiDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayGuiString);
            Calendar homNay = Calendar.getInstance();
            Calendar ngayGui = Calendar.getInstance();
            ngayGui.setTime(ngayGuiDate);
            long khacBiet = homNay.getTimeInMillis() - ngayGui.getTimeInMillis(); //trả về millis
            int soNgay = (int) khacBiet / (24 * 60 * 60 * 1000);
            return soNgay;
        } catch (ParseException e) {

        }
        return -1;
    }

    public static boolean GuiDuThangKhong(int soNgayGui) {
        if (soNgayGui % 30 == 0) {
            return true;
        }
        return false;
    }
}
