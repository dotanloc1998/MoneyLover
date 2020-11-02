package com.sinhvien.moneylover;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GuiTienActivity extends AppCompatActivity {
    Button buttonQuayLai, buttonXacNhanGui;
    TextView textViewMaSo, textViewNganHang, textViewNgayGui, textViewSoTienGui, textViewKyHan, textViewLaiSuat, textViewLaiSuatKhongKyHan, textViewTraLai, textViewKhiDenHan;
    EditText editTextSoTienGui;
    MyDB db;
    int idCuaSo;
    SoTietKiem soTietKiemCanGuiTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_tien);
        AnhXa();
        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textViewMaSo.setText("Mã sổ: " + soTietKiemCanGuiTien.getMaSo());
        NganHang nganHang = db.LayNganHang(soTietKiemCanGuiTien.getMaNganHang());
        textViewNganHang.setText("Ngân hàng: " + nganHang.getTenNganHang());
        textViewNgayGui.setText("Ngày mở sổ: " + soTietKiemCanGuiTien.getNgayGui());
        textViewSoTienGui.setText("Số tiền gửi: " + soTietKiemCanGuiTien.getSoTienGui());
        textViewKyHan.setText("Kỳ hạn: " + soTietKiemCanGuiTien.getKyHan());
        textViewLaiSuat.setText("Lãi suất: " + soTietKiemCanGuiTien.getLaiSuat() + "%");
        textViewLaiSuatKhongKyHan.setText("Lãi suất không kỳ hạn: " + soTietKiemCanGuiTien.getLaiSuatKhonKyHan() + "%");
        textViewTraLai.setText("Trả lãi: " + soTietKiemCanGuiTien.getTraLai());
        textViewKhiDenHan.setText("Khi đến hạn: " + soTietKiemCanGuiTien.getKhiDenHan());

        buttonXacNhanGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int soTienGui = Integer.parseInt(editTextSoTienGui.getText().toString());
                    if (soTienGui < 100000) {
                        Toast.makeText(getApplicationContext(), "Vui lòng gửi từ 100,000đ trở lên", Toast.LENGTH_SHORT).show();
                    } else {
                        if (soTietKiemCanGuiTien.getTraLai().equals("Đầu kỳ")) {
                            int tienMoi = soTietKiemCanGuiTien.getSoTienGui() + soTienGui;
                            int laiMoi = TinhToan.TinhLaiThang(tienMoi, soTietKiemCanGuiTien.getLaiSuat(), soTietKiemCanGuiTien.getKyHan());
                            soTietKiemCanGuiTien.setSoTienGui(tienMoi);
                            soTietKiemCanGuiTien.setTienLai(laiMoi);
                            db.SuaSoTietKiem(soTietKiemCanGuiTien, idCuaSo);
                        } else {
                            int tienMoi = soTietKiemCanGuiTien.getSoTienGui() + soTienGui;
                            soTietKiemCanGuiTien.setSoTienGui(tienMoi);
                            db.SuaSoTietKiem(soTietKiemCanGuiTien, idCuaSo);
                        }
                        Toast.makeText(getApplicationContext(), "Gửi tiền thành công", Toast.LENGTH_SHORT).show();
                        SoTietKiemActivity.CapNhatGiaoDien(getApplicationContext());
                        finish();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Số liệu nhập quá lớn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void AnhXa() {
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        buttonXacNhanGui = findViewById(R.id.buttonXacNhanGui);
        textViewMaSo = findViewById(R.id.textViewMaSo);
        textViewNganHang = findViewById(R.id.textViewNganHang);
        textViewNgayGui = findViewById(R.id.textViewNgayGui);
        textViewSoTienGui = findViewById(R.id.textViewSoTienGui);
        textViewKyHan = findViewById(R.id.textViewKyHan);
        textViewLaiSuat = findViewById(R.id.textViewLaiSuat);
        textViewLaiSuatKhongKyHan = findViewById(R.id.textViewLaiSuatKhongKyHan);
        textViewTraLai = findViewById(R.id.textViewTraLai);
        textViewKhiDenHan = findViewById(R.id.textViewKhiDenHan);
        editTextSoTienGui = findViewById(R.id.editTextSoTienGui);
        db = new MyDB(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        String maSo = bundle.getString("maso");
        idCuaSo = db.LayIdCuaSo(maSo, db.NguoiDangDangNhap());
        soTietKiemCanGuiTien = db.LaySoThongQuaId(idCuaSo);
    }
}
