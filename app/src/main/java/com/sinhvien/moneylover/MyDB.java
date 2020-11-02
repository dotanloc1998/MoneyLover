package com.sinhvien.moneylover;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class MyDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "quanlysotietkiem";
    private static final String KEY_ID = "_id";

    //Các bảng:

    //Bảng đăng nhập:
    private static final String TABLE_DANGNHAP = "TDangNhap";
    private static final String KEY_EMAIL = "email";

    //Bảng tài khoản
    private static final String TABLE_NGUOIDUNG = "TNguoiDung";
    private static final String KEY_MATKHAU = "matkhau";
    private static final String KEY_SOTIEN = "sotien";

    //Bảng ngân hàng
    private static final String TABLE_NGANHANG = "TNganHang";
    private static final String KEY_MANGANHANG = "manganhang";
    private static final String KEY_TENNGANHANG = "tennganhang";

    //Bảng sổ tiết kiệm
    private static final String TABLE_SOTIETKIEM = "TSoTietKiem";
    private static final String KEY_MASO = "maso";
    private static final String KEY_NGAYGUI = "ngaygui";
    private static final String KEY_SOTIENGUI = "sotiengui";
    private static final String KEY_KYHAN = "kyhan";
    private static final String KEY_LAISUAT = "laisuat";
    private static final String KEY_LAISUATKHONGKYHAN = "laisuatkhongkyhan";
    private static final String KEY_TRALAI = "tralai";
    private static final String KEY_KHIDENHAN = "khidenhan";
    private static final String KEY_TIENLAI = "tienlai";
    private static final String KEY_NGAYTINHLAIKHONGKYHAN = "ngaytinhlaikhongkyhan";
    private static final String KEY_TRANGTHAI = "trangthai";
    Context context;

    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String taoBangDangNhap = "CREATE TABLE " + TABLE_DANGNHAP +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT" + ")";

        String taoBangTaiKhoan = "CREATE TABLE " + TABLE_NGUOIDUNG +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT," + KEY_MATKHAU + " TEXT," + KEY_SOTIEN + " INTEGER" + ")";

        String taoBangNganHang = "CREATE TABLE " + TABLE_NGANHANG +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MANGANHANG + " TEXT," + KEY_TENNGANHANG + " TEXT" + ")";

        String taoBangSoTietKiem = "CREATE TABLE " + TABLE_SOTIETKIEM +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MASO + " TEXT," + KEY_MANGANHANG + " TEXT," + KEY_NGAYGUI + " TEXT," + KEY_SOTIENGUI + " INTEGER," + KEY_KYHAN + " TEXT," + KEY_LAISUAT + " REAL," + KEY_LAISUATKHONGKYHAN + " REAL," + KEY_TRALAI + " TEXT," + KEY_KHIDENHAN + " TEXT," + KEY_TIENLAI + " INTEGER," + KEY_NGAYTINHLAIKHONGKYHAN + " TEXT," + KEY_TRANGTHAI + " TEXT," + KEY_EMAIL + " TEXT" + ")";

        sqLiteDatabase.execSQL(taoBangDangNhap);
        sqLiteDatabase.execSQL(taoBangTaiKhoan);
        sqLiteDatabase.execSQL(taoBangNganHang);
        sqLiteDatabase.execSQL(taoBangSoTietKiem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Các hàm sẽ sử dụng
    public List<ViewModelSoTietKiem> LaySoCuaTaiKhoan(String email, String tinhTrang) {
        List<ViewModelSoTietKiem> soCuaTaiKhoan = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor timSo = db.query
                (TABLE_SOTIETKIEM, null, KEY_EMAIL + " = ? AND " + KEY_TRANGTHAI + " = ?", new String[]{email, tinhTrang}, null, null, null);
        if (timSo.getCount() > 0) {
            timSo.moveToFirst();
            do {
                int id = timSo.getInt(timSo.getColumnIndex(KEY_ID));
                String maSo = timSo.getString(timSo.getColumnIndex(KEY_MASO));
                int soTienGui = timSo.getInt(timSo.getColumnIndex(KEY_SOTIENGUI)) + timSo.getInt(timSo.getColumnIndex(KEY_TIENLAI));
                String kyHan = timSo.getString(timSo.getColumnIndex(KEY_KYHAN));
                double laiSuat = timSo.getDouble(timSo.getColumnIndex(KEY_LAISUAT));
                String ngayGui = timSo.getString(timSo.getColumnIndex(KEY_NGAYGUI));
                ViewModelSoTietKiem soTietKiem = new ViewModelSoTietKiem(id, maSo, soTienGui, kyHan, laiSuat, ngayGui);
                soCuaTaiKhoan.add(soTietKiem);
            }
            while (timSo.moveToNext());
        }
        return soCuaTaiKhoan;
    }

    public Cursor LaySoCuaTaiKhoan2(String email, String tinhTrang) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor timSo = db.query
                (TABLE_SOTIETKIEM, null, KEY_EMAIL + " = ? AND " + KEY_TRANGTHAI + " = ?", new String[]{email, tinhTrang}, null, null, null);
        if (timSo.getCount() > 0) {
            timSo.moveToFirst();
        }
        return timSo;
    }

    public List<ViewModelSoTietKiem> LaySoCuaNganHang(String email, String tinhTrang, String maNganHang) {
        List<ViewModelSoTietKiem> soCuaTaiKhoan = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor timSo = db.query
                (TABLE_SOTIETKIEM, null, KEY_EMAIL + " = ? AND " + KEY_TRANGTHAI + " = ? AND " + KEY_MANGANHANG + " = ?", new String[]{email, tinhTrang, maNganHang}, null, null, null);
        if (timSo.getCount() > 0) {
            timSo.moveToFirst();
            do {
                int id = timSo.getInt(timSo.getColumnIndex(KEY_ID));
                String maSo = timSo.getString(timSo.getColumnIndex(KEY_MASO));
                int soTienGui = timSo.getInt(timSo.getColumnIndex(KEY_SOTIENGUI)) + timSo.getInt(timSo.getColumnIndex(KEY_TIENLAI));
                String kyHan = timSo.getString(timSo.getColumnIndex(KEY_KYHAN));
                double laiSuat = timSo.getDouble(timSo.getColumnIndex(KEY_LAISUAT));
                String ngayGui = timSo.getString(timSo.getColumnIndex(KEY_NGAYGUI));
                ViewModelSoTietKiem soTietKiem = new ViewModelSoTietKiem(id, maSo, soTienGui, kyHan, laiSuat, ngayGui);
                soCuaTaiKhoan.add(soTietKiem);
            }
            while (timSo.moveToNext());
        }
        return soCuaTaiKhoan;
    }

    public Cursor LayTatCaNganHang() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor nganHangs = db.query(TABLE_NGANHANG, null, null, null, null, null, null);
        if (nganHangs.getCount() > 0) {
            nganHangs.moveToFirst();
        }
        return nganHangs;
    }

    public String LayMaNganHangBangTen(String tenNganHang) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor ten = db.query
                (TABLE_NGANHANG, null, KEY_TENNGANHANG + " = ?", new String[]{tenNganHang}, null, null, null);
        if (ten.getCount() > 0) {
            ten.moveToFirst();
            return ten.getString(ten.getColumnIndex(KEY_MANGANHANG));
        }
        return "";
    }

    public boolean NganHangTonTai(String maNganHang, String tenNganHang) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor nganHang = db.query(TABLE_NGANHANG, null, KEY_TENNGANHANG + " = ? AND " + KEY_MANGANHANG + " = ?", new String[]{tenNganHang, maNganHang}, null, null, null);
        if (nganHang.getCount() > 0) {
            return true;
        }
        return false;
    }

    public String NguoiDangDangNhap() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor dangNhap = db.query(TABLE_DANGNHAP, null, null, null, null, null, null);
        if (dangNhap.getCount() > 0) {
            dangNhap.moveToFirst();
            return dangNhap.getString(dangNhap.getColumnIndex(KEY_EMAIL));
        }
        return "";
    }

    public NguoiDung LayDuLieuNguoiDung(String email) {
        NguoiDung thongTin = new NguoiDung();
        SQLiteDatabase db = getWritableDatabase();
        String cauLenh = "Select *" + " From " + TABLE_NGUOIDUNG + " Where " + KEY_EMAIL + " = " + "'" + email + "'";
        Cursor nguoiDung = db.rawQuery(cauLenh, null);
        if (nguoiDung.getCount() > 0) {
            nguoiDung.moveToFirst();
            String matKhau = nguoiDung.getString(nguoiDung.getColumnIndex(KEY_MATKHAU));
            int soTien = nguoiDung.getInt(nguoiDung.getColumnIndex(KEY_SOTIEN));
            thongTin.setEmail(email);
            thongTin.setMatKhau(matKhau);
            thongTin.setSoTien(soTien);
        }
        return thongTin;
    }

    public void CapNhatTienNguoiDung(NguoiDung taiKhoan) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, taiKhoan.getEmail());
        values.put(KEY_MATKHAU, taiKhoan.getMatKhau());
        values.put(KEY_SOTIEN, taiKhoan.getSoTien());
        db.update(TABLE_NGUOIDUNG, values, KEY_EMAIL + " = " + "'" + taiKhoan.getEmail() + "'", null);
        db.close();
    }

    public void ThemNganHang(NganHang nganHangMoi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MANGANHANG, nganHangMoi.getMaNganHang());
        values.put(KEY_TENNGANHANG, nganHangMoi.getTenNganHang());
        db.insert(TABLE_NGANHANG, null, values);
        db.close();
    }

    public void ThemSoTietKiem(SoTietKiem soTietKiem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MASO, soTietKiem.getMaSo());
        values.put(KEY_MANGANHANG, soTietKiem.getMaNganHang());
        values.put(KEY_NGAYGUI, soTietKiem.getNgayGui());
        values.put(KEY_SOTIENGUI, soTietKiem.getSoTienGui());
        values.put(KEY_KYHAN, soTietKiem.getKyHan());
        values.put(KEY_LAISUAT, soTietKiem.getLaiSuat());
        values.put(KEY_LAISUATKHONGKYHAN, soTietKiem.getLaiSuatKhonKyHan());
        values.put(KEY_TRALAI, soTietKiem.getTraLai());
        values.put(KEY_KHIDENHAN, soTietKiem.getKhiDenHan());
        values.put(KEY_TIENLAI, soTietKiem.getTienLai());
        values.put(KEY_EMAIL, soTietKiem.getEmail());
        values.put(KEY_TRANGTHAI, soTietKiem.getTrangThai());
        values.put(KEY_NGAYTINHLAIKHONGKYHAN, soTietKiem.getNgayTinhLaiKhongKyHan());
        db.insert(TABLE_SOTIETKIEM, null, values);
        db.close();
    }

    public void SuaSoTietKiem(SoTietKiem soTietKiemCanSua, int idCuaSo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MASO, soTietKiemCanSua.getMaSo());
        values.put(KEY_MANGANHANG, soTietKiemCanSua.getMaNganHang());
        values.put(KEY_NGAYGUI, soTietKiemCanSua.getNgayGui());
        values.put(KEY_SOTIENGUI, soTietKiemCanSua.getSoTienGui());
        values.put(KEY_KYHAN, soTietKiemCanSua.getKyHan());
        values.put(KEY_LAISUAT, soTietKiemCanSua.getLaiSuat());
        values.put(KEY_LAISUATKHONGKYHAN, soTietKiemCanSua.getLaiSuatKhonKyHan());
        values.put(KEY_TRALAI, soTietKiemCanSua.getTraLai());
        values.put(KEY_KHIDENHAN, soTietKiemCanSua.getKhiDenHan());
        values.put(KEY_TIENLAI, soTietKiemCanSua.getTienLai());
        values.put(KEY_EMAIL, soTietKiemCanSua.getEmail());
        values.put(KEY_TRANGTHAI, soTietKiemCanSua.getTrangThai());
        values.put(KEY_NGAYTINHLAIKHONGKYHAN, soTietKiemCanSua.getNgayTinhLaiKhongKyHan());
        db.update(TABLE_SOTIETKIEM, values, KEY_ID + " = " + "'" + idCuaSo + "'", null);
        db.close();
    }

    public void ThemTaiKhoan(NguoiDung nguoiDung) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, nguoiDung.getEmail());
        values.put(KEY_MATKHAU, nguoiDung.getMatKhau());
        values.put(KEY_SOTIEN, nguoiDung.getSoTien());
        db.insert(TABLE_NGUOIDUNG, null, values);
        db.close();
    }

    public SoTietKiem LaySoThongQuaId(int idSo) {
        SQLiteDatabase db = getWritableDatabase();
        SoTietKiem soCanTim = new SoTietKiem();
        Cursor so = db.query(TABLE_SOTIETKIEM, null, KEY_ID + " = ?", new String[]{idSo + ""}, null, null, null);
        if (so.getCount() > 0) {
            so.moveToFirst();
            soCanTim.setMaSo(so.getString(so.getColumnIndex(KEY_MASO)));
            soCanTim.setMaNganHang(so.getString(so.getColumnIndex(KEY_MANGANHANG)));
            soCanTim.setNgayGui(so.getString(so.getColumnIndex(KEY_NGAYGUI)));
            soCanTim.setSoTienGui(so.getInt(so.getColumnIndex(KEY_SOTIENGUI)));
            soCanTim.setKyHan(so.getString(so.getColumnIndex(KEY_KYHAN)));
            soCanTim.setLaiSuat(so.getDouble(so.getColumnIndex(KEY_LAISUAT)));
            soCanTim.setLaiSuatKhonKyHan(so.getDouble(so.getColumnIndex(KEY_LAISUATKHONGKYHAN)));
            soCanTim.setTraLai(so.getString(so.getColumnIndex(KEY_TRALAI)));
            soCanTim.setKhiDenHan(so.getString(so.getColumnIndex(KEY_KHIDENHAN)));
            soCanTim.setTienLai(so.getInt(so.getColumnIndex(KEY_TIENLAI)));
            soCanTim.setEmail(so.getString(so.getColumnIndex(KEY_EMAIL)));
            soCanTim.setTrangThai(so.getString(so.getColumnIndex(KEY_TRANGTHAI)));
            soCanTim.setNgayTinhLaiKhongKyHan(so.getString(so.getColumnIndex(KEY_NGAYTINHLAIKHONGKYHAN)));
        }
        return soCanTim;
    }

    public SoTietKiem LaySoThongQuaMaSo(String maSo, String email) {
        SQLiteDatabase db = getWritableDatabase();
        SoTietKiem soCanTim = new SoTietKiem();
        Cursor so = db.query(TABLE_SOTIETKIEM, null, KEY_MASO + " = ? AND " + KEY_EMAIL + " = ?", new String[]{maSo, email}, null, null, null);
        if (so.getCount() > 0) {
            so.moveToFirst();
            soCanTim.setMaSo(so.getString(so.getColumnIndex(KEY_MASO)));
            soCanTim.setMaNganHang(so.getString(so.getColumnIndex(KEY_MANGANHANG)));
            soCanTim.setNgayGui(so.getString(so.getColumnIndex(KEY_NGAYGUI)));
            soCanTim.setSoTienGui(so.getInt(so.getColumnIndex(KEY_SOTIENGUI)));
            soCanTim.setKyHan(so.getString(so.getColumnIndex(KEY_KYHAN)));
            soCanTim.setLaiSuat(so.getDouble(so.getColumnIndex(KEY_LAISUAT)));
            soCanTim.setLaiSuatKhonKyHan(so.getDouble(so.getColumnIndex(KEY_LAISUATKHONGKYHAN)));
            soCanTim.setTraLai(so.getString(so.getColumnIndex(KEY_TRALAI)));
            soCanTim.setKhiDenHan(so.getString(so.getColumnIndex(KEY_KHIDENHAN)));
            soCanTim.setTienLai(so.getInt(so.getColumnIndex(KEY_TIENLAI)));
            soCanTim.setEmail(so.getString(so.getColumnIndex(KEY_EMAIL)));
            soCanTim.setTrangThai(so.getString(so.getColumnIndex(KEY_TRANGTHAI)));
            soCanTim.setNgayTinhLaiKhongKyHan(so.getString(so.getColumnIndex(KEY_NGAYTINHLAIKHONGKYHAN)));
        }
        return soCanTim;
    }

    public int LayIdCuaSo(String maSo, String email) {
        SQLiteDatabase db = getWritableDatabase();
        int id = -1;
        Cursor so = db.query(TABLE_SOTIETKIEM, null, KEY_MASO + " = ?AND " + KEY_EMAIL + " = ?", new String[]{maSo, email}, null, null, null);
        if (so.getCount() > 0) {
            so.moveToFirst();
            id = so.getInt(so.getColumnIndex(KEY_ID));
            return id;
        }
        return id;
    }

    public NganHang LayNganHang(String maNganHang) {
        SQLiteDatabase db = getWritableDatabase();
        NganHang nganHang = new NganHang();
        Cursor nh = db.query(TABLE_NGANHANG, null, KEY_MANGANHANG + " = ?", new String[]{maNganHang}, null, null, null);
        if (nh.getCount() > 0) {
            nh.moveToFirst();
            nganHang.setMaNganHang(nh.getString(nh.getColumnIndex(KEY_MANGANHANG)));
            nganHang.setTenNganHang(nh.getString(nh.getColumnIndex(KEY_TENNGANHANG)));
        }
        return nganHang;
    }

    public boolean KiemTraSoTonTai(String maSo, String email) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor so = db.query(TABLE_SOTIETKIEM, null, KEY_MASO + " = ? AND " + KEY_EMAIL + " = ?", new String[]{maSo, email}, null, null, null);
        if (so.getCount() > 0) {
            return true;
        }
        return false;
    }

    public void ThemDangNhap(String dangNhap) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, dangNhap);
        db.insert(TABLE_DANGNHAP, null, values);
        db.close();
    }

    public void DangXuat(String tenDangNhap) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_DANGNHAP, KEY_EMAIL + " = " + "'" + tenDangNhap + "'", null);
        db.close();
    }
}
