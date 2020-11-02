package com.sinhvien.moneylover;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ChiTietSoTietKiemActivity extends AppCompatActivity {
    TextView textViewMaSo, textViewNganHang, textViewNgayGui, textViewSoTienGui, textViewKyHan, textViewLaiSuat, textViewLaiSuatKhongKyHan, textViewTraLai, textViewKhiDenHan;
    MyDB db;
    int idCuaSo;
    SoTietKiem soTietKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_so_tiet_kiem);
        AnhXa();
        textViewMaSo.setText("Mã sổ: " + soTietKiem.getMaSo());
        NganHang nganHang = db.LayNganHang(soTietKiem.getMaNganHang());
        textViewNganHang.setText("Ngân hàng: " + nganHang.getTenNganHang());
        textViewNgayGui.setText("Ngày mở sổ: " + soTietKiem.getNgayGui());
        textViewSoTienGui.setText("Số tiền gửi: " + soTietKiem.getSoTienGui());
        textViewKyHan.setText("Kỳ hạn: " + soTietKiem.getKyHan());
        textViewLaiSuat.setText("Lãi suất: " + soTietKiem.getLaiSuat() + "%");
        textViewLaiSuatKhongKyHan.setText("Lãi suất không kỳ hạn: " + soTietKiem.getLaiSuatKhonKyHan() + "%");
        textViewTraLai.setText("Trả lãi: " + soTietKiem.getTraLai());
        textViewKhiDenHan.setText("Khi đến hạn: " + soTietKiem.getKhiDenHan());
    }

    private void AnhXa() {
        textViewMaSo = findViewById(R.id.textViewMaSo);
        textViewNganHang = findViewById(R.id.textViewNganHang);
        textViewNgayGui = findViewById(R.id.textViewNgayGui);
        textViewSoTienGui = findViewById(R.id.textViewSoTienGui);
        textViewKyHan = findViewById(R.id.textViewKyHan);
        textViewLaiSuat = findViewById(R.id.textViewLaiSuat);
        textViewLaiSuatKhongKyHan = findViewById(R.id.textViewLaiSuatKhongKyHan);
        textViewTraLai = findViewById(R.id.textViewTraLai);
        textViewKhiDenHan = findViewById(R.id.textViewKhiDenHan);
        db = new MyDB(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        String maSo = bundle.getString("maso");
        idCuaSo = db.LayIdCuaSo(maSo, db.NguoiDangDangNhap());
        soTietKiem = db.LaySoThongQuaId(idCuaSo);
    }
}
