package com.sinhvien.moneylover;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoTietKiemActivity extends AppCompatActivity {
    ImageButton nutThemSTK, nutThemNganHang, nutNguoiDung;
    private static ListView listViewSoTietKiem, listViewSoTatToan;
    private static Spinner spinnerTenNganHang;
    private static Cursor nganHang;
    private static TextView textViewTongTien, textViewTongTienCuaNganHang;
    private static List<ViewModelSoTietKiem> soTietKiems;
    MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_so_tiet_kiem);
        AnhXa();
        nutThemSTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor nganHang = db.LayTatCaNganHang();
                if (nganHang.getCount() > 0) {
                    Intent intent = new Intent(getApplicationContext(), ThemSoActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng thêm ngân hàng trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nutNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NguoiDungActivity.class);
                startActivity(intent);
            }
        });
        nutThemNganHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThemNganHangActivity.class);
                startActivity(intent);
            }
        });
        CapNhatSoTietKiem(getApplicationContext());
        CapNhatGiaoDien(getApplicationContext());
        spinnerTenNganHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String maNganHang = db.LayMaNganHangBangTen(spinnerTenNganHang.getSelectedItem().toString());
                List<ViewModelSoTietKiem> soTietKiems = db.LaySoCuaNganHang(db.NguoiDangDangNhap(), "CTT", maNganHang);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                int sum = 0;
                for (ViewModelSoTietKiem soTietKiem : soTietKiems) {
                    sum += soTietKiem.getSoTienGui();
                }
                String giaDaFormat = decimalFormat.format(sum);
                textViewTongTienCuaNganHang.setText(" (" + giaDaFormat + " đ" + ")");
                SoTietKiemAdapter soTietKiemAdapter = new SoTietKiemAdapter(getApplicationContext(), soTietKiems);
                listViewSoTietKiem.setAdapter(soTietKiemAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        registerForContextMenu(listViewSoTietKiem);
        listViewSoTatToan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewModelSoTietKiem duocChon = (ViewModelSoTietKiem) listViewSoTatToan.getAdapter().getItem(i);
                Intent moGiaoDienChiTietSo = new Intent(getApplicationContext(), ChiTietSoTietKiemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("maso", duocChon.getMaSo());
                moGiaoDienChiTietSo.putExtras(bundle);
                startActivity(moGiaoDienChiTietSo);
            }
        });
    }

    private void AnhXa() {
        nutThemSTK = findViewById(R.id.nutThemSTK);
        nutThemNganHang = findViewById(R.id.nutThemNganHang);
        nutNguoiDung = findViewById(R.id.nutNguoiDung);
        listViewSoTietKiem = findViewById(R.id.listViewSoTietKiem);
        listViewSoTatToan = findViewById(R.id.listViewSoTatToan);
        textViewTongTien = findViewById(R.id.textViewTongTien);
        textViewTongTienCuaNganHang = findViewById(R.id.textViewTongTienCuaNganHang);
        db = new MyDB(getApplicationContext());
        spinnerTenNganHang = findViewById(R.id.spinnerTenNganHang);
    }

    public static void CapNhatGiaoDien(Context context) {
        MyDB db = new MyDB(context);
        nganHang = db.LayTatCaNganHang();
        List<String> danhSachSpinner = new ArrayList<>();
        if (nganHang.getCount() > 0) {
            do {
                danhSachSpinner.add(nganHang.getString(nganHang.getColumnIndex("tennganhang")));
            } while (nganHang.moveToNext());
        }
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, danhSachSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTenNganHang.setAdapter(adapter);
        soTietKiems = db.LaySoCuaTaiKhoan(db.NguoiDangDangNhap(), "DTT");
        Collections.reverse(soTietKiems);
        SoTietKiemAdapter soTietKiemAdapter = new SoTietKiemAdapter(context, soTietKiems);
        listViewSoTatToan.setAdapter(soTietKiemAdapter);
        soTietKiems = db.LaySoCuaTaiKhoan(db.NguoiDangDangNhap(), "CTT");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        int sum = 0;
        for (ViewModelSoTietKiem soTietKiem : soTietKiems) {
            sum += soTietKiem.getSoTienGui();
        }
        String giaDaFormat = decimalFormat.format(sum);
        textViewTongTien.setText(context.getString(R.string.tongTien) + " " + giaDaFormat + " (" + soTietKiems.size() + " sổ" + ")");
    }

    public static void CapNhatSoTietKiem(Context context) {
        MyDB db = new MyDB(context);
        Cursor soCuaTaiKhoan = db.LaySoCuaTaiKhoan2(db.NguoiDangDangNhap(), "CTT");
        if (soCuaTaiKhoan.getCount() > 0) {
            do {
                int idCuaSo = soCuaTaiKhoan.getInt(soCuaTaiKhoan.getColumnIndex("_id"));
                SoTietKiem soTietKiemMoi = db.LaySoThongQuaId(idCuaSo);
                String loaiKyHan = soTietKiemMoi.getKyHan();
                if (!loaiKyHan.equals("Không kỳ hạn")) {
                    String traLai = soTietKiemMoi.getTraLai();
                    if (traLai.equals("Cuối kỳ")) {
                        String ngayGuiTien = soTietKiemMoi.getNgayGui();
                        while (TinhToan.DungNgayChua(ngayGuiTien, loaiKyHan)) {
                            ngayGuiTien = TinhToan.CongKyHan(ngayGuiTien, loaiKyHan);
                            String ngayTinhLaiKhongKyHan = TinhToan.CongKyHan(ngayGuiTien,loaiKyHan);
                            String khiToiHan = soTietKiemMoi.getKhiDenHan();
                            if (khiToiHan.equals("Tái tục gốc và lãi")) {
                                soTietKiemMoi.setNgayTinhLaiKhongKyHan(ngayTinhLaiKhongKyHan);
                                soTietKiemMoi.setNgayGui(ngayGuiTien);
                                int soTienGoc = soTietKiemMoi.getSoTienGui();
                                double laiSuat = soTietKiemMoi.getLaiSuat();
                                String soThang = soTietKiemMoi.getKyHan();
                                int tienGocMoi = soTienGoc + TinhToan.TinhLaiThang(soTienGoc, laiSuat, soThang);
                                soTietKiemMoi.setSoTienGui(tienGocMoi);
                                db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                            } else if (khiToiHan.equals("Tái tục gốc")) {
                                soTietKiemMoi.setNgayTinhLaiKhongKyHan(ngayTinhLaiKhongKyHan);
                                soTietKiemMoi.setNgayGui(ngayGuiTien);
                                int soTienGoc = soTietKiemMoi.getSoTienGui();
                                double laiSuat = soTietKiemMoi.getLaiSuat();
                                String soThang = soTietKiemMoi.getKyHan();
                                int tienLai = TinhToan.TinhLaiThang(soTienGoc, laiSuat, soThang);
                                NguoiDung nguoiDung = db.LayDuLieuNguoiDung(db.NguoiDangDangNhap());
                                nguoiDung.setSoTien(nguoiDung.getSoTien() + tienLai);
                                db.CapNhatTienNguoiDung(nguoiDung);
                                db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                            } else {
                                int soTienGoc = soTietKiemMoi.getSoTienGui();
                                double laiSuat = soTietKiemMoi.getLaiSuat();
                                String soThang = soTietKiemMoi.getKyHan();
                                int tienKiemDuoc = soTienGoc + TinhToan.TinhLaiThang(soTienGoc, laiSuat, soThang);
                                soTietKiemMoi.setTrangThai("DTT");
                                NguoiDung nguoiDung = db.LayDuLieuNguoiDung(db.NguoiDangDangNhap());
                                nguoiDung.setSoTien(nguoiDung.getSoTien() + tienKiemDuoc);
                                db.CapNhatTienNguoiDung(nguoiDung);
                                db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                                break;
                            }
                        }
                    } else if (traLai.equals("Đầu kỳ")) {
                        String ngayGuiTien = soTietKiemMoi.getNgayGui();
                        while (TinhToan.DungNgayChua(ngayGuiTien, loaiKyHan)) {
                            ngayGuiTien = TinhToan.CongKyHan(ngayGuiTien, loaiKyHan);
                            String ngayTinhLaiKhongKyHan = TinhToan.CongKyHan(ngayGuiTien,loaiKyHan);
                            String khiToiHan = soTietKiemMoi.getKhiDenHan();
                            if (khiToiHan.equals("Tái tục gốc và lãi")) {
                                soTietKiemMoi.setNgayTinhLaiKhongKyHan(ngayTinhLaiKhongKyHan);
                                soTietKiemMoi.setNgayGui(ngayGuiTien);
                                int tienGocMoi = soTietKiemMoi.getSoTienGui() + soTietKiemMoi.getTienLai();
                                double laiSuat = soTietKiemMoi.getLaiSuat();
                                String soThang = soTietKiemMoi.getKyHan();
                                int tienLaiMoi = TinhToan.TinhLaiThang(tienGocMoi, laiSuat, soThang);
                                soTietKiemMoi.setSoTienGui(tienGocMoi);
                                soTietKiemMoi.setTienLai(tienLaiMoi);
                                db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                            } else if (khiToiHan.equals("Tái tục gốc")) {
                                soTietKiemMoi.setNgayTinhLaiKhongKyHan(ngayTinhLaiKhongKyHan);
                                soTietKiemMoi.setNgayGui(ngayGuiTien);
                                int tienLai = soTietKiemMoi.getTienLai();
                                NguoiDung nguoiDung = db.LayDuLieuNguoiDung(db.NguoiDangDangNhap());
                                nguoiDung.setSoTien(nguoiDung.getSoTien() + tienLai);
                                db.CapNhatTienNguoiDung(nguoiDung);
                                db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                            } else {
                                int soTienGoc = soTietKiemMoi.getSoTienGui();
                                int tienLai = soTietKiemMoi.getTienLai();
                                soTietKiemMoi.setTrangThai("DTT");
                                int tienKiemDuoc = soTienGoc + tienLai;
                                NguoiDung nguoiDung = db.LayDuLieuNguoiDung(db.NguoiDangDangNhap());
                                nguoiDung.setSoTien(nguoiDung.getSoTien() + tienKiemDuoc);
                                db.CapNhatTienNguoiDung(nguoiDung);
                                db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                                break;
                            }
                        }
                    } else {
                        String ngayGuiTien = soTietKiemMoi.getNgayGui();
                        String ngayTraLai = soTietKiemMoi.getNgayTinhLaiKhongKyHan();
                        while (TinhToan.DungNgayChuaThang(ngayTraLai, 1) || TinhToan.DungNgayChua(ngayGuiTien, loaiKyHan)) {
                            ngayTraLai = TinhToan.CongThang(ngayTraLai, 1);
                            int soTienGoc = soTietKiemMoi.getSoTienGui();
                            double laiSuat = soTietKiemMoi.getLaiSuat();
                            String kyHan = soTietKiemMoi.getKyHan();
                            String khiToiHan = soTietKiemMoi.getKhiDenHan();
                            int tienLai = soTietKiemMoi.getTienLai();
                            soTietKiemMoi.setNgayTinhLaiKhongKyHan(ngayTraLai);
                            int tienLaiLuuDong = TinhToan.TinhLaiDinhKyHangThang(soTienGoc + tienLai, laiSuat, kyHan);
                            tienLai += tienLaiLuuDong;
                            soTietKiemMoi.setTienLai(tienLai);
                            db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                            if (TinhToan.DungNgayChua(ngayGuiTien, loaiKyHan)) {
                                if (khiToiHan.equals("Tái tục gốc và lãi")) {
                                    ngayGuiTien = TinhToan.CongKyHan(ngayGuiTien, loaiKyHan);
                                    String ngayTraLaiMoi = TinhToan.CongThang(ngayGuiTien, 1);
                                    soTietKiemMoi.setNgayGui(ngayGuiTien);
                                    soTietKiemMoi.setNgayTinhLaiKhongKyHan(ngayTraLaiMoi);
                                    soTietKiemMoi.setSoTienGui(soTietKiemMoi.getSoTienGui() + soTietKiemMoi.getTienLai());
                                    soTietKiemMoi.setTienLai(0);
                                    db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                                } else if (khiToiHan.equals("Tái tục gốc")) {
                                    ngayGuiTien = TinhToan.CongKyHan(ngayGuiTien, loaiKyHan);
                                    String ngayTraLaiMoi = TinhToan.CongThang(ngayGuiTien, 1);
                                    soTietKiemMoi.setNgayGui(ngayGuiTien);
                                    soTietKiemMoi.setNgayTinhLaiKhongKyHan(ngayTraLaiMoi);
                                    soTietKiemMoi.setTienLai(0);
                                    NguoiDung nguoiDung = db.LayDuLieuNguoiDung(db.NguoiDangDangNhap());
                                    nguoiDung.setSoTien(nguoiDung.getSoTien() + tienLai);
                                    db.CapNhatTienNguoiDung(nguoiDung);
                                } else {
                                    soTietKiemMoi.setTrangThai("DTT");
                                    NguoiDung nguoiDung = db.LayDuLieuNguoiDung(db.NguoiDangDangNhap());
                                    nguoiDung.setSoTien(nguoiDung.getSoTien() + soTienGoc + tienLai);
                                    db.CapNhatTienNguoiDung(nguoiDung);
                                    db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    String ngayLayLai = soTietKiemMoi.getNgayTinhLaiKhongKyHan();
                    double laiKhongKyHan = soTietKiemMoi.getLaiSuatKhonKyHan();
                    int soTienGoc = soTietKiemMoi.getSoTienGui();
                    while (TinhToan.DungNgayChuaNgay(ngayLayLai, 1)) {
                        int tienLai = TinhToan.TinhLaiNgay(soTienGoc, laiKhongKyHan, 1);
                        ngayLayLai = TinhToan.CongNgay(ngayLayLai, 1);
                        soTietKiemMoi.setNgayTinhLaiKhongKyHan(ngayLayLai);
                        NguoiDung nguoiDung = db.LayDuLieuNguoiDung(db.NguoiDangDangNhap());
                        nguoiDung.setSoTien(nguoiDung.getSoTien() + tienLai);
                        db.CapNhatTienNguoiDung(nguoiDung);
                    }
                    db.SuaSoTietKiem(soTietKiemMoi, idCuaSo);
                }
            } while (soCuaTaiKhoan.moveToNext());
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lua_chon, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.optionSuaSo:
                Intent moGiaoDienSuaSo = new Intent(getApplicationContext(), SuaSoActivity.class);
                Bundle bundle = new Bundle();
                ViewModelSoTietKiem duocChon = (ViewModelSoTietKiem) listViewSoTietKiem.getAdapter().getItem(info.position);
                bundle.putString("maso", duocChon.getMaSo());
                moGiaoDienSuaSo.putExtras(bundle);
                startActivity(moGiaoDienSuaSo);
                return true;
            case R.id.optionGuiThem:
                Intent moGiaoDienGuiTien = new Intent(getApplicationContext(), GuiTienActivity.class);
                bundle = new Bundle();
                duocChon = (ViewModelSoTietKiem) listViewSoTietKiem.getAdapter().getItem(info.position);
                bundle.putString("maso", duocChon.getMaSo());
                moGiaoDienGuiTien.putExtras(bundle);
                if (duocChon.getKyHan().equals("Không kỳ hạn")) {
                    startActivity(moGiaoDienGuiTien);
                } else {
                    SoTietKiem canCheck = db.LaySoThongQuaMaSo(duocChon.getMaSo(),db.NguoiDangDangNhap());
                    String ngayTinhLai = canCheck.getNgayTinhLaiKhongKyHan();
                    String loaiKyHan = duocChon.getKyHan();
                    if (TinhToan.ToiNgayGuiThemChua(ngayTinhLai, loaiKyHan)) {
                        startActivity(moGiaoDienGuiTien);
                    } else {
                        HienThiThongBao("Chưa đến ngày có thể gửi thêm");
                    }
                }
                return true;
            case R.id.optionRut:
                Intent moGiaoDienRutTien = new Intent(getApplicationContext(), RutTienActivity.class);
                bundle = new Bundle();
                duocChon = (ViewModelSoTietKiem) listViewSoTietKiem.getAdapter().getItem(info.position);
                bundle.putString("maso", duocChon.getMaSo());
                moGiaoDienRutTien.putExtras(bundle);
                startActivity(moGiaoDienRutTien);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void HienThiThongBao(String thongBao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SoTietKiemActivity.this);
        builder.setTitle("Thông báo!");
        builder.setMessage(thongBao);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
