package com.poly.duan1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.poly.duan1.R;
import com.poly.duan1.model.ThietBi;

import java.util.List;

public class ThietBiAdapter extends BaseAdapter {
    private List<ThietBi> arrThietBi;
    private Context context;
    private LayoutInflater inflater;

    public ThietBiAdapter(Context context, List<ThietBi> arrThietBi) {
        this.arrThietBi = arrThietBi;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return arrThietBi.size();
    }

    @Override
    public Object getItem(int position) {
        return arrThietBi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder{
        TextView txtMaThietBi;
        TextView txtTenThietBi;
        TextView txtGiaBan;
        TextView txtLoaiThietBi;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_thiet_bi, null);
            holder.txtMaThietBi = convertView.findViewById(R.id.tvAddMaThietBi);
            holder.txtTenThietBi = convertView.findViewById(R.id.tvAddTenTB);
            holder.txtGiaBan = convertView.findViewById(R.id.tvAddGiaBan);
            holder.txtLoaiThietBi = convertView.findViewById(R.id.tvAddNameLoaiTB);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ThietBi entry = arrThietBi.get(position);
        holder.txtMaThietBi.setText("Mã thiết bị: " + entry.getMaThietBi());
        holder.txtTenThietBi.setText("Tên thiết bị: " + entry.getTenThietBi());
        holder.txtGiaBan.setText("Giá bán: " + entry.getGiaBan() + " (triệu đồng)");
        holder.txtLoaiThietBi.setText("Số lượng: " + entry.getSoLuong());
        return convertView;
    }
}
