package com.sinhvien.moneylover;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThemNganHangActivity extends AppCompatActivity {
    Button buttonQuayLai, buttonThemNganHang;
    EditText editTextMaNganHang, editTextTenNganHang;
    MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ngan_hang);
        AnhXa();
        buttonThemNganHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maNganHang = editTextMaNganHang.getText().toString();
                String tenNganHang = editTextTenNganHang.getText().toString();
                if (maNganHang.isEmpty() || tenNganHang.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (db.NganHangTonTai(maNganHang, tenNganHang)) {
                        Toast.makeText(getApplicationContext(), "Ngân hàng đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        NganHang nganHang = new NganHang(maNganHang, tenNganHang);
                        db.ThemNganHang(nganHang);
                        finish();
                        Toast.makeText(getApplicationContext(), "Thêm ngân hàng thành công", Toast.LENGTH_SHORT).show();
                        SoTietKiemActivity.CapNhatGiaoDien(getApplicationContext());
                    }
                }
            }
        });
        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        buttonThemNganHang = findViewById(R.id.buttonThemNganHang);
        editTextMaNganHang = findViewById(R.id.editTextMaNganHang);
        editTextTenNganHang = findViewById(R.id.editTextTenNganHang);
        db = new MyDB(getApplicationContext());
    }
}
