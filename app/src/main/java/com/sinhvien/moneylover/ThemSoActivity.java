package com.sinhvien.moneylover;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThemSoActivity extends AppCompatActivity {
    Button buttonQuayLai, buttonMoSo;
    TextView textViewNgayGui;
    EditText editTextMaSo, editTextLaiSuat, editTextLaiKhongKyHan, editTextSoTienGui;
    Spinner spinnerTenNganHang, spinnerKyHan, spinnerTraLai, spinnerKhiDenHan;
    MyDB myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_so);
        AnhXa();
        TruyenDuLieuChoSpinner();
        editTextLaiKhongKyHan.setText("0.05");
        editTextSoTienGui.setText("1000000");
        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Calendar calendar = Calendar.getInstance();
        CapNhatNgay(calendar);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(calendar.YEAR, year);
                calendar.set(calendar.MONTH, monthOfYear);
                calendar.set(calendar.DAY_OF_MONTH, dayOfMonth);
                CapNhatNgay(calendar);
            }
        };

        textViewNgayGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ThemSoActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonMoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextMaSo.getText().toString().isEmpty() || editTextSoTienGui.getText().toString().isEmpty() || editTextLaiSuat.getText().toString().isEmpty() || editTextLaiKhongKyHan.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (tryParseDouble(editTextLaiSuat.getText().toString()) && tryParseDouble(editTextLaiKhongKyHan.getText().toString())) {
                        if (myDB.KiemTraSoTonTai(editTextMaSo.getText().toString(), myDB.NguoiDangDangNhap())) {
                            Toast.makeText(getApplicationContext(), "Mã sổ đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else {
                            if (Integer.parseInt(editTextSoTienGui.getText().toString()) < 1000000) {
                                Toast.makeText(getApplicationContext(), "Tối thiểu phải gửi 1,000,000 đ", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    Date ngayDuocChon = new SimpleDateFormat("dd/MM/yyyy").parse(textViewNgayGui.getText().toString());
                                    Date ngayHomNay = new Date();
                                    if (ngayDuocChon.equals(Calendar.getInstance()) || ngayDuocChon.before(ngayHomNay)) {
                                        if (!spinnerKyHan.getSelectedItem().toString().equals("Không kỳ hạn") && spinnerTraLai.getSelectedItem().toString().equals("Đầu kỳ")) {
                                            String thang = spinnerKyHan.getSelectedItem().toString();
                                            int tienGoc = Integer.parseInt(editTextSoTienGui.getText().toString());
                                            double laiSuat = Double.parseDouble(editTextLaiSuat.getText().toString());
                                            int tienLai = TinhToan.TinhLaiThang(tienGoc, laiSuat, thang);
                                            String ngayTinhLai = "";
                                            ThemSo(tienLai, ngayTinhLai);
                                        } else if (spinnerKyHan.getSelectedItem().toString().equals("Không kỳ hạn") && spinnerTraLai.getSelectedItem().toString().equals("Đầu kỳ")) {
                                            Toast.makeText(getApplicationContext(), "Trả lãi Đầu kỳ không áp dụng cho Không kỳ hạn", Toast.LENGTH_SHORT).show();
                                        } else if (!spinnerKyHan.getSelectedItem().toString().equals("Không kỳ hạn") && spinnerTraLai.getSelectedItem().toString().equals("Định kỳ hàng tháng")) {
                                            ThemSo(0, textViewNgayGui.getText().toString());
                                        } else if (spinnerKyHan.getSelectedItem().toString().equals("Không kỳ hạn")) {
                                            ThemSo(0, textViewNgayGui.getText().toString());
                                        } else {
                                            String[] thang = spinnerKyHan.getSelectedItem().toString().split(" ");
                                            int soThang = Integer.parseInt(thang[0]);
                                            Calendar ngayChon = Calendar.getInstance();
                                            ngayChon.setTime(ngayDuocChon);
                                            ngayChon.add(Calendar.MONTH, soThang);
                                            String ngayTinhLai = new SimpleDateFormat("dd/MM/yyyy").format(ngayChon.getTime());
                                            ThemSo(0, ngayTinhLai);
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Ngày được chọn phải <= ngày hôm nay", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (ParseException e) {
                                    Toast.makeText(getApplicationContext(), "Có lỗi đã xảy ra", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Bạn đã nhập sai định dạng của Lãi suất hoặc Lãi suất không kỳ hạn", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void CapNhatNgay(Calendar calendar) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat);
        textViewNgayGui.setText(dateFormat.format(calendar.getTime()));
    }

    private void AnhXa() {
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        buttonMoSo = findViewById(R.id.buttonMoSo);
        editTextMaSo = findViewById(R.id.editTextMaSo);
        editTextLaiSuat = findViewById(R.id.editTextLaiSuat);
        editTextLaiKhongKyHan = findViewById(R.id.editTextLaiKhongKyHan);
        editTextSoTienGui = findViewById(R.id.editTextSoTienGui);
        spinnerTenNganHang = findViewById(R.id.spinnerTenNganHang);
        spinnerKyHan = findViewById(R.id.spinnerKyHan);
        spinnerTraLai = findViewById(R.id.spinnerTraLai);
        spinnerKhiDenHan = findViewById(R.id.spinnerKhiDenHan);
        myDB = new MyDB(getApplicationContext());
        textViewNgayGui = findViewById(R.id.textViewNgayGui);
    }

    private void TruyenDuLieuChoSpinner() {
        Cursor nganHang = myDB.LayTatCaNganHang();
        List<String> danhSachSpinner = new ArrayList<>();
        if (nganHang.getCount() > 0) {
            do {
                danhSachSpinner.add(nganHang.getString(nganHang.getColumnIndex("tennganhang")));
            } while (nganHang.moveToNext());
        }
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, danhSachSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTenNganHang.setAdapter(adapter);

        danhSachSpinner = new ArrayList<>();
        danhSachSpinner.add("Không kỳ hạn");
        danhSachSpinner.add("1 tháng");
        danhSachSpinner.add("3 tháng");
        danhSachSpinner.add("6 tháng");
        danhSachSpinner.add("12 tháng");
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, danhSachSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKyHan.setAdapter(adapter);

        danhSachSpinner = new ArrayList<>();
        danhSachSpinner.add("Cuối kỳ");
        danhSachSpinner.add("Đầu kỳ");
        danhSachSpinner.add("Định kỳ hàng tháng");
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, danhSachSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTraLai.setAdapter(adapter);

        danhSachSpinner = new ArrayList<>();
        danhSachSpinner.add("Tái tục gốc và lãi");
        danhSachSpinner.add("Tái tục gốc");
        danhSachSpinner.add("Tất toán sổ");
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, danhSachSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKhiDenHan.setAdapter(adapter);
    }

    public static boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void ThemSo(int tienLai, String ngayTinhLai) {
        String maNganHang = myDB.LayMaNganHangBangTen(spinnerTenNganHang.getSelectedItem().toString());
        String maSo = editTextMaSo.getText().toString();
        String ngayMoSo = textViewNgayGui.getText().toString();
        int soTienGui = Integer.parseInt(editTextSoTienGui.getText().toString());
        double laiSuat = Double.parseDouble(editTextLaiSuat.getText().toString());
        double khongKyHan = Double.parseDouble(editTextLaiKhongKyHan.getText().toString());
        String kyHan = spinnerKyHan.getSelectedItem().toString();
        String traLai = spinnerTraLai.getSelectedItem().toString();
        String khiDenHan = spinnerKhiDenHan.getSelectedItem().toString();
        String nguoiGui = myDB.NguoiDangDangNhap();
        SoTietKiem soTietKiemMoi = new SoTietKiem(maSo, maNganHang, ngayMoSo, soTienGui, kyHan, laiSuat, khongKyHan, traLai, khiDenHan, tienLai, nguoiGui, ngayTinhLai);
        myDB.ThemSoTietKiem(soTietKiemMoi);
        SoTietKiemActivity.CapNhatGiaoDien(getApplicationContext());
        finish();
    }
}
