package com.sinhvien.moneylover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class DangNhapActivity extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button nutDangKy, nutDangNhap;
    MyDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        AnhXa();
        nutDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        nutDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NguoiDung nguoiDung = myDB.LayDuLieuNguoiDung(editTextEmail.getText().toString());
                if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (nguoiDung.getEmail() == null) {
                        Toast.makeText(getApplicationContext(), "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        myDB.ThemDangNhap(editTextEmail.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), SoTietKiemActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        finish();
                    }
                }
            }
        });
    }

    private void AnhXa() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        nutDangKy = findViewById(R.id.nutDangKy);
        nutDangNhap = findViewById(R.id.nutDangNhap);
        myDB = new MyDB(getApplicationContext());
    }
}
