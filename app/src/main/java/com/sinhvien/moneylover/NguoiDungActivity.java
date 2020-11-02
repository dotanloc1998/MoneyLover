package com.sinhvien.moneylover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class NguoiDungActivity extends AppCompatActivity {
    Button buttonDangXuat;
    MyDB myDB;
TextView textViewTienKiemDuoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        myDB = new MyDB(getApplicationContext());
        NguoiDung nguoiDung = myDB.LayDuLieuNguoiDung(myDB.NguoiDangDangNhap());
        textViewTienKiemDuoc = findViewById(R.id.textViewTienKiemDuoc);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String giaDaFormat = decimalFormat.format(nguoiDung.getSoTien());
        textViewTienKiemDuoc.setText("Tiền kiếm được: "+ giaDaFormat);
        buttonDangXuat = findViewById(R.id.buttonDangXuat);
        buttonDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.DangXuat(myDB.NguoiDangDangNhap());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finishAffinity();
                finish();
                startActivity(intent);
            }
        });
    }
}
