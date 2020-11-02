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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SuaSoActivity extends AppCompatActivity {
    Button buttonQuayLai, buttonSuaSo;
    EditText editTextMaSo, editTextLaiSuat, editTextLaiKhongKyHan, editTextSoTienGui;
    TextView textViewNgayGui;
    Spinner spinnerTenNganHang, spinnerKyHan, spinnerTraLai, spinnerKhiDenHan;
    MyDB myDB;
    SoTietKiem soTietKiemCanSua;
    int idCuaSoCanSua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_so);
        AnhXa();
        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (soTietKiemCanSua != null) {
            TruyenDuLieuChoSpinner(soTietKiemCanSua);
        }

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
                new DatePickerDialog(SuaSoActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonSuaSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextMaSo.getText().toString().isEmpty() || editTextSoTienGui.getText().toString().isEmpty() || editTextLaiSuat.getText().toString().isEmpty() || editTextLaiKhongKyHan.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (tryParseDouble(editTextLaiSuat.getText().toString()) && tryParseDouble(editTextLaiKhongKyHan.getText().toString())) {
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
                                        SuaSo(tienLai, ngayTinhLai);
                                    } else if (spinnerKyHan.getSelectedItem().toString().equals("Không kỳ hạn") && spinnerTraLai.getSelectedItem().toString().equals("Đầu kỳ")) {
                                        Toast.makeText(getApplicationContext(), "Trả lãi Đầu kỳ không áp dụng cho Không kỳ hạn", Toast.LENGTH_SHORT).show();
                                    } else if (!spinnerKyHan.getSelectedItem().toString().equals("Không kỳ hạn") && spinnerTraLai.getSelectedItem().toString().equals("Định kỳ hàng tháng")) {
                                        SuaSo(0, textViewNgayGui.getText().toString());
                                    } else if (spinnerKyHan.getSelectedItem().toString().equals("Không kỳ hạn")) {
                                        SuaSo(0, textViewNgayGui.getText().toString());
                                    } else {
                                        String[] thang = spinnerKyHan.getSelectedItem().toString().split(" ");
                                        int soThang = Integer.parseInt(thang[0]);
                                        Calendar ngayChon = Calendar.getInstance();
                                        ngayChon.setTime(ngayDuocChon);
                                        ngayChon.add(Calendar.MONTH, soThang);
                                        String ngayTinhLai = new SimpleDateFormat("dd/MM/yyyy").format(ngayChon.getTime());
                                        SuaSo(0, ngayTinhLai);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Ngày được chọn phải <= ngày hôm nay", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                Toast.makeText(getApplicationContext(), "Có lỗi đã xảy ra", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Bạn đã nhập sai định dạng của Lãi suất hoặc Lãi suất không kỳ hạn", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void SuaSo(int tienLai, String ngayTinhLai) {
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
        myDB.SuaSoTietKiem(soTietKiemMoi, idCuaSoCanSua);
        SoTietKiemActivity.CapNhatSoTietKiem(getApplicationContext());
        SoTietKiemActivity.CapNhatGiaoDien(getApplicationContext());
        finish();
    }

    private void AnhXa() {
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        buttonSuaSo = findViewById(R.id.buttonSuaSo);
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
        Bundle thongTin = getIntent().getExtras();
        String maSo = thongTin.getString("maso");
        soTietKiemCanSua = myDB.LaySoThongQuaMaSo(maSo, myDB.NguoiDangDangNhap());
        idCuaSoCanSua = myDB.LayIdCuaSo(maSo, myDB.NguoiDangDangNhap());
    }

    private void CapNhatNgay(Calendar calendar) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat);
        textViewNgayGui.setText(dateFormat.format(calendar.getTime()));
    }

    private void TruyenDuLieuChoSpinner(SoTietKiem soTietKiemCanSua) {
        editTextMaSo.setText(soTietKiemCanSua.getMaSo());
        editTextLaiSuat.setText(soTietKiemCanSua.getLaiSuat() + "");
        editTextLaiKhongKyHan.setText(soTietKiemCanSua.getLaiSuatKhonKyHan() + "");
        editTextSoTienGui.setText(soTietKiemCanSua.getSoTienGui() + "");
        int viTriSpinner = 0;

        Cursor nganHang = myDB.LayTatCaNganHang();
        List<String> danhSachSpinner = new ArrayList<>();
        do {
            danhSachSpinner.add(nganHang.getString(nganHang.getColumnIndex("tennganhang")));
        } while (nganHang.moveToNext());

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, danhSachSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTenNganHang.setAdapter(adapter);
        NganHang nganHangCuaSo = myDB.LayNganHang(soTietKiemCanSua.getMaNganHang());
        viTriSpinner = adapter.getPosition(nganHangCuaSo.getTenNganHang());
        spinnerTenNganHang.setSelection(viTriSpinner);

        danhSachSpinner = new ArrayList<>();
        danhSachSpinner.add("Không kỳ hạn");
        danhSachSpinner.add("1 tháng");
        danhSachSpinner.add("3 tháng");
        danhSachSpinner.add("6 tháng");
        danhSachSpinner.add("12 tháng");
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, danhSachSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKyHan.setAdapter(adapter);
        viTriSpinner = adapter.getPosition(soTietKiemCanSua.getKyHan());
        spinnerKyHan.setSelection(viTriSpinner);

        danhSachSpinner = new ArrayList<>();
        danhSachSpinner.add("Cuối kỳ");
        danhSachSpinner.add("Đầu kỳ");
        danhSachSpinner.add("Định kỳ hàng tháng");
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, danhSachSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTraLai.setAdapter(adapter);
        viTriSpinner = adapter.getPosition(soTietKiemCanSua.getTraLai());
        spinnerTraLai.setSelection(viTriSpinner);

        danhSachSpinner = new ArrayList<>();
        danhSachSpinner.add("Tái tục gốc và lãi");
        danhSachSpinner.add("Tái tục gốc");
        danhSachSpinner.add("Tất toán sổ");
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, danhSachSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKhiDenHan.setAdapter(adapter);
        viTriSpinner = adapter.getPosition(soTietKiemCanSua.getKhiDenHan());
        spinnerKhiDenHan.setSelection(viTriSpinner);
    }
}
