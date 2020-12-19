package com.poly.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.poly.duan1.R;
import com.poly.duan1.model.LoaiThietBi;

import java.util.List;

public class LoaiTBAdapter extends BaseAdapter {
    private List<LoaiThietBi> dsLoaiTB;
    private Context context;
    private LayoutInflater layoutInflater;

    public LoaiTBAdapter(Context context, List<LoaiThietBi> dsLoaiTB) {
        this.context = context;
        this.dsLoaiTB = dsLoaiTB;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return dsLoaiTB.size();
    }

    @Override
    public Object getItem(int position) {
        return dsLoaiTB.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder{
        TextView txtMaLoaiTB;
        TextView txtLoaiTB;
        TextView txtViTri;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.row_loai_tb, null);
            holder.txtMaLoaiTB = convertView.findViewById(R.id.tvAddMaLoaiTB);
            holder.txtLoaiTB = convertView.findViewById(R.id.tvAddLoaiTB);
            holder.txtViTri = convertView.findViewById(R.id.tvAddViTri);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LoaiThietBi entry = dsLoaiTB.get(position);
        holder.txtMaLoaiTB.setText("Mã loại thiết bị: " + entry.getMaLoaiThietBi());
        holder.txtLoaiTB.setText("Loại thiết bị: " + entry.getLoaiThietBi());
        holder.txtViTri.setText("Vị trí: " + entry.getViTri());
        return convertView;
    }
}
