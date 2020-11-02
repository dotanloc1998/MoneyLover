package com.sinhvien.moneylover;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RutTienActivity extends AppCompatActivity {
    Button buttonQuayLai, buttonXacNhanRut;
    TextView textViewMaSo, textViewNganHang, textViewNgayGui, textViewSoTienGui, textViewKyHan, textViewLaiSuat, textViewLaiSuatKhongKyHan, textViewTraLai, textViewKhiDenHan;
    EditText editTextSoTienRut;
    MyDB db;
    int idCuaSo;
    SoTietKiem soTietKiemCanRutTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rut_tien);
        AnhXa();
        buttonQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        textViewMaSo.setText("Mã sổ: " + soTietKiemCanRutTien.getMaSo());
        NganHang nganHang = db.LayNganHang(soTietKiemCanRutTien.getMaNganHang());
        textViewNganHang.setText("Ngân hàng: " + nganHang.getTenNganHang());
        textViewNgayGui.setText("Ngày mở sổ: " +soTietKiemCanRutTien.getNgayGui());
        textViewSoTienGui.setText("Số tiền gửi: " + soTietKiemCanRutTien.getSoTienGui());
        textViewKyHan.setText("Kỳ hạn: " + soTietKiemCanRutTien.getKyHan());
        textViewLaiSuat.setText("Lãi suất: " + soTietKiemCanRutTien.getLaiSuat() + "%");
        textViewLaiSuatKhongKyHan.setText("Lãi suất không kỳ hạn: " + soTietKiemCanRutTien.getLaiSuatKhonKyHan() + "%");
        textViewTraLai.setText("Trả lãi: " + soTietKiemCanRutTien.getTraLai());
        textViewKhiDenHan.setText("Khi đến hạn: " + soTietKiemCanRutTien.getKhiDenHan());

        buttonXacNhanRut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int soTienRut = Integer.parseInt(editTextSoTienRut.getText().toString());
                    if (soTienRut > soTietKiemCanRutTien.getSoTienGui()) {
                        Toast.makeText(getApplicationContext(), "Vui lòng rút ít hơn hoặc bằng số tiền gửi", Toast.LENGTH_SHORT).show();
                    } else {
                        String traLai = soTietKiemCanRutTien.getTraLai();
                        String kyHan = soTietKiemCanRutTien.getKyHan();
                        String thongBao = "";
                        if (kyHan.equals("Không kỳ hạn")) {
                            int soNgayGui = TinhToan.GuiDuocBaoNhieuNgay(soTietKiemCanRutTien.getNgayGui());
                            thongBao = "Sổ tiết kiệm: " + soTietKiemCanRutTien.getMaSo() + " thuộc loại Không kỳ hạn nên phải gửi trên 15 ngày mới được rút";
                            if (soNgayGui <= 15) {
                                Toast.makeText(getApplicationContext(), thongBao, Toast.LENGTH_LONG).show();
                            } else {
                                thongBao = "Bạn thực sự muốn rút tiền ?";
                                HienThiThongBao(soTienRut, thongBao);
                            }
                        } else {
                            String ngayRutTien = TinhToan.CongKyHan(soTietKiemCanRutTien.getNgayGui(), soTietKiemCanRutTien.getKyHan());
                            if (traLai.equals("Đầu kỳ")) {
                                thongBao = "Sổ tiết kiệm: " + soTietKiemCanRutTien.getMaSo() + " đến hạn ngày " + ngayRutTien + ". Số tiền rút ra trước hạn sẽ được tính lãi theo lãi suất không kỳ hạn (" + soTietKiemCanRutTien.getLaiSuatKhonKyHan() + "" + "%/năm) " + "dựa vào số ngày đã gửi và bắt buộc phải rút hết. Tiền lãi ứng trước sẽ mất hoàn toàn." + "\nBạn có muốn tiếp tục";
                                soTienRut = soTietKiemCanRutTien.getSoTienGui();
                                HienThiThongBao(soTienRut, thongBao);
                            } else if (traLai.equals("Định kỳ hàng tháng")) {
                                thongBao = "Sổ tiết kiệm: " + soTietKiemCanRutTien.getMaSo() + " đến hạn ngày " + ngayRutTien + ". Số tiền rút ra trước hạn sẽ được tính lãi theo lãi suất không kỳ hạn (" + soTietKiemCanRutTien.getLaiSuatKhonKyHan() + "" + "%/năm). Và tiền lãi sẽ trở về 0đ." + "\nBạn có muốn tiếp tục";
                                HienThiThongBao(soTienRut, thongBao);
                            } else {
                                thongBao = "Sổ tiết kiệm: " + soTietKiemCanRutTien.getMaSo() + " đến hạn ngày " + ngayRutTien + ". Số tiền rút ra trước hạn sẽ được tính lãi theo lãi suất không kỳ hạn (" + soTietKiemCanRutTien.getLaiSuatKhonKyHan() + "" + "%/năm)." + "\nBạn có muốn tiếp tục";
                                HienThiThongBao(soTienRut, thongBao);
                            }
                        }
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Số liệu nhập quá lớn",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void AnhXa() {
        buttonQuayLai = findViewById(R.id.buttonQuayLai);
        buttonXacNhanRut = findViewById(R.id.buttonXacNhanRut);
        textViewMaSo = findViewById(R.id.textViewMaSo);
        textViewNganHang = findViewById(R.id.textViewNganHang);
        textViewNgayGui = findViewById(R.id.textViewNgayGui);
        textViewSoTienGui = findViewById(R.id.textViewSoTienGui);
        textViewKyHan = findViewById(R.id.textViewKyHan);
        textViewLaiSuat = findViewById(R.id.textViewLaiSuat);
        textViewLaiSuatKhongKyHan = findViewById(R.id.textViewLaiSuatKhongKyHan);
        textViewTraLai = findViewById(R.id.textViewTraLai);
        textViewKhiDenHan = findViewById(R.id.textViewKhiDenHan);
        editTextSoTienRut = findViewById(R.id.editTextSoTienRut);
        db = new MyDB(getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        String maSo = bundle.getString("maso");
        idCuaSo = db.LayIdCuaSo(maSo,db.NguoiDangDangNhap());
        soTietKiemCanRutTien = db.LaySoThongQuaId(idCuaSo);
    }

    private void HienThiThongBao(final int soTienRut, String thongBao) {
        final TextView textViewThongBao;
        Button buttonKhong, buttonCo;
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);
        textViewThongBao = dialogView.findViewById(R.id.textViewThongBao);
        textViewThongBao.setText(thongBao);
        textViewThongBao.setMovementMethod(new ScrollingMovementMethod());
        buttonKhong = dialogView.findViewById(R.id.buttonKhong);
        buttonCo = dialogView.findViewById(R.id.buttonCo);
        buttonKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String traLai = soTietKiemCanRutTien.getTraLai();
                String kyHan = soTietKiemCanRutTien.getKyHan();
                if (kyHan.equals("Không kỳ hạn")) {
                    int tienTrongSo = soTietKiemCanRutTien.getSoTienGui();
                    if (soTienRut < tienTrongSo) {
                        XuLySoKhongKyHan(soTienRut, tienTrongSo);
                    } else {
                        soTietKiemCanRutTien.setTrangThai("DTT");
                        XuLySoKhongKyHan(soTienRut, tienTrongSo);
                    }
                } else {
                    int soNgayGui = TinhToan.GuiDuocBaoNhieuNgay(soTietKiemCanRutTien.getNgayGui());
                    if (TinhToan.GuiDuThangKhong(soNgayGui)) {
                        int soThangGui = soNgayGui % 30;
                        int tienTrongSo = soTietKiemCanRutTien.getSoTienGui();
                        if (soTienRut < tienTrongSo) {
                            if (traLai.equals("Đầu kỳ")) {
                                Toast.makeText(getApplicationContext(), "Phải rút hết tiền", Toast.LENGTH_LONG).show();
                            } else {
                                int tienConLai = tienTrongSo - soTienRut;
                                double laiSuat = soTietKiemCanRutTien.getLaiSuatKhonKyHan();
                                int tienNhanDuoc = TinhToan.TinhLaiThang2(soTienRut, laiSuat, soThangGui);
                                XuLyDinhKy(tienConLai, tienNhanDuoc);
                            }
                        } else {
                            soTietKiemCanRutTien.setTrangThai("DTT");
                            int tienConLai = tienTrongSo - soTienRut;
                            double laiSuat = soTietKiemCanRutTien.getLaiSuatKhonKyHan();
                            int tienNhanDuoc = TinhToan.TinhLaiThang2(soTienRut, laiSuat, soThangGui);
                            XuLyDinhKy(tienConLai, tienNhanDuoc);
                        }
                    } else {
                        int tienTrongSo = soTietKiemCanRutTien.getSoTienGui();
                        if (soTienRut < tienTrongSo) {
                            if (traLai.equals("Đầu kỳ")) {
                                Toast.makeText(getApplicationContext(), "Phải rút hết tiền", Toast.LENGTH_LONG).show();
                            } else {
                                int tienConLai = tienTrongSo - soTienRut;
                                double laiSuat = soTietKiemCanRutTien.getLaiSuatKhonKyHan();
                                int tienNhanDuoc = TinhToan.TinhLaiNgay(soTienRut,laiSuat,soNgayGui);
                                XuLyDinhKy(tienConLai, tienNhanDuoc);
                            }
                        } else {
                            soTietKiemCanRutTien.setTrangThai("DTT");
                            int tienConLai = tienTrongSo - soTienRut;
                            double laiSuat = soTietKiemCanRutTien.getLaiSuatKhonKyHan();
                            int tienNhanDuoc = TinhToan.TinhLaiNgay(soTienRut,laiSuat,soNgayGui);
                            XuLyDinhKy(tienConLai, tienNhanDuoc);
                        }
                    }
                }
            }
        });
        dialogBuilder.setView(dialogView);
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void XuLySoKhongKyHan(int soTienRut, int tienTrongSo) {
        int tienConLai = tienTrongSo - soTienRut;
        soTietKiemCanRutTien.setSoTienGui(tienConLai);
        NguoiDung nguoiDung = db.LayDuLieuNguoiDung(db.NguoiDangDangNhap());
        nguoiDung.setSoTien(nguoiDung.getSoTien() + soTienRut);
        db.SuaSoTietKiem(soTietKiemCanRutTien, idCuaSo);
        db.CapNhatTienNguoiDung(nguoiDung);
    }

    private void XuLyDinhKy(int tienConLai, int tienNhanDuoc) {
        soTietKiemCanRutTien.setSoTienGui(tienConLai);
        soTietKiemCanRutTien.setTienLai(0);
        NguoiDung nguoiDung = db.LayDuLieuNguoiDung(db.NguoiDangDangNhap());
        nguoiDung.setSoTien(nguoiDung.getSoTien() + tienNhanDuoc);
        db.SuaSoTietKiem(soTietKiemCanRutTien, idCuaSo);
        db.CapNhatTienNguoiDung(nguoiDung);
        SoTietKiemActivity.CapNhatSoTietKiem(getApplicationContext());
        SoTietKiemActivity.CapNhatGiaoDien(getApplicationContext());
        finish();
    }
}
