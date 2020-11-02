package com.sinhvien.moneylover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        switch (position) {
            case 0:
                viewHolder.hinhAnhQuangCao.setImageResource(R.drawable.cloud);
                viewHolder.textQuangCao.setText("Lên kế hoạch tài chính thông minh và từng bước tiết kiệm để hiện thực hóa ước mơ");
                break;
            case 1:
                viewHolder.hinhAnhQuangCao.setImageResource(R.drawable.angrycat);
                viewHolder.textQuangCao.setText("Mèo giận");
                break;
            case 2:
                viewHolder.hinhAnhQuangCao.setImageResource(R.drawable.meowdelfeature);
                viewHolder.textQuangCao.setText("Mèo vui");
                break;
            case 3:
                viewHolder.hinhAnhQuangCao.setImageResource(R.drawable.doublecat);
                viewHolder.textQuangCao.setText("Mèo cha với mèo con");
                break;
            default:
                break;
        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 4;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView hinhAnhQuangCao;
        TextView textQuangCao;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            hinhAnhQuangCao = itemView.findViewById(R.id.hinhAnhQuangCao);
            textQuangCao = itemView.findViewById(R.id.textQuangCao);
            this.itemView = itemView;
        }
    }
}
