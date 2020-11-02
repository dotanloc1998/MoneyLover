package com.sinhvien.moneylover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SoTietKiemAdapter extends BaseAdapter {
    Context context;
    List<ViewModelSoTietKiem> soTietKiems;

    public SoTietKiemAdapter(Context context, List<ViewModelSoTietKiem> soTietKiems) {
        this.context = context;
        this.soTietKiems = soTietKiems;
    }

    @Override
    public int getCount() {
        return soTietKiems.size();
    }

    @Override
    public Object getItem(int i) {
        return soTietKiems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder {
        TextView textViewMaSo, textViewKyHanGui, textViewLaiSuatNam, textViewNgayMoSo, textViewTongTienGoc;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.dong_so_tiet_kiem, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewMaSo = view.findViewById(R.id.textViewMaSo);
            viewHolder.textViewKyHanGui = view.findViewById(R.id.textViewKyHanGui);
            viewHolder.textViewLaiSuatNam = view.findViewById(R.id.textViewLaiSuatNam);
            viewHolder.textViewNgayMoSo = view.findViewById(R.id.textViewNgayMoSo);
            viewHolder.textViewTongTienGoc = view.findViewById(R.id.textViewTongTienGoc);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ViewModelSoTietKiem soTietKiem = soTietKiems.get(i);
        viewHolder.textViewMaSo.setText("Mã sổ: " + soTietKiem.getMaSo());
        viewHolder.textViewKyHanGui.setText("Kỳ hạn gửi: " + soTietKiem.getKyHan());
        viewHolder.textViewLaiSuatNam.setText("Lãi suất năm: " + soTietKiem.getLaiSuat());
        viewHolder.textViewNgayMoSo.setText("Ngày mở sổ: " + soTietKiem.getNgayGui());
        viewHolder.textViewTongTienGoc.setText("Tổng số tiền gốc: " + soTietKiem.getSoTienGui());
        return view;
    }
}
