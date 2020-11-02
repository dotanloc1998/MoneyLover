package com.sinhvien.moneylover;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DangKyActivity extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button nutDangKy, nutDangNhap;
    MyDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        AnhXa();
        nutDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextEmail.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    if (checkEmail(editTextEmail.getText().toString())) {
                        if (checkDoDai(editTextPassword.getText().toString())) {
                            if (coKyTuDacBietKhong(editTextPassword.getText().toString())) {
                                if (coChuBinhThuongKhong(editTextPassword.getText().toString())) {
                                    if (coSoKhong(editTextPassword.getText().toString())) {
                                        NguoiDung thongTin = myDB.LayDuLieuNguoiDung(editTextEmail.getText().toString());
                                        if (thongTin.getEmail() == null) {
                                            NguoiDung nguoiDungMoi = new NguoiDung(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                                            myDB.ThemTaiKhoan(nguoiDungMoi);
                                            myDB.ThemDangNhap(editTextEmail.getText().toString());
                                            finishAffinity();
                                            finish();
                                            Intent intent = new Intent(getApplicationContext(), SoTietKiemActivity.class);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        HienThiThongBao("Phải có ít nhất 1 số");
                                    }
                                } else {
                                    HienThiThongBao("Phải có 1 kí tự chữ");
                                }
                            } else {
                                HienThiThongBao("Phải có ít nhất 1 kí tự đặc biệt");
                            }
                        } else {
                            HienThiThongBao("Độ dài mật khẩu phải từ 8 kí tự");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        nutDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
                startActivity(intent);
                finish();
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

    private boolean checkEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean checkDoDai(String matKhau) {
        return String.valueOf(matKhau).length() >= 8;
    }

    private boolean coKyTuDacBietKhong(String matKhau) {
        Pattern pattern;
        Matcher matcher;
        String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(matKhau);
        return matcher.matches();
    }

    private boolean coChuBinhThuongKhong(String matKhau) {
        Pattern pattern;
        Matcher matcher;
        String PASSWORD_PATTERN = "^(?=.*[a-zA-Z]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(matKhau);
        return matcher.matches();
    }

    private boolean coSoKhong(String matKhau) {
        Pattern pattern;
        Matcher matcher;
        String PASSWORD_PATTERN = "^(?=.*[0-9]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(matKhau);
        return matcher.matches();
    }

    private void HienThiThongBao(String thongBao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DangKyActivity.this);
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
