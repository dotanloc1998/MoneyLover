package com.sinhvien.moneylover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smarteist.autoimageslider.SliderView;

public class MainActivity extends AppCompatActivity {
    SliderView imageSliderLogo;
    Button buttonDangKy, buttonDangNhap;
    MyDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        String dangDangNhap = myDB.NguoiDangDangNhap();
        if (dangDangNhap != "") {
            Intent intent = new Intent(getApplicationContext(), SoTietKiemActivity.class);
            startActivity(intent);
            finish();
        }
        imageSliderLogo.setSliderAdapter(new SliderAdapter(getApplicationContext()));
        buttonDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });
        buttonDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        imageSliderLogo = findViewById(R.id.imageSliderLogo);
        buttonDangKy = findViewById(R.id.buttonDangKy);
        buttonDangNhap = findViewById(R.id.buttonDangNhap);
        myDB = new MyDB(getApplicationContext());
    }
}
